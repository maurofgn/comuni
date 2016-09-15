package org.mf.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.mf.bean.Comune;
import org.mf.bean.Provincia;
import org.mf.bean.Regione;
import org.mf.business.service.ComuneService;
import org.mf.business.service.ProvinciaService;
import org.mf.business.service.RegioneService;
import org.mf.enu.Nose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

public class LoadComuni {
	
	/**
	 * 
	 * @param comuneService
	 * @param provinciaService
	 * @param regioneService
	 * @return comuni loaded
	 */
	@Autowired
	public int load(ComuneService comuneService, ProvinciaService provinciaService, RegioneService regioneService) {
		
		ClassPathResource resource = new ClassPathResource("comuni-italiani.csv");
		
		CSVFormat format = CSVFormat.RFC4180
			.withSkipHeaderRecord(true)
			.withHeader("Nome","Nose","regione","metropoli","provincia","Flag_capoluogo","Sigla_automobilistica","Codice_Catastale","Popolazione");
		
		Iterable<CSVRecord> csvParser = null;
		try {
			csvParser = CSVParser.parse(resource.getFile(), Charset.forName("Cp1252"), format);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		
		List<Regione> regions = new ArrayList<Regione>(20);
		
		Provincia prov = null;
		Regione regione = null;
		int count = 0;
		
		for (CSVRecord record : csvParser) {

		    String nome = record.get("Nome");
		    String nose = record.get("Nose");
		    String regioneName = record.get("regione");
		    String metropoli = record.get("metropoli");
		    String provincia = record.get("provincia");
	    	if ("-0".equals(provincia))
	    		provincia = metropoli;	//metropoli

		    boolean flagCapoluogo = "1".equals(record.get("Flag_capoluogo"));
		    String siglaAutomobilistica = record.get("Sigla_automobilistica");
		    String codiceCatastale = record.get("Codice_Catastale");
		    String popolazione = record.get("Popolazione");
		    
		    int popola = getInteger(popolazione.replaceAll("\\.", "").replaceAll(",", ""));
		    
	    	if (regione == null || !regione.getNome().equalsIgnoreCase(regioneName)) {
	    		regione = new Regione(regioneName, Nose.getNose(nose));
	    		regions.add(regione);
	    		regione = regioneService.create(regione);
	    	}
	    	if (prov == null || !prov.getNome().equalsIgnoreCase(provincia)) {
	    		prov = new Provincia(provincia, siglaAutomobilistica, regione);
	    		prov = provinciaService.create(prov);
	    	}
	    	Comune comune = new Comune(nome, popola, codiceCatastale, prov, flagCapoluogo);
	    	comune = comuneService.create(comune);
	    	if (flagCapoluogo) {
	    		prov.setCapoluogo(comune.getComuneId());
	    		provinciaService.save(prov);
	    	}
	    	count++;
		}
		
		return count;
	}
	
//	@Autowired
//	public void loadOld(ComuneService comuneService, ProvinciaService provinciaService, RegioneService regioneService) {
//		
//		List<Regione> regions = new ArrayList<Regione>(20);
//		
//		boolean excludeFirst = true;
//		Provincia prov = null;
//		Regione regione = null;
//		
//		ClassPathResource resource = new ClassPathResource("comuni-italiani.csv");
//		
//		Iterable<CSVRecord> records = null;
//		try {
//			Reader in = new FileReader(resource.getFile());
//			records = CSVFormat
//					.RFC4180.withHeader("Nome","Nose","regione","metropoli","provincia","Flag_capoluogo","Sigla_automobilistica","Codice_Catastale","Popolazione")
//					.parse(in);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		for (CSVRecord record : records) {
//			if (excludeFirst) {
//				excludeFirst = false;
//				continue;
//			}
//		    String nome = record.get("Nome");
//		    String nose = record.get("Nose");
//		    String regioneName = record.get("regione");
//		    String metropoli = record.get("metropoli");
//		    String provincia = record.get("provincia");
//	    	if ("-0".equals(provincia))
//	    		provincia = metropoli;	//metropoli
//
//		    boolean flagCapoluogo = "1".equals(record.get("Flag_capoluogo"));
//		    String siglaAutomobilistica = record.get("Sigla_automobilistica");
//		    String codiceCatastale = record.get("Codice_Catastale");
//		    String popolazione = record.get("Popolazione");
//		    
//		    int popola = getInteger(popolazione.replaceAll("\\.", "").replaceAll(",", ""));
//		    
//	    	if (regione == null || !regione.getNome().equalsIgnoreCase(regioneName)) {
//	    		regione = new Regione(regioneName, Nose.getNose(nose));
//	    		regions.add(regione);
//	    		regione = regioneService.create(regione);
//	    	}
//	    	if (prov == null || !prov.getNome().equalsIgnoreCase(provincia)) {
//	    		prov = new Provincia(provincia, siglaAutomobilistica, regione);
//	    		prov = provinciaService.create(prov);
//	    	}
//	    	Comune comune = new Comune(nome, popola, codiceCatastale, prov, flagCapoluogo);
//	    	comune = comuneService.create(comune);
//	    	if (flagCapoluogo) {
//	    		prov.setCapoluogo(comune.getComuneId());
//	    		provinciaService.save(prov);
//	    	}
//		}
//		
//	}
	
	public static Integer getInteger(String i) {
		
		Integer retValue;
		try {
			retValue = Integer.parseInt(i);
		} catch (Exception e) {
			retValue = 0;
		}
		return retValue;
	}
	
}
