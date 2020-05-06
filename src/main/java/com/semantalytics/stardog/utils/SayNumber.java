package com.semantalytics.stardog.utils;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.Values;

import java.util.Locale;

public class SayNumber extends AbstractFunction implements UserDefinedFunction {

    public SayNumber() {
        super(1, FunctionName.Utils.sayNumber.toString());
    }

    private SayNumber(final SayNumber sayNumber) {
        super(sayNumber);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {
        if(assertNumericLiteral(values[0])) {
            final int value = Literal.intValue((Literal)values[0]);

            //TODO Handle language tag

            final String number = new RuleBasedNumberFormat(Locale.US, RuleBasedNumberFormat.SPELLOUT).format(value);

            return ValueOrError.General.of(Values.literal(number));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public SayNumber copy() {
        return new SayNumber(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FunctionName.Utils.sayNumber.name();
    }
}