package my.mindstorms;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

/**
 * Złożny robot śledzący linię - z fazą kalibracji i parametrami
 * B - prawy
 * C - lewy
 */
public class SmoothLFollower {

	/**
	 * Informacje o śledzonej linii
	 */
	private static class LineValues {
		public float range;
		public float mid;
		
		public LineValues(int range, int mid) {
			this.range = range;
			this.mid = mid;
		}
		
	}
	
	private final ColorSensor light;
	
	/**
	 * bazowa prędkość na prostej
	 */
	private float power;
	/**
	 *  jak mocną korektę stosować przy przekraczaniu linii
	 */
	private float gain;
	
	/**
	 * Konstruktor
	 */
	public SmoothLFollower(float power, float gain) {
		super();
		light = new ColorSensor(SensorPort.S3);
		light.setFloodlight(true);
		this.power = power;
		this.gain = gain;
	}

	/**
	 * główna pętla śledzenia
	 */
	private void follow(LineValues line) {
		// jedź
		Motor.B.forward();
		Motor.C.forward();
		while(true) {
			// mierz światło
			int meassuredLight = light.getLightValue();
			// wylicz korekcję
			// korekcja dodatnia: robot wyjeżdża z linii
			// korekcja ujemna: robot wjeżdża na linię
			float correction = gain * ((meassuredLight - line.mid) / line.range);
//			RConsole.println("Mid:" + line.mid + ", Range:" + line.range + ", Corr:" + correction); // debug
			// i zastosuj na silnikach
			Motor.B.setSpeed(power + correction);
			Motor.C.setSpeed(power - correction);
		}
	}
	
	/**
	 * kalibracja światła na linii
	 */
	private LineValues calibrate() {
		int min = 100;
		int max = 0;
		
		// powoli
		Motor.B.setSpeed(power / 2);
		Motor.C.setSpeed(power / 2);
		
		// obracaj się w prawo
		Motor.B.backward();
		Motor.C.forward();
		
		// i badaj podłoże
		while(Motor.C.getTachoCount() < 200) {

			int meassuredLight = light.getLightValue();
			// znajdź najciemniejszy
			if (meassuredLight > max) {
				max = meassuredLight;
			}
			// i najjaśniejszy obszar
			if (meassuredLight < min) {
				min = meassuredLight;
			}
		}
		// wylicz zakres i średnią wartość
		LineValues result = new LineValues(max - min, (max + min) / 2);

		// wróć po zakończonej kalibracji na krawędź linii
		Motor.B.forward();
		Motor.C.backward();
		while (light.getLightValue() > result.mid); 
		
		return result;
	}
	
	
	public static void main(String[] aArg) throws Exception {
//	    RConsole.open();// debug

		LCD.drawString("Smooth LineFollower ", 0, 1);
		Button.waitForAnyPress();

		SmoothLFollower lineFollower = new SmoothLFollower(240, 300); // parametry nalezy dostosowac do toru
		lineFollower.follow(lineFollower.calibrate());
	}
}
