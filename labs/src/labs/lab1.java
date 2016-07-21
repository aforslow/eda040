package labs;

//import java.util.concurrent.Semaphore;
import se.lth.cs.realtime.semaphore.*;

public class lab1 {
	Semaphore sysoutlock;
	
	lab1() {
		sysoutlock = new MutexSem();
	}
	
	public void print(String message) {
		sysoutlock.take();
		System.out.println(message);
		System.out.println(message + "2");
		sysoutlock.give();
	}
	
	public static void main(String[] args) {
		
		lab1 l = new lab1();
		Thread t = new Thread(l);
		
		l.print("Hej");
		l.print2("OJ");
		
	}

}
