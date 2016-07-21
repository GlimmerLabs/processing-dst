// INCLUDED IN THIS DOCUMENT:
/*  Data Types:
      Stitch/Pattern Types
        Stitch(int tempChangex, int tempChangey, char tempType);
        Pattern();
        Declaration of a Pattern called pat (i.e. Pattern design = new Pattern();)
      Stitch/Pattern Functions
        Stitch addStitch(Pattern pat, int tempChangex, int tempChangey, char tempType);
        void readPattern(Pattern pat);
        void clearPattern(Pattern pat);
        
    Saving as a DST file:
      constants
      void saveDST(Pattern pat, String file);
    
    Turtle Interface:
      Helper Functions
        float eq_distance(float newX, float newY, float x, float y); // the distance equation
        Boolean divides_evenly(float dividend, float divisor); 
      Turtle Object Code
        Turtle (float xin, float yin);
          int draw_points(float newX, float newY, float size);
          void forward (float distance);
          void left (float turnangle);
          void right (float turnangle);
          void penUp();
          void penDown();
        Declaration of a turtle called pen (i.e. Turtle pen;)
      User Setup Functions:
        void stageSize (float width, float height); //length and width should be in inches (the size of the frame)
          //MUST BE PUT IN THE SETTINGS() FUNCTION BEFORE ALL ELSE
        void em_setup(); //sets all the defaults and makes sure noLoop() is active.
        void stitchSize(float sizeof_stitch); // Default is 9. Recommended size: 8-12
        void em_background (color rgb); //This has no effect on the embroidery machine. Default is white.
        void stitchColor (color rgb); //Sets the initial color. Default is black.
        void changeColor (color newColor); //Stops the machine to allow a change of threads.
      User Motions:
        void needleDown();
        void needleUp();
        void turnRight(float degree);
        void turnLeft(float degree);
        void pointTo(float degree); //teleports the turtle/pen
        void go_to(float x, float y);
        void move(float steps);
      User Shapes:
        em_rect(float width, float height);
        em_square(float sidelength);
        em_circle(float radius);
      
*/

// +------------+----------------------------------------------------
// | Data Types |
// +------------+

/*--------------------Stitch/Pattern Types-------------------- */

class Stitch {
  int changex;
  int changey;
  char type;
  
  Stitch (int tempChangex, int tempChangey, char tempType) {
    changex = tempChangex;
    changey = tempChangey;
    type = tempType;
  }
}

class Pattern {
  Stitch[] stitches = new Stitch[2];
  int size = 0;
}

Pattern design = new Pattern();

/*--------------------Stitch/Pattern Functions-------------------- */


Stitch addStitch(Pattern pat, float tempChangex, float tempChangey, char tempType){
  if (pat.size == 0) {
    pat.stitches[pat.size] = new Stitch(round(tempChangex), round(tempChangey), tempType);
    return pat.stitches[pat.size++];
  }
  else {
    pat.stitches = (Stitch[]) expand(pat.stitches, pat.size+1);
    pat.stitches[pat.size] = new Stitch(round(tempChangex), round(tempChangey), tempType);
    return pat.stitches[pat.size++];
  }
}

void readPattern(Pattern pat) {
  if (pat.size == 0) {
      println("Empty Pattern.");
  }
  for (int i = 0; i < pat.size; i++) {
    println(pat.stitches[i].changex, pat.stitches[i].changey, pat.stitches[i].type);
  }
}

void clearPattern(Pattern pat) {
  if (pat.size > 0) {
    while (pat.size != 0) {
      pat.stitches = (Stitch[]) shorten(pat.stitches);
      pat.size--;
    }
  }
}

// +--------------------+---------------------------------------------
// | Saving as DST file |
// +--------------------+
//
//Citations: The algorithm for converting into dst format was taken from
//stitchcode: https://github.com/backface/stitchcode/blob/master/stitchcode.py

/*--------------------Constants-------------------- */

final byte BIT7 = (byte)128;
final byte BIT6 = (byte)64;
final byte BIT5 = (byte)32;
final byte BIT4 = (byte)16;
final byte BIT3 = (byte)8;
final byte BIT2 = (byte)4;
final byte BIT1 = (byte)2;
final byte BIT0 = (byte)1;

