package buffer;

import se.lth.cs.realtime.semaphore.*;

/**
 * The buffer.
 */
class Buffer {
	Semaphore mutex; // For mutual exclusion blocking.
	Semaphore free; // For buffer full blocking.
	Semaphore avail; // For blocking when no data is available.
	final int size=8;
	String[] buffData; // The actual buffer.
	int nextToPut;
	int nextToGet;

	Buffer() {
		buffData = new String[size];
		mutex = new MutexSem();
		free = new CountingSem(size);
		avail = new CountingSem();
	}

	void putLine(String input) {
		free.take(); // Wait for buffer empty.
		mutex.take(); // Wait for exclusive access.
		buffData[nextToPut] = new String(input); // Store copy of object.
		if (++nextToPut >= size) nextToPut = 0;
		mutex.give(); // Allow others to access.
		avail.give(); // Allow others to get line.
	}

	String getLine() {
		// Exercise 2 ...
		// Here you should add code so that if the buffer is empty, the
		// calling process is delayed until a line becomes available.
		// A caller of putLine hanging on buffer full should be released.
		// ...
		avail.take(); // Wait for data available
		mutex.take();
		String ans = buffData[nextToGet]; // Get reference to data
		buffData[nextToGet] = null; 	  // Extra care
		if (++nextToGet >= size) nextToGet = 0;
		mutex.give();
		free.give();
		return ans;
	}
}
