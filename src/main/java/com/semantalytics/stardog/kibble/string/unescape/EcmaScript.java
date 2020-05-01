package com.semantalytics.stardog.kibble.string.unescape;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static org.apache.commons.text.StringEscapeUtils.unescapeEcmaScript;

public final class EcmaScript extends AbstractFunction implements StringFunction {

    protected EcmaScript() {
        super(1, UnescapeVocabulary.ecmaScript.stringValue());
    }

    private EcmaScript(final EcmaScript ecmaScript) {
        super(ecmaScript);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();

        return literal(unescapeEcmaScript(string));
    }

    @Override
    public EcmaScript copy() {
        return new EcmaScript(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UnescapeVocabulary.csv.name();
    }
}
