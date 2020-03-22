package com.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

public class HttpClient {

	public static String fullURL(String path, Map<String, String> parameters) {
		ArrayList<String> list = new ArrayList<String>();
		for (Entry<String, String> p : parameters.entrySet()) {
			list.add(String.format("%s=%s", p.getKey(), p.getValue()));
		}

		return String.format("%s?%s", path, String.join("&", list));
	}

	@SuppressWarnings("deprecation")
	public static String HttpClientPost(String url, Map<String, String> parameters) {
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
				System.out.println(str);
				return str;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;

	}

	public static Map<String, Object> sendGet(String path, Map<String, String> parameters) {
		return sendGet(fullURL(path, parameters));
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> sendGet(String sendUrl) {
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
	static List<Object> solr(String path, String q, String fl, int rows) throws IOException {
		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("fl", fl);
		parameters.put("q", q);
		parameters.put("rows", String.valueOf(rows));

		Map<String, Object> resultGet = sendGet(path, parameters);
		Map<String, Object> response = (Map<String, Object>) resultGet.get("response");
		if (response == null) {
			return new ArrayList<Object>();
		}
		return (List<Object>) response.get("docs");
	}

	static String solr_path(String path, String q, String fl, int rows) {
		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("fl", fl);
		parameters.put("q", q);
		parameters.put("rows", String.valueOf(rows));

		return fullURL(path, parameters);
	}

	static String s_search_patent_solr = "http://s-gateway-qa.k8s.zhihuiya.com/s-search-patent-solr/patsnap/PATENT/select";

	public static String solr_keyword_address(String language, String keyword, int rows) {
		String TTL = "TTL";
		String ABST = "ABST";

		language = language.toUpperCase();
		if (!language.equals("EN")) {
			TTL = "TTL_" + language;
			ABST = "ABST_" + language;
		}

		keyword = keyword.replaceAll("\\s+", "%20");
		return solr_path(s_search_patent_solr, String.format("%s:%s%%20OR%%20%s:%s", TTL, keyword, ABST, keyword),
				String.format("%s,%s,_id", TTL, ABST), rows);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, String>> solr_with_keyword(String language, String keyword, int rows)
			throws IOException {
		String TTL = "TTL";
		String ABST = "ABST";

		language = language.toUpperCase();
		if (!language.equals("EN")) {
			TTL = "TTL_" + language;
			ABST = "ABST_" + language;
		}

		keyword = keyword.replaceAll("\\s+", "%20");
		List<Map<String, String>> docs = (List) solr(s_search_patent_solr,
				String.format("%s:%s%%20OR%%20%s:%s", TTL, keyword, ABST, keyword),
				String.format("%s,%s,_id", TTL, ABST), rows);
		for (Object object : docs) {
			Map<String, Object> dict = (Map<String, Object>) object;
			dict.put("TTL", dict.remove(TTL));
			dict.put("ABST", dict.remove(ABST));
		}
		return docs;
	}

	public static void main(String[] args) throws Exception {
		String keyword = "medicine";

		System.out.println("start with solr");
		long start = System.currentTimeMillis();
		List<Map<String, String>> docs = solr_with_keyword("English", keyword, 10000);
		System.out.println(docs.size());
		long end = System.currentTimeMillis();
		System.out.println("time cost in solr = " + (end - start));
//		System.out.println(docs);
	}
}
