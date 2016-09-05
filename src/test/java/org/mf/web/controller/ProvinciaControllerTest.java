package org.mf.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import org.mf.bean.Provincia;
import org.mf.bean.Regione;
import org.mf.test.ProvinciaFactoryForTest;
import org.mf.test.RegioneFactoryForTest;

//--- Services 
import org.mf.business.service.ProvinciaService;
import org.mf.business.service.RegioneService;

//--- List Items 
import org.mf.web.listitem.RegioneListItem;

import org.mf.web.common.Message;
import org.mf.web.common.MessageHelper;
import org.mf.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class ProvinciaControllerTest {
	
	@InjectMocks
	private ProvinciaController provinciaController;
	@Mock
	private ProvinciaService provinciaService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private RegioneService regioneService; // Injected by Spring

	private ProvinciaFactoryForTest provinciaFactoryForTest = new ProvinciaFactoryForTest();
	private RegioneFactoryForTest regioneFactoryForTest = new RegioneFactoryForTest();

	List<Regione> regiones = new ArrayList<Regione>();

	private void givenPopulateModel() {
		Regione regione1 = regioneFactoryForTest.newRegione();
		Regione regione2 = regioneFactoryForTest.newRegione();
		List<Regione> regiones = new ArrayList<Regione>();
		regiones.add(regione1);
		regiones.add(regione2);
		when(regioneService.findAll()).thenReturn(regiones);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Provincia> list = new ArrayList<Provincia>();
		when(provinciaService.findAll()).thenReturn(list);
		
		// When
		String viewName = provinciaController.list(model);
		
		// Then
		assertEquals("provincia/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = provinciaController.formForCreate(model);
		
		// Then
		assertEquals("provincia/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Provincia)modelMap.get("provincia")).getProvinciaId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/provincia/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<RegioneListItem> regioneListItems = (List<RegioneListItem>) modelMap.get("listOfRegioneItems");
		assertEquals(2, regioneListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Provincia provincia = provinciaFactoryForTest.newProvincia();
		Integer provinciaId = provincia.getProvinciaId();
		when(provinciaService.findById(provinciaId)).thenReturn(provincia);
		
		// When
		String viewName = provinciaController.formForUpdate(model, provinciaId);
		
		// Then
		assertEquals("provincia/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(provincia, (Provincia) modelMap.get("provincia"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/provincia/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<RegioneListItem> regioneListItems = (List<RegioneListItem>) modelMap.get("listOfRegioneItems");
		assertEquals(2, regioneListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Provincia provincia = provinciaFactoryForTest.newProvincia();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		when(provinciaService.create(provincia)).thenReturn(provincia); 
		
		// When
		String viewName = provinciaController.create(provincia, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/provincia/form/"+provincia.getProvinciaId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(provincia, (Provincia) modelMap.get("provincia"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Provincia provincia = provinciaFactoryForTest.newProvincia();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = provinciaController.create(provincia, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("provincia/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(provincia, (Provincia) modelMap.get("provincia"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/provincia/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<RegioneListItem> regioneListItems = (List<RegioneListItem>) modelMap.get("listOfRegioneItems");
		assertEquals(2, regioneListItems.size());
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Provincia provincia = provinciaFactoryForTest.newProvincia();
		
		Exception exception = new RuntimeException("test exception");
		when(provinciaService.create(provincia)).thenThrow(exception);
		
		// When
		String viewName = provinciaController.create(provincia, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("provincia/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(provincia, (Provincia) modelMap.get("provincia"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/provincia/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "provincia.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<RegioneListItem> regioneListItems = (List<RegioneListItem>) modelMap.get("listOfRegioneItems");
		assertEquals(2, regioneListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Provincia provincia = provinciaFactoryForTest.newProvincia();
		Integer provinciaId = provincia.getProvinciaId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Provincia provinciaSaved = new Provincia();
		provinciaSaved.setProvinciaId(provinciaId);
		when(provinciaService.update(provincia)).thenReturn(provinciaSaved); 
		
		// When
		String viewName = provinciaController.update(provincia, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/provincia/form/"+provincia.getProvinciaId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(provinciaSaved, (Provincia) modelMap.get("provincia"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Provincia provincia = provinciaFactoryForTest.newProvincia();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = provinciaController.update(provincia, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("provincia/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(provincia, (Provincia) modelMap.get("provincia"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/provincia/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<RegioneListItem> regioneListItems = (List<RegioneListItem>) modelMap.get("listOfRegioneItems");
		assertEquals(2, regioneListItems.size());
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Provincia provincia = provinciaFactoryForTest.newProvincia();
		
		Exception exception = new RuntimeException("test exception");
		when(provinciaService.update(provincia)).thenThrow(exception);
		
		// When
		String viewName = provinciaController.update(provincia, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("provincia/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(provincia, (Provincia) modelMap.get("provincia"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/provincia/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "provincia.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<RegioneListItem> regioneListItems = (List<RegioneListItem>) modelMap.get("listOfRegioneItems");
		assertEquals(2, regioneListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Provincia provincia = provinciaFactoryForTest.newProvincia();
		Integer provinciaId = provincia.getProvinciaId();
		
		// When
		String viewName = provinciaController.delete(redirectAttributes, provinciaId);
		
		// Then
		verify(provinciaService).delete(provinciaId);
		assertEquals("redirect:/provincia", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Provincia provincia = provinciaFactoryForTest.newProvincia();
		Integer provinciaId = provincia.getProvinciaId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(provinciaService).delete(provinciaId);
		
		// When
		String viewName = provinciaController.delete(redirectAttributes, provinciaId);
		
		// Then
		verify(provinciaService).delete(provinciaId);
		assertEquals("redirect:/provincia", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "provincia.error.delete", exception);
	}
	
	
}
