package a3;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class Dice {

	int num_players;
	int num_games_in_parallel;
	ConcurrentHashMap<Integer, Integer> results = new ConcurrentHashMap<>();

	public Dice(int num_players, int num_games_in_parallel) {
		this.num_players = num_players;
		this.num_games_in_parallel = num_games_in_parallel;
	}

	public void LudiIncipiant() throws InterruptedException {

		// Spieler erstellen
		DicePlayer[] dicePlayers = new DicePlayer[num_players];

		for (int i = 0; i < num_players; i++) {
			dicePlayers[i] = new DicePlayer(i, i);
		}

		DiceGameScheduler diceGameScheduler = new DiceGameScheduler(this.num_games_in_parallel, dicePlayers, results);

		Integer current = 0;

		while (current != null) {

			CyclicBarrier barrier = new CyclicBarrier(this.num_games_in_parallel, () -> {

			});

			current = diceGameScheduler.ScheduleGames(barrier, current);
		}

		int highest_value = 0;
		int highest_index = 0;

		Iterator it = results.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			if ((int) pair.getValue() > highest_value) {
				highest_value = (int) pair.getValue();
				highest_index = (int) pair.getKey();
			}

			it.remove();
		}

		System.out.println(highest_index + " won with " + highest_value + " Points");

	}

	public static void main(String[] args) throws InterruptedException {
		int num_players = 10;
		int num_games_in_parallel = 2;
		Dice diceGames = new Dice(num_players, num_games_in_parallel);
		diceGames.LudiIncipiant();
	}

}
