package lift;

public class Lift extends Thread{
	Monitor mon;
	LiftView view;
	boolean moveUp;
	int next;
	
	Lift(Monitor mon, LiftView view) {
		this.mon = mon;
		this.view = view;
		moveUp = true;
		next = 0;
	}
	
	public void move() {
		if (moveUp) {
			next++;
			if (next == 6) {
				moveUp = false;
			}
		} else {
			next--;
			if (next == 0) {
				moveUp = true;
			}
		}
	}
	
	public void run() {
		while (true) {
			int tmp = next;
			move();
			mon.moveLift(next);
			view.moveLift(tmp, next);
		}
	}
}
