package domain.location;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import domain.board.BoardSize;
import domain.board.contracts.IBoardSize;

public class DiagonalLocationPairTest
{
	private final static IBoardSize size = new BoardSize(10, 10);
	private final static DiagonalLocationPair stepAboveRight = new DiagonalLocationPair(48, 43, size);
	private final static DiagonalLocationPair stepAboveLeft = new DiagonalLocationPair(38, 32, size);
	private final static DiagonalLocationPair flyFar = new DiagonalLocationPair(46, 5, size);
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorRestrictions()
	{
		new DiagonalLocationPair(25, 44, size);
	}
	
	@Test
	public void testDiagonalDistance()
	{
		assertEquals(1, stepAboveRight.getDiagonalDistance());
		assertEquals(9, flyFar.getDiagonalDistance());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetCenterBetweenTooClose()
	{
		stepAboveLeft.getCenterBetween();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetCenterBetweenTooFar()
	{
		flyFar.getCenterBetween();
	}
	
	@Test
	public void testGetCenterBetween()
	{
		DiagonalLocationPair catchPair = new DiagonalLocationPair(37, 28, size);
		assertEquals(new Location(32, size), catchPair.getCenterBetween());
	}
	
	@Test
	public void testIsBetween()
	{
		assertTrue(flyFar.isBetween(new Location(46, size)));
		assertTrue(flyFar.isBetween(new Location(32, size)));
		assertTrue(flyFar.isBetween(new Location(5, size)));
		
		assertFalse(flyFar.isBetweenStrict(new Location(46, size)));
		assertTrue(flyFar.isBetweenStrict(new Location(32, size)));
		assertFalse(flyFar.isBetweenStrict(new Location(5, size)));
	}
	
	@Test
	public void testGetPairsBetweenInclusive()
	{
		List<DiagonalLocationPair> pairs = flyFar.getPairsBetween();
		assertEquals(9, pairs.size());
		for(int i=0; i < pairs.size()-1; i++)
		{
			assertEquals(pairs.get(i).getTo(), pairs.get(i+1).getFrom());
		}
	}
	
	@Test
	public void testGetPairsBetweenExclusive()
	{
		List<DiagonalLocationPair> pairs = flyFar.getPairsBetweenStrict();
		assertEquals(7, pairs.size());
		for(int i=0; i < pairs.size()-1; i++)
		{
			assertEquals(pairs.get(i).getTo(), pairs.get(i+1).getFrom());
		}
	}
}
