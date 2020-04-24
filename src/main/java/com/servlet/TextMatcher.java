package com.servlet;

import java.util.ArrayList;

import com.util.Utility;
import com.util.regex.Matcher;
import com.util.regex.Pattern;

public class TextMatcher {

	static String[][] split2array(String x, String y) {
		return split2array(x, y, true);
	}

	static String[][] split2array(String x, String y, boolean endlingSpaces) {
//		String x = _x.trim();
//		String y = _y.trim();

		System.out.println("x = " + x);
		System.out.println("y = " + y);

		if (endlingSpaces) {
			x = Pattern.compile("\\((?!\\?)").matcher(x).replaceAll("(?:");
			y = Pattern.compile("\\((?!\\?)").matcher(y).replaceAll("(?:");

			if (x.endsWith(" +")) {
				x += "\\S?";
				y += "\\S?";
			}

			if (x.endsWith("\\s")) {
				x = "\\S?" + x;
				y = "\\S?" + y;
			}
		}

		String[] x_arr = x.split("\\s\\+|\\s+");
		String[] y_arr = y.split("\\s\\+|\\s+");

		String[][] result = { x_arr, y_arr };
		return result;
	}

	static String remove_lookaround(String regex) {
		return regex.replaceAll("\\(\\?=[^(]+?\\)|\\(\\?![^(]+?\\)|\\(\\?<=[^(]+?\\)|\\(\\?<![^(]+?\\)", "");
	}

	static String[] construct(String[] x_arr, String[] y_arr) {
		int i = 0;
		int j = 0;
		int ii = 0;
		int jj = 0;

		ArrayList<String> e = new ArrayList<String>();

		while (i < x_arr.length) {
			String _x = x_arr[i].substring(ii);
			String _y = y_arr[j].substring(jj);
			if (_x.startsWith(_y)) {
				e.add(_y);
				ii += y_arr[j].length() - jj;
				if (ii == x_arr[i].length()) {
					i += 1;
					ii = 0;
				}
				j += 1;
				jj = 0;
			} else {
				assert _y.startsWith(_x);
				e.add(_x);

				jj += x_arr[i].length() - ii;
				if (jj == y_arr[j].length()) {
					j += 1;
					jj = 0;
				}
				i += 1;
				ii = 0;
			}
		}

		assert j == y_arr.length;

		assert String.join("", e).equals(String.join("", x_arr));
		assert String.join("", e).equals(String.join("", y_arr));

		System.out.println("e = " + e);

		j = 0;
		for (i = 0; i < x_arr.length; ++i) {
			String s = "";
			while (!x_arr[i].isEmpty()) {
				s += '(' + x_arr[i].substring(0, e.get(j).length()) + ')';
				x_arr[i] = x_arr[i].substring(e.get(j).length());
				j += 1;
			}
			x_arr[i] = s;
		}
		j = 0;
		for (i = 0; i < y_arr.length; ++i) {
			String s = "";
			while (!y_arr[i].isEmpty()) {
				s += "$" + (j + 1);
				y_arr[i] = y_arr[i].substring(e.get(j).length());
				j += 1;
			}
			y_arr[i] = s;
		}

		String text = String.join(" +", x_arr);
		String text_replacement = String.join(" ", y_arr);
		String[] arr = { text, text_replacement };
		return arr;
	}

	public TextMatcher(String _x, String _y) {
		String[][] xy = split2array(_x, _y);

		String[] x_arr = xy[0];
		String[] y_arr = xy[1];

		if (!String.join("", x_arr).equals(String.join("", y_arr))) {
			this.text = _x;
			this.text_replacement = _y;
			return;
		}

		String[] arr = construct(x_arr, y_arr);
		this.text = arr[0];
		this.text_replacement = arr[1];

		System.out.println("text = " + this.text);
		System.out.println("text_transformed = " + this.text_replacement);

		System.out.println(this.text + '=' + this.text_replacement);
	}

	public String text, text_replacement;

	static int flags = Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE;

	static public String[] transform(String text, String text_replacement, String text_original) {
		Matcher m = Pattern.compile(text, flags).matcher(text_original);
		if (!m.find()) {
			System.out.println(text_original);
			return null;
		}

		System.out.println(text_original);

		String _sSegmented = Pattern.compile(text, flags).matcher(text_original).replaceAll(text_replacement);
		System.out.println(_sSegmented);

		String mark = Utility.toString(Utility.strlen(text_original.substring(0, m.start())), ' ')
				+ text_original.substring(m.start(), m.end());
//				+ Utility.toString(Utility.strlen(text_original.substring(m.start(), m.end())), '-');

		System.out.println(mark);

		String[] result = { _sSegmented, mark };
		return result;
	}

	public String[] transform(String sSegmented) {
		return transform(text, text_replacement, sSegmented);
	}

	public static void main(String[] args) {

	}

}
