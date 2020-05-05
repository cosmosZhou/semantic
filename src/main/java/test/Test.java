package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jblas.DoubleMatrix;

import com.servlet.Python;
import com.util.MySQL;
import com.util.Native;
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

	public static void test_cws() throws Exception {
		int err = 0;
		int sum = 0;
		for (Map<String, Object> dict : MySQL.instance
				.select("select* from tbl_segment_cn where training = false order by rand() limit 1000")) {
			++sum;
			String text = (String) dict.get("text");
//			String seg = (String) dict.get("seg");

			String resFromCpp = String.join(" ", Native.segmentCN(text));
			String resFromPython = Python.eval(String.format("segment('%s')", Utility.quote(text)), true);

			if (!resFromCpp.equals(resFromPython)) {
				System.out.println("text = " + text);
				System.out.println("resFromCpp  = " + resFromCpp);
				System.out.println("resFromPython = " + resFromPython);
				err += 1;
			}
		}

		System.out.println("err = " + err);
		System.out.println("sum = " + sum);
		System.out.println("acc = " + ((sum - err) * 1.0 / sum));
	}

	public static void test_pos() throws Exception {
		int err = 0;
		int sum = 0;
		for (Map<String, Object> dict : MySQL.instance
				.select("select* from tbl_syntax_cn order by rand() limit 1000")) {
			++sum;
			String text = (String) dict.get("text");
			String infix = (String) dict.get("infix");

			List<String> segList = new ArrayList<String>();
			for (String[] group : Utility.regex(infix, "\\(*([^()]+)\\)*")) {
				segList.add(group[0].split("/")[0]);
			}

			String seg[] = Utility.toArray(segList);

			String resFromCpp[] = Native.posCN(seg);

			for (int i = 0; i < seg.length; i++) {
				seg[i] = String.format("'%s'", Utility.quote(seg[i]));
			}

			String[] resFromPython = Utility.dejsonify(
					Python.eval(String.format("pos_tag([%s], lang='cn')", String.join(",", seg))), String[].class);
			if (!Utility.equals(resFromCpp, resFromPython)) {
				System.out.println("text = " + text);
				System.out.println("resFromCpp    = " + String.join(", ", resFromCpp));
				System.out.println("resFromPython = " + String.join(", ", resFromPython));
				err += 1;
			}
		}

		System.out.println("err = " + err);
		System.out.println("sum = " + sum);
		System.out.println("acc = " + ((sum - err) * 1.0 / sum));
	}

	public static void test_hyponym_cn() throws Exception {
		int err = 0;
		int sum = 0;
		for (int cnt = 0; cnt < 100; ++cnt) {
			List<Map<String, Object>> list = MySQL.instance
					.select("select* from tbl_lexicon_cn order by rand() limit 4");

			String[] array = new String[list.size()];
			for (int i = 0; i < array.length; i++) {
				array[i] = (String) list.get(i).get("text");
			}
			++sum;
			DoubleMatrix resFromCpp = new DoubleMatrix(Native.hyponymCNs(array));

			String text[] = array.clone();
			for (int i = 0; i < array.length; i++) {
				text[i] = String.format("'%s'", Utility.quote(text[i]));
			}

			DoubleMatrix resFromPython = new DoubleMatrix(Utility.dejsonify(
					Python.eval(String.format("hyponyms('cn', [%s])", String.join(",", text))), double[][].class));

			double max = Utility.absi(resFromCpp.sub(resFromPython)).max();
			if (max > 0.01) {
				System.out.println("text = " + String.join(",", text));
				System.out.println("resFromCpp    = " + resFromCpp);
				System.out.println("resFromPython = " + resFromPython);
				err += 1;
			}
		}
		System.out.println("err = " + err);
		System.out.println("sum = " + sum);
		System.out.println("acc = " + ((sum - err) * 1.0 / sum));
	}

	public static void test_keyword_cn() throws Exception {
		int err = 0;
		int sum = 0;
		for (Map<String, Object> dict : MySQL.instance
				.select("select* from tbl_keyword_cn order by rand() limit 1000")) {
			++sum;
			String text = (String) dict.get("text");
//			String seg = (String) dict.get("seg");
//			System.out.println("text = " + text);
			int resFromCpp = Native.keywordCN(text);

			double resFromPython = Double
					.parseDouble(Python.eval(String.format("keyword('%s', 'cn')", Utility.quote(text)), true));

			if ((resFromCpp > 0.5) ^ (resFromPython > 0.5)) {
				System.out.println("text = " + text);
				System.out.println("seg4cpp  = " + resFromCpp);
				System.out.println("seg4Python = " + resFromPython);
				err += 1;

				System.out.println(Python.eval(String.format("keyword_debug('%s', 'cn')", Utility.quote(text)), false));
			}
		}

		System.out.println("err = " + err);
		System.out.println("sum = " + sum);
		System.out.println("acc = " + ((sum - err) * 1.0 / sum));
	}

	public static void test_keyword_en() throws Exception {
		int err = 0;
		int sum = 0;
		for (Map<String, Object> dict : MySQL.instance
				.select("select* from tbl_keyword_en order by rand() limit 1000")) {
			++sum;
			String text = (String) dict.get("text");
//			String seg = (String) dict.get("seg");
//			System.out.println("text = " + text);
//			double resFromCpp = Native.keywordENDouble(text);
			int resFromCpp = Native.keywordEN(text);

			double resFromPython = Double
					.parseDouble(Python.eval(String.format("keyword('%s', 'en')", Utility.quote(text)), true));

			if ((resFromCpp > 0.5) ^ (resFromPython > 0.5)) {
				System.out.println("text = " + text);
				System.out.println("seg4cpp  = " + resFromCpp);
				System.out.println("seg4Python = " + resFromPython);
				err += 1;
				System.out.println(Python.eval(String.format("keyword_debug('%s', 'en')", Utility.quote(text)), false));
			}
		}

		System.out.println("err = " + err);
		System.out.println("sum = " + sum);
		System.out.println("acc = " + ((sum - err) * 1.0 / sum));
	}

	public static void main(String[] args) throws Exception {
		test_cws();
		test_pos();
		test_hyponym_cn();
		test_keyword_cn();
		test_keyword_en();
	}
}
//https://lucene.apache.org/solr/guide/7_7/result-clustering#clustering-concepts
//download.carrotsearch.com/lingo3g/manual/
//https://get.carrotsearch.com/lingo3g/manual/
