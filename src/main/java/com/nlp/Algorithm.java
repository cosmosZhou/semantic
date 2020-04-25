package com.nlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.ahocorasick.trie.Trie;
import org.apache.commons.lang.SystemUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.ini4j.ConfigParser.InterpolationException;
import org.ini4j.ConfigParser.NoOptionException;
import org.ini4j.ConfigParser.NoSectionException;

import com.deeplearning.CWSTagger;
import com.deeplearning.NERTaggerDict;
import com.deeplearning.Service;
import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import com.patsnap.core.analysis.manager.CarrotManager;
import com.patsnap.core.analysis.manager.CarrotManager.ClusteringResult;
import com.servlet.Python;
import com.util.HttpClient;
import com.util.HttpClientWebApp;
import com.util.MySQL;
import com.util.Native;
import com.util.Utility;

//compile -Pprod install -Pprod
//sh tomcat/bin/startup.sh 
//tail -100f tomcat/logs/catalina.out
//http://192.168.3.133:9000/semantic/algorithm/ahocorasick/test
//http://192.168.3.133:8080/semantic/solr.jsp
//http://localhost:8080/semantic/solr.jsp
//http://localhost:8080/semantic/algorithm/ahocorasick/test
@Path("algorithm")
public class Algorithm {
	static {
		if (SystemUtils.IS_OS_WINDOWS) {
			System.out.println("SystemUtils.IS_OS_WINDOWS");
			Utility.workingDirectory = "d:/360/solution/";
		} else {
			System.out.println("SystemUtils.IS_OS_LINUX");
			Utility.workingDirectory = "/home/zhoulizhi/solution/";
		}
	}

	@GET
	@Path("ahocorasick/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String ahocorasick(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		return Utility.jsonify(AhoCorasick.instance.ahocorasick(text, service));
	}

	@GET
	@Path("_ahocorasick/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String _ahocorasick(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		return Utility.jsonify(AhoCorasick.instance._ahocorasick(text, service));
	}

	@POST
	@Path("ahocorasick")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String ahocorasick(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");

		String service = request.getParameter("service");
		return Utility.jsonify(AhoCorasick.instance.ahocorasick(text, service));
	}

	@POST
	@Path("_ahocorasick")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String _ahocorasick(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");

		String service = request.getParameter("service");
		return Utility.jsonify(AhoCorasick.instance._ahocorasick(text, service));
	}

	@POST
	@Path("random_select")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String random_select(@Context HttpServletRequest request) throws Exception {

		String service = request.getParameter("service");
//		System.out.println("service = " + service);

		String exclude = request.getParameter("exclude");
//		System.out.println("exclude = " + exclude);

		String slot = request.getParameter("slot");
//		System.out.println("slot = " + slot);

		return AhoCorasick.instance.random_select(service, slot, exclude);
	}

	@GET
	@Path("python/ner/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String python_ner(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		String intent = Utility.jsonify(AhoCorasick.instance.ahocorasick(text, service));
		return HttpClientWebApp.instance.ner(text, service, intent);
	}

	@GET
	@Path("python/ner/{text}")
	@Produces("text/plain;charset=utf-8")
	public String python_ner(@PathParam("text") String text) throws Exception {
		String service = HttpClientWebApp.instance.service(text);
		String intent = Utility.jsonify(AhoCorasick.instance.ahocorasick(text, service));
		return HttpClientWebApp.instance.ner(text, service, intent);
	}

	@GET
	@Path("_python/ner/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String _python_ner(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		String intent = Utility.jsonify(AhoCorasick.instance._ahocorasick(text, service));
		return HttpClientWebApp.instance.ner(text, service, intent);
	}

	@POST
	@Path("python/ner")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String python_ner(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");
		String service = request.getParameter("service");
		if (service == null) {
			service = HttpClientWebApp.instance.service(text);
		}

		String intent = Utility.jsonify(AhoCorasick.instance.ahocorasick(text, service));
		return HttpClientWebApp.instance.ner(text, service, intent);
	}

	@GET
	@Path("java/cws/{text}")
	@Produces("text/plain;charset=utf-8")
	public String cws(@PathParam("text") String text) throws Exception {
		return Utility.jsonify(CWSTagger.instance.cut(text));
	}

