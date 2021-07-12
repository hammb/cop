package javab1;

import static org.junit.Assert.*;

import org.junit.Test;




public class HelloTest {
	
	@Test
	public void test() {
		Hello HelloMessage = new Hello("Hallo Java Welt");
		assertEquals("Hallo Java Welt", HelloMessage.getLastPrintedText());
	}

}
