package com.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientGet {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> sendGet(String path, Map<String, String> parameters) throws IOException {

		ArrayList<String> list = new ArrayList<String>();
		for (Entry<String, String> p : parameters.entrySet()) {
			list.add(String.format("%s=%s", p.getKey(), p.getValue()));
		}

		String sendUrl = String.format("%s?%s", path, String.join("&", list));

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
		}
		return mres;
	}

	@SuppressWarnings("unchecked")
	static ArrayList<Object> solr(String path, String q, String fl, int rows) throws IOException {
		HashMap<String, String> parameters = new HashMap<String, String>();

		parameters.put("fl", fl);
		parameters.put("q", q);
		parameters.put("rows", String.valueOf(rows));

		Map<String, Object> resultGet = sendGet(path, parameters);
		Map<String, Object> response = (Map<String, Object>) resultGet.get("response");
		ArrayList<Object> docs = (ArrayList<Object>) response.get("docs");
//		System.out.println(docs.size());
		return docs;
	}

	static String s_search_patent_solr = "http://s-gateway-qa.k8s.zhihuiya.com/s-search-patent-solr/patsnap/PATENT/select";

	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> solr_with_keyword(String keyword, int rows) throws IOException {
		ArrayList<Object> docs = solr(s_search_patent_solr, String.format("TTL:%s%%20OR%%20ABST:%s", keyword, keyword),
				"TTL,ABST,_id", rows);
		HashMap<String, Object> map = new HashMap<String, Object>();		
		for (Object object : docs) {
			HashMap<String, Object> obj = (HashMap<String, Object>) object;
			String _id = (String) obj.get("_id");
			obj.remove(_id);
			map.put(_id, obj);
		}
		return map;
	}

	public static HashMap<String, Object> solr_with_keyword(String keyword) throws IOException {
		return solr_with_keyword(keyword, 10000);
	}

	public static void main(String[] args) throws Exception {
		String keyword = "medicine";

		System.out.println("start with solr");
		long start = System.currentTimeMillis();
		HashMap<String, Object> docs = solr_with_keyword(keyword, 10000);
		System.out.println(docs.size());
		long end = System.currentTimeMillis();
		System.out.println("time cost in solr = " + (end - start));
//		System.out.println(docs);
	}
}
