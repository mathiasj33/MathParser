package com.github.mathiasj33.parser.syntaxtree;

import java.text.ParseException;

public class PlusExpr extends Expr {
	
	@Override
	public int evaluate() throws ParseException {
		if(left == null || right == null) throw new ParseException("Exception at PlusNode", 0);
		return left.evaluate() + right.evaluate();
	}

	@Override
	public boolean isOperator() {
		return true;
	}
	
	@Override
	public String toString() {
		return "PlusNode(" + left + ";" + right + ")";
	}
}
