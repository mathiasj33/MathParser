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
import com.github.mathiasj33.parser.syntaxtree.PowExpr;
import com.github.mathiasj33.parser.tokens.Token;
import com.github.mathiasj33.parser.tokens.TokenType;

public class Parser {

    private Stack<Expr> stack = new Stack<>();

    public static void main(String[] args) {
        try {
            new Parser().evaluate("2+5*3^2");
        } catch (ParseException e) {
            e.printStackTrace();
        } // Für jede Klammerung: Unterstack, der gleich ausgewertet wird.
        // Auflösen: Keine Klammern -> Hoch sofort, dann Mal, dann Plus
    }
    // TODO: Hochnehmen -> Precedence, Minus, geteilt, negative Werte, kleine Sprache?? Funktionen usw. dann reichts aber auch. Oder umwandeln in jasmin assembly mit grafik display?

    public void evaluate(String exprString) throws ParseException {
        List<Token> tokens = new Lexer(exprString).getTokens();
        System.out.println(tokens);
        Expr expr = parseTokens1(tokens);
        System.out.println(expr.evaluate());
    }

    private Expr parseTokens1(List<Token> tokens) throws ParseException {
        // TODO: mehrere Digits beachten
        List<Expr> exprs = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            switch (token.getType()) {
                case DIGIT: {
                    exprs.add(new NumberExpr((int) token.getData()));
                    break;
                }
                case PLUS: {
                    exprs.add(new PlusExpr());
                    break;
                }
                case MULTIPLY: {
                    exprs.add(new MultiplyExpr());
                    break;
                }
                case POW: {
                    exprs.add(new PowExpr());
                    break;
                }
            }
        }

        System.out.println(exprs);

        for (int i = 0; i < exprs.size(); i++) {
            Expr expr = exprs.get(i);
            if (expr instanceof PowExpr) {
                NumberExpr left = (NumberExpr) exprs.get(i - 1);
                NumberExpr right = (NumberExpr) exprs.get(i + 1);
                expr.setLeft(left);
                expr.setRight(right);
            }
        }

        return exprs.get(0);
    }

    private Expr parseTokens(List<Token> tokens) throws ParseException {
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(stack);
            Token token = tokens.get(i);
            Token next = null;
            if (i < tokens.size() - 1) {
                next = tokens.get(i + 1);
            }

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
        for (int i = enclosedExprs.size() - 1; i >= 0; i--) {
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
            } else {
                stack.push(curr);
                stack.push(right);
                return;
            }
        }
    }
}
