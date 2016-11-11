import processing.core.*;

/*--------------------User Setup Functions-------------------- */

public class setUp extends PApplet {
	// length and width should be in inches (the size of the frame)
	public void stageSize (float width, float height) {
		// There are 50 pixels per centimeter and there are 2.54 cm/in.
		// We try to leave a margin of error so that you don't hit the frame.
		double factor = 2.55 * 40;
		println("Stage Size (in pixels): ", (int)(width*factor), (int)(height*factor));
		println("Center: ", (int)(width*factor)/2, (int)(height*factor)/2);
		size((int)(width*factor), (int)(height*factor));
	}

	public void em_setup() {
		noLoop();
		background(255); // white background
		fill(0); // black thread
	}

	public void clean(Pattern design) {
		background(255);
		design.clearPattern();
	}

	// Default is 9. Recommended size: 8-12
	public void stitchSize(Pen needle, float sizeof_stitch) {
		needle.stitch_size = sizeof_stitch;
	}

	// This has no effect on the embroidery machine. Default is white.
	public void em_background(int red, int green, int blue) {
		background(red, green, blue);
	}

	// Sets the initial color. Default is black.
	void stitchColor(int red, int green, int blue) {
		fill(red, green, blue);
		stroke(red, green, blue);
	}

//	// Stops the machine to allow a change of threads.
//	// On the display it will only show a color change.
//	void changeColor(Pattern design, int red, int green, int blue) {
//		fill(red, green, blue);
//		stroke(red, green, blue);
//		design.addStitch(0, 0, 'c');
//	}

	/*--------------------User Motions-------------------- */

//	void setDown(Pen needle) {
//		needle.down();
//	}
//
//	void setUp (Pen needle) {
//		needle.up();
//	}
//
//	void turnRight(float degree) {
//		needle.right(degree);
//	}
//
//	void turnLeft(float degree) {
//		needle.left(degree);
//	}
//
//	void pointTo(float degree) {
//		needle.angle = degree;
//	}
//
//	float get_angle() {
//		return needle.angle;
//	}
//
//	float get_x() {
//		return needle.x;
//	}
//
//	float get_y() {
//		return needle.y;
//	}
//
//	void go_to(float x, float y) {
//		if (needle.penDown) {
//			needle.draw_points(x, y, needle.stitch_size);
//		} else {
//			design.addStitch(x - needle.x, y - needle.y, 'j');
//			stroke(255, 0, 0);
//			line(needle.x, needle.y, x, y);
//			stroke(0);
//			needle.x = x;
//			needle.y = y;
//		}
//	}
//
//	void move(float steps) {
//		needle.forward(steps);
//	}

//	/*--------------------User Shapes-------------------- */
//
//	void em_line(float targetX, float targetY) {
//		go_to(targetX, targetY);
//	}
//
//	void em_rect(float width, float height) {
//		for (int i = 0; i < 2; i++) {
//			move(width);
//			pen.right();
//			move(height);
//			turnRight(90);
//		}
//	}
//
//	void em_square(float sidelength) {
//		for (int i = 0; i < 4; i++) {
//			move(sidelength);
//			turnRight(90);
//		}
//	}
//
//	void em_circle(float radius) {
//		float circumference = 2 * PI * radius;
//		float turn_angle = 360 / (circumference / needle.stitch_size);
//		for (int i = 0; i < 360 / turn_angle; i++) {
//			move(needle.stitch_size);
//			turnRight(turn_angle);
//		}
//	}
//
//	void em_star(int size) {
//		move(size);
//		turnLeft(72);
//		move(size);
//		for (int i = 0; i < 4; i++) {
//			turnLeft(180 + 36);
//			move(size);
//			turnLeft(72);
//			move(size);
//		}
//	}
//
//	void em_polygon(int n, int side_length) {
//
//		float exterior_angle = 360 / n;
//
//		for (int i = 0; i < n; i++) {
//			needle.forward(side_length);
//			turnRight(exterior_angle);
//		}
//	}
//
//	void em_ellipse(float xcenter, float ycenter, float a, float b) {
//
//		float initangle = needle.angle;
//		float x = xcenter + (a * cos(0) * cos(initangle)) + (b * sin(0) * -sin(initangle));
//		float y = ycenter + (a * cos(0) * sin(initangle)) + (b * sin(0) * cos(initangle));
//
//		needleUp();
//		go_to(x, y);
//		setDown();
//
//		for (float i = 0; i <= 360; i += 5) {
//			float angle = i / 180.0 * PI;
//			x = xcenter + (a * cos(angle) * cos(initangle)) + (b * sin(angle) * -sin(initangle));
//			y = ycenter + (a * cos(angle) * sin(initangle)) + (b * sin(angle) * cos(initangle));
//			go_to(x, y);
//		}
//
//		// math.stackexchange.com/questions/426150/what-is-the-general-equation-of-the-ellipse-that-is-not-in-the-origin-and-rotate
//		// x = (xcenter, ycenter) + (a*cos(theta))*(cos(pen.angle),
//		// sin(pen.angle))+
//		// (b*sin(theta))*(-1*sin(pen.angle), cos(pen.angle))
//	}
//
//	void em_ellipse2(float a, float b) {
//
//		float initx = needle.x;
//		float inity = needle.y;
//		float initangle = needle.angle;
//		float x = initx + (a * cos(0) * cos(initangle)) + (b * sin(0) * -sin(initangle));
//		float y = inity + (a * cos(0) * sin(initangle)) + (b * sin(0) * cos(initangle));
//
//		needleUp();
//		go_to(x, y);
//		setDown();
//
//		for (float i = 0; i <= 360; i += 5) {
//			float angle = i / 180.0 * PI;
//			x = initx + (a * cos(angle) * cos(initangle)) + (b * sin(angle) * -sin(initangle));
//			y = inity + (a * cos(angle) * sin(initangle)) + (b * sin(angle) * cos(initangle));
//			go_to(x, y);
//		}
//
//	}
//
//	void em_arc(float a, float b, float arcAngle) {
//
//		float initx = needle.x;
//		float inity = needle.y;
//		float initangle = needle.angle;
//		float x = initx + (a * cos(0) * cos(initangle)) + (b * sin(0) * -sin(initangle));
//		float y = inity + (a * cos(0) * sin(initangle)) + (b * sin(0) * cos(initangle));
//
//		needleUp();
//		go_to(x, y);
//		setDown();
//
//		for (float i = 0; i <= arcAngle; i += 5) {
//			float angle = i / 180.0 * PI;
//			x = initx + (a * cos(angle) * cos(initangle)) + (b * sin(angle) * -sin(initangle));
//			y = inity + (a * cos(angle) * sin(initangle)) + (b * sin(angle) * cos(initangle));
//			go_to(x, y);
//		}
//
//	}
//
//	void em_triangle(int x1, int y1, int x2, int y2) {
//
//		float initx = needle.x;
//		float inity = needle.y;
//
//		go_to(x1, y1);
//		go_to(x2, y2);
//		go_to(initx, inity);
//	}
//
//	void em_rhombus(float side_length, float degree) {
//
//		float angle2 = 180 - degree;
//
//		for (int i = 0; i < 4; i++) {
//			move(side_length);
//			turnRight(degree);
//			move(side_length);
//			turnRight(angle2);
//		}
//	}
}