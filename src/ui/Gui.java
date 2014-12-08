package ui;

import bvp.Bord;
import bvp.Figuren;
import bvp.Figuur;
import common.Configs;
import common.Location;
import common.Player;
import domain.board.Board;
import domain.board.BoardSize;
import domain.piece.Dame;
import domain.piece.Piece;
import domain.square.Square;

public class Gui {
	private final Bord guiFrame = new Bord("Checkers");
	private final Board board;
	
	private Bord getGuiFrame()
	{
		return guiFrame;
	}
	
	private Board getBoard() {
		return board;
	}
	
	public Gui(Board board) {
		this.board = board;
	}

	private Figuur getFigure(Figuren pieces, Piece piece) //50 x 50
	{
		if(piece instanceof Dame) //TODO find better way
		{
			if(piece.getPlayer() == Player.White)
			{
				return pieces.getFiguur("wittedamschijf");
			}
			else
			{
				return pieces.getFiguur("zwartedamschijf");
			}
		}
		else
		{
			if(piece.getPlayer() == Player.White)
			{
				return pieces.getFiguur("witteschijf");
			}
			else
			{
				return pieces.getFiguur("zwarteschijf");
			}
		}
	}
	
	public void paint()
	{
		Bord frame = getGuiFrame();
		BoardSize size = getBoard().getSize();
		Figuren numbers = new Figuren("data\\cijfers32.fig");
		Figuren pieces = new Figuren("data\\schijven.fig");
		Figuur background = new Figuur(size.getCols()*50, size.getRows()*50);
		Figuur whiteSquare = new Figuur(Configs.SquareSizePx, Configs.SquareSizePx);
		Figuur blackSquare = new Figuur(Configs.SquareSizePx, Configs.SquareSizePx);
		whiteSquare.vulRechthoek(0,0,Configs.SquareSizePx,Configs.SquareSizePx,Configs.LightColor);
		blackSquare.vulRechthoek(0,0,Configs.SquareSizePx,Configs.SquareSizePx,Configs.DarkColor);
		for (int row = 0; row < size.getRows(); row++)
		{
			for (int col = 0; col < size.getCols(); col++)
			{
				Location location = new Location(row, col, size);
				Square square = getBoard().getSquare(location);
				if (location.isWhite())
				{
					background.plaatsFiguur(whiteSquare, col*Configs.SquareSizePx, row*Configs.SquareSizePx);
				}
				else
				{
					background.plaatsFiguur(blackSquare, col*Configs.SquareSizePx, row*Configs.SquareSizePx);
					int hPixels = col*Configs.SquareSizePx;
					int vPixels = row*Configs.SquareSizePx;
					if(square.hasPiece())
					{
						Piece piece = square.getPiece();
						Figuur figure = getFigure(pieces, piece);
						background.plaatsFiguur(figure, hPixels, vPixels);
					}
					int index = location.getIndex();
    				String digits = Integer.toString(index);
    				for(int i=0; i<digits.length(); i++)
    				{
    					String digit = digits.substring(i, i+1);
    					Figuur figure = numbers.getFiguur(digit).scaleer(10,10);
						background.plaatsFiguur(figure, hPixels + Configs.SquareSizePx/3 + 10*i, vPixels + Configs.SquareSizePx/3);
    				}
				}
			}
		}
		frame.toon(background);
	}
}