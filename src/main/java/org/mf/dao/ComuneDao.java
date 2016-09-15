package org.mf.dao;

import java.util.List;

import org.mf.bean.jpa.ComuneEntity;

public interface ComuneDao {

	/**
	 * 
	 * @param start
	 * @param length
	 * @param regione
	 * @param provincia
	 * @param nomeCitta
	 * @return
	 */
	List<ComuneEntity> retrieveByProvRegion(Integer start, Integer length, Integer regione, Integer provincia, String nomeCitta);

}
