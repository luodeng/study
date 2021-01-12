package com.roden.study.javax.sql;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class CachedRowSetDemo {
    public static void main(String[] args) throws SQLException {
        Connection connection= DriverManager.getConnection("","","");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select  1");

        RowSetFactory factory= RowSetProvider.newFactory();
        CachedRowSet cachedRowSet=factory.createCachedRowSet();
        cachedRowSet.populate(resultSet);

        resultSet.close();
        statement.close();
        connection.close();

        cachedRowSet.afterLast();
        while (cachedRowSet.previous()){
            System.out.println(cachedRowSet.getInt(1));

            cachedRowSet.updateString(2,"update");
            cachedRowSet.updateRow();
        }

        connection= DriverManager.getConnection("","","");
        connection.setAutoCommit(false);
        cachedRowSet.acceptChanges(connection);

    }
}