	@POST
	@Path("java/cws")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String cws(@Context HttpServletRequest request) throws Exception {
		return Utility.jsonify(CWSTagger.instance.cut(request.getParameter("text")));
	}

//	http://127.0.0.1:8080/nlp/algorithm/java/ner/map/播放下一站下一站传奇城乘风破浪
	@GET
	@Path("java/ner/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String java_ner(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		return Utility.jsonify(NERTaggerDict.predict(service, text, false));
	}

	@GET
	@Path("_java/ner/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String _java_ner(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		return Utility.jsonify(NERTaggerDict._predict(service, text, false));
	}

	@GET
	@Path("java/ner/{text}")
	@Produces("text/plain;charset=utf-8")
	public String java_ner(@PathParam("text") String text) throws Exception {

		String service = Service.instance.predict(text);

		if (service.indexOf('.') >= 0) {
			String[] service_code = service.split("\\.");
			service = service_code[0];
		}

		return Utility.jsonify(NERTaggerDict.predict(service, text, false));
	}

	@POST
	@Path("java/ner")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String java_ner(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");
		String service = request.getParameter("service");
		if (service == null) {
			service = Service.instance.predict(text);
		}

		return Utility.jsonify(NERTaggerDict.predict(service, text, false));
	}

	@POST
	@Path("_java/ner")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String _java_ner(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");
		String service = request.getParameter("service");
		if (service == null) {
			service = Service.instance.predict(text);
		}

		return Utility.jsonify(NERTaggerDict._predict(service, text, false));
	}

	@GET
	@Path("python/service/{text}")
	@Produces("text/plain;charset=utf-8")
	public String python_service(@PathParam("text") String text) throws Exception {
		return HttpClientWebApp.instance.service(text);
	}

	@POST
	@Path("python/service")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String python_service(@Context HttpServletRequest request) throws Exception {
		return HttpClientWebApp.instance.service(request.getParameter("text"));
	}

	@POST
	@Path("python")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String python(@Context HttpServletRequest request) throws Exception {
		String script = request.getReader().readLine();
		return Python.eval(script);
	}

	// http://127.0.0.1:8080/nlp/algorithm/java/service/播放下一站下一站传奇城乘风破浪
	@GET
	@Path("java/service/{text}")
	@Produces("text/plain;charset=utf-8")
	public String java_service(@PathParam("text") String text) throws Exception {
		return Service.instance.predict(text);
	}

//	http://127.0.0.1:8080/nlp/algorithm/cpp/service/播放下一站下一站传奇城乘风破浪
	@GET
	@Path("cpp/service/{text}")
	@Produces("text/plain;charset=utf-8")
	public String cpp_service(@PathParam("text") String text) throws Exception {
		return Service.instance.cpp_predict(text);
	}

	@POST
	@Path("cpp/service")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String cpp_service(@Context HttpServletRequest request) throws Exception {
		return Service.instance.cpp_predict(request.getParameter("text"));
	}

//	http://127.0.0.1:8080/nlp/algorithm/cpp/ner/map/导航去山西省万荣县通化镇通化三村朝阳巷204号
	@GET
	@Path("cpp/ner/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String cpp_ner(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		return Utility.jsonify(NERTaggerDict.predict(service, text, true));
	}

//	http://127.0.0.1:8080/nlp/algorithm/cpp/ner/map/导航去山西省万荣县通化镇通化三村朝阳巷204号
	@GET
	@Path("cpp/ner/{text}")
	@Produces("text/plain;charset=utf-8")
	public String cpp_ner(@PathParam("text") String text) throws Exception {
		String service = Service.instance.cpp_predict(text);

		if (service.indexOf('.') >= 0) {
			String[] service_code = service.split("\\.");
			service = service_code[0];
		}

		return Utility.jsonify(NERTaggerDict.predict(service, text, true));
	}

	@POST
	@Path("cpp/ner")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String cpp_ner(@Context HttpServletRequest request) throws Exception {
		String service = request.getParameter("service");
		String text = request.getParameter("text");
		if (service == null) {
			service = Service.instance.cpp_predict(text);
		}
		return Utility.jsonify(NERTaggerDict.predict(service, text, true));
	}

	@POST
	@Path("_cpp/ner")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String _cpp_ner(@Context HttpServletRequest request) throws Exception {
		String service = request.getParameter("service");
		String text = request.getParameter("text");
		if (service == null) {
			service = Service.instance.cpp_predict(text);
		}

		return Utility.jsonify(NERTaggerDict._predict(service, text, true));
	}

