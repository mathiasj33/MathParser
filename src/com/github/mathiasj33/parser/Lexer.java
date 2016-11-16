package com.github.mathiasj33.parser;

import java.util.ArrayList;
import java.util.List;

import com.github.mathiasj33.parser.tokens.Token;
import com.github.mathiasj33.parser.tokens.TokenType;

public class Lexer {
	
	private String expr;
	
	public Lexer(String expr) {
		this.expr = expr;
	}
	
	public List<Token> getTokens() {
		List<Token> tokens = new ArrayList<>();
		for(char c : expr.toCharArray()) {
			if(isInt(c)) {
				int value = Integer.parseInt(Character.toString(c));
				tokens.add(new Token(TokenType.DIGIT, value));
			}
			else {
				tokens.add(new Token(TokenType.PLUS));
			}
		}
		tokens.add(new Token(TokenType.EOF));
		return tokens;
	}
	
	private boolean isInt(char c) {
		try {
			Integer.parseInt(Character.toString(c));
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}