void saveDST(Pattern pat, String file)
{
    if (pat.size == 0)
    {
        println("Error: Pattern is empty.");
        return;
    }
    
    //Adding 512 bytes of 0 for the header.
    byte[] header = new byte[512];
    
    for (int i = 0; i < 512; i++)
    {
      header[i] = 0;
    }
    
    //create a byte array 3 times the size of pat, plus 3 for the final 3 bytes
    byte[] arr = new byte[(3 * pat.size) + 3]; 
    
    
    //Recurse over pattern and translate stitches to bytes, which are stored in arr;
    
    int i = 0;
    int n = 0;
    
    while (n < pat.size)
    {
    byte b1 = (byte)0;
    byte b2 = (byte)0;
    byte b3 = (byte)0;
    
    int x = pat.stitches[n].changex;
    int y = pat.stitches[n].changey;
    char stitch = pat.stitches[n].type;
    
    if (x > 40) //setting the x-y change bits. Check here when debugging.-----------------------------------------
    {
      b3 |= BIT2;
      x = x - 81;
    }
    if (x < -40)
    {
      b3 |= BIT3;
      x = x + 81;
    }
    if (y > 40)
    {
      b3 |= BIT5;
      y = y - 81;
    }
    if(y < -40)
    {
      b3 |= BIT4;
      y = y + 81;
    }
    if (x > 13)
    {
      b2 |= BIT2;
      x = x - 27;
    }
    if (x < -13)
    {
      b2 |= BIT3;
      x = x + 27;
    }
     if (y > 13)
    {
      b2 |= BIT5;
      y = y - 27;
    }
     if (y < -13)
    {
      b2 |= BIT4;
      y = y + 27;
    }
     if (x > 4)
    {
      b1 |= BIT2;
      x = x - 9;
    }
     if (x < -4)
    {
      b1 |= BIT3;
      x = x + 9;
    }
     if (y > 4)
    {
      b1 |= BIT5;
      y = y - 9;
    }
     if (y < -4)
    {
      b1 |= BIT4;
      y = y + 9;
    }
     if (x > 1)
    {
      b2 |= BIT0;
      x = x - 3;
    }
     if (x < -1)
    {
      b2 |= BIT1;
      x = x + 3;
    }
     if (y > 1)
    {
      b2 |= BIT7;
      y = y - 3;
    }
     if (y < -1)
    {
      b2 |= BIT6;
      y = y + 3;
    }
     if (x > 0)
    {
      b1 |= BIT0;
      x = x - 1;
    }
     if (x < 0)
    {
      b1 |= BIT1;
      x = x + 1;
    }
     if (y > 0)
    {
      b1 |= BIT7;
      y = y - 1;
    }
     if (y < 0)
    {
      b1 |= BIT6;
      y = y + 1;
    }
    
    if (stitch == 'c') //setting the jump/color change bits.
    {
      b3 |= BIT6;
    }
     if (stitch == 'j')
    {
      b3 |= BIT7;
    }
    
    b3 |= BIT0; //setting the 'set' bits.
    b3 |= BIT1;
    
    arr[i] = b1;
    i++;
    arr[i] = b2;
    i++;
    arr[i] = b3;
    i++;
    n++;
    }
  
  //Add the ending to arr and write arr to file.
    byte b = 0; //Adds the final 3 bits to arr.
    arr[i] = b;
    i++;
    arr [i] = b;
    i++;
    b |= BIT7 | BIT6 | BIT5 | BIT4 | BIT1 | BIT0;
    arr [i] = b;
    
    byte[] contents = concat(header, arr);
    saveBytes(file, contents);
    
}

// +------------------+----------------------------------------------
// | Turtle Interface |
// +------------------+

/*--------------------Helper Functions-------------------- */

float eq_distance(float newX, float newY, float x, float y) {
    float distance = sqrt(sq(newX - x) + sq(newY - y));
    return distance;
}

Boolean divides_evenly(float dividend, float divisor) {
  
  if((dividend % divisor) == 0){
    return true;
  }
  else {
    return false;
  }
}

/*--------------------Turtle/Object Code-------------------- */

