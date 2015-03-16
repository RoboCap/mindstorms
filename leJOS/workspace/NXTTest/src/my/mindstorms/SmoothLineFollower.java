package my.mindstorms;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

/**
 * Złożny robot śledzący linię - z fazą kalibracji i parametrami
 */
public class SmoothLineFollower {

	/**
	 * Informacje o śledzonej linii
	 */
	private static class LineValues {
		public int range;
		public int mid;
		
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
	public SmoothLineFollower(float power, float gain) {
		super();
		light = new ColorSensor(SensorPort.S3);
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
		Motor.B.forward();
		Motor.C.backward();
		
		// i badaj podłoże
		while(Motor.B.getTachoCount() < 100) {
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
		LineValues result = new LineValues(max - min, (int)(max + min / 2));
		// wróć po zakończonej kalibracji na krawędź linii
		Motor.B.backward();
		Motor.C.forward();
		while (light.getLightValue() < result.mid); 
		
		return result;
	}
	
	
	public static void main(String[] aArg) throws Exception {
		LCD.drawString("Smooth LineFollower ", 0, 1);
		Button.waitForAnyPress();

		SmoothLineFollower lineFollower = new SmoothLineFollower(90, 100);
		lineFollower.follow(lineFollower.calibrate());
	}
}
