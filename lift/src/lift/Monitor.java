package lift;

public class Monitor {
	int here; 			// If here !=next , here (floor number) tells from which floor
						// the lift is moving and next to which floor it is moving.
	int next; 			// If here ==next , the lift is standing still on the floor
						// given by here.
	int [] waitEntry ;	// The number of persons waiting to enter the lift at the
						// various floors.
	int [] waitExit; 	// The number of persons (inside the lift) waiting to leave
					 	// the lift at the various floors.
	int load; 			// The number of people currently occupying the lift.
	
	LiftView view;
	
	Monitor(LiftView view) {
		waitEntry 	= new int[7];
		waitExit 	= new int[7];
		this.view 	= view;
	}

	public synchronized void moveLift (int next) {
		here = this.next;
		notifyAll();
		while (waitExit[here] > 0 || (waitEntry[here] > 0 && load < 4)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.next = next;
		notifyAll();
	}
	
	private boolean canEnter (int floor) {
		return load < 4 && next == here && floor == here;
	}
	
	synchronized public void liftAction (int initFloor, int targetFloor) {
		waitEntry[initFloor]++;
		view.drawLevel(initFloor, waitEntry[initFloor]);
		while (!canEnter(targetFloor)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		load++;
		waitEntry[initFloor]--;
		waitExit[targetFloor]++;
		view.drawLevel(here, waitEntry[here]);
		view.drawLift(here, load);
		notifyAll();
		while (here != targetFloor) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		exitLift();
	}
	
	public void exitLift() {
		waitExit[here]--;
		load--;
		view.drawLevel(here, load);
		notifyAll();
	}
}
