package com.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.InsertManyOptions;

public class MongoDB {
	public static String MONGO_HOST = PropertyConfig.get("mongo", "host");

	public static int MONGO_PORT = Integer.parseInt(PropertyConfig.get("mongo", "port"));

//	public static String MONGO_DATABASE = PropertyConfig.get("mongo", "database");;

//	public static String MONGO_COLLECTION = PropertyConfig.get("mongo", "collection");

	/**
	 * 日期转换 type = 0 yyyy-MM-dd HH:mm:ss type = 1 yyyy-MM-dd type = 2 yyyy/MM/dd
	 * type = 3 yyyyMMdd HHmmss type = 4 HH:mm:ss type = 5 yyyy年MM月dd日 HH时mm分ss秒
	 * type = 6 MM/dd/yyyy
	 * 
	 * @param date
	 * @param type
	 * @return
	 */
	public static String formatDate(Date date, int type) {
		String result = "";
		if (type < 0) {
			type = 0;
		}
		if (date != null) {

			switch (type) {
			case 0:
				result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
				break;
			case 1:
				result = new SimpleDateFormat("yyyy-MM-dd").format(date);
				break;
			case 2:
				result = new SimpleDateFormat("yyyy/MM/dd").format(date);
				break;
			case 3:
				result = new SimpleDateFormat("yyyyMMdd HHmmss").format(date);
				break;
			case 4:
				result = new SimpleDateFormat("HH:mm:ss").format(date);
				break;
			case 5:
				result = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(date);
				break;
			case 6:
				result = new SimpleDateFormat("MM/dd/yyyy").format(date);
				break;
			default:
				result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
				break;
			}
		}
		return result;
	}

	/**
	 * MongoClient的实例代表数据库连接池，是线程安全的，可以被多线程共享，客户端在多线程条件下仅维持一个实例即可
	 * Mongo是非线程安全的，目前mongodb API中已经建议用MongoClient替代Mongo
	 */
	private MongoClient mongoClient = null;

	public MongoDB() {
		List<ServerAddress> saList = new ArrayList<ServerAddress>();

		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.connectionsPerHost(50);// 与目标数据库能够建立的最大connection数量为50
//			builder.autoConnectRetry(true);// 自动重连数据库启动
		// 如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
		builder.threadsAllowedToBlockForConnectionMultiplier(50);
		// 如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
		builder.maxWaitTime(1000 * 60 * 2);
		// 与数据库建立连接的timeout设置为1分钟
		builder.connectTimeout(1000 * 60 * 1);
		MongoClientOptions clientOptions = builder.build();

		// 数据库连接实例
		ServerAddress address1 = new ServerAddress(MONGO_HOST, MONGO_PORT);
		saList.add(address1);
		mongoClient = new MongoClient(saList, clientOptions);

		mongoClient.getDatabase("bert").getCollection("en.embedding").createIndex(new Document("text", 1),
				new IndexOptions().unique(true));// 创建唯一索引
		mongoClient.getDatabase("bert").getCollection("cn.embedding").createIndex(new Document("text", 1),
				new IndexOptions().unique(true));// 创建唯一索引
//		mongoClient.getDatabase("solr").getCollection("patent").createIndex(new Document("_id", 1),
//				new IndexOptions().unique(true));// 创建唯一索引
	}

	// 单例模式
	public static final MongoDB instance = new MongoDB();

	public MongoCollection<Document> getCollection(String dbName, String collectionName) {
		return mongoClient.getDatabase(dbName).getCollection(collectionName);
	}

