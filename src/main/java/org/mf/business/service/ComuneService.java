/*
 * Created on 1 ago 2016 ( Time 13:27:29 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package org.mf.business.service;

import java.util.List;

import org.mf.bean.Comune;
import org.mf.util.AutoCompleteData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Business Service Interface for entity Comune.
 */
public interface ComuneService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param comuneId
	 * @return entity
	 */
	Comune findById(Integer comuneId) ;

	/**
	 * Load the page request.
	 * @return Page
	 */
	Page<Comune> findAll(Pageable pageRequest);

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Comune> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Comune save(Comune entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Comune update(Comune entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Comune create(Comune entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param comuneId
	 */
	void delete(Integer comuneId);

	/**
	 * 
	 * @param term to use for select data
	 * @return
	 */
	List<AutoCompleteData> autoCompleteName(String term, Integer regione, Integer provincia);

	/**
	 * 
	 * @return total records in table
	 */
	Long count();

	/**
	 * 
	 * @param pageRequest
	 * @param nome
	 * @param provinciaId
	 * @param regioneId
	 * @return
	 */
	Page<Comune> findAll(PageRequest pageRequest, String nome, Integer provinciaId, Integer regioneId);

}
