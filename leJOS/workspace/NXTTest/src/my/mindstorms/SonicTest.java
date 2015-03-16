package my.mindstorms;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

/**
 * Test czujnika odległości
 */
public class SonicTest {

	  public static void main(String[] args) throws Exception {
		    UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);

		    while (!Button.ESCAPE.isDown()) {
		      LCD.drawString(sonic.getVendorID(), 0, 0);
		      LCD.drawString(sonic.getProductID(), 0, 1);
		      LCD.drawString(sonic.getVersion(), 0, 2);
		      sonic.ping();
		      LCD.drawInt(sonic.getDistance(), 0, 3);
		    }
		  }

}
