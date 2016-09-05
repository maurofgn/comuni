/*
 * Created on 5 ago 2016 ( Time 15:52:43 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package org.mf.business.service.mapping;

import org.mf.bean.Regione;
import org.mf.bean.jpa.RegioneEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

/**
 * Mapping between entity beans and display beans.
 */
@Service
public class RegioneServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public RegioneServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'RegioneEntity' to 'Regione'
	 * @param regioneEntity
	 */
	public Regione mapRegioneEntityToRegione(RegioneEntity regioneEntity) {
		if (regioneEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Regione regione = map(regioneEntity, Regione.class);

		return regione;
	}
	
	/**
	 * Mapping from 'Regione' to 'RegioneEntity'
	 * @param regione
	 * @param regioneEntity
	 */
	public void mapRegioneToRegioneEntity(Regione regione, RegioneEntity regioneEntity) {
		if(regione == null) {
			return;
		}

		//--- Generic mapping 
		map(regione, regioneEntity);

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}