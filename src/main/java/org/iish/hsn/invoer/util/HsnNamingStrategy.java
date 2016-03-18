package org.iish.hsn.invoer.util;

import org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy;

/**
 * Prevent Hibernate from changing the table and column names when building the tables.
 */
public class HsnNamingStrategy extends SpringNamingStrategy {
    @Override
    public String tableName(String tableName) {
        return tableName;
    }

    @Override
    public String columnName(String columnName) {
        return columnName;
    }
}
