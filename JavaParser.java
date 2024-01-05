package pl1transpiler;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class JavaParser {
	
	static ArrayList<String> java_expression = new ArrayList<String>();
	
	public JavaParser() {
		
	}
	
	public String createVarExpression(SimpleNode root) {
		try {
			addType(root);
			addIdentifier(root);
		if(isVarExpression(root)) {
			
			if(java_expression.size() != 0) {
				String listString = java_expression.stream()
		                .map(Object::toString)
		                .collect(Collectors.joining(""));
				
				String expression = String.join("", listString);
				expression = expression + ";";
				return expression;
			}
			return "Java Expression is 0";
			
		}
		
		else {
			return "Error in createVarExpression";
		}
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return "Unhandled Error";
	}
	
	
	public boolean isVarExpression(SimpleNode root) throws Exception {
		if( root.jjtGetChild(0).toString() == "VAR") {
			return true;
		}
		return false;
	}
	
	public void addType(SimpleNode root) throws Exception {
		
		Node type = root.jjtGetChild(0).jjtGetChild(1);
		if(type.toString() == "TYPE") {
			if (type.jjtGetChild(0).toString() == "DECIMAL") {
				Node decimalType = type.jjtGetChild(0).jjtGetChild(0);
				
				switch(decimalType.jjtGetChild(0).toString()) {
					case "INT": java_expression.add("int ");
						break;
					case "DOUBLE" : java_expression.add("double ");
						break;
					default: System.out.println("In default case");
						break;
				}
			}
			if (type.jjtGetChild(0).toString() == "STRING") {
				java_expression.add("String ");
			}
		}
		
		
	}
	
	public static void addIdentifier(SimpleNode root) throws Exception{
		Node id = root.jjtGetChild(0).jjtGetChild(0);
		
		if(id.toString() == "ID") {
			Symbol_table symboltable = new Symbol_table();
			java_expression.add(symboltable.getAllIdentifier().get(0));
			
		}
		
	}
}
