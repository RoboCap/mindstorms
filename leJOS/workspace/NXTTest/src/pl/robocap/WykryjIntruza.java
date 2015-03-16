package pl.robocap;

import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;

/**
 * Jeśli wykryje coś w swoim otoczeniu (określony dystans), zatrzymuje się i
 * wydaje dźwięk
 */
public class WykryjIntruza {

	public static void main(String[] args) {
		UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S4);
		while (true) {
			sonar.ping();
			if (sonar.getDistance() < 25) {
				Sound.playNote(Sound.FLUTE, Sound.C2, 500); 
			}
		}
	}

}
