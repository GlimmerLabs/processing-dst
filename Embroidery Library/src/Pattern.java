import processing.core.*;

/**
 * The class pattern
 */
public class Pattern {
	private PApplet p;
	public Stitch[] stitches = new Stitch[2];
	public int size = 0;
	
	public Pattern (PApplet parent) {
		p = parent;
	}

	void addStitch(float tempChangex, float tempChangey, char tempType) {

		if (p.abs(tempChangex) > 121 || p.abs(tempChangey) > 121) {
			int xcounter = p.ceil(p.abs(tempChangex) / 121);
			int ycounter = p.ceil(p.abs(tempChangey) / 121);
			int counter = 0;
			if (xcounter >= ycounter) {
				counter = xcounter;
			} else if (ycounter >= xcounter) {
				counter = ycounter;
			}
			for (int i = 0; i < counter; i++) {
				if (this.size == 0) {
					this.stitches[this.size++] = new Stitch(p.round(tempChangex / counter), 
							p.round(tempChangey / counter), tempType);
				} else {
					this.stitches = (Stitch[]) p.expand(this.stitches, this.size + 1);
					this.stitches[this.size++] = new Stitch(p.round(tempChangex / counter), 
							p.round(tempChangey / counter), tempType);
				}
			}
		} else {
			if (this.size == 0) {
				this.stitches[this.size++] = new Stitch(p.round(tempChangex), p.round(tempChangey), tempType);
			} else {
				this.stitches = (Stitch[]) p.expand(this.stitches, this.size + 1);
				this.stitches[this.size++] = new Stitch(p.round(tempChangex), p.round(tempChangey), tempType);
			}
		}
	}

	public void read() {
		if (this.size == 0) {
			p.println("Empty Pattern.");
		}
		for (int i = 0; i < this.size; i++) {
			p.println(this.stitches[i].changex, this.stitches[i].changey, this.stitches[i].type);
		}
	}

	public void clear() {
		if (this.size > 0) {
			while (this.size > 1) {
				this.stitches = (Stitch[]) p.shorten(this.stitches);
				this.size--;
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
	
	private final static byte BIT7 = (byte) 128;
	private final static byte BIT6 = (byte) 64;
	private final static byte BIT5 = (byte) 32;
	private final static byte BIT4 = (byte) 16;
	private final static byte BIT3 = (byte) 8;
	private final static byte BIT2 = (byte) 4;
	private final static byte BIT1 = (byte) 2;
	private final static byte BIT0 = (byte) 1;

	public void saveDST(Pattern pat, String file) {
		if (pat.size == 0) {
			p.println("Error: Pattern is empty.");
			return;
		}

		// Adding 512 bytes of 0 for the header.
		byte[] header = new byte[512];

		for (int i = 0; i < 512; i++) {
			header[i] = 0;
		}

		// create a byte array 3 times the size of pat, plus 3 for the final 3
		// bytes
		byte[] arr = new byte[(3 * pat.size) + 3];

		// Recurse over pattern and translate stitches to bytes, which are
		// stored in arr;

		int i = 0;
		int n = 0;

		while (n < pat.size) {
			byte b1 = (byte) 0;
			byte b2 = (byte) 0;
			byte b3 = (byte) 0;

			int x = pat.stitches[n].changex;
			int y = pat.stitches[n].changey;
			char stitch = pat.stitches[n].type;

			// setting the x-y change bits.
			if (x > 40) {
				b3 |= BIT2;
				x -= 81;
			}
			if (x < -40) {
				b3 |= BIT3;
				x += 81;
			}
			if (y > 40) {
				b3 |= BIT5;
				y -= 81;
			}
			if (y < -40) {
				b3 |= BIT4;
				y += 81;
			}
			if (x > 13) {
				b2 |= BIT2;
				x -= 27;
			}
			if (x < -13) {
				b2 |= BIT3;
				x += 27;
			}
			if (y > 13) {
				b2 |= BIT5;
				y -= 27;
			}
			if (y < -13) {
				b2 |= BIT4;
				y += 27;
			}
			if (x > 4) {
				b1 |= BIT2;
				x -= 9;
			}
			if (x < -4) {
				b1 |= BIT3;
				x += 9;
			}
			if (y > 4) {
				b1 |= BIT5;
				y -= 9;
			}
			if (y < -4) {
				b1 |= BIT4;
				y += 9;
			}
			if (x > 1) {
				b2 |= BIT0;
				x -= 3;
			}
			if (x < -1) {
				b2 |= BIT1;
				x += 3;
			}
			if (y > 1) {
				b2 |= BIT7;
				y -= 3;
			}
			if (y < -1) {
				b2 |= BIT6;
				y += 3;
			}
			if (x > 0) {
				b1 |= BIT0;
				x -= 1;
			}
			if (x < 0) {
				b1 |= BIT1;
				x += 1;
			}
			if (y > 0) {
				b1 |= BIT7;
				y -= 1;
			}
			if (y < 0) {
				b1 |= BIT6;
				y += 1;
			}

			// setting the jump/color change bits.
			if (stitch == 'c') {
				b3 |= BIT6;
				b3 |= BIT7;
			}
			if (stitch == 'j') {
				b3 |= BIT7;
			}

			b3 |= BIT0; // setting the 'set' bits.
			b3 |= BIT1;

			arr[i] = b1;
			i++;
			arr[i] = b2;
			i++;
			arr[i] = b3;
			i++;
			n++;
		}

		// Add the ending to arr and write arr to file.
		byte b = 0; // Adds the final 3 bits to arr.
		arr[i] = b;
		i++;
		arr[i] = b;
		i++;
		b |= BIT7 | BIT6 | BIT5 | BIT4 | BIT1 | BIT0;
		arr[i] = b;

		byte[] contents = p.concat(header, arr);
		p.saveBytes(file, contents);
	}
}
