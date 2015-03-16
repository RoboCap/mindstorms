package my.mindstorms;

import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;

/**
 * Prosty programik testujący sensor kolorów
 */
public class ColorTest {

	public static void main(String[] args) throws Exception {
		ColorSensor light = new ColorSensor(SensorPort.S3);
		light.setFloodlight(true);

		while (true) {
			LCD.drawInt(light.getLightValue(), 4, 0, 0);
			LCD.drawInt(light.getNormalizedLightValue(), 4, 0, 1);
			LCD.drawInt(SensorPort.S3.readRawValue(), 4, 0, 2);
			LCD.drawInt(SensorPort.S3.readValue(), 4, 0, 3);
		}
	}

}
