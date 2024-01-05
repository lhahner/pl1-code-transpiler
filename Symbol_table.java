package pl1transpiler;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;

public class Symbol_table {
	
	public Symbol_table () {
		INIT();
	}
	/****
	*<Declaration> ::= (<float sequence> | <fixed sequence>) (REAL | COMPLEX) <precision specification>

	<float sequence> ::= FLOAT <precision specification> (DECIMAL <precision specification> | BINARY <precision specification> (UNSIGNED| SIGNED)) 

	<fixed sequence> ::= FIXED <precision specification>(DECIMAL <precision specification> | BINARY <precision specification>)
	*/
	
	static final String DCL_SYMBOL[] = {"DCL", "operator"};
	static final String DECLARE_SYMBOL[] = {"DECLARE", "operator"};
	static final String DECIMAL_SYMBOL[] = {"DECIMAL", "type"};
	static final String BINARY_SYMBOL[] = {"BINARY", "type"};
	static final String DEC_SYMBOL[] = {"DEC", "type"};
	static final String BIN_SYMBOL[] = {"BIN", "type"};
	static final String FIXED_SYMBOL[] = {"FIXED", "type"};
	static final String FLOAT_SYMBOL[] = {"FLOAT", "type"};
	static final String CHARACTER_SYMBOL[] = {"CHARACTER", "type"};
	static final String CHAR_SYMBOL[] = {"CHAR", "type"};
	static final String VARYING_SYMBOL[] = {"VARYING", "type"};
	static final String VAR_SYMBOL[] = {"VAR", "type"};
	static final String PIC_SYMBOL[] = {"PIC", "type"};
	static final String PICTURE_SYMBOL[] = {"PICTURE", "type"};
	static final String STATIC_SYMBOL[] = {"STATIC", "type"};
	static final String INIT_SYMBOL [] = {"INIT", "operator"};
	static final String PTR_SYMBOL [] = {"PTR", "type"};
	static final String REAL_SYMBOL [] = {"REAL", "type"};
	static final String COMPLEX_SYMBOL [] = {"COMPLEX", "type"};
	static final String UNSIGEND_SYMBOL [] = {"UNSIGEND", "type"};
	static final String SIGEND_SYMBOL [] = {"SIGNED", "type"};
	
	static Hashtable<Integer, String[]> hashtable = new Hashtable<>();
	
	public static void insert(String [] symbol) throws Exception {
		
		try {
		
		if(getBySymbol(symbol[0]) != null) {
			return;
		}
		int id = hashtable.size() + 1;
		hashtable.put(id, symbol);
		
		} catch(Exception e) {
			System.out.println(e);
		}
		
	}
	public void delete (int key) {
		hashtable.remove(key);
	}
	
	public static String[] getById(int key) {
		return hashtable.get(key);
	}
	
	public static String getBySymbol(String symbol) {
		
			for(int i = 1; i<=hashtable.size(); i++) {
				
					if(getById(i)[0].equals(symbol)) {
						return getById(i)[0];
					}
					else {
						continue;
					}
				
			
		}
		return null;
	}
	
	//can only be called when the scanner was executed once
	public static ArrayList<String> getAllIdentifier() {
		ArrayList<String> identfiertList = new ArrayList<String>();
		for(int i = 1; i<=hashtable.size(); i++) {
		
			if(getById(i)[1].equals("id")) {
				identfiertList.add(getById(i)[0]);
			}
			else {
				continue;
			}
		}
		
		
		return identfiertList;
	}
	
	public boolean isSymbol(String symbol) {
		
		if(getBySymbol(symbol) != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void printAll() {
		for(int i = 1; i<=hashtable.size(); i++) {
			
			System.out.print("\n" + i + " : ");
			
			for(int j = 0; j < getById(i).length; j++) {
				
			System.out.print(getById(i)[j] + ", ");
			
			}
		}
	}
	
	private final void INIT() {
		try {
			
		insert(DCL_SYMBOL);
		insert(DECLARE_SYMBOL);
		insert(DECIMAL_SYMBOL);
		insert(BINARY_SYMBOL);
		insert(DEC_SYMBOL);
		insert(BIN_SYMBOL);
		insert(FIXED_SYMBOL);
		insert(FLOAT_SYMBOL);
		insert(CHARACTER_SYMBOL);
		insert(CHAR_SYMBOL);
		insert(CHAR_SYMBOL);
		insert(STATIC_SYMBOL);
		insert(INIT_SYMBOL);
		insert(PTR_SYMBOL);
		insert(REAL_SYMBOL);
		insert(VARYING_SYMBOL);
		insert(PIC_SYMBOL);
		insert(COMPLEX_SYMBOL);
		insert(UNSIGEND_SYMBOL);
		insert(SIGEND_SYMBOL);

		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
}