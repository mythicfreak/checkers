package domain.updates;

import common.Player;
import domain.action.contracts.IAction;
import domain.board.contracts.IReadOnlyBoard;
import domain.location.Location;
import domain.updates.contracts.IBasicUpdateProcessor;
import domain.updates.contracts.IUpdateProcessor;

/**
 * This class directly propagates all {@link IUpdate}s from source to observer, 
 * thereby limiting the direct dependency of many classes outside this 
 * package on the often-changed IGameObserver interface.
 */
public abstract class UpdatePropagator extends UpdateSource implements IUpdateProcessor
{
	@Override
	public void linkBasic(IBasicUpdateProcessor propagator)
	{
		this.subscribeBasic(propagator);
		propagator.subscribeBasic(this);
	}
	
	@Override
	public void unlinkBasic(IBasicUpdateProcessor processor)
	{
		this.unsubscribeBasic(processor);
		processor.unsubscribeBasic(this);
	}
	
	@Override
	public void link(IUpdateProcessor propagator)
	{
		this.subscribe(propagator);
		propagator.subscribe(this);
	}
	
	@Override
	public void unlink(IUpdateProcessor processor)
	{
		this.unsubscribe(processor);
		processor.unsubscribe(this);
	}
	
	@Override
	public void updateBoard(IReadOnlyBoard board, Player performer)
	{
		emitUpdateBoard(board, performer);
	}

	@Override
	public void gameOver(Player winner)
	{
		emitGameOver(winner);
	}
	
	@Override
	public void promotion(IReadOnlyBoard board, Location location)
	{
		emitPromotion(board, location);
	}
	
	@Override
	public void outOfMoves(Player player)
	{
		emitOutOfMoves(player);
	}
	
	@Override
	public void acceptRemise()
	{
		emitAcceptRemise();
	}
	
	@Override
	public void declineRemise()
	{
		emitDeclineRemise();
	}
	
	@Override
	public void proposeRemise(Player proposer)
	{
		emitProposeRemise(proposer);
	}
	
	@Override
	public void resign(Player resignee)
	{
		emitResign(resignee);
	}
	
	@Override
	public void start(IReadOnlyBoard board, Player starter)
	{
		emitStart(board, starter);
	}
	
	@Override
	public void executeAction(IAction action)
	{
		emitExecuteAction(action);
	}
	
	@Override
	public void switchPlayer(IReadOnlyBoard board, Player switchedIn)
	{
		emitSwitchPlayer(board, switchedIn);
	}
	
	@Override
	public void forcedRemise()
	{
		emitForcedRemise();
	}
	
	@Override
	public void warning(String message)
	{
		emitWarning(message);
	}
	
	@Override
	public void error(String message, Exception ex)
	{
		emitError(message, ex);
	}
}