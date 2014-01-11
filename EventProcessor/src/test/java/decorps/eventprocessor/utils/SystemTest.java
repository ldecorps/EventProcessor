package decorps.eventprocessor.utils;

import java.util.Enumeration;
import java.util.Properties;

import org.junit.Test;

public class SystemTest {
	@Test
	public void printJavaVariables() throws Exception {
		Properties props = System.getProperties();
		Enumeration<?> e = props.propertyNames();

		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			System.out.println(key + " -- " + props.getProperty(key));
		}
	}
}
