package common;

import java.awt.Color;

import domain.board.BoardSize;
import domain.board.contracts.IBoardSize;

public class Configs //TODO move to xml file
{
	public final static IBoardSize Size = new BoardSize(10, 10);
	public final static boolean MandatoryMaximalCatching = true;
	public static boolean BackwardCatchingAllowed = true;
	public final static Player FirstPlayer = Player.White;
	public final static boolean FlyingDame = true;
	public final static int RemiseThreshold = 25;

	// GUI
	public final static Color DarkColor = Color.GRAY;
	public final static Color LightColor = Color.WHITE;
	public final static int SquareSizePx = 50;
	public final static String CijfersPath = "data\\cijfers32.fig";
	public final static String SchijvenPath = "data\\schijven.fig";
	public final static int PaintDelayMs = 100; 

	// Locale
	public final static String Language = "nl";
	public final static String Country = "BE";
}
