package my.mindstorms;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

/**
 * Debuggujemy naszego robota z komputera PC
 */
public class HelloWorld {

	  public static void main(String[] args) {
	        System.out.println("Hello World!");
	        while (SensorPort.S1.readValue() != 1) {
	        	Motor.A.forward();
			}
	        Motor.A.stop();
	        Button.waitForAnyPress();
	    }
}
