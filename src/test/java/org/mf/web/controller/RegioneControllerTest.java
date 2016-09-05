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
import org.mf.bean.Regione;
import org.mf.test.RegioneFactoryForTest;

//--- Services 
import org.mf.business.service.RegioneService;


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
public class RegioneControllerTest {
	
	@InjectMocks
	private RegioneController regioneController;
	@Mock
	private RegioneService regioneService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private RegioneFactoryForTest regioneFactoryForTest = new RegioneFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Regione> list = new ArrayList<Regione>();
		when(regioneService.findAll()).thenReturn(list);
		
		// When
		String viewName = regioneController.list(model);
		
		// Then
		assertEquals("regione/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = regioneController.formForCreate(model);
		
		// Then
		assertEquals("regione/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Regione)modelMap.get("regione")).getRegioneId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/regione/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Regione regione = regioneFactoryForTest.newRegione();
		Integer regioneId = regione.getRegioneId();
		when(regioneService.findById(regioneId)).thenReturn(regione);
		
		// When
		String viewName = regioneController.formForUpdate(model, regioneId);
		
		// Then
		assertEquals("regione/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(regione, (Regione) modelMap.get("regione"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/regione/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Regione regione = regioneFactoryForTest.newRegione();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		when(regioneService.create(regione)).thenReturn(regione); 
		
		// When
		String viewName = regioneController.create(regione, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/regione/form/"+regione.getRegioneId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(regione, (Regione) modelMap.get("regione"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Regione regione = regioneFactoryForTest.newRegione();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = regioneController.create(regione, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("regione/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(regione, (Regione) modelMap.get("regione"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/regione/create", modelMap.get("saveAction"));
		
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

		Regione regione = regioneFactoryForTest.newRegione();
		
		Exception exception = new RuntimeException("test exception");
		when(regioneService.create(regione)).thenThrow(exception);
		
		// When
		String viewName = regioneController.create(regione, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("regione/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(regione, (Regione) modelMap.get("regione"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/regione/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "regione.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Regione regione = regioneFactoryForTest.newRegione();
		Integer regioneId = regione.getRegioneId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Regione regioneSaved = new Regione();
		regioneSaved.setRegioneId(regioneId);
		when(regioneService.update(regione)).thenReturn(regioneSaved); 
		
		// When
		String viewName = regioneController.update(regione, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/regione/form/"+regione.getRegioneId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(regioneSaved, (Regione) modelMap.get("regione"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Regione regione = regioneFactoryForTest.newRegione();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = regioneController.update(regione, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("regione/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(regione, (Regione) modelMap.get("regione"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/regione/update", modelMap.get("saveAction"));
		
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

		Regione regione = regioneFactoryForTest.newRegione();
		
		Exception exception = new RuntimeException("test exception");
		when(regioneService.update(regione)).thenThrow(exception);
		
		// When
		String viewName = regioneController.update(regione, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("regione/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(regione, (Regione) modelMap.get("regione"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/regione/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "regione.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Regione regione = regioneFactoryForTest.newRegione();
		Integer regioneId = regione.getRegioneId();
		
		// When
		String viewName = regioneController.delete(redirectAttributes, regioneId);
		
		// Then
		verify(regioneService).delete(regioneId);
		assertEquals("redirect:/regione", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Regione regione = regioneFactoryForTest.newRegione();
		Integer regioneId = regione.getRegioneId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(regioneService).delete(regioneId);
		
		// When
		String viewName = regioneController.delete(redirectAttributes, regioneId);
		
		// Then
		verify(regioneService).delete(regioneId);
		assertEquals("redirect:/regione", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "regione.error.delete", exception);
	}
	
	
}
