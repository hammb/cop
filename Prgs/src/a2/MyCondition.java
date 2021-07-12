package a2;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyCondition implements Condition{
	
	AtomicInteger counter;
	Lock lock;

	public MyCondition(Lock lock) {
		this.lock = lock;
		this.counter = new AtomicInteger(0);
	}
	
	public AtomicInteger getCounter() {
		return this.counter;
	}

	@Override
	public void await() throws InterruptedException {
		this.lock.wait();
		this.counter.addAndGet(1);
	}

	@Override
	public boolean await(long arg0, TimeUnit arg1) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long awaitNanos(long arg0) throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void awaitUninterruptibly() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean awaitUntil(Date arg0) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void signal() {
		this.lock.notify();
	}

	@Override
	public void signalAll() {
		this.lock.notifyAll();
	}

}
