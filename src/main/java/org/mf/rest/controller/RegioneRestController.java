/*
 * Created on 5 ago 2016 ( Time 15:52:43 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package org.mf.rest.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.mf.bean.Regione;
import org.mf.business.service.RegioneService;
import org.mf.web.listitem.RegioneListItem;

/**
 * Spring MVC controller for 'Regione' management.
 */
@Controller
public class RegioneRestController {

	@Resource
	private RegioneService regioneService;
	
	@RequestMapping( value="/items/regione",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<RegioneListItem> findAllAsListItems() {
		List<Regione> list = regioneService.findAll();
		List<RegioneListItem> items = new LinkedList<RegioneListItem>();
		for ( Regione regione : list ) {
			items.add(new RegioneListItem( regione ) );
		}
		return items;
	}
	
	@RequestMapping( value="/regione",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Regione> findAll() {
		return regioneService.findAll();
	}

	@RequestMapping( value="/regione/{regioneId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Regione findOne(@PathVariable("regioneId") Integer regioneId) {
		return regioneService.findById(regioneId);
	}
	
	@RequestMapping( value="/regione",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Regione create(@RequestBody Regione regione) {
		return regioneService.create(regione);
	}

	@RequestMapping( value="/regione/{regioneId}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Regione update(@PathVariable("regioneId") Integer regioneId, @RequestBody Regione regione) {
		return regioneService.update(regione);
	}

	@RequestMapping( value="/regione/{regioneId}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("regioneId") Integer regioneId) {
		regioneService.delete(regioneId);
	}
	
}
