package pl1transpiler;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

/*****************************************
Dezimaldatentyp
---------------
<Zahlenkette> ::= <declare-anweisung> <Bezeichner> <attribute>
<declare-anweisung> ::= [DECLARE | DCL]
<Bezeichner> ::= <Liste> | <Liste> <Bezeichner> | <Liste> <Ziffer> <Bezeichner> | <Liste> <Ziffer>
<attribut> ::= <basisattribut> <skalierungsattrbut> <moduattirbut> <genauigkeitsattribut>
<basisattribut> ::= DECIMAL| BINARY | DEC | BIN
<skalierungsattritbut> ::= FIXED | FLOAT
<modusattribut> ::= REAL | COMPLEX | REAL | CPLX
<genauigkeitsattribut> ::= (<genauigkeit>)
<genauigkeit> ::= <Ziffer>,<Ziffer>
<Ziffer> ::= 0...9 | -9...0
<Liste> ::= A...Z

Zeichenkettendatentype
----------------------
<Zeichenkette> ::= <declare-anweisung> <Bezeichner> [CHARACTER | CHAR] <(genauigkeit)> [VARYING | VAR]
<declare-anweisung> ::= [DECLARE | DCL]
<Bezeichner> ::= <Liste> | <Liste> <Bezeichner> | <Liste> <Ziffer> <Bezeichner> | <Liste> <Ziffer>
<genauigkeit> ::= <Ziffer>,<Ziffer>
<Ziffer> ::= 0...9 | -9...0
<Liste> ::= A...Z

Abbildungsketten
----------------
<Abbildungskette> ::=  <declare-anweisung> <Bezeichner> [PICTURE | PIC] '<abbildungsspezifiktaion>'
<declare-anweisung> ::= [DECLARE | DCL]
<Bezeichner> ::= <Liste> | <Liste> <Bezeichner> | <Liste> <Ziffer> <Bezeichner> | <Liste> <Ziffer>
<abbildungsspezfikation> ::= [X | A | [9 | 9V9 | V9 ] | [Z | *] | [9.9 | 9,9 | 9B9] | S]
<Ziffer> ::= 0...9 | -9...0
<Liste> ::= A...Z
 ****************************************/

public class Lexscanner {
	
	public static Symbol_table st = new Symbol_table();
	Scanner lexical_reader = readFile();
	
	int state = 0;	
	int start = 0;
	int lexical_value;
	int letterCounter;
	
	String token;
	ArrayList<Character> symbols = new ArrayList<Character>();
	String num;
	String c;
	
	public Lexscanner() {
		
	}
	
	void printReadtoken(String token) {
		System.out.println("Read Token: " + token);
	}
	
