package com.github.mathiasj33.parser;

import java.util.List;
import java.util.Stack;

import com.github.mathiasj33.parser.syntaxtree.Expr;
import com.github.mathiasj33.parser.tokens.Token;

public class Parser {
	
	public static void main(String[] args) {
		new Parser().evaluate("14+3+124");
	}
	//TODO: erst ohne lookahead, dann mit lookahead und mal (, minus, divide)
	
	public void evaluate(String expr) {
		List<Token> tokenList = new Lexer(expr).getTokens();
		Stack<Expr> nodeStack = new Stack<>();
	}
}
