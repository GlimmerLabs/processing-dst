// +---------------------+------------------------------------------
// | Example Code (temp) |
// +---------------------+
Pattern design = new Pattern();
Turtle needle = new Turtle(0, 0);

color green = color(0, 255, 0);

 
 void settings() {
   stageSize(5, 7);
 }
 
 void setup() {
   em_setup();
 }
 
 void draw() {
   go_to(205, 307);
   clean();
   stitchColor(green);
   
   needleDown();
   for (int i = 0; i < 18; i++) {
     em_ellipse2(100, 150);
     turnRight(20);
   }
   needleUp();
   
   saveDST(design, "temp.dst");
 }