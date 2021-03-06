/*
 * Created on 1 ago 2016 ( Time 13:27:29 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package org.mf.business.service.mapping;

import org.mf.bean.Comune;
import org.mf.bean.jpa.ComuneEntity;
import org.mf.bean.jpa.ProvinciaEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

/**
 * Mapping between entity beans and display beans.
 */
@Service
public class ComuneServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public ComuneServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'ComuneEntity' to 'Comune'
	 * @param comuneEntity
	 */
	public Comune mapComuneEntityToComune(ComuneEntity comuneEntity) {
		if (comuneEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Comune comune = map(comuneEntity, Comune.class);

		//--- Link mapping ( link to Provincia )
		if(comuneEntity.getProvincia() != null) {
			comune.setProvinciaId(comuneEntity.getProvincia().getProvinciaId());
			comune.setSiglaProv(comuneEntity.getProvincia().getSigla());
		}
		return comune;
	}
	
	/**
	 * Mapping from 'Comune' to 'ComuneEntity'
	 * @param comune
	 * @param comuneEntity
	 */
	public void mapComuneToComuneEntity(Comune comune, ComuneEntity comuneEntity) {
		if(comune == null) {
			return;
		}

		//--- Generic mapping 
		map(comune, comuneEntity);

		//--- Link mapping ( link : comune )
		if( hasLinkToProvincia(comune) ) {
			ProvinciaEntity provincia1 = new ProvinciaEntity();
			provincia1.setProvinciaId( comune.getProvinciaId() );
			comuneEntity.setProvincia( provincia1 );
		} else {
			comuneEntity.setProvincia( null );
		}

	}
	
	/**
	 * Verify that Provincia id is valid.
	 * @param Provincia Provincia
	 * @return boolean
	 */
	private boolean hasLinkToProvincia(Comune comune) {
		if(comune.getProvinciaId() != null) {
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