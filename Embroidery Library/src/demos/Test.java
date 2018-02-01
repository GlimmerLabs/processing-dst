package demos;

import processing.core.*;
import library.*;

// +---------------------+------------------------------------------
// | Example Code (temp) |
// +---------------------+

public class Test extends PApplet {
	Pattern design = new Pattern(this);
	Pen needle = new Pen(this, 0, 0, design);
	
	public static void main(String[] args) {
		PApplet.main("demos.Test");
	}

	public void settings() {
		size(1000, 1000);
	}

	public void setup() {
		noLoop();
		background(255);
		fill(0, 255, 0);
	}

	public void draw() {
		needle.go_to(150, 150);
		design.clear();

		triPetal(needle, design);
		needle.go_to(300, 400);
		triPetal(needle, design);
		needle.go_to(490, 195);
		needle.setColor(design, 255, 0, 128);
		triPetal(needle, design);
		needle.go_to(600, 500);
		needle.setColor(design, 0, 255, 0);
		triPetal(needle, design);
		design.saveDST(design, "temp.dst");
	}
	
	
	public void triPetal(Pen n, Pattern p) {
		n.down();
		for (int i = 0; i < 3; i++) {
			switch (i) {
			case 0 :
				n.angle = 0;
				break;
			case 1 : 
				n.setColor(design, 255, 0, 0);
				n.angle = 20;
				break;
			case 2 :
				n.setColor(design, 255, 255, 255);
				n.angle = 40;
			}
			for (int j = 0; j <= 6; j ++) {
				n.em_rect(100, 100);
				n.right(60);
			}
		}
		n.up();
	}
}