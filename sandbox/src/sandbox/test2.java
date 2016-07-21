package sandbox;

public class test2 extends Thread{
	running r;
	
	test2(running runs) {
		r = runs;
	}
	
	public void run() {
		while (true) {
			try {
				sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			r.state(true);
//			notifyAll();
			System.out.println("test2");
		}
	}
}
