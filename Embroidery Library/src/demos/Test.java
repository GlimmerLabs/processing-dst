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
		size(500, 700);
	}

	public void setup() {
		noLoop();
		background(255);
		fill(0, 255, 0);
	}

	public void draw() {
		needle.go_to(250, 350);
		design.clear();


		triPetal(needle, design);
		//design.saveDST(design, "/users/Voozell/Desktop/temp.dst");
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
				n.setColor(design, 0, 0, 255);
				n.angle = 40;
			}
			for (int j = 0; j <= 6; j ++) {
				n.em_rect(100, 200);
				n.right(60);
			}
		}
		n.up();
	}
}