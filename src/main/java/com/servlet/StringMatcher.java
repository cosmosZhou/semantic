package com.servlet;

import java.util.ArrayList;
import com.util.regex.Matcher;
import com.util.regex.Pattern;
import com.util.regex.Pattern.Branch;
import com.util.Utility;

public class StringMatcher {

	static String[][] split2array(String _x, String _y) {
		return split2array(_x, _y, true);
	}

	static String[][] split2array(String _x, String _y, boolean endlingSpaces) {
		String x = _x.trim();
		String y = _y.trim();

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

	public StringMatcher(String _x, String _y) {
		String[][] xy = split2array(_x, _y);

		String[] x_arr = xy[0];
		String[] y_arr = xy[1];

		if (!String.join("", x_arr).equals(String.join("", y_arr))) {
			String __x = remove_lookaround(_x);
			Matcher m = Pattern.compile("\\((?!\\?)").matcher(__x);
			int index = 1;
			String x_transformed = "";
			String y_transformed = _y;
			int end = 0;
			while (m.find()) {
				int right_parenthesis_index = __x.indexOf(')', m.start());
				String replacement = __x.substring(m.start(), right_parenthesis_index + 1);
				x_transformed += __x.substring(end, m.start()) + replacement;
				y_transformed = y_transformed.replace("$" + index, replacement);
				++index;
				end = right_parenthesis_index + 1;
			}
			x_transformed += __x.substring(end);
			Pattern x_patten = Pattern.compile(x_transformed);
			Pattern y_patten = Pattern.compile(y_transformed);
			System.out.println(x_patten.matchRoot);
			System.out.println(y_patten.matchRoot);

//			assert x_patten.matchRoot.toString().length() == x_patten.normalizedPattern.length();
//			assert y_patten.matchRoot.toString().length() == y_patten.normalizedPattern.length();

			String x_pattern_str = x_patten.matchRoot.toStringTrimmed();
			String y_pattern_str = y_patten.matchRoot.toStringTrimmed();

			System.out.println(x_pattern_str);
			System.out.println(y_pattern_str);

			if (!x_pattern_str.equals(y_pattern_str)) {
				this.text = x_pattern_str;
				this.text_replacement = _y.replace(" ", "");
			}

			this.seg = _x;
			this.seg_replacement = _y;
			return;
		}

		int i = 0;
		int j = 0;
		int ii = 0;
		int jj = 0;

		ArrayList<String> e = new ArrayList<String>();

		while (i < x_arr.length) {
			_x = x_arr[i].substring(ii);
			_y = y_arr[j].substring(jj);
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

		this.seg = String.join(" +", x_arr);
		this.seg_replacement = String.join(" ", y_arr);

		System.out.println("seg = " + this.seg);
		System.out.println("seg_transformed = " + this.seg_replacement);

		System.out.println(this.seg + '=' + this.seg_replacement);
	}

	public String seg, seg_replacement;
	public String text, text_replacement;

	public String[] transform(String sSegmented) {
		Matcher m = Pattern.compile(seg, Pattern.UNICODE_CHARACTER_CLASS).matcher(sSegmented);
		if (!m.find()) {
			System.out.println(sSegmented);
			return null;
		}

		System.out.println(sSegmented);

		String _sSegmented = Pattern.compile(seg, Pattern.UNICODE_CHARACTER_CLASS).matcher(sSegmented).replaceAll(seg_replacement);
		System.out.println(_sSegmented);

		String[] result = { _sSegmented, Utility.toString(Utility.byte_length(sSegmented.substring(0, m.start())), ' ')
				+ Utility.toString(Utility.byte_length(sSegmented.substring(m.start(), m.end())), '-') };
		return result;
	}

	public String transform_text(String text) {
		Matcher m = Pattern.compile(this.text, Pattern.UNICODE_CHARACTER_CLASS).matcher(text);
		if (!m.find())
			return null;

		System.out.println(text);
		System.out.println(Utility.toString(Utility.byte_length(text.substring(0, m.start())), ' ')
				+ Utility.toString(Utility.byte_length(text.substring(m.start(), m.end())), '-'));

		String _text = Pattern.compile(this.text, Pattern.UNICODE_CHARACTER_CLASS).matcher(text).replaceAll(this.text_replacement);
		System.out.println(_text);

		return _text;
	}

	public static void main(String[] args) {

	}

}
