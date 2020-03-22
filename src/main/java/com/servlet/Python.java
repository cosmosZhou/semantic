package com.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.HttpClient;
import com.util.PropertyConfig;

public class Python {
	public static String eval(String script) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("python", script);
		return HttpClient.HttpClientPost("http://localhost:5000/eval", parameters);
	}
	
	public static void training(String table) {

		String[] arguments = { "python", PropertyConfig.get("model", "pwd") + "/pytext/training.py", table };

		System.out.println(String.join(" ", arguments));
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(Runtime.getRuntime().exec(arguments).getInputStream(), "GBK"))) {
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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