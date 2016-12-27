package com.github.mathiasj33.parser.syntaxtree;

import java.text.ParseException;

public class BracketOpenExpr extends Expr {
	@Override
	public int evaluate() throws ParseException {
		if(left == null || right == null) throw new ParseException("Exception at Bracket_Open", 0);
		return 0;
	}

	@Override
	public boolean isOperator() {
		return true;
	}
	
	@Override
	public String toString() {
		return "BracketOpen";
	}
}
