package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import org.apache.commons.lang.ArrayUtils;

import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.stardog.stark.Values.literal;

public final class IndexOfFirst extends AbstractExpression implements UserDefinedFunction {

    public IndexOfFirst() {
        super(new Expression[0]);
    }

    public IndexOfFirst(final IndexOfFirst indexOfFirst) {
        super(indexOfFirst);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.indexOfFirst.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if(getArgs().size() == 2) {
            final ValueOrError firstArgValue = getFirstArg().evaluate(valueSolution);
            if (!firstArgValue.isError() && assertArrayLiteral(firstArgValue.value())) {
                final ArrayLiteral arrayLiteral = (ArrayLiteral) firstArgValue.value();

                final ValueOrError secondArgValue = getSecondArg().evaluate(valueSolution);
                if (!secondArgValue.isError()) {
                    final MappingDictionary mappingDictionary = valueSolution.getDictionary();
                    final long elementId = mappingDictionary.add(secondArgValue.value());

                    //TODO return ordinal/offset typed literal???
                    return ValueOrError.General.of(literal(ArrayUtils.indexOf(arrayLiteral.getValues(), elementId)));
                } else {
                    return ValueOrError.Error;
                }
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public IndexOfFirst copy() {
        return new IndexOfFirst(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.indexOfFirst.toString();
    }
}

