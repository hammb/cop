package a7;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.AkkaDispatcherReplace;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import akka.routing.RouterConfig;

public class AkkaCharacterRace extends AbstractActor {

	public enum Msg{STOP}
	final int numOfChars = 122;
	final int toOccur = 100;
	ConcurrentHashMap<String, Integer> occurences = new ConcurrentHashMap<String, Integer>();
	
	@Override
	public void preStart() throws Exception {
		RouterConfig router = new RoundRobinPool(8);
		Props dprops = CharacterGenerator.props().withDispatcher("akka.actor.my-dispatcher");
		Props rprops = dprops.withRouter(router);
		final ActorRef generator = getContext().actorOf(rprops,"generator");
		for(int j = 0; j < 100000; ++j) {
		for (int i = 97; i < numOfChars; i++) {
			CharacterToGen gen = new CharacterToGen((char)i);
			generator.tell(gen, getSelf());
		}
		}
	}
	
	private boolean CheckOccurence(int toOccur,String key)
	{
		Integer currentOccurence = occurences.get(key);
		if ( currentOccurence == toOccur )
		{	
			System.out.format("key: %s kam 100mal vor\n",key);
			getContext().stop(getSelf());
			return true;
		}
		return false;
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(CharacterGenerated.class, msg ->{
					String value = String.valueOf(msg.getValue());
					if(occurences.containsKey(value))
					{
						Integer currentValue = occurences.get(value);
						occurences.put(value,(currentValue + 1));
					}
					else {
						occurences.putIfAbsent(value, 1);
					}
					CheckOccurence(toOccur, value);
				})
				.build();
	}
	
	public static Props props() {
	    return Props.create(AkkaCharacterRace.class);
	  }
	
	public static void main(String[] args) {
		File confFile = new File("application-dispatcher.conf");
	    Config akkaConf = ConfigFactory.parseFile(confFile);
	    ActorSystem system = 
	    ActorSystem.create("AkkaDispatcherReplaceSystem", 
	                 akkaConf);
	    ActorRef actorRef = 
	      system.actorOf(AkkaCharacterRace.props(), 
	               "AkkaCharacterRace");
	    try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // should suffice for 20 workers
	    System.out.println("stop");
	    system.terminate();
	}
	
}
