package seperate_diff;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


public class separate_diff {
	
	public static void main(String[] args) throws IOException {
		String chroot = "C:/Users/krzysztof.urbanczyk/Documents/Murex/TREREP/output/";
//						 C:\Users\krzysztof.urbanczyk\Documents\Murex\Reporting fixes\kozi_sap\output
		String input1 = chroot + "n1a.txt";
		String input2 = chroot + "n2a.txt";
		String output1 = chroot + "output1.txt";
		String output2 = chroot + "output2.txt";
		
		
		
		separateToFiles(input1, input2, output1, output2);
		

	}


	public static void separateToFiles(String input1, String input2, String output1, String output2) throws FileNotFoundException, UnsupportedEncodingException{
	
		Scanner file1 = new Scanner(new FileReader(input1));
		Scanner file2 = new Scanner(new FileReader(input2));
		PrintWriter fileOut1 = new PrintWriter(output1, "UTF-8");
		PrintWriter fileOut2 = new PrintWriter(output2, "UTF-8");
		String s1, s2;
		int counter = 0;
		int length = 0;
	
		
		while(file1.hasNextLine() & file2.hasNextLine()) {
			

			
			try{
				s1 = file1.nextLine();


		}catch(Exception e){
			s1 = null;
			continue; // :DDDDD
			
		}
			try{
				s2 = file2.nextLine();


		}catch(Exception e){
			s2 = null;
			continue; // :DDDDD
			
		}
		
		
			if(!s1.equals(s2)){
				fileOut1.println(s1);
				fileOut2.println(s2);
				++counter;
			}
			++length;
			
		}


		
		System.out.println("File length: " + length + ", line differences: " + counter);
		file1.close();
		file2.close();
		fileOut1.close();
		fileOut2.close();
	}

}


