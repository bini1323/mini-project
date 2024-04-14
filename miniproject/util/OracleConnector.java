package util;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class OracleConnector {
	public static final String JDBC_ORACLE_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String JDBC_ORACLE_URL = "jdbc:mysql://localhost:3306/miniproject";
	public static final String JDBC_ORACLE_USER = "root";
	public static final String JDBC_ORACLE_PW = "1234";

	public static Connection getConnection() {

		Connection conn = null;

		try {
			Class.forName(JDBC_ORACLE_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("드라이버를 로드하는데 문제 발생" + e.getMessage());
		}
		try {
			conn = DriverManager.getConnection(
					JDBC_ORACLE_URL,
					JDBC_ORACLE_USER,
					JDBC_ORACLE_PW);
		}catch(Exception e) {
			System.out.println("DB 연결 오류");
		}

		return conn;
	}
}
