package com.util;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.ini4j.ConfigParser.InterpolationException;
import org.ini4j.ConfigParser.NoOptionException;
import org.ini4j.ConfigParser.NoSectionException;

public class MySQL extends DataSource.MySQLDataSource {
	private static Logger log = Logger.getLogger(MySQL.class);

	static public MySQL instance;

	static {
		System.out.println("initializing MySQL");
		synchronized (MySQL.class) {
			String section = "mysql";

			String host = PropertyConfig.get(section, "host");

			System.out.println("host = " + host);
			String port;
			if (PropertyConfig.config.hasOption(section, "port")) {
				port = PropertyConfig.get(section, "port");
			} else {
				port = "3306";
			}

			String database = PropertyConfig.get(section, "database");
			String url = String.format("jdbc:mysql://%s:%s/%s?", host, port, database);

			String user = PropertyConfig.get(section, "user");
			String password = PropertyConfig.get(section, "password");
			if (password.startsWith("\"")) {
				password = password.substring(1, password.length() - 1);
			}

			if (SystemUtils.IS_OS_WINDOWS) {
				String serverTimezone = "UTC";
//				String serverTimezone = "GMT";
				instance = static_construct(url, user, password, serverTimezone);
			} else {
				instance = static_construct(url, user, password);
			}
		}
	}

	static MySQL static_construct(String url, String user, String password) {
		// instance = new MySQL(PropertyConfig.getProperty("url",
		// "jdbc:mysql://121.40.196.48:3306/ucc?"),
		// PropertyConfig.getProperty("user", "root"),
		// PropertyConfig.getProperty("password", "client1!"));
		return new MySQL(url, user, password);
	}

	static MySQL static_construct(String url, String user, String password, String serverTimezone) {
		return new MySQL(url, user, password, serverTimezone);
	}

	static MySQL static_construct() throws NoSectionException, NoOptionException, InterpolationException {
		return static_construct(PropertyConfig.get("mysql", "url"), PropertyConfig.get("mysql", "user"),
				PropertyConfig.get("mysql", "password"));
	}

	public MySQL(String url, String user, String password) {
		super(url, user, password);
	}

	public MySQL(String url, String user, String password, String serverTimezone) {
		super(url, user, password, serverTimezone);
	}

	public void selectDialogClassificationLabel(String topicPK, ArrayList<String> nameList) throws SQLException {

		String parentpk = null;
		String name = null;
		String pk = topicPK;
		do {
			for (ResultSet res : new Query("select name, parentpk from ectopic WHERE pk = '" + pk + "'")) {
				parentpk = res.getString("parentpk");
				name = res.getString("name");
			}
			// if (name == null || parentpk == null)
			// break;
			if (parentpk == null)
				break;

			// topicArr.add(pk);
			nameList.add(name);
			pk = parentpk;
		} while (!"-1".equals(pk));

		// if (topicArr.isEmpty())
		// return;

		// Collections.reverse(topicArr);
		Collections.reverse(nameList);
	}

	public void selectDialogClassification(String pkDialog, ArrayList<String> arr) throws SQLException {

		String sql = "select msg_content from ecchatrecords WHERE pk = '" + pkDialog + "'";

		log.info("sql: \n" + sql);

		for (ResultSet result : new Query(sql)) {
			String content = result.getString("msg_content");
			log.info("content: " + content);
			arr.add(content);
		}
	}

	public TreeMap<String, String> select_repertoire(String service) throws Exception {
		TreeMap<String, String> dict = new TreeMap<String, String>();
		try (DataSource inst = open()) {

			String services = null;
			switch (service) {
			case "map":
			case "localsearch":
				services = "'map', 'localsearch'";
				break;
			case "audio":
			case "music":
			case "joke":
				services = "'audio', 'music', 'joke'";
				break;
			case "sms":
			case "contact":
			case "call":
				services = "'sms', 'contact', 'call'";
				break;
			default:
				break;
			}

			for (ResultSet res : new Query(
					String.format("select text, slot from tbl_repertoire where service in (%s)", services))) {
				dict.put(res.getString("text"), res.getString("slot"));
//				System.out.println(res.getString("text") + " = " + res.getString("slot"));
			}
		}
		return dict;
	}

