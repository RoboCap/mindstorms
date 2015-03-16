package my.mindstorms;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Test klasy pilota - jeżdżenie po kwadratowym polu
 */
public class SquareTracer {
	
	DifferentialPilot pilot = new DifferentialPilot(3.5f, 19f, Motor.B, Motor.C);
	
	private void square(int length, int angle) {
		for (int i = 0; i < 4; i++) {
			pilot.travel(length);
			pilot.rotate(Math.signum(length) * angle);
		}
	}

	public static void main(String[] args) {
		SquareTracer squareTracer = new SquareTracer();
		squareTracer.square(10, 90);
		squareTracer.square(10, 120);
		squareTracer.square(-10, 120);
		squareTracer.square(-10, 90);
	}

}
