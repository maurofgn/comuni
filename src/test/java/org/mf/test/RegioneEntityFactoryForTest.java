package org.mf.test;

import org.mf.bean.jpa.RegioneEntity;

public class RegioneEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public RegioneEntity newRegioneEntity() {

		Integer regioneId = mockValues.nextInteger();

		RegioneEntity regioneEntity = new RegioneEntity();
		regioneEntity.setRegioneId(regioneId);
		return regioneEntity;
	}
	
}
