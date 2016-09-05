package org.mf.enu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Nose {
	
	Centro("Centro"), Isole("Isole"), Nord_est("Nord-est"), Nord_ovest("Nord-ovest"), Sud("Sud");
	
	private String value;
	
	private Nose(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static Nose getNose(String s) {
		if (s.equalsIgnoreCase(Centro.getValue()))
			return Centro;
		else if (s.equalsIgnoreCase(Isole.getValue()))
			return Isole;
		else if (s.equalsIgnoreCase(Nord_est.getValue()))
			return Nord_est;
		else if (s.equalsIgnoreCase(Nord_ovest.getValue()))
			return Nord_ovest;
		else if (s.equalsIgnoreCase(Sud.getValue()))
			return Sud;
		else 
			return null;
	}
	
	private static Nose[] vals = values();
	
    public Nose next() {
        return vals[(this.ordinal()+1) % vals.length];
    }
    
    private static final List<Nose> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    
	private static final int SIZE = VALUES.size();
	  
	private static final Random RANDOM = new Random();
	
	public static Nose random()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
}
