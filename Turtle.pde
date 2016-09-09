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

/*--------------------Turtle/Object Code-------------------- */

// We were inspired by the following:
// http://j4mie.org/blog/simple-turtle-for-processing/
//  We changed the code to reflect the functunality of a needle
//  on an embroidery machine.

class Turtle {
  float x, y; // Current position of the turtle
  float angle = 0; // Current heading of the turtle
  float stitch_size = 9;
  color thread = color(0);
  boolean penDown = true; // Is pen down?

  // Set up initial position
  Turtle (float xin, float yin) {
    x = xin;
    y = yin;
  }

  int draw_points(float newX, float newY, float size) {
    float length = eq_distance(x, y, newX, newY);
    float dx = newX - x;
    float dy = newY -y;
    int n = round(length/size); // n = number of stitches
    float xFactor = dx / n;
    float yFactor = dy / n;

    if (penDown) {
      for (int i = 1; i <= n; i++) {
        float tempX = x + xFactor;
        float tempY = y + yFactor;
        line(x, y, tempX, tempY);
        ellipse(tempX, tempY, 4, 4);
        addStitch(design, xFactor, yFactor, 'n');
        x = tempX;
        y = tempY;
      }
    } else {
      addStitch(design, newX-x, newY-y, 'j');
      stroke(255, 0, 0);
      line(x, y, newX, newY);
      noStroke();
      x = newX;
      y = newY;
    }
    return design.size;
  }

  void forward (float distance) {
    float xtarget = x + cos(radians(angle)) * distance;
    float ytarget = y + sin(radians(angle)) * distance;
    stroke(thread);
    strokeWeight(1);

    ellipse(x, y, 4, 4);
    draw_points(xtarget, ytarget, stitch_size);
  }

  // Turn left by given angle
  void left (float turnangle) {
    if (angle - turnangle < 0) {
      angle = angle - turnangle + 360;
    } else angle -= turnangle;
  }

  // Turn right by given angle
  void right (float turnangle) {
    if (angle + turnangle > 360) {
      angle = angle + turnangle - 360;
    } else angle += turnangle;
  }

  // Set the pen to be up
  void penUp() {
    penDown = false;
  }

  // Set the pen to be down
  void penDown() {
    penDown = true;
  }
}