	// http://127.0.0.1:8080/nlp/algorithm/java/_service/播放下一站下一站传奇城乘风破浪
	@GET
	@Path("_java/service/{text}")
	@Produces("text/plain;charset=utf-8")
	public String _java_service(@PathParam("text") String text) throws Exception {
		return Utility.jsonify(Service.instance._predict(text));
	}

	@POST
	@Path("java/service")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String java_service(@Context HttpServletRequest request) throws Exception {
		return Service.instance.predict(request.getParameter("text"));
	}

	@POST
	@Path("_java/service")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String _java_service(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");
		if (text != null)
			return Utility.jsonify(Service.instance._predict(text));
		String label = request.getParameter("label");
		if (label != null)
			return Utility.jsonify(Service.instance.label);
		return null;
	}

	@GET
	@Path("ahocorasick/test")
	@Produces("text/plain;charset=utf-8")
	public String ahocorasick_test() throws Exception {
		String path2Dictionary = Utility.workingDirectory + "corpus/ahocorasick/cn/dictionary.txt";

		TreeMap<String, String> dictionaryMap = new TreeMap<String, String>();

		for (String word : new Utility.Text(path2Dictionary).collect(new ArrayList<String>())) {
			dictionaryMap.put(word, String.format("[%s]", word));
		}

		HashMap<String, Object> json = new HashMap<String, Object>();

		Trie<String> ahoCorasickNaive = new Trie<String>(dictionaryMap);

		String text = new Utility.Text(Utility.workingDirectory + "corpus/ahocorasick/cn/text.txt").fetchContent();

		System.out.println("text.length() = " + text.length());
		long countNative;
		{
			Native.initializeAhocorasickDictionary(path2Dictionary);
			long start = System.currentTimeMillis();
			countNative = Native.parseText(text).length;
			long cost = System.currentTimeMillis() - start;

			HashMap<String, Object> jsonNative = new HashMap<String, Object>();
			jsonNative.put("count", countNative);
			jsonNative.put("cost", cost);
			json.put("nativeImplementation", jsonNative);
		}

		int countNaive;
		{
			long start = System.currentTimeMillis();
			countNaive = ahoCorasickNaive.parseText(text).size();
			long cost = System.currentTimeMillis() - start;

			HashMap<String, Object> jsonNaive = new HashMap<String, Object>();
			jsonNaive.put("count", countNaive);
			jsonNaive.put("cost", cost);
			json.put("naiveImplementation", jsonNaive);
			System.out.println("naive cost time       = " + cost);
		}
		long countDoubleArray;
		{
			AhoCorasickDoubleArrayTrie<String> ahoCorasickDoubleArrayTrie = new AhoCorasickDoubleArrayTrie<String>(
					dictionaryMap);

			long start = System.currentTimeMillis();
			countDoubleArray = ahoCorasickDoubleArrayTrie.parseText(text).size();
			long cost = System.currentTimeMillis() - start;

			HashMap<String, Object> jsonDoubleArray = new HashMap<String, Object>();
			jsonDoubleArray.put("count", countDoubleArray);
			jsonDoubleArray.put("cost", cost);
			json.put("DoubleArrayImplementation", jsonDoubleArray);
			System.out.println("DoubleArray cost time = " + cost);
		}
		System.out.println("count Naive       = " + countNaive);
		System.out.println("count Native      = " + countNative);
		System.out.println("count DoubleArray = " + countDoubleArray);
		return Utility.jsonify(json);
	}

	@GET
	@Path("ahocorasick/test/native")
	@Produces("text/plain;charset=utf-8")
	public String ahocorasick_test_native() throws Exception {
		String path2Dictionary = Utility.workingDirectory + "corpus/ahocorasick/en/dictionary.txt";
		Native.initializeAhocorasickDictionary(path2Dictionary);

		Native.ahocorasickTest();
		return "true";
	}

	@GET
	@Path("carrot2/ling3g/{language}/{keyword}")
	@Produces("text/plain;charset=utf-8")
	public String carrot2_ling3g(@PathParam("language") String language, @PathParam("keyword") String keyword)
			throws Exception {
		return Utility.jsonify(CarrotManager.instance.getClusteringResult(language, keyword, 10000));
	}

