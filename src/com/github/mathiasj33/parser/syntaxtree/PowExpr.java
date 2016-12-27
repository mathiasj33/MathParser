package com.github.mathiasj33.parser.syntaxtree;

import java.text.ParseException;

public class PowExpr extends Expr {

	@Override
	public int evaluate() throws ParseException {
		return (int) Math.pow(left.evaluate(), right.evaluate());
	}

	@Override
	public boolean isOperator() {
		return true;
	}

}
