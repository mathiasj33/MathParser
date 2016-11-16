package com.github.mathiasj33.parser.syntaxtree;

import java.text.ParseException;

public abstract class Node {
	public Node left;
	public Node right;
	
	public abstract int evaluate() throws ParseException;
	public abstract boolean isOperator();
}
