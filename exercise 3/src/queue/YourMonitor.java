package queue;

import se.lth.cs.realtime.RTError;

class YourMonitor {
	private int nCounters;
	// Put your attributes here...
	private int nCustomers;
	private int nFreeClerks;
	private int lastCustomerArrived = 99;
	private int lastCustomerServed = 99;
	private boolean[] counterFree;
	private int lca;
	

	YourMonitor(int n) { 
		nCounters = n;
		// Initialize your attributes here...
		counterFree = new boolean[nCounters];
	}

	/**
	 * Return the next queue number in the intervall 0...99. 
	 * There is never more than 100 customers waiting.
	 */
	synchronized int customerArrived() { 
		// Implement this method...
		lastCustomerArrived = (lastCustomerArrived + 1) % 100;
		notifyAll();
		return lastCustomerArrived;
	}

	/**
	 * Register the clerk at counter id as free. Send a customer if any. 
	 */
	synchronized void clerkFree(int id) { 
		// Implement this method...
		if (!counterFree[id]) {
			counterFree[id] = true;
			nFreeClerks++;
			notifyAll();
		}
	}

	/**
	 * Wait for there to be a free clerk and a waiting customer, then
	 * return the cueue number of next customer to serve and the counter 
	 * number of the engaged clerk.
	 */
	synchronized DispData getDisplayData() throws InterruptedException { 
		// Implement this method...
		while (lastCustomerArrived == lastCustomerServed ||
				nFreeClerks == 0) wait();
		while (!counterFree[lca]) lca = (lca+1) % nCounters;
		DispData ans = new DispData();
		ans.counter = lca;
		counterFree[lca] = false;
		nFreeClerks--;
		ans.ticket = lastCustomerServed = (lastCustomerServed + 1) % 100;
		return ans;
	}
}
