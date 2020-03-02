package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import com.util.HttpClientGet;
import com.util.Utility;

/**
 * @author hankcs
 */
public class Test {
	static Set<String> loadDictionary(String path) throws IOException {
		Set<String> dictionary = new TreeSet<String>();
		new Utility.Text(path).collect(dictionary);
		return dictionary;
	}

	static String loadText(String path) throws IOException {
		return new Utility.Text(path).fetchContent();
	}

	public static void main(String[] args) throws Exception {
		String keyword = "medicine";
//		String q = String.format("TTL:%s OR ABST:%s", keyword, keyword);
//		String q_ml = String.format("TTL_MT:%s OR ABST_MT:%s", keyword, keyword);
//		String q_cn = String.format("TTL_CN:%s OR ABST_CN:%s", keyword, keyword);
//		String q_cn_ml = String.format("TTL_MT_CN:%s OR ABST_MT_CN:%s", keyword, keyword);

//		String url = String.format(
//				"http://s-gateway-qa.k8s.zhihuiya.com/s-search-patent-solr/patsnap/PATENT/select?fl=TTL,ABST&q=%s&rows=1000",
//				q);
//		System.out.println(url);
		
		HashMap<String, Object> result = HttpClientGet.solr_with_keyword(keyword, 10000);
		
		System.out.println(result);	
		
	}
}
//https://lucene.apache.org/solr/guide/7_7/result-clustering#clustering-concepts
//download.carrotsearch.com/lingo3g/manual/
//https://get.carrotsearch.com/lingo3g/manual/


