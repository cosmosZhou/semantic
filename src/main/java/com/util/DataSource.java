package com.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// http://repo1.maven.org/maven2/com/zaxxer/HikariCP-java7/2.4.8/
public class DataSource implements AutoCloseable {

	void init(HikariConfig config) {
		int minimum = 10;
		int maximum = 50;

		config.addDataSourceProperty("cachePrepStmts", true);
		config.addDataSourceProperty("prepStmtCacheSize", 500);

		config.setAutoCommit(true);

		config.setMinimumIdle(minimum);

		config.setMaximumPoolSize(maximum);
		ds = new HikariDataSource(config);
	}

	static public class MySQLDataSource extends DataSource {

		public MySQLDataSource(String url, String user, String password) {
			this(url, user, password, null, false, true, true);
		}

		public MySQLDataSource(String url, String user, String password, String serverTimezone) {
			this(url, user, password, serverTimezone, true, true, true);
		}

		public MySQLDataSource(String url, String user, String password, String serverTimezone, boolean useSSL,
				boolean useUnicode, boolean autoReconnect) {

			HikariConfig config = new HikariConfig();

			if (serverTimezone != null)
				config.setDriverClassName("com.mysql.cj.jdbc.Driver");
			else
				config.setDriverClassName("com.mysql.jdbc.Driver");

			url += "user=" + user;
			url += "&password=" + password;
			url += "&characterEncoding=" + "utf-8";
			if (serverTimezone != null)
				url += "&serverTimezone=" + serverTimezone;

			url += "&useSSL=" + useSSL;
			url += "&useUnicode=" + useUnicode;
			url += "&autoReconnect=" + autoReconnect;

			config.setJdbcUrl(url);
			log.info("url = " + url);
			config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
			config.setConnectionTestQuery("SELECT 1");

			this.init(config);
		}
	}

	static public class OracleDataSource extends DataSource {

		public OracleDataSource(String url, String user, String password) {
			HikariConfig config = new HikariConfig();
			config.setDriverClassName("oracle.jdbc.driver.OracleDriver");

			oracle.jdbc.pool.OracleDataSource dataSource = null;
			try {
				dataSource = new oracle.jdbc.pool.OracleDataSource();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			log.info("url = " + url);
			dataSource.setURL(url);
			dataSource.setUser(user);
			dataSource.setPassword(password);
			config.setDataSource(dataSource);

			this.init(config);
		}
	}

	abstract public class Invoker<TYPE> {
		protected abstract TYPE invoke() throws Exception;

		public DataSource getDataSource() throws Exception {
			return DataSource.this;
		}

		public TYPE execute() throws Exception {
			TYPE obj = null;

			try (DataSource self = open()) {
				obj = invoke();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return obj;
		}

	}

	abstract public class Executor {
		public Executor() throws Exception {
			try (DataSource self = open()) {
				invoke();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		protected abstract void invoke() throws Exception;

		public DataSource getDataSource() throws Exception {
			return DataSource.this;
		}

	}

	HikariDataSource ds;
	Connection con = null;

	public DatabaseMetaData getDatabaseMetaData() throws SQLException {
		return con.getMetaData();
	}

	int cnt = 0;

	public synchronized DataSource open() throws Exception {
		++cnt;
		if (con != null) {
			log.info("Connection is already opened. cnt = " + cnt);
			// the Connection might be shut down automatically if it is not
			// used for a long time;
			if (con.isClosed()) {
				con = getConnection();
			}
			return this;
		}

		// Class.forName("com.mysql.jdbc.Driver");
		// con = DriverManager.getConnection(url, user, password);
		con = getConnection();
//		log.info("Connection is opened.");
		return this;
	}

	public synchronized void close() throws Exception {
		--cnt;
		if (con == null || cnt < 0) {
			log.info("Connection is already closed. cnt = " + cnt);
			return;
		}

		if (cnt == 0) {
			con.close();
			con = null;
			// System.gc();
		}
//		log.info("Connection is closed.");
	}

	public class Query implements Iterable<ResultSet>, Iterator<ResultSet>, AutoCloseable {

		public Query(String sql) throws SQLException {
			prepareStatement(sql);
		}

		public void prepareStatement(String sql) throws SQLException {
			preparedStatement = con.prepareStatement(sql);
			result = preparedStatement.executeQuery();
		}

		ResultSet result;
		PreparedStatement preparedStatement;

		public void close() throws SQLException {
			result.close();
			preparedStatement.close();
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			try {
				if (result.next())
					return true;
				else {
					close();
					return false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

		@Override
		public ResultSet next() {
			return result;
		}

		@Override
		public void remove() {
		}

		@Override
		public Iterator<ResultSet> iterator() {
			// TODO Auto-generated method stub
			return this;
		}
	}

	public int execute(String sql) throws Exception {
		int ret = 0;
		try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
			ret = preparedStatement.executeUpdate();
			System.out.println("sql: " + sql);
		} catch (Exception e) {
//			e.printStackTrace();
			throw e;
		}

		return ret;
	}

	public int execute(String sql, Object... args) throws Exception {
		return execute(String.format(sql, args));
	}

	public class BatchExecutive {
		public BatchExecutive() throws SQLException {
			statement = con.createStatement();
		}

		Statement statement;

		public void addBatch(String sql) throws SQLException {
			statement.addBatch(sql);
		}

		public void executeBatch() throws SQLException {
			try {
				statement.executeBatch();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				statement.close();
			}
		}
	}

	protected int[] execute(String sql[]) throws SQLException {
		return execute(sql, sql.length);
	}

	protected int[] execute(String sql[], int length) throws SQLException {

		int[] res = null;
		try (Statement statement = con.createStatement()) {
			for (int i = 0; i < length; ++i) {
				System.out.println("sql: " + sql[i]);
				if (sql[i] == null)
					throw new Exception("null sql occurred.");
				statement.addBatch(sql[i]);
			}
			res = statement.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// con.commit();

		return res;
	}

	static public enum Driver {
		mysql, oracle
	}

	@SuppressWarnings("deprecation")
	public void shutdown() {
		ds.shutdown();
	}

	/**
	 * 
	 * @return
	 */
	public Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			ds.resumePool();
			return null;
		}
	}

	public static Logger log = Logger.getLogger(Utility.class);
}
