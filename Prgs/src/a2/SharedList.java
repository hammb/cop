package a2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import java.util.concurrent.locks.ReentrantLock;

public class SharedList {

	private Queue<Integer> queue;
	public ReentrantLock lock;
	private boolean running = true;

	public void setRunning(boolean running) {
		this.running = running;
	}

	public SharedList() {
		this.queue = new LinkedList<>();
		this.lock = new ReentrantLock();
	}

	public void add(char c) {
		synchronized (this.lock) {
			this.queue.add((int) c);
			this.lock.notify();
		}
	}

	public void countOccurrences(int num_threads, CharacterGeneratorThread threads[]) {

		(new Thread() {
			public void run() {
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				try {
					while (SharedList.this.running) {
						synchronized (lock) {
							try {
								while (SharedList.this.queue.isEmpty()) {
									lock.wait();
								}

								int key = (int) SharedList.this.queue.remove();
								
								if(map.containsKey(key)) {
									map.put(key, map.get(key) + 1);
								}else {
									map.put(key, 1);
								}
								
								if (map.get(key) > 10) {
									System.out.println((char) key);
									SharedList.this.setRunning(false);
								}

							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				} finally {
					for (int i = 0; i < num_threads; i++) {
						try {
							threads[i].join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

}