	public List<Map<String, Object>> select(String sql) {
//		log.info("Query : " + sql);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try (DataSource inst = open()) {

			for (ResultSet res : new Query(sql)) {
				ResultSetMetaData metaData = res.getMetaData();
				HashMap<String, Object> dict = new HashMap<String, Object>();
				int columnCount = metaData.getColumnCount();
				for (int i = 1; i <= columnCount; ++i) {
					String key = metaData.getColumnLabel(i);
					dict.put(key, res.getObject(key));
				}
				list.add(dict);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	static public interface Filter {
		Object sift(ResultSet res) throws SQLException;
	}

	public List<Map<String, Object>> select(String sql, Filter filter, int limit) {
		log.info("Query : " + sql);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try (DataSource inst = open(); Query query = new Query(sql)) {
			for (ResultSet res : query) {
				Object mark = filter.sift(res);
				if (mark == null) {
					continue;
				}

				ResultSetMetaData metaData = res.getMetaData();
				HashMap<String, Object> dict = new HashMap<String, Object>();
				int columnCount = metaData.getColumnCount();
				for (int i = 1; i <= columnCount; ++i) {
					String key = metaData.getColumnLabel(i);
					dict.put(key, res.getObject(key));
				}
				dict.put("mark", mark);

				list.add(dict);
				if (list.size() >= limit)
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int execute(String sql) {
		try (DataSource inst = open()) {
			return super.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int[] execute(String sql[]) {
		try (DataSource inst = open()) {
			return super.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int execute(String sql, Object... args) {
		return execute(String.format(sql, args));
	}

	public List<Map<String, Object>> select_from(String sql, Object ...args) {
		return select(String.format(sql, args));
	}

	public static class Description {
		Description(String Field, String Type, String Null, String Key, String Default, String Extra) {
			this.Field = Field;
			this.Type = Type;
			this.Null = Null;
			this.Key = Key;
			this.Default = Default;
			this.Extra = Extra;
		}

		String Field, Type, Null, Key, Default, Extra;
	}

	public Description[] desc(String table) throws SQLException {
		ArrayList<Description> list = new ArrayList<Description>();
		for (ResultSet res : new Query("desc " + table)) {
			list.add(new Description(res.getString("Field"), res.getString("Type"), res.getString("Null"),
					res.getString("Key"), res.getString("Default"), res.getString("Extra")));
		}
		return list.toArray(new Description[list.size()]);
	}

	public List<String> show_tables() {
		List<String> list = new ArrayList<String>();
		try (DataSource inst = open()) {

			for (ResultSet res : new Query("show tables")) {
				list.add(res.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public String[] tbl_service_distinct_category() throws Exception {
		ArrayList<String> arr = new ArrayList<String>();
		String sql = "select distinct service from tbl_service order by service";
//		String sql = "select distinct service from tbl_service order by service desc";
		try (DataSource inst = open()) {
			for (ResultSet res : new Query(sql)) {
				arr.add(res.getString("service"));
			}
		}

		return Utility.toArray(arr);

	}

	public void selectTopicClassificationFromParent(String classPK, HashSet<String> pkMap) throws SQLException {
		for (ResultSet res : new Query("select pk from comment_category WHERE parent_pk = '" + classPK + "'")) {
			pkMap.add(res.getString("pk"));
		}
	}

	public void selectDialogClassificationFromRoot(String classPK, HashSet<String> pkMap) throws SQLException {
		for (ResultSet res : new Query(
				"select pk from ectopic WHERE parentpk = '-1' and companyPK = '" + classPK + "'")) {
			pkMap.add(res.getString("pk"));
		}
	}

	public void selectTopicClassificationFromRoot(String classPK, HashSet<String> pkMap) throws SQLException {
		for (ResultSet res : new Query(
				"select pk from comment_category WHERE deepth = 1 and company_PK = '" + classPK + "'")) {
			pkMap.add(res.getString("pk"));
		}
	}

	String bufferForReportQuery[] = new String[50];
	int bufferLengthForReportQuery = 0;

	String bufferForUnknownQuestion[] = new String[50];
	int bufferLengthForUnknownQuestion = 0;

	public boolean isBatchInProcessForReportQuery() {
		return this.bufferLengthForReportQuery > 0;
	}

	public boolean isBatchInProcessForUnknownQuestion() {
		return this.bufferLengthForUnknownQuestion > 0;
	}

	void retrieveDatabaseInfo() throws Exception {
		try (DataSource conn = MySQL.instance.open()) {
			DatabaseMetaData metadata = MySQL.instance.getDatabaseMetaData();

			System.out.println("数据库已知的用户: " + metadata.getUserName());
			System.out.println("数据库的系统函数的逗号分隔列表: " + metadata.getSystemFunctions());
			System.out.println("数据库的时间和日期函数的逗号分隔列表: " + metadata.getTimeDateFunctions());
			System.out.println("数据库的字符串函数的逗号分隔列表: " + metadata.getStringFunctions());
			System.out.println("数据库供应商用于 'schema' 的首选术语: " + metadata.getSchemaTerm());
			System.out.println("数据库URL: " + metadata.getURL());
			System.out.println("是否允许只读:" + metadata.isReadOnly());
			System.out.println("数据库的产品名称:" + metadata.getDatabaseProductName());
			System.out.println("数据库的版本:" + metadata.getDatabaseProductVersion());
			System.out.println("驱动程序的名称:" + metadata.getDriverName());
			System.out.println("驱动程序的版本:" + metadata.getDriverVersion());

			System.out.println();
			System.out.println("数据库中使用的表类型");
			ResultSet rs = metadata.getTableTypes();
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
			rs.close();

			System.out.println();

			System.out.println("获取指定的数据库的所有表的类型");

			System.out.println("获取指定的数据库的表的主键");
			// 获取指定的数据库的表的主键，第二个参数也是模式名称的模式,使用null了
			System.out.println();

			System.out.println("DatabaseMetaData.getIndexInfo()方法返回信息:");

			MySQL.instance.close();
		}
	}

	static String description[] = { "Field", "Type", "Null", "Key", "Default", "Extra", };

	public ArrayList<String[]> readFromTeletext() throws Exception {

		ArrayList<String[]> arr = new ArrayList<String[]>();

		String sql = "select * from kf_knowledge ";
		System.out.println("sql: \n" + sql);
		for (ResultSet result : new Query(sql)) {

			String pk = result.getString("pk");
			String company_pk = result.getString("company_pk");
			String title = result.getString("title");
			String description = result.getString("description");
			String content = result.getString("content");

			String res[] = { pk, company_pk, title, description, content };
			arr.add(res);
		}
		return arr;
	}

	public boolean insert(String table, String text, int label, int training) {
		try (DataSource inst = this.open()) {
			super.execute(String.format("insert into %s (text, label, training) VALUES('%s', %d, %d)", table,
					Utility.quote_mysql(text), label, training));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void update(String table, String text, int label, int training) {
		try (DataSource inst = this.open()) {
			super.execute(String.format(
					"insert into %s (text, label, training) VALUES('%s', %d, %d) on duplicate key update text=VALUES(text), label=VALUES(label)",
					table, Utility.quote_mysql(text), label, training));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> insert(String table, String[]... args) {
		List<String> list = new ArrayList<String>();

		try (DataSource inst = this.open()) {
			Description[] description = this.desc(table);
			int training_index = -1;
			for (int i = 0; i < description.length; ++i) {
				if (description[i].Field.equals("training")) {
					training_index = i;
					break;
				}
			}

			String[] training = args[training_index];

			for (int i = 0; i < training.length; ++i) {
				if (!training[i].startsWith("+"))
					continue;

				boolean valid_record = true;
				for (int j = 0; j < args.length; ++j) {
					if (args[j][i].isEmpty()) {
						valid_record = false;
						break;
					}
				}

				if (!valid_record)
					continue;

				training[i] = training[i].substring(1);

				String field_names[] = new String[args.length];
				String values[] = new String[args.length];
				for (int j = 0; j < args.length; ++j) {
					field_names[j] = description[j].Field;

					values[j] = args[j][i];
					if (description[j].Type.startsWith("varchar")) {
						values[j] = String.format("'%s'", Utility.quote_mysql(values[j]));
					}
				}

				String sql = String.format("replace into %s (%s) values(%s)", table, String.join(", ", field_names),
						String.join(", ", values));
				list.add(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] array = list.toArray(new String[list.size()]);
		new Thread(new Runnable() {
			@Override
			public void run() {
				MySQL.this.execute(array);
			}
		}).start();

		return list;
	}

	public static void main(String[] args) throws Exception {
		instance.select_repertoire("map");
	}
}
