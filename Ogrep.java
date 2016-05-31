package ogrep;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Ogrep {

	public static void main(String[] args) {
		Scanner sc2 = null, s2 = null;
		String s;
	    try {
	    	sc2 = new Scanner(new File("C:/Users/krzysztof.urbanczyk/Documents/Murex/TREREP/LocalFX/UM_INTF_TREREP_IRD_3_E.sql"));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    }
	    while (sc2.hasNextLine()) {
	            s2 = new Scanner(sc2.nextLine());
	        while (s2.hasNext()) {
	            s = s2.next();
	            if(s.toUpperCase().endsWith("_REP")){
	            	System.out.println(s);
	            }
	        }
	    }

	}

}
