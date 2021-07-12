package a2;



public class MySemaphore {
	
	
	int max_num_threads;
	int num_alloced_threads;
	
	public MySemaphore(int limit) {
		this.max_num_threads = limit;
		this.num_alloced_threads = 0;
	}

	public synchronized void acquire() throws InterruptedException {
		while (this.max_num_threads == this.num_alloced_threads) {
			wait();
		}
		
		if (this.num_alloced_threads == 0) {
			notifyAll();
		}
		
		this.num_alloced_threads++;
	}

	public void acquireN(int n) throws InterruptedException {
		while (this.max_num_threads == this.num_alloced_threads) {
			wait();
		}
		
		if (this.num_alloced_threads == 0) {
			notifyAll();
		}
		
		this.num_alloced_threads += n;
	}

	public synchronized void release() throws InterruptedException {
		while(this.num_alloced_threads == 0) {
			wait();
		}
		
		if (this.num_alloced_threads == this.max_num_threads) {
			notifyAll();
		}
		this.num_alloced_threads--;
	}

}