	public void insert(String dbName, String collectionName, String[] keys, Object[] values) {
		MongoDatabase db = null;
		MongoCollection<Document> dbCollection = null;

		if (keys == null || values == null) {
			return;
		}
		if (keys.length != values.length) {
			return;
		}
		// 获取数据库实例
		db = mongoClient.getDatabase(dbName);
		// 获取数据库中指定的集合
		dbCollection = db.getCollection(collectionName);
		List<Document> documents = new ArrayList<Document>();
		for (int i = 0; i < keys.length; i++) {
			documents.add(new Document(keys[i], values[i]));
		}
		// 插入到mongodb对应的collection中
		dbCollection.insertMany(documents);
	}

	
	public void insert(String dbName, String collectionName, List<Document> documents) {
		MongoDatabase db = null;
		MongoCollection<Document> dbCollection = null;

		// 获取数据库实例
		db = mongoClient.getDatabase(dbName);
		// 获取数据库中指定的集合
		dbCollection = db.getCollection(collectionName);
		// 插入到mongodb对应的collection中
		dbCollection.insertMany(documents);
	}

	public void insert(String dbName, String collectionName, Document insertObj) {
		MongoDatabase db = null;
		MongoCollection<Document> dbCollection = null;

		if (insertObj == null) {
			return;
		}
		// 获取数据库实例
		db = mongoClient.getDatabase(dbName);
		// 获取数据库中指定的结合
		dbCollection = db.getCollection(collectionName);

		dbCollection.insertOne(insertObj);
	}

	public List<Document> find(String dbName, String collectionName, String[] keys, Object[] values, int num) {
		MongoDatabase db = null;
		MongoCollection<Document> dbCollection = null;
		FindIterable<Document> cursor = null;
		List<Document> resultList = new ArrayList<Document>();

		if (keys == null || values == null) {
			return resultList;
		}
		if (keys.length != values.length) {
			return resultList;
		}
		db = mongoClient.getDatabase(dbName);
		dbCollection = db.getCollection(collectionName);
		// 载入查询条件
		Document queryObject = new Document();
		for (int i = 0; i < keys.length; i++) {
			queryObject.put(keys[i], values[i]);
		}
		// 返回DBCursor对象
		cursor = dbCollection.find(queryObject);

		// 判断是否返回所有的数据，num=-1：返回查询全部数据，num!= -1:返回指定的num数据
		if (num != -1) {
			for (Document t : cursor) {
				resultList.add(t);
				if (resultList.size() >= num)
					break;
			}
		} else {
			for (Document t : cursor) {
				resultList.add(t);
			}
		}
		return resultList;
	}

	public long getCollectionCount(String dbName, String collectionName) {
		MongoDatabase db = null;
		MongoCollection<Document> dbCollection = null;
		db = mongoClient.getDatabase(dbName);
		dbCollection = db.getCollection(collectionName);
		return dbCollection.count();
	}

	public long getCount(String dbName, String collectionName, Document obj) {
		MongoDatabase db = mongoClient.getDatabase(dbName);
		MongoCollection<Document> dbCollection = db.getCollection(collectionName);
		if (obj != null) {
			return dbCollection.count(obj);
		} else {
			return dbCollection.count();
		}
	}

	public List<Document> find(String dbName, String collectionName, Document query) {
		MongoDatabase db = mongoClient.getDatabase(dbName);
		MongoCollection<Document> dbCollection = null;
		FindIterable<Document> dbCursor = null;
		dbCollection = db.getCollection(collectionName);
		dbCursor = dbCollection.find(query);
		return dbCursor2List(dbCursor);
	}

	public Document findOne(String dbName, String collectionName, Document query) {
		return mongoClient.getDatabase(dbName).getCollection(collectionName).find(query).first();
	}

	/**
	 * 查询DBCursor转换为List
	 */
	public List<Document> dbCursor2List(FindIterable<Document> dbCursor) {
		List<Document> list = new ArrayList<Document>();
		if (dbCursor != null) {
			for (Document t : dbCursor) {
				list.add(t);
			}
		}
		return list;

	}

