
/*
 * Created on 5 ago 2016 ( Time 15:52:31 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package org.mf.mock;

import java.util.LinkedList;
import java.util.List;

import org.mf.bean.jpa.ComuneEntity;
import org.mf.test.MockValues;

public class ComuneEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public ComuneEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}

	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public ComuneEntity createInstance( Integer comuneId ) {
		ComuneEntity entity = createNewInstance();
		// Init Primary Key fields
		entity.setComuneId( comuneId) ;
		return entity ;
	}

	/**
	 * Creates an instance without Primary Key
	 * @param id1
	 * @return
	 */
	public ComuneEntity createNewInstance() {
		ComuneEntity entity = new ComuneEntity();
		// Init Data fields
		entity.setNome( mockValues.nextString(80) ) ; // java.lang.String 
		entity.setCodicecatastale( mockValues.nextString(4) ) ; // java.lang.String 
		entity.setAbitanti( mockValues.nextInteger() ) ; // java.lang.Integer 
		entity.setCapoluogo( mockValues.nextBoolean() ) ; // java.lang.Boolean 
		// Init Link fields (if any)
		// setProvincia( TODO ) ; // Provincia 
		return entity ;
	}

	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<ComuneEntity> createList(int count) {
		List<ComuneEntity> list = new LinkedList<ComuneEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
