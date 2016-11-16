package com.github.mathiasj33.parser.tokens;

import com.github.mathiasj33.parser.syntaxtree.Node;
import com.github.mathiasj33.parser.syntaxtree.NumberNode;

public class NumberToken implements Token {
	private final int number;

	public NumberToken(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}

	@Override
	public boolean isOperator() {
		return false;
	}
	
	@Override
	public String toString() {
		return "NumberToken(" + number + ")";
	}

	@Override
	public Node getNode() {
		return new NumberNode(number);
	}
}
