import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


public class line_extracter{

	


	static void getLine(Set <String> Szukane, String reportName, String output) throws IOException{
//	String current = new java.io.File(".").getCanonicalPath();
//	System.out.println("Current dir:" + current);
	
	String s;
	
	FileReader fr = new FileReader(reportName);
	Scanner plik = new Scanner(fr);
	Iterator<String> itr;
	String checker;
	int counter = 0;

	// usuwanie przecinkow
	PrintWriter doZapisania = new PrintWriter(output, "UTF-8");

	while (plik.hasNextLine()) {
		s = plik.nextLine();
		itr = Szukane.iterator();

		while(itr.hasNext()){
			checker = itr.next();
//			if(checker.equals(s.substring(997-8, 997))){			//trade number

			if(checker.equals(s.substring(1526-10,1526).trim())){		
				doZapisania.println(s);
				counter++;
				System.out.println(checker+", "); // wyswietla co wyciaga
			}
			
//		System.out.println(Szukane[i]);
		}
	}
	
	System.out.println(">> " + counter + " lines copied");
	doZapisania.close();
	plik.close();
	
	}
	
	
	static void getLine(Set <String> Szukane, int start, int end, String reportName, String output) throws IOException{
//		String current = new java.io.File(".").getCanonicalPath();
//		System.out.println("Current dir:" + current);
		
		String s;
		
		FileReader fr = new FileReader(reportName);
		Scanner plik = new Scanner(fr);
		Iterator<String> itr;
		String checker;
		int counter = 0;
		String tempString = "";

		// usuwanie przecinkow
		PrintWriter doZapisania = new PrintWriter(output, "UTF-8");

		while (plik.hasNextLine()) {
			s = plik.nextLine();
			itr = Szukane.iterator();
			tempString = s.substring(start, end).trim();
//			System.out.print(tempString+" - ");
			while(itr.hasNext()){
				checker = itr.next();
//				if(checker.equals(s.substring(997-8, 997))){			//trade number
				 
				if(checker.equals(tempString)){		
					doZapisania.println(s);
					counter++;
					System.out.println(checker+", "); // wyswietla co wyciaga
				}

			}
//			System.out.println();
		}
		
		System.out.println(">> " + counter + " lines copied");
		doZapisania.close();
		plik.close();
		
		}

	static void getLinesAndDisplayTradeNumber(Set <String> Szukane, int start, int end, String reportName, PrintWriter doZapisania) throws IOException{
String s;
		
		FileReader fr = new FileReader(reportName);
		Scanner plik = new Scanner(fr);
		Iterator<String> itr;
		String checker;
		int counter = 0;
		int line = 0;

		// usuwanie przecinkow
//		PrintWriter doZapisania = new PrintWriter(output, "UTF-8");

		while(plik.hasNextLine()) {
			s = plik.nextLine();
			itr = Szukane.iterator();
			++line;
			while(itr.hasNext()){
				checker = itr.next();
//				if(checker.equals(s.substring(997-8, 997))){			//trade number
			try{
				if(checker.contains(s.substring(start, end).trim()) && !s.substring(start, end).trim().equals("")){		
					doZapisania.println(s);
					counter++;
					System.out.println(s.substring(997-8, 997)+", "); // wyswietla co wyciaga
//					System.out.println("Start_"+s.substring(start, end).trim()+"_koniec");
				}
			}catch(Exception e){
				System.out.println("DUD:" + line);
				continue;
				
			}
//			System.out.println(Szukane[i]);
			}
		}
		
		System.out.println(">> " + counter + " lines copied");
//		doZapisania.close();
		plik.close();
	}
}
