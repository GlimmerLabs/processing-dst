package library;
import processing.core.*;

public class Stitch {
	public int changex;
	public int changey;
	public char type;

	Stitch(int tempChangex, int tempChangey, char tempType) {
		changex = tempChangex;
		changey = -(tempChangey);
		type = tempType;
	}
}
