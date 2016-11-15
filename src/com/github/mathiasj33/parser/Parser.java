package com.github.mathiasj33.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.github.mathiasj33.parser.syntaxtree.Node;
import com.github.mathiasj33.parser.tokens.Token;

public class Parser {
	
	public static void main(String[] args) {
		new Parser().evaluate("4+3+4+123+23+1+5");
	}
	
	public void evaluate(String expr) {
		List<Token> tokenList = new Lexer(expr).getTokens();
		
		List<Node> nodeList = new ArrayList<>();
		tokenList.forEach(token -> {
			nodeList.add(token.getNode());
		});
		
		System.out.println(nodeList);
		int value = 0;
		for(int i = nodeList.size() - 1; i >= 0; i--) {
			Node n = nodeList.get(i);
			if(n.isOperator()) {
				n.left = nodeList.get(i - 1);
				n.right = nodeList.get(i + 1);
				nodeList.remove(i + 1);
				nodeList.remove(i);
				nodeList.set(i - 1, n);
				i -= 1;
			}
			System.out.println(nodeList);
		}
		try {
			System.out.println(nodeList.get(0).evaluate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
