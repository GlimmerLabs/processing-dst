/**
  The class pattern
*/
public class Pattern {
    private Stitch[] stitches = new Stitch[2];
    private int size = 0;
    
    void addStitch(float tempChangex, float tempChangey, char tempType) {
  
      if (abs(tempChangex) > 121 || abs(tempChangey) > 121) {
        int xcounter = ceil(abs(tempChangex)/121);
        int ycounter = ceil(abs(tempChangey)/121);
        int counter = 0;
        if (xcounter >= ycounter) {
          counter = xcounter;
        } else if (ycounter >= xcounter) {
          counter = ycounter;
        }
        for (int i = 0; i < counter; i++) {
          if (this.size == 0) {
            this.stitches[this.size++] = new Stitch(round(tempChangex/counter), round(tempChangey/counter), tempType);
          } else {
            this.stitches = (Stitch[]) expand(this.stitches, this.size+1);
            this.stitches[this.size++] = new Stitch(round(tempChangex/counter), round(tempChangey/counter), tempType);
          }
        }
      } else {
        if (this.size == 0) {
          this.stitches[this.size++] = new Stitch(round(tempChangex), round(tempChangey), tempType);
        } else {
          this.stitches = (Stitch[]) expand(this.stitches, this.size+1);
          this.stitches[this.size++] = new Stitch(round(tempChangex), round(tempChangey), tempType);
        }
      }
    }
    
    void readPattern() {
      if (this.size == 0) {
        println("Empty Pattern.");
      }
      for (int i = 0; i < this.size; i++) {
        println(this.stitches[i].changex, this.stitches[i].changey, this.stitches[i].type);
      }
    }
    
    void clearPattern() {
      if (this.size > 0) {
        while (this.size > 1) {
          this.stitches = (Stitch[]) shorten(this.stitches);
          this.size--;
        }
      }
    }
  }
  
  