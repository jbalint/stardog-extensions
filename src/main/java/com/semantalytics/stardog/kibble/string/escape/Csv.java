package com.semantalytics.stardog.kibble.string.escape;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.text.StringEscapeUtils.*;

public final class Csv extends AbstractFunction implements StringFunction {

    protected Csv() {
        super(1, EscapeVocabulary.csv.toString());
    }

    private Csv(final Csv csv) {
        super(csv);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(escapeCsv(string)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Csv copy() {
        return new Csv(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EscapeVocabulary.csv.toString();
    }
}
