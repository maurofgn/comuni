
/*
 * Created on 29 ago 2016 ( Time 11:04:35 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package org.mf.mock;

import java.util.LinkedList;
import java.util.List;

import org.mf.bean.Regione;
import org.mf.enu.Nose;
import org.mf.test.MockValues;

public class RegioneMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public Regione createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}

	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public Regione createInstance( Integer regioneId ) {
		Regione regione = createNewInstance();
		// Init Primary Key fields
		regione.setRegioneId( regioneId) ;
		return regione;
	}

	/**
	 * Creates an instance without Primary Key
	 * @param id1
	 * @return
	 */
	public Regione createNewInstance() {
		Regione regione = new Regione();
		// Init Data fields
		regione.setNome( mockValues.nextString(80) ) ; 	// java.lang.String 
		regione.setNose( Nose.Centro) ; 				// java.lang.String 
		// Init Link fields (if any)
		// setListOfProvincia( TODO ) ; // List<Provincia> 
		return regione;
	}

	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<Regione> createList(int count) {
		List<Regione> list = new LinkedList<Regione>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}