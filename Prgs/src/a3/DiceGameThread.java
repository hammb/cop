package a3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class DiceGameThread extends Thread{

	private DicePlayer player1;
	private DicePlayer player2;
	ConcurrentHashMap<Integer, Integer> results;
	CyclicBarrier barrier;
	
	public DiceGameThread(DicePlayer player1, DicePlayer player2, ConcurrentHashMap<Integer, Integer> results, CyclicBarrier barrier) {
		this.player1 = player1;
		this.player2 = player2;
		this.results = results;
		this.barrier = barrier;
	}
	
	//Eine Runde
	public void run() {
		int count = 0;
		for(int i = 0; i < 1000; i ++) {
			if(player1.getDice() > player2.getDice()) {
				count++;
			}else {
				count--;
			}
		}
		
		if(count > 0) {
			int wins = results.containsKey(player1.getId()) ? results.get(player1.getId()) : 0;
			results.put(player1.getId(), wins + 1);
		}else if (count < 0) {
			int wins = results.containsKey(player2.getId()) ? results.get(player2.getId()) : 0;
			results.put(player2.getId(), wins + 1);
		}else {
			int wins = results.containsKey(goldenDice().getId()) ? results.get(goldenDice().getId()) : 0;
			results.put(goldenDice().getId(), wins + 1);
		}
		
		try {
			this.barrier.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private DicePlayer goldenDice() {
		int count = 0;
		
		while(count == 0) {
			if(player1.getDice() > player2.getDice()) {
				count++;
			}else {
				count--;
			}
		}
		
		if(count > 0) {
			return player1;
		}else{
			return player2;
		}
	}
	
}
