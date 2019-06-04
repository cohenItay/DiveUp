package Managers;

import java.sql.Connection;

abstract class BaseSqlQuery
{
    protected SqlConnection dbconnection;
    protected Connection connection;

    BaseSqlQuery()
    {
        dbconnection= SqlConnection.getInstance();
        connection = SqlConnection.getConnection();
    }
}
