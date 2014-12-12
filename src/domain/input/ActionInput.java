package domain.input;

import java.util.HashMap;

import common.Player;
import domain.Game;
import domain.action.Action;
import domain.action.ActionFactory;
import domain.action.ActionRequest;
import domain.board.Board;
import domain.location.Location;
import domain.piece.Piece;

public class ActionInput implements IInput
{
	private final String move;
	private final Game game;
	
	private String getMove()
	{
		return move;
	}
	
	private Game getGame()
	{
		return game;
	}
	
	public ActionInput(String move, Game game)
	{
		this.move = move;
		this.game = game;
	}

	@Override
	public boolean process()
	{
		Game game = getGame();
		Board board = game.getBoard();
		Player currentPlayer = game.getCurrentPlayer();
		
		try
		{
			ActionRequest request = analyzeAction();
			Action action = ActionFactory.create(request, board, currentPlayer);
			if(action.isValidOn(board, currentPlayer))
			{
				action.executeOn(board, currentPlayer);
				checkForPromotions(currentPlayer);
				game.switchCurrentPlayer();
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(IllegalArgumentException ex)
		{
			game.getUI().showWarning(ex.getMessage());
			return false;
		}
	}
	
	private void checkForPromotions(Player player) //TODO find better place
	{
		Board board = getGame().getBoard();
		HashMap<Location, Piece> playerPieces = board.getPlayerPieces(player);
		for(Location location : playerPieces.keySet())
		{
			if(location.isPromotionRow(player))
			{
				Piece piece = playerPieces.get(location);
				if(piece.canPromote())
				{
					board.promotePiece(location);
				}
			}
		}
	}
	
	private ActionRequest analyzeAction()
	{
		//1-7: step
		//1-45: fly = multi-step
		//1x12: catch
		//1x12x23: multi-catch
		//1x23: fly-catch
		//1x23x34: multi-fly-catch
		String move = getMove();
		if(move.matches("\\d+\\s*-\\s*\\d+")) //step or fly
		{
			String parts[] = move.split("\\s*-\\s*");
			int fromIndex = Integer.parseInt(parts[0]);
			int toIndex = Integer.parseInt(parts[1]);
			return new ActionRequest(false, fromIndex, toIndex);
		}
		else if(move.matches("\\d+(\\s*x\\s*\\d+)+")) //(multi-)(fly-)catch
		{
			String[] parts = move.split("\\s*x\\s*");
			ActionRequest result = new ActionRequest(true);
			for(int i=0; i < parts.length; i++)
			{
				int index = Integer.parseInt(parts[i]);
				result.addIndex(index);
			}
			return result;
		}
		throw new IllegalArgumentException("Invalid pattern");
	}
}