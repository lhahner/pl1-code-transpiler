package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class BodyMapper extends Mapper implements ITranslationBehavior{
	
	ArrayList<String > statments = new ArrayList<String>();
	
	public String translate(SimpleNode simpleNode) {
		if(super.hasChildren(simpleNode)) {
			return null; //Maybe implement;
		}
		else {
			return "{ \n }";
		}
	}
}
