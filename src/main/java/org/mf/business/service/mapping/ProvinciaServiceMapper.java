/*
 * Created on 5 ago 2016 ( Time 15:52:43 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package org.mf.business.service.mapping;

import org.mf.bean.Provincia;
import org.mf.bean.jpa.ProvinciaEntity;
import org.mf.bean.jpa.RegioneEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

/**
 * Mapping between entity beans and display beans.
 */
@Service
public class ProvinciaServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public ProvinciaServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'ProvinciaEntity' to 'Provincia'
	 * @param provinciaEntity
	 */
	public Provincia mapProvinciaEntityToProvincia(ProvinciaEntity provinciaEntity) {
		if (provinciaEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Provincia provincia = map(provinciaEntity, Provincia.class);

		//--- Link mapping ( link to Regione )
		if(provinciaEntity.getRegione() != null) {
			provincia.setRegioneId(provinciaEntity.getRegione().getRegioneId());
		}
		return provincia;
	}
	
	/**
	 * Mapping from 'Provincia' to 'ProvinciaEntity'
	 * @param provincia
	 * @param provinciaEntity
	 */
	public void mapProvinciaToProvinciaEntity(Provincia provincia, ProvinciaEntity provinciaEntity) {
		if(provincia == null) {
			return;
		}

		//--- Generic mapping 
		map(provincia, provinciaEntity);

		//--- Link mapping ( link : provincia )
		if( hasLinkToRegione(provincia) ) {
			RegioneEntity regione1 = new RegioneEntity();
			regione1.setRegioneId( provincia.getRegioneId() );
			provinciaEntity.setRegione( regione1 );
		} else {
			provinciaEntity.setRegione( null );
		}

	}
	
	/**
	 * Verify that Regione id is valid.
	 * @param Regione Regione
	 * @return boolean
	 */
	private boolean hasLinkToRegione(Provincia provincia) {
		if(provincia.getRegioneId() != null) {
			return true;
		}
		return false;
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