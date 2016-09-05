package org.mf.test;

import org.mf.bean.Comune;

public class ComuneFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Comune newComune() {

		Integer comuneId = mockValues.nextInteger();

		Comune comune = new Comune();
		comune.setComuneId(comuneId);
		return comune;
	}
	
}