// We based our turtle code from the following:
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
    
    for (int i = 1; i <= n; i++) {
      float tempX = x + xFactor;
      float tempY = y + yFactor;
      line(x, y, tempX, tempY);
      ellipse(tempX, tempY, 4, 4);
      addStitch(design, xFactor, yFactor, 'n');
      x = tempX;
      y = tempY;
    }

    return design.size;
  }
  
  void forward (float distance) {
    float xtarget = x + cos(radians(angle)) * distance;
    float ytarget = y + sin(radians(angle)) * distance;
    stroke(thread);
    strokeWeight(1);
    
    if (penDown) {
      ellipse(x, y, 4, 4);
      draw_points(xtarget, ytarget, stitch_size);
    }
    else {
      addStitch(design, xtarget-x, ytarget-y, 'j');
      line(pen.x, pen.y, xtarget, ytarget);
    }
  }
  
  // Turn left by given angle
  void left (float turnangle) {
    if (angle - turnangle < 0) {
      angle = angle - turnangle + 360;
    }
    else angle -= turnangle;
  }
  
  // Turn right by given angle
  void right (float turnangle) {
    if (angle + turnangle > 360) {
      angle = angle + turnangle - 360;
    }
    else angle += turnangle;
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

Turtle pen;

/*--------------------User Setup Functions-------------------- */

//length and width should be in inches (the size of the frame)
void stageSize (float width, float height) {
  // There are 50 pixels per centimeter and there are 2.54 cm/in.
  // We try to leave a margin of error so that you don't hit the frame.
  float factor = 2.55 * 50;
  println((int)(width*factor), (int)(height*factor));
  size((int)(width*factor), (int)(height*factor));
}

void em_setup() {
  noLoop();
  background(255);  // white background
  fill(0);          // black thread
  pen = new Turtle(0, 0);
}

void clean() {
  background(255);
  clearPattern(design);
}

// Default is 9. Recommended size: 8-12
void stitchSize(float sizeof_stitch) {
  pen.stitch_size = sizeof_stitch;
}

//This has no effect on the embroidery machine. Default is white.
void em_background (color rgb) {
  background(rgb);
}

//Sets the initial color. Default is black.
void stitchColor (color rgb) {
  fill(rgb);
  stroke(rgb);
} 

//Stops the machine to allow a change of threads.
//On the display it will only show a color change.
void changeColor (color newColor) {
  fill(newColor);
  stroke(newColor);
  addStitch(design, 0, 0, 'c');
}

/*--------------------User Motions-------------------- */

void needleDown() {
  pen.penDown();
}

void needleUp() {
  pen.penUp();
}

void turnRight(float degree) {
  pen.right(degree);
}

void turnLeft(float degree) {
  pen.left(degree);
}

void pointTo(float degree) {
  pen.angle = radians(degree);
  println(degrees(pen.angle));
}

void go_to(float x, float y) {
  if(pen.penDown) {
    needleUp();
  }
  addStitch(design, x-pen.x, y-pen.y, 'j');
  line(pen.x, pen.y, x, y);
  pen.x = x;
  pen.y = y;
}

void move(float steps) {
  pen.forward(steps);
}

/*--------------------User Shapes-------------------- */

void em_rect(float width, float height) {
  for (int i = 0; i < 2; i++) {
    move(width);
    turnRight(90);
    move(height);
    turnRight(90);
  }
}

void em_square(float sidelength) {
  for (int i = 0; i < 4; i++) {
    move(sidelength);
    turnRight(90);
  }
}

void em_circle(float radius) {
  float circumference = 2*PI*radius;
  float turn_angle = 360/(circumference/pen.stitch_size);
  for (int i = 0; i < 360/turn_angle; i++) {
    move(pen.stitch_size);
    turnRight(turn_angle);
  }
}

// +------------------+----------------------------------------------
// | Test Code (temp) |
// +------------------+

void settings() {
  stageSize(5, 7);
}

void setup() {
  em_setup();
  stitchSize(9);
}

void star(int size) {
  move(size);
  turnLeft(72);
  move(size);
  for (int i = 0; i < 4; i++) {
    turnLeft(180+36);
    move(size);
    turnLeft(72);
    move(size);
  }
}

void draw() {
  color red = color(255, 0, 0);
  
  needleUp();
  go_to(300, 300);
  clean();
  
  
  needleDown();
  //em_square(100);
  
  for (int i = 0; i <= 18; i++) {
    star(100);
    turnRight(124);
  }
  
  needleUp();
  
  /*
  needleDown();
  for (int i = 0; i <= 16; i ++) {
    em_circle(100);
    turnRight(20);
  }
  needleUp();
  */
  
  readPattern(design);
  //saveDST(design, "testing-stitchsize.dst");
}
