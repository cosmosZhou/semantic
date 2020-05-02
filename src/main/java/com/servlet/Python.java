package com.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.HttpClient;
import com.util.PropertyConfig;
import com.util.Utility;

public class Python {
	public static String eval(String script, boolean text) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("python", script);
		if (text)
			parameters.put("text", "True");

		String url = String.format("http://localhost:%s/eval", PropertyConfig.get("model", "tcp"));
		return HttpClient.HttpClientPost(url, parameters);
	}

	public static String eval(String script) {
		return eval(script, false);
	}

	static Triplet[] toArray(List<String> list) {
		Triplet[] array = new Triplet[list.size() / 3];
		for (int i = 0; i < list.size(); i += 3) {
			array[i / 3] = new Triplet(list.get(i), list.get(i + 1), list.get(i + 2));
		}
		return array;
	}

	static public class Triplet {
		Triplet(String x, String y_true, String y_pred) {
			this.x = x;
			this.y_true = y_true;
			this.y_pred = y_pred;
		}

		public String x, y_true, y_pred;
	}

	public static class Report {
		Report(Triplet[] sample, double acc) {
			this.sample = sample;
			this.acc = acc;
		}

		public double acc;
		public Triplet[] sample;
	}

	public static String training(String table, String... args) {

		String[] arguments = { "python", PropertyConfig.get("model", "pwd") + "/pytext/training.py", table };

		arguments = Utility.copier(arguments, args);

		System.out.println(String.join(" ", arguments));
		String line = null;
		String result = null;
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(Runtime.getRuntime().exec(arguments).getInputStream(), "GBK"))) {

			while ((line = in.readLine()) != null) {
				System.out.println(line);
				result = line;
//				if (Pattern.compile("\\d+/\\d+ \\[|Epoch \\d+/\\d+").matcher(line).find()) {
//					continue;
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) {
		String[] arguments = new String[] { "python", "E://workspace/hello.py", "lei", "23" };
		try {
			Process process = Runtime.getRuntime().exec(arguments);
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
			// java代码中的process.waitFor()返回值为0表示我们调用python脚本成功，
			// 返回值为1表示调用python脚本失败，这和我们通常意义上见到的0与1定义正好相反
			int re = process.waitFor();
			System.out.println(re);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}