	public List<Document> find(String dbName, String collectionName, Document query, Document sort) {
		MongoDatabase db = mongoClient.getDatabase(dbName);
		MongoCollection<Document> dbCollection = null;
		FindIterable<Document> dbCursor = null;
		dbCollection = db.getCollection(collectionName);
		if (query != null) {
			dbCursor = dbCollection.find(query);
		} else {
			dbCursor = dbCollection.find();
		}
		if (sort != null) {
			dbCursor.sort(sort);
		}
		return dbCursor2List(dbCursor);
	}

	public List<Document> find(String dbName, String collectionName, Document query, Document sort, int start,
			int limit) {
		MongoDatabase db = null;
		MongoCollection<Document> dbCollection = null;
		FindIterable<Document> dbCursor = null;

		db = mongoClient.getDatabase(dbName);
		dbCollection = db.getCollection(collectionName);
		if (query != null) {
			dbCursor = dbCollection.find(query);
		} else {
			dbCursor = dbCollection.find();
		}
		if (sort != null) {
			dbCursor.sort(sort);
		}
		if (start < 0) {
			dbCursor.batchSize(start);
		} else {
			dbCursor.skip(start).limit(limit);
		}
		return dbCursor2List(dbCursor);
	}

	public double[][] acquireWordEmbedding(String lang, String[] text) {
		double[][] embedding = new double[text.length][];
		Thread thread = acquireWordEmbedding(lang, text, embedding);
		if (thread != null) {
			thread.run();
		}
		return embedding;
	}

	public Thread acquireWordEmbedding(String lang, String[] text, double[][] embedding) {
		int[] indices = selectWordEmbedding(lang, text, embedding);

		Thread thread = null;
		if (indices.length > 0) {
			String[] unknownWords = new String[indices.length];
			int j = 0;
			for (int i : indices) {
				unknownWords[j++] = text[i];
			}

			double[][] unknownEmbedding = lang.equals("cn") ? Native.lexiconEmbeddingCN(unknownWords)
					: Native.lexiconEmbeddingEN(unknownWords);
			int index = 0;
			for (int i : indices) {
				embedding[i] = unknownEmbedding[index++];
			}

			thread = new Thread(() -> {
				MongoDB.instance.insertWordEmbedding(lang, unknownWords, unknownEmbedding);
			});
		}

		return thread;
	}

//	https://docs.mongodb.com/manual/reference/operator/query/in/
	public int[] selectWordEmbedding(String lang, String[] text, double[][] embedding) {
		MongoDatabase db = mongoClient.getDatabase("bert");
		MongoCollection<Document> dbCollection = db.getCollection(lang + ".embedding");

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int index = 0;
		for (String t : text) {
			map.put(t, index++);
		}

		int non_zero_length = 0;
		for (Document t : dbCollection.find(new Document("text", new Document("$in", map.keySet())))) {
			embedding[map.get(t.get("text"))] = Utility.dejsonify((String) t.get("embedding"), double[].class);
			++non_zero_length;
		}

		int zero_indices[] = new int[embedding.length - non_zero_length];
		index = 0;
		for (int i = 0; i < embedding.length; ++i) {
			if (embedding[i] == null) {
				zero_indices[index++] = i;
			}
		}

//		System.out.println("zero_indices = " + Utility.toString(zero_indices, ",", "[]", zero_indices.length));
		return zero_indices;
	}

	synchronized public void insertWordEmbedding(String lang, String[] text, double[][] embedding) {
		MongoDatabase db = mongoClient.getDatabase("bert");
		MongoCollection<Document> dbCollection = db.getCollection(lang + ".embedding");

		List<Document> documents = new ArrayList<Document>();
		for (int i = 0; i < embedding.length; i++) {
			Document query = new Document().append("text", text[i]);
			if (dbCollection.find(query).first() == null)
				documents.add(query.append("embedding", Utility.jsonify(embedding[i])));
		}

		dbCollection.insertMany(documents, new InsertManyOptions().ordered(false));
	}
}
//mongoDB usage:
//use bert
//db.embedding.find().pretty()
//db.embedding.find().count()
//db.embedding.remove({text:/.*/})
//db.embedding.find({text:/导航/}).count()