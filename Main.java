package pl1transpiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static String content = "";
	static String token = "";
	static int DECLARE = 1;
	static ArrayList<String> pl1_parsetree = new ArrayList<String>();  
	static JavaParser jP = new JavaParser();
	static Symbol_table st = new Symbol_table();
	
	
	public static void main(String[] args) {
		
		Lexscanner lex = new Lexscanner();
		
		
		Scanner sc = lex.readFile();
		try {
		File f = new File("/home/lennart/pl1transpiler/src/pl1transpiler/code.txt");
		FileInputStream isr = new FileInputStream(f);
		Pl1Parser pp = new Pl1Parser(isr);
		SimpleNode root = pp.program();
		
		while(sc.hasNext()) {
			
			content = sc.useDelimiter(("[.:;?!\"]+")).next();
			
		}
		
		System.out.println("content: " + content);
		
		
		while(content.length() > 0) {
			
			if(lex.getToken(content) == "failed") {
				break;
			}
			token = lex.getToken(content);
			
		    // �berpr�fen, ob die L�nge des Tokens die verbleibende L�nge des Inhalts �berschreitet
		    if (token.length() > content.length()) {
		        // Behandlung des Fehlers oder Abbruch der Schleife, je nach Anforderungen
		        break;
		    }
		  
			content = content.substring(token.length()).trim();
			System.out.println("token: " + token);
			
		}
		st.printAll();
		root.dump(" ");
		
		jP.createExpression(root);
		
		System.out.println(jP.concatExpression());
		
		}
		catch(Exception ioe) {
			System.out.println(ioe);
		}
		
	}
	
	

}
