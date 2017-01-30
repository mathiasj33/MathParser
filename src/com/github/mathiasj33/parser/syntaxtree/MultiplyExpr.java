package com.github.mathiasj33.parser.syntaxtree;

import java.text.ParseException;

public class MultiplyExpr extends Expr {
    
    public MultiplyExpr() {
        precedence = 1;
    }

    @Override
    public int evaluate() throws ParseException {
        if (left == null || right == null) {
            throw new ParseException("Exception at MultiplyNode", 0);
        }
        return left.evaluate() * right.evaluate();
    }

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public String toString() {
        return "MultiplyNode(" + left + ";" + right + ")";
    }
}