	String getToken(String next) {
		
		symbols.clear();
		state = 0;
		letterCounter = 0;
		
		c = next;
		
		while(letterCounter < next.length()) {
			
			switch(state) {
			
			case 0:
				
				if(c.charAt(letterCounter) == ' ' || c.charAt(letterCounter) == '\t' || c.charAt(letterCounter)  == '\n') {
					letterCounter++;
					state = 0;
				}
				
				if(Character.isLetter(c.charAt(letterCounter))) {
					state = 1;
				}
				
				if(Character.isDigit(c.charAt(letterCounter))) {
					state = 2;
				}
				
				if(c.charAt(letterCounter) == '(') {
					state = 3;
				}
				
				if(c.charAt(letterCounter) == '\'') {
					System.out.println("Here 21");
					state = 4;
				}
				
				if(c.charAt(letterCounter) == ',') {
					state = 3;
				}
				
				if(c.charAt(letterCounter) == ';') {
					state = 5;
				}
				
				
				else if(letterCounter == next.length()) {
					this.install_id(toToken(symbols));
					return toToken(symbols);
				}
				
				break;
			//Handle Letters
			case 1:
				
				if(c.charAt(letterCounter) == ' ' || 
				   c.charAt(letterCounter)  == '\t' ||
				   c.charAt(letterCounter)  == '\n') {
					
					System.out.println("Here 12");
					if(Character.isLetter(c.charAt(0))){
						String adder = toToken(symbols);
						this.install_id(adder);
						return toToken(symbols);
					}
					else {
						fail();
					}
					
				
				}
				
				else if(Character.isLetter(c.charAt(letterCounter))) {
					System.out.println("Here 13");
					this.symbols.add((char) c.charAt(letterCounter));
					
					letterCounter++;
					if(letterCounter == next.length()) {
						this.install_id(toToken(symbols));
						return toToken(symbols);
					}
					
					state = 1;
				}
				
				else if(Character.isDigit(c.charAt(letterCounter))) {
					System.out.println("Here 14");
					state = 2;
				}
				
				else if(c.charAt(letterCounter) == ',') {
					this.install_id(toToken(symbols));
						return toToken(symbols);
					
					
					
				}
				
				else if(letterCounter == next.length()) {
					System.out.println("Here 15");
					this.install_id(toToken(symbols));
					return toToken(symbols);
				}
				else if(c.charAt(letterCounter) == '(') {
					return toToken(symbols);
				}
				
				break;
			
			//Handle Numbers
			case 2:
				
				if(c.charAt(letterCounter) == ' ' || 
				   c.charAt(letterCounter)  == '\t' ||
				   c.charAt(letterCounter)  == '\n') {
					System.out.println("Here 11");
					if(Character.isLetter(c.charAt(0))){
						this.install_id(toToken(symbols));
					}
					return toToken(symbols);
				}
				
				else if(Character.isDigit(c.charAt(letterCounter))) {
					if(letterCounter == 0 && Character.isLetter(c.charAt(letterCounter+1))){
						fail();
					}
					else {
					System.out.println("Here 6");
					this.symbols.add((char) c.charAt(letterCounter));
					letterCounter++;
					state = 2;
					}
				}
				
				else if(Character.isLetter(c.charAt(letterCounter))) {
					
					System.out.println("Here 7");
					state = 1;
				}
				
				else if(c.charAt(letterCounter) == '(') {
					System.out.println("Here 8");
					state = 3;
				}
				
				else if(c.charAt(letterCounter) == ',') {
					System.out.println("Here 9");
					state = 3;
				}
				
				else if(c.charAt(letterCounter) == ')') {
					System.out.println("Here 10");
					state = 3;
				}
				
				else if(Character.isLetter(c.charAt(letterCounter))) {
					fail();
				}
				
				else if(letterCounter == next.length()) {
					
					return toToken(symbols);
				}
				
				break;
			
			//Handle Special Characters
			case 3: 
			
				if(c.charAt(letterCounter) == ' ' || 
				   c.charAt(letterCounter)  == '\t' ||
				   c.charAt(letterCounter)  == '\n') {
					
					return toToken(symbols);
				}
				
				else if(c.charAt(letterCounter) == '(') {
					
					System.out.println("Here 1");
					this.symbols.add((char) c.charAt(letterCounter));
					letterCounter++;
					state = 3;
					
				}
				
				else if(c.charAt(letterCounter) == ')') {
					if(c.charAt(0) == '\'') {
						
						System.out.println("xxx");
						this.symbols.add((char) c.charAt(letterCounter));
						letterCounter++;
						System.out.println(c.charAt(letterCounter));
						state = 4;
						
					}
					else {
						
						System.out.println("Here f");
						this.symbols.add((char) c.charAt(letterCounter));
						
						return toToken(symbols);
					}
				}
	
				else if(c.charAt(letterCounter) == ',') {
					if(letterCounter != 0 && c.charAt(letterCounter-1) == ')') {
					
							return toToken(symbols);
						
					}
					else if(letterCounter == 0 && Character.isLetter(c.charAt(letterCounter+1))) {
							fail();
						
					}
					else {
					
					System.out.println("Here 2");
					this.symbols.add((char) c.charAt(letterCounter));
					letterCounter++;
					state = 2;
					}
				}
				
				else if(c.charAt(letterCounter) == '\'') {
					if(c.charAt(0) == '(') {
						
						System.out.println("Here m");
						state = 4;
						
						}
					
					else {
						
						System.out.println("Here 3");
						this.symbols.add((char) c.charAt(letterCounter));
						letterCounter++;
						state = 1;
						
					}
					
					if(letterCounter == next.length()) {
						
						return toToken(symbols);
					}
						
				}
				
				else if(Character.isDigit(c.charAt(letterCounter))) {
					System.out.println("Here 5");
					state = 2;
				}
			
				
				else if(letterCounter == next.length()) {
				
					return toToken(symbols);
				}
				break;
			
			//Handle String values "xx"
			case 4:
			
				if(c.charAt(letterCounter) == ' ' || 
				   c.charAt(letterCounter)  == '\t' ||
				   c.charAt(letterCounter)  == '\n') {
					
					if(letterCounter == next.length()) {
						
						return toToken(symbols);
					}
					
					this.symbols.add((char) c.charAt(letterCounter));
					letterCounter++;
					
					System.out.println("Here 25a");
					
					
					
					state = 4;
				}
				
				else if(c.charAt(letterCounter) == '\'') {

					System.out.println("Here 25f");
					
					if(letterCounter == next.length()) {
						
						return toToken(symbols);
					}
					
					this.symbols.add((char) c.charAt(letterCounter));
					letterCounter++;
					
					state = 4;
		
				}
				
				else if(Character.isLetter(c.charAt(letterCounter))) {
					
					System.out.println("Here 26");
					this.symbols.add((char) c.charAt(letterCounter));
					letterCounter++;
					
					state = 4;
				}
				
				else if(Character.isDigit(c.charAt(letterCounter))) {
					
					this.symbols.add((char) c.charAt(letterCounter));
					letterCounter++;
					System.out.println("Here 28");
					state = 4;
				}
				
				else if(c.charAt(letterCounter) == ')') {
					state = 3;
				}
				
				else if(c.charAt(letterCounter) == '(') {
					state = 3;
				}
				
				else if((letterCounter != 0 && c.charAt(0) == '\'')) {
					System.out.println("Here");
					if(c.charAt(letterCounter) == '\'') {
						this.symbols.add((char) c.charAt(letterCounter));
						letterCounter++;
						System.out.println("Here 25x");
					
						return toToken(symbols);
					}
					else if(c.charAt(letterCounter) == '(') {
						state = 3;
					}
					else if(Character.isUnicodeIdentifierPart(c.charAt(letterCounter))) {
						this.symbols.add((char) c.charAt(letterCounter));
						letterCounter++;
						state = 4;
					}
					else {
						state = 4;
					}
				}
				
				else if(Character.isUnicodeIdentifierPart(c.charAt(letterCounter))) {
					this.symbols.add((char) c.charAt(letterCounter));
					letterCounter++;
					state = 4;
				}
				
				else if(letterCounter == next.length()) {
					System.out.println("Here 27");
					
					return toToken(symbols);
				}
				
				if(letterCounter == next.length()) {
					
					return toToken(symbols);
				}
				
				break;
			
			//Handle Simicolons
			case 5:
				
				if(c.charAt(letterCounter) == ';') {
					
					return toToken(symbols);
				}
				
				break;
			case 99:
				return "failed";
			
			default: return "Default error";
			
			
			}
			
			
		}
		
		 return "Fehler";
	}
	