	@GET
	@Path("mysql/{sql}")
	@Produces("text/plain;charset=utf-8")
	public String mysql(@PathParam("sql") String mysql) {
		if (mysql.startsWith("select"))
			return Utility.jsonify(MySQL.instance.select(mysql));
		return String.valueOf(MySQL.instance.execute(mysql));
	}

	@POST
	@Path("mysql")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String mysql(@Context HttpServletRequest request) throws Exception {
		String mysql = request.getReader().readLine();
		System.out.println("mysql = " + mysql);

		if (mysql.startsWith("select"))
			return Utility.jsonify(MySQL.instance.select(mysql));
		return String.valueOf(MySQL.instance.execute(mysql));
	}

	@POST
	@Path("mysql/batch")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String mysql_batch(@Context HttpServletRequest request) throws Exception {
		String sql = request.getReader().readLine();
		System.out.println("mysql_batch: " + sql);
		String[] statements = Utility.dejsonify(sql, String[].class);

		return String.valueOf(MySQL.instance.execute(statements));
	}

	@POST
	@Path("keyword")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String keyword(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");
		switch (request.getParameter("lang").toLowerCase()) {
		case "cn":
			return String.valueOf(Native.keywordCN(text));
		case "en":
			return String.valueOf(Native.keywordEN(text));
		default:
			return null;
		}
	}

	@POST
	@Path("cache")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String cache(@Context HttpServletRequest request) throws IOException, SolrServerException {
		String text = request.getParameter("text");
		String lang = request.getParameter("lang");
		System.out.println("text = " + text);
		int cache_index = Integer.parseInt(request.getParameter("cache_index"));

		List<String> ids = CarrotManager.ClusteringResult.cache[cache_index].get(text);
		System.out.println("CarrotManager.ClusteringResult.cache[cache_index].get(text).size() = " + ids.size());

		if (ids.size() > 512) {
			ids = ids.subList(0, 512);
		}
		List<SolrDocument> result = HttpClient.solr_with_id(lang, ids);

		return Utility.jsonify(result);
	}

	@POST
	@Path("segment")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String segment(@Context HttpServletRequest request) throws Exception {
		String text = request.getReader().readLine();
		List<Map<String, Object>> result = MySQL.instance
				.select_from("select seg, training from tbl_segment_cn where text = '%s'", Utility.quote_mysql(text));

		Map<String, Object> map;
		if (result.isEmpty()) {
			map = new HashMap<String, Object>();
			map.put("seg", String.join(" ", Native.segmentCN(text)));
			map.put("training", "+" + new Random().nextInt(2));
		} else {
			map = result.get(0);
		}

		return Utility.jsonify(map);
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("syntax")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String syntax(@Context HttpServletRequest request) throws Exception {
		String text = request.getReader().readLine();
		List<Map<String, Object>> result = MySQL.instance.select_from("select* from tbl_syntax_cn where text = '%s'",
				Utility.quote_mysql(text));

		Map<String, Object> map;
		if (result.isEmpty()) {
			String seg[] = Native.segmentCN(text);
			String pos[] = Native.posCN(seg);

			for (int i = 0; i < pos.length; i++) {
				pos[i] = String.format("'%s'", pos[i]);
				seg[i] = String.format("'%s'", Utility.quote(seg[i]));
			}

			String json = Python.eval(String.format("compiler.parse_from_segment([%s], [%s]).__str__(return_dict=True)",
					String.join(",", seg), String.join(",", pos)));

			map = Utility.dejsonify(json, HashMap.class);

			map.put("training", "+" + new Random().nextInt(2));
		} else {
			map = result.get(0);
			String infix = (String) map.get("infix");

			HashMap<String, Object> dict = Utility.dejsonify(
					Python.eval(
							String.format("compiler.compile('%s').__str__(return_dict=True)", Utility.quote(infix))),
					HashMap.class);
			map.putAll(dict);
		}

		System.out.println("dict = " + map);
		return Utility.jsonify(map);
	}

	@POST
	@Path("clustering")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String clustering(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");
		String lang = request.getParameter("lang");
		int rows = Integer.parseInt(request.getParameter("rows"));
		ClusteringResult result = CarrotManager.instance.getClusteringResult(lang, text, rows);
		return Utility.jsonify(result);
	}

	@GET
	@Path("training/{table}")
	@Produces("text/plain;charset=utf-8")
	public String training(@PathParam("table") String table)
			throws NoSectionException, NoOptionException, InterpolationException, IOException, InterruptedException {
		Python.training(table);
		return "true";
	}
}