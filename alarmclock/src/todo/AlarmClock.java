package todo;
import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

public class AlarmClock extends Thread {

	private static ClockInput	input;
	private static ClockOutput	output;
	private static Semaphore	sem; 
	private static Clock 		clock;

	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		sem = input.getSemaphoreInstance();
		clock = new Clock(output);
	}

	// The AlarmClock thread is started by the simulator. No
	// need to start it by yourself, if you do you will get
	// an IllegalThreadStateException. The implementation
	// below is a simple alarmclock thread that beeps upon 
	// each keypress. To be modified in the lab.
	public void run() {
		Ticker ticker = new Ticker(clock);
		ticker.start();
		int time = 0;
		int prevChoice = 0;
		
		while (true) {
			sem.take();
			clock.alarmFlag(input.getAlarmFlag());
			if (clock.isBeeping()) {
				clock.alarmOff();
			}
			
			if (prevChoice == ClockInput.SET_TIME
					&& input.getChoice() != ClockInput.SET_TIME) {
				clock.setTime(time);
				prevChoice = input.getChoice();
			}
			
			if (input.getChoice() == ClockInput.SET_ALARM) {
				clock.setAlarmTime(input.getValue());
			}
			
			if (input.getChoice() == ClockInput.SET_TIME) {
				prevChoice = ClockInput.SET_TIME;
				time = input.getValue();
			}
			
		}
	}

}
