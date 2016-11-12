import processing.core.*;

// +---------------------+------------------------------------------
// | Example Code (temp) |
// +---------------------+

public class Test extends PApplet {
	Pattern design = new Pattern(this);
	Pen needle = new Pen(this, 0, 0, design);
	
	public static void main(String[] args) {
		PApplet.main("Test");
	}

	public void settings() {
		size(500, 700);
	}

	public void setup() {
		noLoop();
		background(255);
		fill(255);
	}

	public void draw() {
		needle.go_to(205, 307);
		design.clear();
		needle.setColor(design, 0, 255, 0);

		needle.down();
		for (int i = 0; i <= 360; i += 20) {
			needle.em_ellipse2(100, 100);
			needle.right(20);
		}
		needle.up();

		design.saveDST(design, "temp.dst");
	}
}