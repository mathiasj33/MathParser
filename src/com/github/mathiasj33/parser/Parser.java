package com.github.mathiasj33.parser;

import com.github.mathiasj33.parser.syntaxtree.BinaryMinusExpr;
import com.github.mathiasj33.parser.syntaxtree.BracketExpr;
import com.github.mathiasj33.parser.syntaxtree.DivideExpr;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.github.mathiasj33.parser.syntaxtree.Expr;
import com.github.mathiasj33.parser.syntaxtree.MultiplyExpr;
import com.github.mathiasj33.parser.syntaxtree.NumberExpr;
import com.github.mathiasj33.parser.syntaxtree.PlusExpr;
import com.github.mathiasj33.parser.syntaxtree.PowExpr;
import com.github.mathiasj33.parser.syntaxtree.UnaryMinusExpr;
import com.github.mathiasj33.parser.tokens.Token;
import com.github.mathiasj33.parser.tokens.TokenType;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {

    public static void main(String[] args) {
        try {
            new Parser().evaluate("-5*3^2");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    // TODO: kleine Sprache?? Funktionen usw. dann reichts aber auch. Oder umwandeln in jasmin assembly mit grafik display?

    public void evaluate(String exprString) throws ParseException {
        List<Token> tokens = new Lexer(exprString).getTokens();
        Expr expr = parseTokens(tokens);
        System.out.println(expr.evaluate());
    }

    private Expr parseTokens(List<Token> tokens) throws ParseException {
        List<Expr> exprs = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            switch (token.getType()) {
                case DIGIT: {
                    String digitString = "" + (int) token.getData();
                    while (tokens.get(++i).getType() == TokenType.DIGIT) {
                        digitString += (int) tokens.get(i).getData();
                    }
                    exprs.add(new NumberExpr(Integer.parseInt(digitString)));
                    i--;
                    break;
                }
                case PLUS: {
                    exprs.add(new PlusExpr());
                    break;
                }
                case MINUS: {
                    if(exprs.size() == 0 || exprs.get(exprs.size() - 1).isOperator()) {
                        exprs.add(new UnaryMinusExpr());
                    } else {
                        exprs.add(new BinaryMinusExpr());
                    }
                    break;
                }
                case MULTIPLY: {
                    exprs.add(new MultiplyExpr());
                    break;
                }
                case DIVIDE: {
                    exprs.add(new DivideExpr());
                    break;
                }
                case POW: {
                    exprs.add(new PowExpr());
                    break;
                }
                case BRACKET_OPEN: {
                    List<Token> bracketedTokens = new ArrayList<>();
                    while (tokens.get(++i).getType() != TokenType.BRACKET_CLOSE) {
                        bracketedTokens.add(tokens.get(i));
                    }
                    bracketedTokens.add(new Token(TokenType.EOF));

                    BracketExpr bracket = new BracketExpr();
                    bracket.setLeft(parseTokens(bracketedTokens));
                    exprs.add(bracket);
                }
            }
        }

        Queue<Expr> queue = parseExprs(exprs);
        return getTopExpr(queue);
    }

    private Queue<Expr> parseExprs(List<Expr> exprs) {
        Queue<Expr> queue = new LinkedList<>();

        Expr lowestPrecedence = getLowestPrecedenceExpr(exprs);
        queue.add(lowestPrecedence);

        if (lowestPrecedence instanceof NumberExpr)
            return queue;
        else if (lowestPrecedence instanceof BracketExpr)
            return queue;
        int pos = exprs.indexOf(lowestPrecedence);

        if (!(lowestPrecedence instanceof UnaryMinusExpr))
            queue.addAll(parseExprs(exprs.subList(0, pos)));
        queue.addAll(parseExprs(exprs.subList(pos + 1, exprs.size())));

        return queue;
    }

    private Expr getLowestPrecedenceExpr(List<Expr> exprs) {
        return exprs.stream().min((Expr o1, Expr o2) -> {
            return o1.getPrecedence() - o2.getPrecedence();
        }).get();
    }

    private Expr getTopExpr(Queue<Expr> queue) {
        Expr expr = queue.poll();
        if (!expr.isOperator())
            return expr;
        expr.setLeft(getTopExpr(queue));
        if (!(expr instanceof UnaryMinusExpr))
            expr.setRight(getTopExpr(queue));
        return expr;
    }
}
