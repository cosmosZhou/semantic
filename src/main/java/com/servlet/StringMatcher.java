package com.servlet;

import static com.servlet.TextMatcher.construct;
import static com.servlet.TextMatcher.remove_lookaround;
import static com.servlet.TextMatcher.split2array;

import com.util.regex.Matcher;
import com.util.regex.Pattern;

public class StringMatcher {

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

		String[] arr = construct(x_arr, y_arr);
		this.seg = arr[0];
		this.seg_replacement = arr[1];
		System.out.println("seg = " + this.seg);
		System.out.println("seg_transformed = " + this.seg_replacement);
		System.out.println(this.seg + '=' + this.seg_replacement);
	}

	public String seg, seg_replacement;
	public String text, text_replacement;

	public String[] transform(String sSegmented) {
		return TextMatcher.transform(seg, seg_replacement, sSegmented);
	}

	public String transform_text(String text_original) {
		return TextMatcher.transform(text, text_replacement, text_original)[0];
	}

	public static void main(String[] args) {

	}
}
