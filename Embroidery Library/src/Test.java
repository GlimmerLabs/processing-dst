import processing.core.*;

// +---------------------+------------------------------------------
// | Example Code (temp) |
// +---------------------+

public class Test extends PApplet {
	Pattern design = new Pattern();
	Pen needle = new Pen(0, 0, design);
	
	public static void main(String[] args) {
		PApplet.main("Test");
	}

	public void settings() {
		setUp.stageSize(5, 7);
	}

	public void setup() {
		setUp.em_setup();
	}

	public void draw() {
		needle.go_to(205, 307);
		design.clean();
		setUp.stitchColor(0, 255, 0);

		needle.setDown();
		for (int i = 0; i < 18; i++) {
			needle.em_ellipse2(100, 150);
			needle.right(20);
		}
		needle.up();

		design.saveDST(design, "temp.dst");
	}
}