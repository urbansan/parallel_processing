import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Differences {

	public static void main(String[] args) throws IOException{
		String chroot = "C:/Users/krzysztof.urbanczyk/Documents/Murex/TREREP/output/";
		String output = chroot + "inputs/input_values.txt";
//		String outputOld = chroot + "outputOld.txt";
//		String newReport = chroot + "input1.txt";
//		String oldReport = chroot + "input2.txt";
		
//		String newReport = chroot + "MA.NAS.G2.MUR.OPUS.TREREP_20160129.DAT_20160130_014412";
//		String oldReport = chroot + "MA.NAS.G2.MUR.OPUS.TREREP_20160128.DAT_20160129_023411";
		
		String newReport = chroot + "MA.NAS.G2.MUR.OPUS.TREREP_20160129.DAT_20160130_014412";
		String oldReport = chroot + "MA.NAS.G2.MUR.OPUS.TREREP_20160128.DAT_20160129_023411";
		PrintWriter doZapisania = new PrintWriter(output, "UTF-8");
//		PrintWriter doZapisaniaOld = new PrintWriter(outputOld, "UTF-8");
		
		diffPerFied(oldReport, newReport, doZapisania);
		
		

	}
	
	
	
	public static void  diffPerFied(String oldReport, String newReport, PrintWriter doZapisania) throws IOException{
		Set <String> differences = new HashSet<>();
		String toRead;
		String tempString;
		String phrase;
		FileReader oR = new FileReader(oldReport);
		Scanner oldFile = new Scanner(oR);
		
		FileReader nR = new FileReader(newReport);
		Scanner newFile = new Scanner(nR);
		
		while (newFile.hasNextLine()) {
			toRead = newFile.nextLine();
			differences.add(toRead.substring(30-15, 30)+";"+toRead.substring(1526-8, 1526)); //DRNI
			
//			line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 30-15, 30, reportName, output); // GSNR
		}
		System.out.println(">> Kontener nowego pliku wczytany");
		while (oldFile.hasNextLine()) {
			tempString = oldFile.nextLine(); //.substring(1526-8, 1526).trim(); //DRNI
			phrase = tempString.substring(30-15, 30)+";"+tempString.substring(1526-8, 1526);
			if(differences.contains(phrase)){
				differences.remove(phrase);
			}else{
				differences.add(phrase);
			}
		}

		oldFile.close();
		newFile.close();
		
		System.out.println("Count of differences: "+differences.size());
		
		Iterator<String> itr =differences.iterator();


		while(itr.hasNext()){

			doZapisania.println(itr.next().substring(0, 15));
		}

		}

}
