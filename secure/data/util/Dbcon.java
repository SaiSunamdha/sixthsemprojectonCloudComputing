package com.secure.data.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbcon {
	static Connection con;
public static Connection getConnection()throws Exception
{
	Class.forName("oracle.jdbc.driver.OracleDriver");
	con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","gsharing","gsharing");
	return con;
}
}