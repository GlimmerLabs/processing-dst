package demos;

import processing.core.*;
import library.*;

// +---------------------------+------------------------------------
// | Example Code (twelvetree) |
// +---------------------------+

/**
 * This is an example of how you can use Processing's infinite draw
 * loop and interactive commands can be fun.
 */
public class Twelvetree extends PApplet {
	
	Pattern design = new Pattern(this);
	Pen needle = new Pen(this, 0, 0, design);
	
	Pattern design2 = new Pattern(this);
	Pen needle2 = new Pen(this, 250, 0, design2);

	public static void main(String[] args) {
		PApplet.main("demos.twelvetree");
	}

	public void settings() {
		size(500, 250);
	}

	public void setup() {
		//noLoop();
		background(255);
		fill(0, 0, 255);
	
		this.frameRate((float) 30);
	}

	public void draw() {
		if (this.keyCode == LEFT) {
			home();
			for (int i = 0; i < 12; i++) {
				tree(60, 64);
				needle.right(30);
			}
			//design.saveDST(design, "twelvetreePS.dst");
		} else if (this.keyCode == RIGHT) {
			home2();
			for (int i = 0; i < 12; i++) {
				tree2(60, 64);
				needle2.right(30);
			}
			//design.saveDST(design2, "twelvetreePS2.dst");
		} else if (this.key == 'q') {
			System.exit(0);
		}
	}
	
	public void home() {
		needle.up();
		needle.go_to(120, 120);
		needle.angle = 90;
		design.clear();
		needle.down();
	}
	
	public void home2() {
		needle2.up();
		needle2.go_to(380, 120);
		needle2.angle = 90;
		design2.clear();
		needle2.down();
	}
	
	public void tree(int angle, int length) {
		if (length > 1) {
			needle.forward(length);
			needle.left(angle);
			tree(angle, length/2);
			needle.right(angle * 2);
			tree(angle, length/2);
			needle.left(angle);
			needle.forward(-1 * length);
		}
	}
	
	public void tree2(int angle, int length) {
		if (length > 1) {
			needle2.stitch_size = length;
			needle2.forward(length);
			needle2.left(angle);
			tree2(angle, length/2);
			needle2.stitch_size = length;
			needle2.right(angle * 2);
			tree2(angle, length/2);
			needle2.stitch_size = length;
			needle2.left(angle);
			needle2.forward(-1 * length);
		}
	}
}
