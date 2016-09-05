package org.mf.test;

import org.mf.bean.Regione;

public class RegioneFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Regione newRegione() {

		Integer regioneId = mockValues.nextInteger();

		Regione regione = new Regione();
		regione.setRegioneId(regioneId);
		return regione;
	}
	
}
