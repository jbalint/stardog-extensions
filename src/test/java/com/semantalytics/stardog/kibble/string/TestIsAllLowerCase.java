package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestIsAllLowerCase extends AbstractStardogTest {

    @Test
    public void testTrue() {
      
            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:isAllLowerCase(\"Stardog\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final boolean aValue = Literal.booleanValue(((Literal)aResult.next().value("result").get()));

                assertEquals(false, aValue);
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testFalse() {

        final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                "select ?result where { bind(string:isAllLowerCase(\"stardog\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final boolean aValue = Literal.booleanValue(((Literal)aResult.next().value("result").get()));

            assertEquals(true, aValue);
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testEmptyString() {
    
            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:isAllLowerCase(\"\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final boolean aValue = Literal.booleanValue(((Literal)aResult.next().value("result").get()));

                assertEquals(false, aValue);
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooFewArgs() {

            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:isAllLowerCase() as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooManyArgs() {

            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:isAllLowerCase(\"one\", \"two\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
       
            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:isAllLowerCase(1) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
