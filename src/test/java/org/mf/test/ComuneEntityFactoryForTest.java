package org.mf.test;

import org.mf.bean.jpa.ComuneEntity;

public class ComuneEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ComuneEntity newComuneEntity() {

		Integer comuneId = mockValues.nextInteger();

		ComuneEntity comuneEntity = new ComuneEntity();
		comuneEntity.setComuneId(comuneId);
		return comuneEntity;
	}
	
}
