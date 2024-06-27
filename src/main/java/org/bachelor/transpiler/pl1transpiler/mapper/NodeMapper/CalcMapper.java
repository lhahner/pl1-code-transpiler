package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class CalcMapper extends Mapper implements ITranslationBehavior {
	
	private String mathExpression;
	private ArrayList<String> terms = new ArrayList<String>();
	
	public String translate(SimpleNode simpleNode) {
		setTerms(simpleNode);
		return "\"" + (String)simpleNode.jjtGetValue() + "\"";
	}
	
	public void setTerms(SimpleNode simpleNode) {
		this.terms.add((String) simpleNode.jjtGetValue());
		if (super.hasChildren(simpleNode)) {
			setTerms((SimpleNode) simpleNode.jjtGetChild(0));
		}
		return;
	}
}