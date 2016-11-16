package com.github.mathiasj33.parser.tokens;

import com.github.mathiasj33.parser.syntaxtree.Node;
import com.github.mathiasj33.parser.syntaxtree.PlusNode;

public class PlusToken implements Token {
	@Override
	public boolean isOperator() {
		return true;
	}
	
	@Override
	public String toString() {
		return "PlusToken";
	}

	@Override
	public Node getNode() {
		return new PlusNode();
	}
}