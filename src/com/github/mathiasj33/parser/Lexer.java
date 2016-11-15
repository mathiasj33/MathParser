package com.github.mathiasj33.parser;

import java.util.ArrayList;
import java.util.List;

import com.github.mathiasj33.parser.tokens.NumberToken;
import com.github.mathiasj33.parser.tokens.PlusToken;
import com.github.mathiasj33.parser.tokens.Token;

public class Lexer {
	
	private String expr;
	
	public Lexer(String expr) {
		this.expr = expr;
	}
	
	public List<Token> getTokens() {
		List<Token> tokens = new ArrayList<>();
		
		for(int i = 0; i < expr.length(); i++) {
			char c = expr.charAt(i);
			
			if(c == '+') {
				tokens.add(new PlusToken());
			}
			else if(isInt(c)) {
				String intString = Character.toString(c);
				for(int j = i + 1; j < expr.length() + 1; j++) {
					char exprC = 0;
					try {
					  exprC = expr.charAt(j);
					} catch(IndexOutOfBoundsException e) {
						tokens.add(new NumberToken(Integer.parseInt(intString)));
						break;
					}
					if(!isInt(exprC)) {
						i = j - 1;
						tokens.add(new NumberToken(Integer.parseInt(intString)));
						break;
					}
					intString += exprC;
				}
				
			}
		}
		
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
