package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


import com.util.HttpClient;
import com.util.Utility;
import com.util.regex.Matcher;
import com.util.regex.Pattern;

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
		String _x = "(?<=\\w\\w)(平乡县|平乡镇)|(?<=平乡县|平乡镇)(\\w\\w)";
		Matcher m = Pattern.compile("\\((?!\\?)").matcher(_x);
//		Matcher m = Pattern.compile("\\((?=[^?])").matcher(_x);
		while (m.find()) {
			System.out.println("m.start() = " + m.start());
			System.out.println("m.end() = " + m.end());
			System.out.println(m.group());
		}

		String regex = "((?:^|(?<=[^一-龥a-zA-Zａ-ｚＡ-Ｚ0-9０-９]))规则) +(库(?:$|(?=[^一-龥a-zA-Zａ-ｚＡ-Ｚ0-9０-９])))";
		String replacement = "$1$2";

		String text = "规则 库中 ， 本地 规则 库 ， ";

		for (char ch = '一'; ch <= '龥'; ++ch) {
			if (Pattern.compile("\\w").matcher("" + ch).matches()) {
				System.out.println(ch + " is a word character");
			}
		}

		assert Pattern.compile(Utility.word_boundary("\\W\\w+\\d")).matcher("，库库0").matches();

		System.out.println("text = " + text);
		text = Pattern.compile(Utility.word_boundary(regex)).matcher(text).replaceAll(replacement);
		System.out.println("text = " + text);

//		Utility.printJavaInfo();
//		String keyword = "medicine";
//		String q = String.format("TTL:%s OR ABST:%s", keyword, keyword);
//		String q_ml = String.format("TTL_MT:%s OR ABST_MT:%s", keyword, keyword);
//		String q_cn = String.format("TTL_CN:%s OR ABST_CN:%s", keyword, keyword);
//		String q_cn_ml = String.format("TTL_MT_CN:%s OR ABST_MT_CN:%s", keyword, keyword);

//		String url = String.format(
//				"http://s-gateway-qa.k8s.zhihuiya.com/s-search-patent-solr/patsnap/PATENT/select?fl=TTL,ABST&q=%s&rows=1000",
//				q);
//		System.out.println(url);

//		List<Map<String, String>> result = HttpClient.solr_with_keyword("en", keyword, 10000);
//		System.out.println(result);

	}
}
//https://lucene.apache.org/solr/guide/7_7/result-clustering#clustering-concepts
//download.carrotsearch.com/lingo3g/manual/
//https://get.carrotsearch.com/lingo3g/manual/
