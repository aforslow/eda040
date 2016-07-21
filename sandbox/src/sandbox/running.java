package sandbox;

public class running {
	boolean running;
	
	public synchronized void state(boolean flag){
		running = flag;
		notifyAll();
	}
	
	public synchronized void printrun() {
		while (!running) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("running");
		notifyAll();
	}
	
	public static void main(String[] args) {
		System.out.println("dfsafasmnb");
		running r = new running();
		test1 pr = new test1(r);
		test2 pr2 = new test2(r);
		pr.start();
		pr2.start();
	}
}
