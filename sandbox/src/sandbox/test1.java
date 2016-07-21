package sandbox;

public class test1 extends Thread{
	running r;
	
	test1(running runs) {
		r = runs;
	}
	public void run() {
		while (true) {
			try {
				sleep(3000);
			} catch (Exception e) {
				
			}
			r.printrun();
			r.running = false;
			System.out.println("afsafsafsafsagb");
		}
	}
}
