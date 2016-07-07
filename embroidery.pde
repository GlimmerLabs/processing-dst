/**
 *   embroidery.pde
 *   The beginnings of a library for processing that will allow the
 *      user to create embroidery files of the extension .dst
 */

// +-----------+-----------------------------------------------------
// | Constants |
// +-----------+

#define BIT7 128
#define BIT6  64
#define BIT5  32
#define BIT4  16
#define BIT3   8
#define BIT2   4
#define BIT1   2
#define BIT0   1

// +-----------------+-----------------------------------------------
// | Predeclarations |
// +-----------------+

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

class Node {
  Stitch datum;
  Node next;
  
  Node (Stitch tempDatum, Node tempNext) {
    datum = tempDatum;
    next = tempNext;
  }
}

class Pattern {
  Node first;
  
  Pattern (Node tempFirst) {
    first = tempFirst;
  }
}

// +---------+-------------------------------------------------------
// | Helpers |
// +---------+

Stitch myStitch1, myStitch2, myStitch3, myStitch4;
Node one, two, three, four;
Pattern myPattern;

void setup() {
  myStitch1 = new Stitch(9, 0, 'n');
  myStitch2 = new Stitch(0, -9, 'n');
  myStitch3 = new Stitch(-9, 0, 'n');
  myStitch4 = new Stitch(0, 9, 'e');
  
  four = new Node(myStitch4, null);
  three = new Node(myStitch3, four);
  two = new Node(myStitch2, three);
  one = new Node(myStitch1, two);
  
  // Tests that the linked list works:
  /*
  println("Type of stitch:", one.datum.type);
  println("Change in x:", one.datum.changex);
  println("Change in y:", one.datum.changey);
  println("Next type of stitch:", one.next.datum.type);
  */
  
  /* Result of println statements:
  Type of stitch: n
  Change in x: 9
  Change in y: 0
  Next type of stitch: n
  */
  
  myPattern = new Pattern(one);
  
  // Tests that the contained linked list works:
  /*
  println("Type of stitch:", myPattern.first.datum.type);
  println("Change in x:", myPattern.first.datum.changex);
  println("Change in y:", myPattern.first.datum.changey);
  println("Next type of stitch:", myPattern.first.next.datum.type);
  */
  
  /* Result of println statements:
  Type of stitch: n
  Change in x: 9
  Change in y: 0
  Next type of stitch: n
  */
}
