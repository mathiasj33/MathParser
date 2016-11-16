package com.github.mathiasj33.parser.tokens;

import com.github.mathiasj33.parser.syntaxtree.Node;

public interface Token {
	public boolean isOperator();
	public Node getNode();
}
