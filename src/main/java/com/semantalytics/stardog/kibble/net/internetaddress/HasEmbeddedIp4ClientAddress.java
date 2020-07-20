package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.net.Inet6Address;
import java.net.InetAddress;

import static com.google.common.net.InetAddresses.*;
import static com.stardog.stark.Values.literal;

public class HasEmbeddedIp4ClientAddress extends AbstractFunction implements UserDefinedFunction {

    public HasEmbeddedIp4ClientAddress() {
        super(1, InternetAddressVocabulary.hasEmbeddedIp4ClientAddress.toString());
    }

    private HasEmbeddedIp4ClientAddress(final HasEmbeddedIp4ClientAddress internetAddressToNumber) {
        super(internetAddressToNumber);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final InetAddress inetAddress = InetAddresses.forString(((Literal)values[0]).label());

            if(inetAddress instanceof Inet6Address) {
                return ValueOrError.General.of(literal(hasEmbeddedIPv4ClientAddress((Inet6Address)inetAddress)));
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new HasEmbeddedIp4ClientAddress(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.hasEmbeddedIp4ClientAddress.toString();
    }

}