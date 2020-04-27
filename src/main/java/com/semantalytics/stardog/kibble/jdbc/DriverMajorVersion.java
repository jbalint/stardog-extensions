package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;
import ucar.nc2.grib.TimeCoordUnion;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static com.complexible.common.rdf.model.Values.literal;

public class DriverMajorVersion extends AbstractFunction implements UserDefinedFunction {

    public DriverMajorVersion() {
        super(1, JdbcVocabulary.driverMajorVersion.stringValue());
    }

    public DriverMajorVersion(final DriverMajorVersion executeDouble) {
        super(executeDouble);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        if(iri.isPresent()) {
            try {
                DriverManager.getDriver(iri.get()).;
            } catch (SQLException e) {
                return ValueOrError.Error;
            }
            return literal(true);
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public DriverMajorVersion copy() {
        return new DriverMajorVersion(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return JdbcVocabulary.isDriverRegistered.name();
    }
}
