package decorps.eventprocessor.vendors.dsi.programparameters;

public class Oscillator1FineTune extends AbstractProgramParameter implements
		ZeroTo100Range, Centered {

	public Oscillator1FineTune(int number, byte b) {
		super(number, b);
	}

}