	String checkToken(String next) {
		
		String currentToken = getToken(next);
		
		
		if(st.isSymbol(currentToken)) {
			sendToken(currentToken);
		}
		else {
			install_id(currentToken);
			sendToken(currentToken);
		}
		
		return null;
	}
	
	String toToken(ArrayList<Character> symbols) {
		String listString = symbols.stream()
                .map(Object::toString)
                .collect(Collectors.joining(""));
		
		String tmp = String.join("", listString);
		token = tmp;
		return token;
	}
	
	String sendToken(String token) {
		//Soll sp�ter Parser Methode aufrufen und String in Parameter 
		//�bergeben
		return token;
	}
	
	String formatToken() {
		return "";
	}
	
	
	int fail() {
		//forward = token_beginning;
		state = 99;
		
		return 99;	
	}

	void install_id(String id) {
		
		/* Eintrag eines Lexems in die Symboltabelle */
		if(st.getBySymbol(id) != null){
			System.out.println("PL1 Symbol!");
			return;
		}
		
			String tmp[] = {id, "id"};
			
			try {
				st.insert(tmp);
				
			}
			catch(Exception e) {
				System.out.println(e);
			}
 	
		
	
		 
	}
	
	//TODO: Add install Size in getToken Methode, not called.
	void install_size(int ref, int size) {
		try {
		String tmp[] = {Integer.toString(size), 
						"size", 
						Integer.toString(size)};
		
		st.insert(tmp);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	void prinSymboltable() {
		st.printAll();
	}
	
	void install_num() {
		/* referenz auf die Symboltabelle */
	}
	
	/*Utilities*/

	
	String getNum() {
		return num;
	}
	
	void recover() {
		
	}

	int countLetters() {
		int count = 0;
		
		while(lexical_reader.hasNext()){
			
			count = count + lexical_reader.next().length();
			lexical_reader.close();
	    }
		return count;
	}
	
	/**
	 * @return Scanner Objekt which has read the file in directory
	 */
	Scanner readFile() {
		File file = new File("/home/lennart/pl1transpiler/src/pl1transpiler/code.txt");
		try {
		
		FileInputStream fis = new FileInputStream(file);
		Scanner lexical_reader = new Scanner(fis);
		
		return lexical_reader;
		} 
		catch(IOException ie)
		{
			System.out.println("Exception: " + ie);
			return null;
		}
		
	
	
	}
}
