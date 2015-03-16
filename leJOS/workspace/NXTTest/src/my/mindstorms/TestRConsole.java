package my.mindstorms;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;

/**
 * example using RConsole
 */
public class TestRConsole {  
  public static void main(String[] args) {
    RConsole.open();
    RConsole.println("Start for loop ");
    for (int i = 0; i < 5; i++) {
      RConsole.print(" " + i);
      LCD.drawInt(i, 2, 2 * i, 4);
    }
    RConsole.println("\n done ");
    RConsole.close();
    Button.waitForAnyPress();
  }
}