package com.github.mathiasj33.parser.syntaxtree;

public class NumberNode extends Node {
	private final int number;

	public NumberNode(int number) {
		this.number = number;
	}
	
	public int evaluate() {
		return number;
	}
	
	@Override
	public String toString() {
		return "NumberNode(" + number + ")";
	}

	@Override
	public boolean isOperator() {
		return false;
	}
}
