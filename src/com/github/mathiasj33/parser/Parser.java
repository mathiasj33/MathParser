package com.github.mathiasj33.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.github.mathiasj33.parser.syntaxtree.BracketOpenExpr;
import com.github.mathiasj33.parser.syntaxtree.Expr;
import com.github.mathiasj33.parser.syntaxtree.MultiplyExpr;
import com.github.mathiasj33.parser.syntaxtree.NumberExpr;
import com.github.mathiasj33.parser.syntaxtree.PlusExpr;
import com.github.mathiasj33.parser.tokens.Token;
import com.github.mathiasj33.parser.tokens.TokenType;

public class Parser {

	private Stack<Expr> stack = new Stack<>();

	public static void main(String[] args) {
		try {
			new Parser().evaluate("2^3*7");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	// TODO: Hochnehmen -> Precedence, Minus, geteilt, kleine Sprache?? Funktionen usw. dann reichts aber auch. Oder umwandeln in jasmin assembly mit grafik display?

	public void evaluate(String exprString) throws ParseException {
		List<Token> tokens = new Lexer(exprString).getTokens();
		Expr expr = parseTokens(tokens);
		System.out.println(expr.evaluate());
	}

	private Expr parseTokens(List<Token> tokens) throws ParseException {
		for (int i = 0; i < tokens.size(); i++) {
			System.out.println(stack);
			Token token = tokens.get(i);
			Token next = null;
			if (i < tokens.size() - 1)
				next = tokens.get(i + 1);

			switch (token.getType()) {
			case DIGIT: {
				if (next.getType() == TokenType.DIGIT) {
					stack.push(new NumberExpr((int) token.getData()));
				} else {
					String s = "" + token.getData();
					while (!stack.isEmpty() && !stack.peek().isOperator()) {
						s = stack.pop().evaluate() + s;
					}
					stack.push(new NumberExpr(Integer.parseInt(s)));
				}
				break;
			}
			case PLUS: {
				reduceStack();
				stack.push(new PlusExpr());
				break;
			}
			case MULTIPLY:
				stack.push(new MultiplyExpr());
				break;
			case BRACKET_OPEN:
				stack.push(new BracketOpenExpr());
				break;
			case BRACKET_CLOSE:
				reduceBracket();
				break;
			case EOF: {
				reduceStack();
				return stack.pop();
			}
			}
		}
		return null;
	}

	private void reduceStack() {
		reduceStack(stack);
	}

	private void reduceBracket() {
		List<Expr> enclosedExprs = new ArrayList<>();
		Expr previous;
		while (!((previous = stack.pop()) instanceof BracketOpenExpr)) {
			enclosedExprs.add(previous);
		}
		
		Stack<Expr> enclosedStack = new Stack<>();
		for(int i = enclosedExprs.size() - 1; i >= 0; i--) {
			enclosedStack.push(enclosedExprs.get(i));
		}
		
		reduceStack(enclosedStack);
		stack.push(enclosedStack.pop());
	}
	
	private void reduceStack(Stack<Expr> stack) {
		while (stack.size() > 1) {
			Expr right = stack.pop();
			Expr curr = stack.pop();
			if (curr.isOperator() && !(curr instanceof BracketOpenExpr)) {
				curr.setLeft(stack.pop());
				curr.setRight(right);
				stack.push(curr);
				System.out.println("Reduce: " + stack);
			}
			else {
				stack.push(curr);
				stack.push(right);
				return;
			}
		}
	}
}
