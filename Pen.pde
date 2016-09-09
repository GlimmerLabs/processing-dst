// +------------------+----------------------------------------------
// | Turtle Interface |
// +------------------+

/*--------------------Helper Functions-------------------- */

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
  int limit = 121; //max units the embroidery mahine can handle
  if (abs(dx) > limit || abs(dy) > limit) {
    return false;
  } else return true;
}

/*--------------------Pen (Turtle) Code-------------------- */

// We were inspired by the following:
// http://j4mie.org/blog/simple-turtle-for-processing/
//  We changed the code to reflect the functunality of a needle
//  on an embroidery machine.

class Turtle {
  private float x, y; // Current position of the turtle
  private float angle = 0; // Current heading of the turtle
  private float stitch_size = 9;
  private color thread = color(0);
  private boolean penDown = true; // Is pen down?

  // Set up initial position
  Turtle (float xin, float yin) {
    this.x = xin;
    this.y = yin;
  }

  int draw_points(float newX, float newY, float size) {
    float length = eq_distance(this.x, this.y, newX, newY);
    float dx = newX - this.x;
    float dy = newY - this.y;
    int n = round(length/size); // n = number of stitches
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

  void forward (float distance) {
    float xtarget = this.x + cos(radians(angle)) * distance;
    float ytarget = this.y + sin(radians(angle)) * distance;
    stroke(thread);
    strokeWeight(1);

    ellipse(x, y, 4, 4);
    draw_points(xtarget, ytarget, stitch_size);
  }

  // Turn left by given angle
  void left (float turnangle) {
    if (this.angle - turnangle < 0) {
      this.angle = this.angle - turnangle + 360;
    } else {this.angle -= turnangle;}
  }

  // Turn right by given angle
  void right (float turnangle) {
    if (this.angle + turnangle > 360) {
      this.angle = this.angle + turnangle - 360;
    } else {this.angle += turnangle;}
  }

  // Set the pen to be up
  void up() {
    this.penDown = false;
  }

  // Set the pen to be down
  void down() {
    this.penDown = true;
  }
}