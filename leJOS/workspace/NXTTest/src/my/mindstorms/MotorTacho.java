package my.mindstorms;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.util.Delay;

/**
 * Display the program number as before. //
 *	Set the motor speed to 2 rev/sec //
 *	Run Motor.A forward. //
 *	Wait for 2 seconds. //
 *	Display the motor angle on the LCD. (what should it be?) //
 *	Stop the motor. //
 *	Display the tachometer reading on the on the next line LCD. //
 *	Start the motor rotating backward. //
 *	Wait till the tacho count reaches 0. //
 *	Display the tacho count on the next line. //
 *	Stop the motor. //
 *	Display the tacho count on the next line. //
 *	Wait for a button press so you can read the LCD. //
 */
public class MotorTacho {

	public static void main(String ... args) {
		
		LCD.drawString("Motor Test", 0, 0);
		Motor.A.setSpeed(720);
		Motor.A.forward();

		Delay.msDelay(2000);
		LCD.drawString(String.valueOf(Motor.A.getTachoCount()), 0, 1);
		
		Motor.A.stop();
		LCD.drawString(String.valueOf(Motor.A.getTachoCount()), 0, 2);

		Button.waitForAnyPress();
	}

}
