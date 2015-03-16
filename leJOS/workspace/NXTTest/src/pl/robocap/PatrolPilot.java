package pl.robocap;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Robot rusza do przodu, wykonuje obrót o 180 stopni (zawraca) i porusza się
 * znów do przodu, aby znaleźć się w tym samym miejscu, co na początku.
 */
public class PatrolPilot {

	public static void main(String[] args) {
		Motor.B.setSpeed(400);
		Motor.C.setSpeed(400);
		
		DifferentialPilot pilot = new DifferentialPilot(3.5f, 19f, Motor.B, Motor.C);
		while (true) {
			pilot.travel(40);
			pilot.rotate(180);
		}

	}

}
