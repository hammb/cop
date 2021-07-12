package a3;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class DiceGameScheduler {
	
	Integer num_games = 0;
	int max_num_games;
	DicePlayer[] dicePlayers;
	ConcurrentHashMap<Integer, Integer[]> gamesToBePlayed = new ConcurrentHashMap<>();
	ConcurrentHashMap<Integer, Integer> results;
	
	public DiceGameScheduler(int max_num_games, DicePlayer[] dicePlayers, ConcurrentHashMap<Integer, Integer> results) {
		this.max_num_games = max_num_games;
		this.dicePlayers = dicePlayers;
		int count = 0;
		for(int i = 0; i < dicePlayers.length; i ++) {
			for(int j = i + 1; j < dicePlayers.length; j ++) {
				if (j == i - 1 && i == dicePlayers.length - 1) {
					break;
				}
				Integer[] playersInGame = {i,j};
				gamesToBePlayed.put(count, playersInGame);
				count++;
			}
		}
		this.results = results;
		
	}
	
	public Integer ScheduleGames(CyclicBarrier barrier, Integer start) {
		int flag = 0;
		
		if (max_num_games + start == gamesToBePlayed.size()) {
			flag = 1;
		}
		
		if (max_num_games + start > gamesToBePlayed.size()) {
			max_num_games = max_num_games + start - gamesToBePlayed.size() - 1;
			flag = 1;
		}
		
		DiceGameThread[] games = new DiceGameThread[max_num_games];
		
		Integer[] players;
		Integer player1;
		Integer player2;
		
		for(int i = 0; i < max_num_games; i++) {
			
			/*
			if(num_games == max_num_games) {
				max_num_games = i;
				break;
			}*/
			
			players = gamesToBePlayed.get(num_games);
			
			player1 = players[0];
			player2 = players[1];
			
			games[i] = new DiceGameThread(dicePlayers[player1], dicePlayers[player2], results, barrier);
			num_games++;
		}
		
		for(int i = 0; i < max_num_games; i++) {
			games[i].start();
		}
		/*
		for(int i = 0; i < max_num_games; i++) {
			try {
				games[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		if (flag == 1) {
			return null;
		}
		
		return num_games;
	}

}
