package pl.robocap;

import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

/**
 * Jeśli przeszkoda (intruz) nie wycofał się po 3 sekundach, robot rozpoczyna
 * ostrzał laserowy
 */
public class OstrzelajIntruza {

	public static void main(String[] args) {
		UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S4);
		ColorSensor armata = new ColorSensor(SensorPort.S3);
		armata.setFloodlight(false);

		while (true) {
			sonar.ping();
			if (sonar.getDistance() < 25) {
				Delay.msDelay(3000);
				sonar.ping();
				if (sonar.getDistance() < 25) {
					armata.setFloodlight(true);
				}
			}
			else {
				armata.setFloodlight(false);
			}
				
		}
	}

}
