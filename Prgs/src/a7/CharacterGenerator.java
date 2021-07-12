package a7;

import java.util.Random;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class CharacterGenerator extends AbstractActor {
	final static Random random = new Random();
	
	public static Props props() {
	    return Props.create(CharacterGenerator.class);
	  }
	
	@Override
	public Receive createReceive() 
	{
		return receiveBuilder()
				.match(CharacterToGen.class, chars -> {
					long val = random.nextLong()%20;
					if(val < 0) {
						val = val * (-1);
					}
					//System.out.format("Chargen: %c sleep:%d \n", chars.value,val);
					Thread.sleep(val);
					CharacterGenerated charGen = new CharacterGenerated(chars.value);
					getSender().tell(charGen, getSelf());
				})
				.build();
	}

}
