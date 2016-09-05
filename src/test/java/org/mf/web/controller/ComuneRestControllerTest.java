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
import org.mf.bean.Comune;
import org.mf.bean.Provincia;
import org.mf.test.ComuneFactoryForTest;
import org.mf.test.ProvinciaFactoryForTest;

//--- Services 
import org.mf.business.service.ComuneService;
import org.mf.business.service.ProvinciaService;

//--- List Items 
import org.mf.web.listitem.ProvinciaListItem;

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
public class ComuneRestControllerTest {
	
	@InjectMocks
	private ComuneController comuneController;
	@Mock
	private ComuneService comuneService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ProvinciaService provinciaService; // Injected by Spring

	private ComuneFactoryForTest comuneFactoryForTest = new ComuneFactoryForTest();
	private ProvinciaFactoryForTest provinciaFactoryForTest = new ProvinciaFactoryForTest();

	List<Provincia> provincias = new ArrayList<Provincia>();

	private void givenPopulateModel() {
		Provincia provincia1 = provinciaFactoryForTest.newProvincia();
		Provincia provincia2 = provinciaFactoryForTest.newProvincia();
		List<Provincia> provincias = new ArrayList<Provincia>();
		provincias.add(provincia1);
		provincias.add(provincia2);
		when(provinciaService.findAll()).thenReturn(provincias);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Comune> list = new ArrayList<Comune>();
		when(comuneService.findAll()).thenReturn(list);
		
		// When
		String viewName = comuneController.list(model);
		
		// Then
		assertEquals("comune/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = comuneController.formForCreate(model);
		
		// Then
		assertEquals("comune/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Comune)modelMap.get("comune")).getComuneId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/comune/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProvinciaListItem> provinciaListItems = (List<ProvinciaListItem>) modelMap.get("listOfProvinciaItems");
		assertEquals(2, provinciaListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Comune comune = comuneFactoryForTest.newComune();
		Integer comuneId = comune.getComuneId();
		when(comuneService.findById(comuneId)).thenReturn(comune);
		
		// When
		String viewName = comuneController.formForUpdate(model, comuneId);
		
		// Then
		assertEquals("comune/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(comune, (Comune) modelMap.get("comune"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/comune/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProvinciaListItem> provinciaListItems = (List<ProvinciaListItem>) modelMap.get("listOfProvinciaItems");
		assertEquals(2, provinciaListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Comune comune = comuneFactoryForTest.newComune();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		when(comuneService.create(comune)).thenReturn(comune); 
		
		// When
		String viewName = comuneController.create(comune, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/comune/form/"+comune.getComuneId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(comune, (Comune) modelMap.get("comune"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Comune comune = comuneFactoryForTest.newComune();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = comuneController.create(comune, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("comune/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(comune, (Comune) modelMap.get("comune"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/comune/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProvinciaListItem> provinciaListItems = (List<ProvinciaListItem>) modelMap.get("listOfProvinciaItems");
		assertEquals(2, provinciaListItems.size());
		
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

		Comune comune = comuneFactoryForTest.newComune();
		
		Exception exception = new RuntimeException("test exception");
		when(comuneService.create(comune)).thenThrow(exception);
		
		// When
		String viewName = comuneController.create(comune, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("comune/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(comune, (Comune) modelMap.get("comune"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/comune/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "comune.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ProvinciaListItem> provinciaListItems = (List<ProvinciaListItem>) modelMap.get("listOfProvinciaItems");
		assertEquals(2, provinciaListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Comune comune = comuneFactoryForTest.newComune();
		Integer comuneId = comune.getComuneId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Comune comuneSaved = new Comune();
		comuneSaved.setComuneId(comuneId);
		when(comuneService.update(comune)).thenReturn(comuneSaved); 
		
		// When
		String viewName = comuneController.update(comune, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/comune/form/"+comune.getComuneId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(comuneSaved, (Comune) modelMap.get("comune"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Comune comune = comuneFactoryForTest.newComune();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = comuneController.update(comune, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("comune/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(comune, (Comune) modelMap.get("comune"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/comune/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProvinciaListItem> provinciaListItems = (List<ProvinciaListItem>) modelMap.get("listOfProvinciaItems");
		assertEquals(2, provinciaListItems.size());
		
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

		Comune comune = comuneFactoryForTest.newComune();
		
		Exception exception = new RuntimeException("test exception");
		when(comuneService.update(comune)).thenThrow(exception);
		
		// When
		String viewName = comuneController.update(comune, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("comune/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(comune, (Comune) modelMap.get("comune"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/comune/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "comune.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ProvinciaListItem> provinciaListItems = (List<ProvinciaListItem>) modelMap.get("listOfProvinciaItems");
		assertEquals(2, provinciaListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Comune comune = comuneFactoryForTest.newComune();
		Integer comuneId = comune.getComuneId();
		
		// When
		String viewName = comuneController.delete(redirectAttributes, comuneId);
		
		// Then
		verify(comuneService).delete(comuneId);
		assertEquals("redirect:/comune", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Comune comune = comuneFactoryForTest.newComune();
		Integer comuneId = comune.getComuneId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(comuneService).delete(comuneId);
		
		// When
		String viewName = comuneController.delete(redirectAttributes, comuneId);
		
		// Then
		verify(comuneService).delete(comuneId);
		assertEquals("redirect:/comune", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "comune.error.delete", exception);
	}
	
	
}
