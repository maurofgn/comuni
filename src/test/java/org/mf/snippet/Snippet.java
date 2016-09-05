package org.mf.snippet;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.atLeastOnce;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mf.bean.Comune;
import org.mf.bean.Provincia;
import org.mf.bean.Regione;
import org.mf.business.service.impl.ComuneServiceImpl;
import org.mf.business.service.impl.ProvinciaServiceImpl;
import org.mf.business.service.impl.RegioneServiceImpl;
import org.mf.mock.ComuneMock;
import org.mf.mock.ProvinciaMock;
import org.mf.mock.RegioneMock;
import org.mf.util.LoadComuni;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class Snippet {

	@Mock
	private ComuneServiceImpl comuneService;
	@Mock
	private ProvinciaServiceImpl provinciaService;
	@Mock
	private RegioneServiceImpl regioneService;
	
	@Test
	public void loadComuni() throws Exception {
		
		Comune comune = new ComuneMock().createInstance();
		Provincia provincia = new ProvinciaMock().createInstance();
		Regione regione = new RegioneMock().createInstance();
		
//		doNothing().when(regioneService).create(any(Regione.class));
		when(regioneService.create(any(Regione.class))).thenReturn(regione);
		when(provinciaService.create(any(Provincia.class))).thenReturn(provincia);
		when(provinciaService.save(any(Provincia.class))).thenReturn(provincia);
		when(comuneService.create(any(Comune.class))).thenReturn(comune);
		
		LoadComuni load = new LoadComuni();
		int count = load.load(comuneService, provinciaService, regioneService);
		assertFalse("Comuni non caricati", count <= 0);
	}
}

