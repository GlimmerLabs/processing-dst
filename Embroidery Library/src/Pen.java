import processing.core.*;

// +------------------+----------------------------------------------
// | Turtle Interface |
// +------------------+

/*--------------------Helper Functions-------------------- */
public class Pen extends PApplet {
	
	// START HELPER FUNCTIONS
	float eq_distance(float x, float y, float newX, float newY) {
		float distance = sqrt(sq(newX - x) + sq(newY - y));
		return distance;
	}

	Boolean divides_evenly(float dividend, float divisor) {

		if ((dividend % divisor) == 0) {
			return true;
		} else {
			return false;
		}
	}

	Boolean at_limit(float dx, float dy) {
		int limit = 121; // max units the embroidery mahine can handle
		if (abs(dx) > limit || abs(dy) > limit) {
			return false;
		} else
			return true;
	}

	/*--------------------Pen (Turtle) Code-------------------- */

	// We were inspired by the following:
	// http://j4mie.org/blog/simple-turtle-for-processing/
	// We changed the code to reflect the functunality of a needle
	// on an embroidery machine.

	public Pattern design;
	public float x, y; // Current position of the turtle
	public float angle = 0; // Current heading of the turtle
	public float stitch_size = 9;
	public int thread = 0;
	public boolean penDown = true; // Is pen down?

	// Set up initial position
	public Pen (float xin, float yin, Pattern design) {
		this.x = xin;
		this.y = yin;
		this.design = design;
	}

	int draw_points(float newX, float newY, float size) {
		float length = eq_distance(this.x, this.y, newX, newY);
		float dx = newX - this.x;
		float dy = newY - this.y;
		int n = round(length / size); // n = number of stitches
		float xFactor = dx / n;
		float yFactor = dy / n;

		if (penDown) {
			for (int i = 1; i <= n; i++) {
				float tempX = x + xFactor;
				float tempY = y + yFactor;
				line(x, y, tempX, tempY);
				ellipse(tempX, tempY, 4, 4);
				design.addStitch(xFactor, yFactor, 'n');
				x = tempX;
				y = tempY;
			}
		} else {
			design.addStitch(newX - this.x, newY - this.y, 'j');
			stroke(255, 0, 0);
			line(this.x, this.y, newX, newY);
			noStroke();
			this.x = newX;
			this.y = newY;
		}
		return design.size;
	}

	public void forward(float distance) {
		float xtarget = this.x + cos(radians(angle)) * distance;
		float ytarget = this.y + sin(radians(angle)) * distance;
		stroke(thread);
		strokeWeight(1);

		ellipse(x, y, 4, 4);
		draw_points(xtarget, ytarget, stitch_size);
	}

	// Turn left by given angle
	public void left(float turnangle) {
		if (this.angle - turnangle < 0) {
			this.angle = this.angle - turnangle + 360;
		} else {
			this.angle -= turnangle;
		}
	}

	// Turn right by given angle
	public void right(float turnangle) {
		if (this.angle + turnangle > 360) {
			this.angle = this.angle + turnangle - 360;
		} else {
			this.angle += turnangle;
		}
	}

	// Set the pen to be up
	public void up() {
		this.penDown = false;
	}

	// Set the pen to be down
	public void down() {
		this.penDown = true;
	}
	
	// Stops the machine to allow a change of threads.
	// On the display it will only show a color change.
	public void setColor(Pattern design, int red, int green, int blue) {
		fill(red, green, blue);
		stroke(red, green, blue);
		design.addStitch(0, 0, 'c');
	}
	
	public void go_to(float x, float y) {
		if (penDown) {
			draw_points(x, y, stitch_size);
		} else {
			design.addStitch(x - this.x, y - this.y, 'j');
			stroke(255, 0, 0);
			line(this.x, this.y, x, y);
			stroke(0);
			this.x = x;
			this.y = y;
		}
	}
	
	/*--------------------User Shapes-------------------- */

	void em_line(float targetX, float targetY) {
		go_to(targetX, targetY);
	}

