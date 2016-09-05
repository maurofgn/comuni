package org.mf.test;

import org.mf.bean.jpa.ProvinciaEntity;

public class ProvinciaEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ProvinciaEntity newProvinciaEntity() {

		Integer provinciaId = mockValues.nextInteger();

		ProvinciaEntity provinciaEntity = new ProvinciaEntity();
		provinciaEntity.setProvinciaId(provinciaId);
		return provinciaEntity;
	}
	
}
