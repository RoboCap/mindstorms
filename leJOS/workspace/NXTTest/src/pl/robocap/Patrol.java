package pl.robocap;

import lejos.nxt.Motor;

/**
 * Robot rusza do przodu, wykonuje obrót o 180 stopni (zawraca) i porusza się
 * znów do przodu, aby znaleźć się w tym samym miejscu, co na początku.
 */
public class Patrol {

	public static void main(String[] args) {
		Motor.B.setSpeed(400);
		Motor.C.setSpeed(400);

		while (true) {
			Motor.B.forward();
			Motor.C.forward();
			while (Motor.B.getTachoCount() < 950);

			Motor.B.rotate(930, true);
			Motor.C.rotate(-930);

			Motor.B.resetTachoCount();
		}

	}

}
