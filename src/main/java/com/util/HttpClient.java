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
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

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
//				System.out.println(str);
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

	static SolrDocumentList solr(String q, String fl, int rows) throws IOException, SolrServerException {
		SolrQuery query = new SolrQuery();

		query.set("fl", fl);
		query.set("q", q);
		query.set("rows", rows);

		QueryResponse response = solrServer.query(query, METHOD.POST);

		return response.getResults();
	}

	static HttpSolrClient solrServer = new HttpSolrClient.Builder(
			"http://s-gateway-qa.k8s.zhihuiya.com/s-search-patent-solr/patsnap/PATENT/").withConnectionTimeout(10000)
					.withSocketTimeout(60000).build();

//	static String s_search_patent_solr = "http://s-gateway-qa.k8s.zhihuiya.com/s-search-patent-solr/patsnap/PATENT/select";

	public static List<SolrDocument> solr_with_keyword(String language, String keyword, int rows)
			throws IOException, SolrServerException {
		String TTL = "TTL";
		String ABST = "ABST";

		language = language.toUpperCase();
		if (!language.equals("EN")) {
			TTL = "TTL_" + language;
			ABST = "ABST_" + language;
		}

//		keyword = keyword.replaceAll("\\s+", "%20");
		List<SolrDocument> docs = solr(String.format("%s:%s or %s:%s", TTL, keyword, ABST, keyword),
				String.format("%s,%s,_id", TTL, ABST), rows);
		for (SolrDocument dict : docs) {
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

	public static void main(String[] args) throws Exception {
	}
}
