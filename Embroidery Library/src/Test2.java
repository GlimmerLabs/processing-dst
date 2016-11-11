import processing.core.*;

// super useful resource: http://gigl.scs.carleton.ca/node/48
public class Test2 extends PApplet {

	public static void main(String[] args) {
		//uncommenting present will let it run in full screen
		//form of the second string should be packageName.ClassName
		PApplet.main(new String[] { /*"--present",*/ "Test2" });

	}

	public void settings() {
		size(400, 400);
	}

	public void setup() {
		background(0);
	}

	public void draw() {
		fill(255, 0, 0);
		rect(100, 100, 200, 200);
	}

}
