package com.github.mathiasj33.parser.syntaxtree;

import java.text.ParseException;

public class BracketExpr extends Expr {

    public BracketExpr() {
        precedence = 3;
    }
    
    @Override
    public int evaluate() throws ParseException {
        if (left == null)
            throw new ParseException("Exception at Bracket_Open", 0);
        return left.evaluate();
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public String toString() {
        return "Bracket(" + left + ")";
    }
}
