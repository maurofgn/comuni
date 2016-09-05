package org.mf.test;

import org.mf.bean.Provincia;

public class ProvinciaFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Provincia newProvincia() {

		Integer provinciaId = mockValues.nextInteger();

		Provincia provincia = new Provincia();
		provincia.setProvinciaId(provinciaId);
		return provincia;
	}
	
}
