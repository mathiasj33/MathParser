package com.github.mathiasj33.parser.syntaxtree;

import java.text.ParseException;

public abstract class Expr {
	protected Expr left;
	protected Expr right;
	
	public abstract int evaluate() throws ParseException;
	public abstract boolean isOperator();
	
	public Expr getLeft() {
		return left;
	}
	public void setLeft(Expr left) {
		this.left = left;
	}
	public Expr getRight() {
		return right;
	}
	public void setRight(Expr right) {
		this.right = right;
	}
}
