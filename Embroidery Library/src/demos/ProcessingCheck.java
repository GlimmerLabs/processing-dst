package demos;
import processing.core.*;

// super useful resource: http://gigl.scs.carleton.ca/node/48
public class ProcessingCheck extends PApplet {

	public static void main(String[] args) {
		//uncommenting present will let it run in full screen
		//form of the second string should be thisPackageName.thisClassName
		PApplet.main(new String[] { /*"--present",*/ "demos.ProcessingCheck" });

	}

	public void settings() {
		size(400, 400);
	}

	public void setup() {
		background(255);
	}

	public void draw() {
		fill(0);
		line((float) 2, (float) 2, (float) 20, (float) 20);
		rect(100, 100, 200, 200);
	}

}
