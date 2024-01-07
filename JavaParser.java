package pl1transpiler;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

import pl1transpiler.Pl1Parser.Node;
import pl1transpiler.Pl1Parser.SimpleNode;

public class JavaParser {
	
	static ArrayList<String> java_expression = new ArrayList<String>();
	Symbol symboltable = new Symbol();
	static int iterationCounter = 0;
	String Major;
	
	public JavaParser() {
		
	}
	
	public String concatExpression() {
		try {
				
				if(java_expression.size() != 0) {
					String listString = java_expression.stream()
			                .map(Object::toString)
			                .collect(Collectors.joining(""));
					
					String expression = String.join("", listString);
					expression = "public class Main { \n" + expression + "\n 	}";
					
					return expression;
					
				}
				
				return "Java Expression is 0";
				
		}
		
		catch (Exception e) {
            e.printStackTrace();
        }
		
		return "Unhandled Error";
	}
	
	public void createExpression(Node root) {
		
		Node currentroot = root;
		String id = getId();
		int currentScope = getScope();
		
		for(int i = 0;i<currentroot.jjtGetNumChildren();i++) {
			if(currentroot.jjtGetChild(i).toString() == "VAR") {
				currentroot = currentroot.jjtGetChild(i);
				createExpression(currentroot);
			}
		}
		
		if(currentroot.jjtGetChild(currentroot.jjtGetNumChildren()-1).toString() == "TYPE") {
			
			addType(currentroot.jjtGetChild(currentroot.jjtGetNumChildren()-1));
			
		}
		
		else {
			
		addClass();
		
		for(int i = 2; i<currentroot.jjtGetNumChildren(); i++) {
			
			
		if(currentroot.jjtGetChild(i).toString() == "MINOR") {
			
			
			
			if(currentroot.jjtGetChild(i).jjtGetChild(2).toString() == "MINOR") {
				
				addClass();
			}
			
			if(currentroot.jjtGetChild(i).jjtGetChild(2).toString() == "TYPE") {
				
				addType(currentroot.jjtGetChild(i).jjtGetChild(2));
					
			}
			
		}
				}
			}
		
			
		}
		
	
	
	
	public boolean isVarExpression(SimpleNode root) throws Exception {
		if( root.jjtGetChild(0).toString() == "VAR") {
			return true;
		}
		return false;
	}
	
	
	
	public void addType(Node root){
		
		Node type = root.jjtGetChild(0);
		
			if (type.toString() == "DECIMAL") {
				Node decimalType = type.jjtGetChild(0).jjtGetChild(0);
				
				switch(decimalType.toString()) {
					case "INT": 
							String idInt;
							if(java_expression.contains(Major)){
								java_expression.add("		private int ");
								idInt = addIdentifier();
								java_expression.add("; \n");
								addSetter("int", idInt);
								addGetter("int", idInt);
							}
							else {
								java_expression.add("		int ");
								idInt = addIdentifier();
								java_expression.add("; \n");
							}
						
						break;
						
					case "DOUBLE" : 
						String idDouble;
						if(java_expression.contains(Major)){
							java_expression.add("		private double ");
							idDouble = addIdentifier();
							java_expression.add("; \n");
							addSetter("double", idDouble);
							addGetter("double", idDouble);
						}
						else {
							java_expression.add("  double ");
							idDouble = addIdentifier();
							java_expression.add("; \n");
						}
						
						break;
					default: System.out.println("In default case");
						break;
				}
				
			}
			if (type.toString() == "STRING") {
				String idString;
				if(java_expression.contains(Major)){
					
					java_expression.add("		private String ");
					idString = addIdentifier();
					java_expression.add("; \n");
					addSetter("String", idString);
					addGetter("String", idString);
				}
				else {
					java_expression.add(" String ");
					idString = addIdentifier();
					java_expression.add("; \n");
				}
			}
			
	}
	
	public void addClass() {
		Major = getId();
		java_expression.add("	} \n	class ");
		addIdentifier();	
		java_expression.add(" { \n ");
	
	}
	
	public void addGetter(String type, String identfier) {
		java_expression.add("		public " + type + " get" + identfier + "() { return " + identfier + "; } \n");
		
	}
	
	public void addSetter(String type, String identfier) {
		java_expression.add("		public void set" + identfier + "("+ type + " " + identfier +") { this." + identfier + " = " + identfier + "; } \n");
	}
	
	public static int getScope() {
		
		Symbol symboltable = new Symbol();
		int scope = Integer.parseInt(symboltable.getAllIdentifier().get(iterationCounter)[1]);
		
		return scope;
	}
	
	public static int getNextScope() {
		Symbol symboltable = new Symbol();
		
		if(symboltable.getAllIdentifier().get(iterationCounter+1) != null) {
			
			int scope = Integer.parseInt(symboltable.getAllIdentifier().get(iterationCounter+1)[1]);
			return scope;
			
		}
		else {
			return 0;
		}
	}
	
	public String getId() {
		Symbol symboltable = new Symbol();
		String id = symboltable.getAllIdentifier().get(iterationCounter)[0];
		
		return id;
	}
	
	public static String addIdentifier(){
			String id;
			Symbol symboltable = new Symbol();
			id = symboltable.getAllIdentifier().get(iterationCounter)[0];
			java_expression.add(symboltable.getAllIdentifier().get(iterationCounter)[0]);
			iterationCounter++;
			return id;
		
		
	}
	
	
}
