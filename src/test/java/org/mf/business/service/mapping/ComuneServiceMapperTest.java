/*
 * Created on 5 ago 2016 ( Time 15:52:31 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package org.mf.business.service.mapping;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.mf.bean.Comune;
import org.mf.bean.jpa.ComuneEntity;
import org.mf.bean.jpa.ProvinciaEntity;
import org.mf.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class ComuneServiceMapperTest {

	private ComuneServiceMapper comuneServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		comuneServiceMapper = new ComuneServiceMapper();
		comuneServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'ComuneEntity' to 'Comune'
	 * @param comuneEntity
	 */
	@Test
	public void testMapComuneEntityToComune() {
		// Given
		ComuneEntity comuneEntity = new ComuneEntity();
		comuneEntity.setNome(mockValues.nextString(80));
		comuneEntity.setCodicecatastale(mockValues.nextString(4));
		comuneEntity.setAbitanti(mockValues.nextInteger());
		comuneEntity.setCapoluogo(mockValues.nextBoolean());
		comuneEntity.setProvincia(new ProvinciaEntity());
		comuneEntity.getProvincia().setProvinciaId(mockValues.nextInteger());
		
		// When
		Comune comune = comuneServiceMapper.mapComuneEntityToComune(comuneEntity);
		
		// Then
		assertEquals(comuneEntity.getNome(), comune.getNome());
		assertEquals(comuneEntity.getCodicecatastale(), comune.getCodicecatastale());
		assertEquals(comuneEntity.getAbitanti(), comune.getAbitanti());
		assertEquals(comuneEntity.getCapoluogo(), comune.getCapoluogo());
		assertEquals(comuneEntity.getProvincia().getProvinciaId(), comune.getProvinciaId());
	}
	
	/**
	 * Test : Mapping from 'Comune' to 'ComuneEntity'
	 */
	@Test
	public void testMapComuneToComuneEntity() {
		// Given
		Comune comune = new Comune();
		comune.setNome(mockValues.nextString(80));
		comune.setCodicecatastale(mockValues.nextString(4));
		comune.setAbitanti(mockValues.nextInteger());
		comune.setCapoluogo(mockValues.nextBoolean());
		comune.setProvinciaId(mockValues.nextInteger());

		ComuneEntity comuneEntity = new ComuneEntity();
		
		// When
		comuneServiceMapper.mapComuneToComuneEntity(comune, comuneEntity);
		
		// Then
		assertEquals(comune.getNome(), comuneEntity.getNome());
		assertEquals(comune.getCodicecatastale(), comuneEntity.getCodicecatastale());
		assertEquals(comune.getAbitanti(), comuneEntity.getAbitanti());
		assertEquals(comune.getCapoluogo(), comuneEntity.getCapoluogo());
		assertEquals(comune.getProvinciaId(), comuneEntity.getProvincia().getProvinciaId());
	}

}