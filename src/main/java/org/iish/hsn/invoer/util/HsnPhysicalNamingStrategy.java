package org.iish.hsn.invoer.util;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Prevent Hibernate from changing the table and column names when building the tables.
 */
public class HsnPhysicalNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }
}
