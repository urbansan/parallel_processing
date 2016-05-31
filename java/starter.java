import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class starter {
	public static void main(String[] args) throws IOException{
		String chroot = "C:/Users/krzysztof.urbanczyk/Documents/Murex/TREREP/output/";
		String output = chroot + "output.txt";
//		String outputOld = chroot + "outputOld.txt";
		String newReport = chroot +
				"MA.NAS.G2.MUR.OPUS.TREREP_20160222.DAT_20160223_004951";
//				"MA.NAS.G2.MUR.OPUS.TREREP_20160129.DAT_20160130_014412";
//		String oldReport = chroot + "MA.NAS.G2.MUR.OPUS.TREREP_20151130.DAT_20151201_001532";
		PrintWriter doZapisania = new PrintWriter(output, "UTF-8");
//		PrintWriter doZapisaniaOld = new PrintWriter(outputOld, "UTF-8");
		
		filterFile(newReport, doZapisania);
//		getPhrases_differencesOldNewFile(oldReport, newReport, doZapisania);
//		getPhrases_differencesOldNewFile(newReport, oldReport, doZapisaniaOld);
		
	
		doZapisania.close();
//		doZapisaniaOld.close();
	}
	
	
	
	public static void filterFile(String reportName, PrintWriter output) throws IOException {

	String input = "C:/Users/krzysztof.urbanczyk/Documents/Murex/TREREP/output/inputs/"
			+ "input_values.txt";
//			+ "input_IRD4_typologies.txt";
	
	//generate list of trades from the first file;
	Set <String>szukaneFrazy = new HashSet<>();
//	
//	szukaneFrazy = getPhrases();
//	szukaneFrazy = getPhrasesFromFile(input);
//	szukaneFrazy.add("7053416");
	szukaneFrazy.add("Default swaps");
//	szukaneFrazy.add("MMCDB");
//	szukaneFrazy.add("MMCPB");
	
//	szukaneFrazy.add("Comm Forward");
//	szukaneFrazy.add("EURIBOR");
//	szukaneFrazy.add("Deposit");
//	szukaneFrazy.add("MMPLC");


//extract the list from the actual file.
//	line_extracter.getLine(szukaneFrazy, reportName, output);
//	line_extracter.getLine(szukaneFrazy, 172-5,  172, reportName, output); //FIND1
	

//	line_extracter.getLine(szukaneFrazy, 426-5, 426, reportName, output); //FIND2
//	line_extracter.getLine(szukaneFrazy, 30-15, 30, reportName, output); //Contract_number
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 43-6, 43, reportName, output); //PID2
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 152-2, 152, reportName, output); //RP1
	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 1570-20, 1570, reportName, output); //MPRODUCT - typology
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 30-15, 30, reportName, output); // GSNR
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 997-8, 997, reportName, output); // DRNI
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 47-10, 47, reportName, output); // PID2
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 2216-2, 2216, reportName, output); // EVENT_TYP
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 1526-8, 1526, reportName, output); // ORIGID
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 31-1, 31, reportName, output); // TSTAT
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 2547-12, 2547, reportName, output); // ISIN1
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 2594-12, 2594, reportName, output); // ISIN2
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 2535-35, 2535, reportName, output); // LONG_IND1
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 2582-35, 2582, reportName, output); // LONG_IND2
//	line_extracter.getLinesAndDisplayTradeNumber(szukaneFrazy, 2268-52, 2268, reportName, output); // UTI
	
	System.out.println(">> Done :)");

	}
	

	
	public static void  getPhrases_differencesOldNewFile(String oldReport, String newReport, PrintWriter output) throws IOException{
		Set <String> differences = new HashSet<>();
		String tempString;
		FileReader oR = new FileReader(oldReport);
		Scanner oldFile = new Scanner(oR);
		
		FileReader nR = new FileReader(newReport);
		Scanner newFile = new Scanner(nR);
		
		while (newFile.hasNextLine()) {
			differences.add(newFile.nextLine().substring(1526-8, 1526).trim()); //DRNI
		}
		System.out.println(">> Kontener nowego pliku wczytany");
		while (oldFile.hasNextLine()) {
			tempString = oldFile.nextLine().substring(1526-8, 1526).trim(); //DRNI
			if(differences.contains(tempString))
				differences.remove(tempString);
		}

		oldFile.close();
		newFile.close();

		line_extracter.getLinesAndDisplayTradeNumber(differences, 1526-8, 1526, newReport, output); //DRNI
		
		}
	
	
	static public Set <String>  getPhrasesFromFile(String inputPath) throws IOException{
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


