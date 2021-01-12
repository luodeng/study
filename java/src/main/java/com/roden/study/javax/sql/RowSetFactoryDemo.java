package com.roden.study.javax.sql;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;

public class RowSetFactoryDemo {
    public static void main(String[] args) throws SQLException {
        RowSetFactory factory= RowSetProvider.newFactory();
        JdbcRowSet jdbcRowSet=factory.createJdbcRowSet();
        jdbcRowSet.setUrl("");
        jdbcRowSet.setUsername("");
        jdbcRowSet.setPassword("");
        jdbcRowSet.setCommand("select 1");
        jdbcRowSet.execute();
    }
}
