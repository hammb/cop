package javab1;

public class Hello {
	
	private String lastPrintedText;
	
	public Hello(String printString) {
		System.out.println(printString);
		this.lastPrintedText = printString;
	}

	public String getLastPrintedText() {
		return lastPrintedText;
	}

	public void setLastPrintedText(String lastPrintedText) {
		this.lastPrintedText = lastPrintedText;
	}

}