	void em_rect(float width, float height) {
		for (int i = 0; i < 2; i++) {
			forward(width);
			right(90);
			forward(height);
			right(90);
		}
	}

	void em_square(float sidelength) {
		for (int i = 0; i < 4; i++) {
			forward(sidelength);
			right(90);
		}
	}

	void em_circle(float radius) {
		float circumference = 2 * PI * radius;
		float turn_angle = 360 / (circumference / stitch_size);
		for (int i = 0; i < 360 / turn_angle; i++) {
			forward(stitch_size);
			right(turn_angle);
		}
	}

	void em_star(int size) {
		forward(size);
		left(72);
		forward(size);
		for (int i = 0; i < 4; i++) {
			left(180 + 36);
			forward(size);
			left(72);
			forward(size);
		}
	}

	void em_polygon(int n, int side_length) {

		float exterior_angle = 360 / n;

		for (int i = 0; i < n; i++) {
			forward(side_length);
			right(exterior_angle);
		}
	}

	void em_ellipse(float xcenter, float ycenter, float a, float b) {

		float initangle = angle;
		float x = xcenter + (a * cos(0) * cos(initangle)) + (b * sin(0) * -sin(initangle));
		float y = ycenter + (a * cos(0) * sin(initangle)) + (b * sin(0) * cos(initangle));

		up();
		go_to(x, y);
		down();

		for (float i = 0; i <= 360; i += 5) {
			float angle = (float) (i / 180.0 * PI);
			x = xcenter + (a * cos(angle) * cos(initangle)) + (b * sin(angle) * -sin(initangle));
			y = ycenter + (a * cos(angle) * sin(initangle)) + (b * sin(angle) * cos(initangle));
			go_to(x, y);
		}

		// math.stackexchange.com/questions/426150/what-is-the-general-equation-of-the-ellipse-that-is-not-in-the-origin-and-rotate
		// x = (xcenter, ycenter) + (a*cos(theta))*(cos(pen.angle),
		// sin(pen.angle))+
		// (b*sin(theta))*(-1*sin(pen.angle), cos(pen.angle))
	}

	void em_ellipse2(float a, float b) {

		float initx = this.x;
		float inity = this.y;
		float initangle = this.angle;
		float x = initx + (a * cos(0) * cos(initangle)) + (b * sin(0) * -sin(initangle));
		float y = inity + (a * cos(0) * sin(initangle)) + (b * sin(0) * cos(initangle));

		up();
		go_to(x, y);
		down();

		for (float i = 0; i <= 360; i += 5) {
			float angle = (float) (i / 180.0 * PI);
			x = initx + (a * cos(angle) * cos(initangle)) + (b * sin(angle) * -sin(initangle));
			y = inity + (a * cos(angle) * sin(initangle)) + (b * sin(angle) * cos(initangle));
			go_to(x, y);
		}

	}

	void em_arc(float a, float b, float arcAngle) {

		float initx = this.x;
		float inity = this.y;
		float initangle = this.angle;
		float x = initx + (a * cos(0) * cos(initangle)) + (b * sin(0) * -sin(initangle));
		float y = inity + (a * cos(0) * sin(initangle)) + (b * sin(0) * cos(initangle));

		up();
		go_to(x, y);
		down();

		for (float i = 0; i <= arcAngle; i += 5) {
			float angle = (float) (i / 180.0 * PI);
			x = initx + (a * cos(angle) * cos(initangle)) + (b * sin(angle) * -sin(initangle));
			y = inity + (a * cos(angle) * sin(initangle)) + (b * sin(angle) * cos(initangle));
			go_to(x, y);
		}

	}

	void em_triangle(int x1, int y1, int x2, int y2) {

		float initx = this.x;
		float inity = this.y;

		go_to(x1, y1);
		go_to(x2, y2);
		go_to(initx, inity);
	}

	void em_rhombus(float side_length, float degree) {

		float angle2 = 180 - degree;

		for (int i = 0; i < 4; i++) {
			forward(side_length);
			right(degree);
			forward(side_length);
			right(angle2);
		}
	}
}