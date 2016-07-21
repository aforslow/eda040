package lift;

import java.util.Random;

public class Person extends Thread {
	int initFloor;
	int targetFloor;
	Random rand;
	Monitor mon;
	LiftView view;
	
	Person(Monitor mon, LiftView view) {
		this.mon = mon;
		this.view = view;
	}
	
	

	public void run() {
		while (true) {
			int delay = 1000*(( int )( Math.random ()*46.0)); // sleep need milliseconds
			try {
				sleep(delay);
			} catch (Exception e) {
				
			}
			initFloor = rand.nextInt(7);
			while ((targetFloor=rand.nextInt(7)) == initFloor) continue;
			mon.liftAction(initFloor, targetFloor);
			//wait until
			//if mon.here == mon.next == initFloor
			//move in
			//DrawLevel
			//Travel
		}	
	}
}
