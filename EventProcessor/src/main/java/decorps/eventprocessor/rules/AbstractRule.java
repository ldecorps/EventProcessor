package decorps.eventprocessor.rules;

import decorps.eventprocessor.vendors.maps.DefaultMap;

public abstract class AbstractRule implements Rule {

	DefaultMap map;

	public AbstractRule() {
		super();
	}

	public void setMap(DefaultMap map) {
		this.map = map;
	}

	public DefaultMap getMap() {
		return map;
	}

}