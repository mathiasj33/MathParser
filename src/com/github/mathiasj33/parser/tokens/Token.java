package com.github.mathiasj33.parser.tokens;

public class Token {
	private final TokenType type;
	private Object data;
	
	public Token(TokenType type) {
		this.type = type;
	}
	
	public Token(TokenType type, Object data) {
		this(type);
		this.data = data;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public Object getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return "Token(" + type + ", " + data + ")";
	}
}
