  // +------------+----------------------------------------------------
  // | Data Types |
  // +------------+
  
  /*--------------------Stitch/Pattern Types-------------------- */
  
  class Stitch {
    private int changex;
    private int changey;
    private char type;
  
    Stitch (int tempChangex, int tempChangey, char tempType) {
      changex = tempChangex;
      changey = -(tempChangey);
      type = tempType;
    }
  }
  
  