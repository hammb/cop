package a1;


public class ConsumerThread extends Thread {
	
	Consumer consumer;
	boolean work = true;
	
	public ConsumerThread(Consumer consumer) {
		this.consumer = consumer;
	}

	@Override
	public void run() {
		long helper = this.consumer.getNextNumber();
		
		while(helper != 0 && this.work && !isInterrupted()) {
			this.consumer.sum(helper);
			helper = this.consumer.getNextNumber();
		}
	}

	public void quit() {
		this.work = false;
		this.interrupt();
	}
}