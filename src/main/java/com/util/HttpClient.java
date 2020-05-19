package com.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.SystemUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.bson.Document;

public class HttpClient {

	public static String fullURL(String path, Map<String, String> parameters) {
		ArrayList<String> list = new ArrayList<String>();
		for (Entry<String, String> p : parameters.entrySet()) {
			list.add(String.format("%s=%s", p.getKey(), p.getValue()));
		}

		return String.format("%s?%s", path, String.join("&", list));
	}

	@SuppressWarnings("deprecation")
	public static String post(String url, Map<String, String> parameters) {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
			List<NameValuePair> list = new ArrayList<>();

			for (Entry<String, String> p : parameters.entrySet()) {
				BasicNameValuePair basicNameValuePair = new BasicNameValuePair(p.getKey(), p.getValue());
				list.add(basicNameValuePair);
			}

			httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));

			try (CloseableHttpResponse response = client.execute(httpPost)) {
				HttpEntity entity = response.getEntity();
				String str = EntityUtils.toString(entity, "UTF-8");
//				System.out.println(str);
				return str;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> postList(String url, Map<String, String> parameters) {
		return Utility.dejsonify(post(url, parameters), List.class);
	}

	public static List<Map<String, Object>> getList(String path, Map<String, String> parameters) {
		return sendGetList(fullURL(path, parameters));
	}

	public static Map<String, Object> getMap(String path, Map<String, String> parameters) {
		return sendGetMap(fullURL(path, parameters));
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> sendGetMap(String sendUrl) {
		Map<String, Object> mres = new HashMap<String, Object>();
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet httpGet = new HttpGet(sendUrl);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
					.setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
			httpGet.setConfig(requestConfig);

			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "UTF-8");

			mres = Utility.dejsonify(result, Map.class);
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mres;
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> sendGetList(String sendUrl) {
		List<Map<String, Object>> mres = new ArrayList<Map<String, Object>>();
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet httpGet = new HttpGet(sendUrl);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
					.setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
			httpGet.setConfig(requestConfig);

			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "UTF-8");

			mres = Utility.dejsonify(result, List.class);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return mres;
	}

	public static SolrDocumentList solr(String q, String fl, int rows) throws IOException, SolrServerException {
		SolrQuery query = new SolrQuery();

		query.set("fl", fl);
		query.set("q", q);
		query.set("rows", rows);

		QueryResponse response = solrServer.query(query, METHOD.POST);

		return response.getResults();
	}

	public static List<Map<String, Object>> solr_with_agent(String q, String fl, int rows)
			throws IOException, SolrServerException {
		Map<String, String> args = new HashMap<String, String>();

		args.put("q", q.replace(":", "%3A").replace(" ", "%20"));
		args.put("fl", fl);
		args.put("rows", String.valueOf(rows));

		return getList("http://192.168.3.133:8080/semantic/algorithm/solr/select", args);
	}

	public static List<Map<String, Object>> solr_with_agent_post(String q, String fl, int rows)
			throws IOException, SolrServerException {
		Map<String, String> args = new HashMap<String, String>();

		args.put("q", q);
		args.put("fl", fl);
		args.put("rows", String.valueOf(rows));

		return postList("http://192.168.3.133:8080/semantic/algorithm/solr/select", args);
	}

	static HttpSolrClient solrServer = new HttpSolrClient.Builder(
			"http://s-gateway-qa.k8s.zhihuiya.com/s-search-patent-solr/patsnap/PATENT/").withConnectionTimeout(10000)
					.withSocketTimeout(60000).build();

//	static String s_search_patent_solr = "http://s-gateway-qa.k8s.zhihuiya.com/s-search-patent-solr/patsnap/PATENT/select";

	public static List<? extends Map<String, Object>> solr_with_keyword(String language, String keyword, int rows)
			throws IOException, SolrServerException {
		String TTL = "TTL";
		String ABST = "ABST";

		language = language.toUpperCase();
		if (!language.equals("EN")) {
			TTL = "TTL_" + language;
			ABST = "ABST_" + language;
		}

		String q = String.format("%s:%s or %s:%s", TTL, keyword, ABST, keyword);

		List<? extends Map<String, Object>> docs;
		if (SystemUtils.IS_OS_WINDOWS) {
			List<String> unknownPatent = new ArrayList<String>();
			List<Map<String, Object>> retrievedPatent = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> dict : solr_with_agent(q, "_id", rows)) {

				Document t = MongoDB.instance.findOne("solr", "patent", new Document(dict));
				if (t == null) {
					unknownPatent.add((String) dict.get("_id"));
				} else {
					dict.put("TTL", t.get(TTL));
					dict.put("ABST", t.get(ABST));
					retrievedPatent.add(dict);
				}
			}

			if (!unknownPatent.isEmpty()) {
				List<Document> list = new ArrayList<Document>();
				for (Map<String, Object> t : solr_with_id(unknownPatent)) {
					list.add(new Document(t));
				}

				MongoDB.instance.insert("solr", "patent", list);
			}

			return retrievedPatent;
		} else {
			String fl = String.format("%s,%s,_id", TTL, ABST);
			docs = solr(q, fl, rows);
		}

		for (Map<String, Object> dict : docs) {
			dict.put("TTL", dict.remove(TTL));
			dict.put("ABST", dict.remove(ABST));
		}
		return docs;
	}

	public static List<SolrDocument> solr_with_id(String language, List<String> _id)
			throws IOException, SolrServerException {
		String TTL = "TTL";
		String ABST = "ABST";

		language = language.toUpperCase();
		if (!language.equals("EN")) {
			TTL = "TTL_" + language;
			ABST = "ABST_" + language;
		}

		List<SolrDocument> docs = solr(String.format("_id:(%s)", String.join(" or ", _id)),
				String.format("%s,%s,_id", TTL, ABST), _id.size());
		for (SolrDocument dict : docs) {
			dict.put("TTL", dict.remove(TTL));
			dict.put("ABST", dict.remove(ABST));
		}
		return docs;
	}

	public static List<? extends Map<String, Object>> solr_with_id(List<String> _id)
			throws IOException, SolrServerException {
		String TTL = "TTL";
		String ABST = "ABST";

		String TTL_CN = "TTL_CN";
		String ABST_CN = "ABST_CN";

		String q = String.format("_id:(%s)", String.join(" or ", _id));
		String fl = String.format("%s,%s,%s,%s,_id", TTL, ABST, TTL_CN, ABST_CN);
		int rows = _id.size();

		if (SystemUtils.IS_OS_WINDOWS) {
			return solr_with_agent_post(q, fl, rows);
		} else {
			return solr(q, fl, rows);
		}
	}

	public static void main(String[] args) throws Exception {
	}
}
