package com.github.mathiasj33.parser.syntaxtree;

import java.text.ParseException;

public class UnaryMinusExpr extends Expr {

    public UnaryMinusExpr() {
        precedence = 2;
    }
    
    @Override
    public int evaluate() throws ParseException {
        if (left == null)
            throw new ParseException("Exception at UnaryMinusNode", 0);
        return -left.evaluate();
    }

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public String toString() {
        return "UnaryMinusNode(" + left + ")";
    }
}
