package pl.robocap;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

/**
 * Wszytskie czesci robota patrolujacego zlozone razem
 */
public class RobotStroz {

	// pilot do sterowania ruchu robota
	static DifferentialPilot pilot = new DifferentialPilot(3.5f, 19f, Motor.B, Motor.C);

	static class ZachowanieRobotaWCzasiePatrolu implements Behavior {
		private boolean czyZatrzymacPatrolowanie;

		public boolean takeControl() {
			return true; // to nasza podstawowa czynnosc.
		}

		public void suppress() {
			czyZatrzymacPatrolowanie = true;
		}

		public void action() {
			czyZatrzymacPatrolowanie = false;
			// jedź po prostej
			RobotStroz.pilot.travel(40, true);

			while (!czyZatrzymacPatrolowanie && RobotStroz.pilot.isMoving()) {
				Thread.yield(); // czekaj aż dojedziesz
			}
			
			// albo dojechałeś, albo zaczął się atak: zatrzymaj silniki
			RobotStroz.pilot.stop();
			
			// jeśli nie było akcji ataku, to możesz zawrócić
			if (!czyZatrzymacPatrolowanie) {
				RobotStroz.pilot.rotate(180);
			} 
		}
	}
	
	static class ZachowanieRobotaWCzasieAtaku implements Behavior {
		private UltrasonicSensor sonar  = new UltrasonicSensor(SensorPort.S4);

		public boolean takeControl() {
			// rozpocznij akcje ataku gdy intruz w zasiegu
			sonar.ping();
			return sonar.getDistance() < 25;
		}

		public void suppress() {
		}

		public void action() {
			ColorSensor armata = new ColorSensor(SensorPort.S3);

			// sprawdz czy wykrywasz intruza
			sonar.ping();
			if (sonar.getDistance() < 25) {
				// jesli TAK: ostrzez dzwiekiem
				Sound.playNote(Sound.FLUTE, Sound.C2, 500);
				// poczekaj 3 sekundy
				Delay.msDelay(3000);
				// sprawdz czy nadal wykrywasz intruza				
				sonar.ping();
				// jesli TAK: ostrzelaj dzialkiem
				if (sonar.getDistance() < 25) {
					armata.setFloodlight(true);
				}
			} else {
				// jesli NIE: wylacz dzialko
				armata.setFloodlight(false);
			}
		}

	}

	/**
	 * glowny blok programu
	 */
	public static void main(String[] args) {
		// przygotuj akcje robota
		Behavior akcjaPatrolowania = new ZachowanieRobotaWCzasiePatrolu();	
		Behavior akcjaAtaku = new ZachowanieRobotaWCzasieAtaku();
		Behavior[] akcjeRobota = { akcjaPatrolowania, akcjaAtaku };
		// specjalny koordynator akcji
		Arbitrator arbitrator = new Arbitrator(akcjeRobota);
		
		// wyświetl informację
		LCD.drawString("Robot Stroz gotowy!", 0, 1);
		// poczekaj na naciśnięcie jakiegokolwiek przycisku
		Button.waitForAnyPress();
		
		// rozpocznij akcje
		arbitrator.start();
	}
}


