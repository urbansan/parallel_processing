package containingLines;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Containig {

	public static void main(String[] args) throws IOException {
		String chroot = "C:/Users/krzysztof.urbanczyk/Documents/Murex/Reporting fixes/kozi_sap/output/";
//						 C:\Users\krzysztof.urbanczyk\Documents\Murex\Reporting fixes\kozi_sap\output
		String output = chroot + "output2.txt";
		String inputList = chroot + "phrases.txt";
		String inputReport = chroot + "kozi_sap_pl_deriv_20160129.txt_";
		
		Set <String> Szukane = getPhrasesFromFile(inputList);
		
		markLinesContainingPhrase(Szukane, inputReport, output);
		

	}


	
	static void markLinesContainingPhrase(Set <String> Szukane, String reportName, String output) throws IOException{
		String s;
		
		PrintWriter doZapisania = new PrintWriter(output, "UTF-8");
		FileReader fr = new FileReader(reportName);
		Scanner plik = new Scanner(fr);
		Iterator<String> itr;
		String phrase;
		int counter = 0;
		int line = 0;
		String marker;

		// usuwanie przecinkow
//		PrintWriter doZapisania = new PrintWriter(output, "UTF-8");

		while(plik.hasNextLine()) {
			s = plik.nextLine();
			itr = Szukane.iterator();
			++line;
			marker = "";
			while(itr.hasNext()){
				phrase = itr.next();

			try{
				if(s.contains(phrase)){	
					counter++;
//					marker = marker + " --%markCount: " + counter + ", phrase: " + phrase;
//					System.out.println("Line: " + line + ", phrase: " + phrase + ", count: " + counter); // wyswietla co wyciaga
//					System.out.println("Start_"+s.substring(start, end).trim()+"_koniec");
					doZapisania.println(s);
				}
			}catch(Exception e){
				System.out.println("DUD:" + line);
				continue;
				
			}
			

//			System.out.println(Szukane[i]);
			}
//			doZapisania.println(s + marker);
			
		}
		
		System.out.println(">> " + counter + " phrases found");
//		doZapisania.close();
		plik.close();
		doZapisania.close();
	}
	
	
	
	
	
	
	static Set <String>  getPhrasesFromFile(String inputPath) throws IOException{
		Set <String> forReturn = new HashSet<>();
	
		FileReader reader = new FileReader(inputPath);
		Scanner file = new Scanner(reader);
		
		while (file.hasNextLine()) {
		forReturn.add(file.nextLine().trim());
		}
		System.out.println(">> input file loaded");
		
		file.close();
		return forReturn;
	}
	
}
