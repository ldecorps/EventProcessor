package decorps.eventprocessor.dsi;

public enum TetraParameters {
	Oscillator1Frequency(0), Oscillator2FineTune(1);

	TetraParameters(int parm) {
		this.parm = parm;
	}

	final public int parm;
}
