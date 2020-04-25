package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Logger;
import org.jblas.DoubleMatrix;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.Utility.RegexIterator;
import com.util.Utility.Replacer;

public class Utility {
	public static void main(String[] args) throws Exception {
		short[] arr = new short[16];
		System.out.println(arr);

		char[][] arrs = new char[16][];
		System.out.println(arrs);
//		testCharArray();
	}

	static public String workingDirectory = "../";

	static public String corpusDirectory() {
		return workingDirectory + "corpus/";
	}

	static public String modelsDirectory() {
		return workingDirectory + "models/";
	}

	static public void saveTo(String file, Object... obj) throws IOException {
		File f = new File(file);
		File path = f.getParentFile();
		if (path != null && !path.exists()) {
			path.mkdirs();
		}

		ObjectOutputStream out = new ObjectOutputStream(
				new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(file))));
		for (Object o : obj)
			out.writeObject(o);
		out.close();
	}

	static ObjectInputStream getObjectGZIPBufferedFileInputStream(String file) throws IOException {
		return new ObjectInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(file))));
	}

	public static Object[] loadFrom(String file, int size) throws IOException, ClassNotFoundException {
		ObjectInputStream in = getObjectGZIPBufferedFileInputStream(file);
		Object[] obj = new Object[size];
		for (int i = 0; i < obj.length; ++i)
			obj[i] = in.readObject();
		in.close();
		return obj;
	}

	public static Object loadFrom(String file) throws IOException, ClassNotFoundException {
		ObjectInputStream in = getObjectGZIPBufferedFileInputStream(file);
		Object obj = in.readObject();
		in.close();
		return obj;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void put(HashMap<String, Object> map, String key, String value) {
		if (map.containsKey(key)) {
			Object object = map.get(key);
			if (object instanceof ArrayList) {
				((ArrayList) object).add(value);
			} else {
				ArrayList list = new ArrayList();
				list.add(object);
				list.add(value);
				map.put(key, list);
			}
		} else {
			map.put(key, value);
		}

	}

	public static Date parseDateFormat(final String strDate) throws ParseException {
		return parseDateFormat(strDate, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date parseDateFormat(final String strDate, final String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return sdf.parse(strDate);
	}

	public static double toDouble(float x) {
		return x;
	}

	public static double[] toDouble(float x[]) {
		double[] y = new double[x.length];
		for (int i = 0; i < y.length; i++) {
			y[i] = x[i];
		}
		return y;
	}

	public static double[][] toDouble(float x[][]) {
		double[][] y = new double[x.length][];
		for (int i = 0; i < y.length; i++) {
			y[i] = toDouble(x[i]);
		}
		return y;
	}

	public static DoubleMatrix addi(DoubleMatrix x, DoubleMatrix y) {
		if (x == null)
			return y;
		if (y == null)
			return x;

		return x.addi(y);
	}

	public static DoubleMatrix[] addi(DoubleMatrix[] x, DoubleMatrix[] y) {
		for (int i = 0; i < x.length; ++i) {
			x[i].addi(y[i]);
		}

		return x;
	}

	public static DoubleMatrix[] add(DoubleMatrix[] x, DoubleMatrix[] y) {
		DoubleMatrix[] z = new DoubleMatrix[x.length];
		for (int i = 0; i < x.length; ++i) {
			z[i] = x[i].add(y[i]);
		}

		return z;
	}

	public static DoubleMatrix[] concatHorizontally(DoubleMatrix[] x, DoubleMatrix[] y) {
		DoubleMatrix[] z = new DoubleMatrix[x.length];
		for (int i = 0; i < x.length; ++i) {
			z[i] = DoubleMatrix.concatHorizontally(x[i], y[i]);
		}
		return z;
	}

	public static DoubleMatrix[] muli(DoubleMatrix[] x, DoubleMatrix[] y) {
		for (int i = 0; i < x.length; ++i) {
			x[i].muli(y[i]);
		}

		return x;
	}

	public static DoubleMatrix[] divi(DoubleMatrix[] x, double y) {
		for (int i = 0; i < x.length; ++i) {
			x[i].divi(y);
		}

		return x;
	}

	public static class BinaryReader {
		public DataInputStream dis;
		private String s_FilePath;

		public BinaryReader(String file) throws FileNotFoundException {
			s_FilePath = file;
			dis = new DataInputStream(new FileInputStream(new File(s_FilePath)));
		}

		public double[] readArray1() throws IOException {
			int dimension = dis.readInt();
			System.out.printf("x = %d\n", dimension);
			double[] arr = new double[dimension];
			for (int i = 0; i < arr.length; ++i) {
				arr[i] = dis.readDouble();
			}
//			System.out.println(Utility.toString(arr));
			return arr;
		}

		public double[][] readArray2() throws IOException {
			int dimension0 = dis.readInt();
			int dimension1 = dis.readInt();
			System.out.printf("x = %d, y = %d\n", dimension0, dimension1);

			double[][] arr = new double[dimension0][dimension1];
			for (int i0 = 0; i0 < arr.length; ++i0) {
				for (int i1 = 0; i1 < arr[i0].length; ++i1) {
					arr[i0][i1] = dis.readDouble();
				}
			}
//			System.out.println(Utility.toString(arr[0]));
			return arr;
		}

		public HashMap<String, Integer> readMap(HashMap<String, Integer> word2id) throws IOException {
			int length = dis.readInt();
			System.out.printf("length = %d\n", length);

			for (int i = 0; i < length; ++i) {
				char[] arr = new char[dis.readInt()];
				for (int j = 0; j < arr.length; ++j) {
					arr[j] = dis.readChar();
				}
				word2id.put(new String(arr), dis.readInt());
			}

			return word2id;
		}

		public HashMap<Character, Integer> readCharMap(HashMap<Character, Integer> word2id) throws IOException {
			int length = dis.readInt();
			System.out.printf("length = %d\n", length);

			for (int i = 0; i < length; ++i) {
				word2id.put(dis.readChar(), dis.readInt());
			}

			return word2id;
		}

		public double[][][] readArray3() throws IOException {
			int dimension0 = dis.readInt();
			int dimension1 = dis.readInt();
			int dimension2 = dis.readInt();
			System.out.printf("d0 = %d, d1 = %d, d2 = %d\n", dimension0, dimension1, dimension2);
			double[][][] arr = new double[dimension0][dimension1][dimension2];
			for (int i0 = 0; i0 < arr.length; ++i0) {
				for (int i1 = 0; i1 < arr[i0].length; ++i1) {
					for (int i2 = 0; i2 < arr[i0][i1].length; ++i2) {
						arr[i0][i1][i2] = dis.readDouble();
					}
				}
			}
//			System.out.println(Utility.toString(arr[0][0]));
			return arr;
		}

		public double[][][][] readArray4() throws IOException {
			int dimension0 = dis.readInt();
			int dimension1 = dis.readInt();
			int dimension2 = dis.readInt();
			int dimension3 = dis.readInt();
			System.out.printf("d0 = %d, d1 = %d, d2 = %d, d3 = %d\n", dimension0, dimension1, dimension2, dimension3);
			double[][][][] arr = new double[dimension0][dimension1][dimension2][dimension3];
			for (int i0 = 0; i0 < arr.length; ++i0) {
				for (int i1 = 0; i1 < arr[i0].length; ++i1) {
					for (int i2 = 0; i2 < arr[i0][i1].length; ++i2) {
						for (int i3 = 0; i3 < arr[i0][i1][i2].length; ++i3) {
							arr[i0][i1][i2][i3] = dis.readDouble();
						}
					}
				}
			}
//			System.out.println(Utility.toString(arr[0][0][0]));
			return arr;
		}

		public double[][][][][] readArray5() throws IOException {
			int dimension0 = dis.readInt();
			int dimension1 = dis.readInt();
			int dimension2 = dis.readInt();
			int dimension3 = dis.readInt();
			int dimension4 = dis.readInt();
			System.out.printf("d0 = %d, d1 = %d, d2 = %d, d3 = %d, d4 = %d\n", dimension0, dimension1, dimension2,
					dimension3, dimension4);
			double[][][][][] arr = new double[dimension0][dimension1][dimension2][dimension3][dimension4];
			for (int i0 = 0; i0 < arr.length; ++i0) {
				for (int i1 = 0; i1 < arr[i0].length; ++i1) {
					for (int i2 = 0; i2 < arr[i0][i1].length; ++i2) {
						for (int i3 = 0; i3 < arr[i0][i1][i2].length; ++i3) {
							for (int i4 = 0; i4 < arr[i0][i1][i2][i3].length; ++i4) {
								arr[i0][i1][i2][i3][i4] = dis.readDouble();
							}
						}
					}
				}
			}
//			System.out.println(Utility.toString(arr[0][0][0][0]));
			return arr;
		}

		public void readBinaryStream() {
			try {
				if (dis != null) {
					while (dis.available() > 0) {
						System.out.println(dis.available());
						System.out.println(dis.readBoolean());
						char c = (char) dis.readChar();
						System.out.println(c);
						System.out.println(dis.readDouble());
						System.out.println(dis.readFloat());
						System.out.println(dis.readInt());
						System.out.println(dis.readLong());
						System.out.println(dis.readShort());
						System.out.println(dis.readUTF());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("hiding")
	static public abstract class Reader<String> implements Iterator<String>, Iterable<String> {
		public void remove() {
			throw new IllegalStateException("This Iterator<Instance> does not support remove().");
		}

		public Iterator<String> iterator() {
			return this;
		}
	}

	static public class Text extends Reader<String> {
		public Text(String path) throws IOException {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			strip_byte_order_mark();
		}

		public Text(InputStream in) throws IOException {
			bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			strip_byte_order_mark();
		}

		void strip_byte_order_mark() throws IOException {
			bufferedReader.mark(2);
			char byteOrderMark = (char) bufferedReader.read();
			if (byteOrderMark != 0xFEFF) {
				bufferedReader.reset();
			}
		}

		public BufferedReader bufferedReader;
		String line;

		public <C extends Collection<String>> C collect(C c) {
			for (String s : this) {
				c.add(s);
			}
			return c;
		}

		public <C extends Collection<String>> C collect(C c, int limit) {
			int cnt = 0;
			for (String s : this) {
				if (cnt >= limit)
					break;
				c.add(s);
				++cnt;
			}
			return c;
		}

		public <C extends Collection<String>> C collect(C c, int start, int end) {
			int cnt = 0;
			for (String s : this) {
				if (cnt < start) {
					++cnt;
					continue;
				}

				if (cnt >= end)
					break;

				c.add(s);
				++cnt;
			}
			return c;
		}

		@Override
		public boolean hasNext() {
			for (;;) {
				try {
					line = bufferedReader.readLine();
					if (line == null) {
						bufferedReader.close();
						return false;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					return false;
				}

				line = line.trim();
				if (line.length() > 0)
					return true;
			}
		}

		@Override
		public String next() {
			return line;
		}

		/**
		 * the length of the string content returned is dependent of the char-Encoding
		 * scheme; x denotes the number of English letters; y denotes the number of
		 * Chinese characters; for ASCII encoding, the length = x + 2 y; for UTF-8-BOM
		 * encoding, the length = 3 + x + 3 y; for UTF-8 encoding, the length = 3 + x +
		 * 3 y;
		 */
		public String fetchContent() throws IOException {

			StringBuilder sbText = new StringBuilder();

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sbText.append(line).append("\n");
			}
			bufferedReader.close();

			return sbText.toString();
		}
	}

	static public <T> void writeString(String path, Collection<T> c) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));

		for (T line : c) {
			if (line == null)
				continue;
			writer.write(line.toString());
			writer.write(lineSeparator);
		}
		writer.close();
	}

	static public <T> void writeString(String path, T[] c) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));

		for (T line : c) {
			if (line == null)
				continue;
			writer.write(line.toString());
			writer.write(lineSeparator);
		}
		writer.close();
	}

	static public void writeString(String path, String str) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
		// BufferedWriter writer = new BufferedWriter(new FileWriter(path));

		writer.write(str);
		// writer.newLine();

		writer.close();
	}

	static public class Timer {
		long start;
		double duration;

		public Timer() {
			start();
		}

		public void start() {
			// start = new Date().getTime();
			start = System.currentTimeMillis();
		}

		public long lapsedSeconds() {
			return (System.currentTimeMillis() - start) / 1000;
		}

		public void report() {
			duration = System.currentTimeMillis() - start;
			duration /= 1000;
			log.info("Total Time duration " + duration + " seconds or " + (duration / 60) + " minutes");
			start();
		}

		public double report(String message) {
			duration = System.currentTimeMillis() - start;
			duration /= 1000;
			System.out.printf("Time cost in %s = %f seconds\n", message, duration);
			start();
			return duration;
		}
	}

	public static Logger log = Logger.getLogger(Utility.class);

	static public String convertToOriginal(String[] arr) {
		String str = "";
		int i = 0;
		for (; i < arr.length - 1; ++i) {
			str += arr[i];
			if (Utility.isEnglish(arr[i].charAt(arr[i].length() - 1)) && Utility.isEnglish(arr[i + 1].charAt(0))) {
				str += " ";
				continue;
			}
		}
		str += arr[i];
		return str;
	}

	static public String[] convertToSegmentation(String str) {
		str = Pattern.compile("([" + Utility.sPunctuation + "])").matcher(str).replaceAll(" $1 ");

		str = Pattern.compile("(?<=[\\d]+)( +([\\.．：:]) +)(?=[\\d]+)").matcher(str).replaceAll("$2");

		int length = str.length();
		while (true) {
			str = Pattern.compile("([" + Utility.sPunctuation + "]) +\\1").matcher(str).replaceAll("$1$1");
			if (str.length() < length) {
				length = str.length();
				continue;
			} else
				break;
		}

		return str.trim().split("[\\u2002\\s]+");
	}

	static public String convertFromSegmentation(String[] arr) {
		String str = "";
		int i = 0;
		for (; i < arr.length - 1; ++i) {
			str += arr[i];
			if (sPunctuation.indexOf(arr[i].charAt(arr[i].length() - 1)) >= 0
					|| sPunctuation.indexOf(arr[i + 1].charAt(0)) >= 0)
				continue;
			str += " ";
		}
		str += arr[i];
		return str;
	}

	static public String convertFromSegmentation(List<String> arr) {
		String str = "";
		int i = 0;
		for (; i < arr.size() - 1; ++i) {
			str += arr.get(i);
			if (sPunctuation.indexOf(arr.get(i).charAt(arr.get(i).length() - 1)) >= 0
					|| sPunctuation.indexOf(arr.get(i + 1).charAt(0)) >= 0)
				continue;
			str += " ";
		}
		str += arr.get(i);
		return str;
	}

	public static boolean isEnglish(char ch) {
		return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= 'ａ' && ch <= 'ｚ' || ch >= 'Ａ' && ch <= 'Ｚ'
				|| ch >= '0' && ch <= '9' || ch >= '０' && ch <= '９';
	}

	public static final String EnglishPunctuation = ",.:;!?()\\[\\]{}'\"=<>";
	public static final String ChinesePunctuation = "，。：；！？（）「」『』【】～‘’′”“《》、…．·";
	public static final String sPunctuation = EnglishPunctuation + ChinesePunctuation;

	@SuppressWarnings("unchecked")
	public static <_Ty> _Ty[] toArray(Collection<_Ty> arr) {
		if (arr.isEmpty()) {
			return null;
		}

		// @SuppressWarnings("unchecked")
		Iterator<_Ty> it = arr.iterator();

		assert it != null : arr;
		_Ty element = it.next();

		assert element != null : arr;

		_Ty[] a = (_Ty[]) Array.newInstance(element.getClass(), arr.size());

		int i = 0;
		for (_Ty s : arr) {
			a[i++] = s;
		}
		return a;
	}

	public static int[] toArray(List<Integer> list) {
		if (list.isEmpty()) {
			return null;
		}

		int arr[] = new int[list.size()];

		int i = 0;
		for (int s : list) {
			arr[i++] = s;
		}
		return arr;
	}

	public static int[] toArray(Set<Integer> list) {
		if (list.isEmpty()) {
			return null;
		}

		int arr[] = new int[list.size()];

		int i = 0;
		for (int s : list) {
			arr[i++] = s;
		}
		return arr;
	}

	public static boolean equals(String[] seg, String[] segSub, int I) {
		for (int i = I, j = 0; i < seg.length && j < segSub.length; ++i, ++j) {
			if (!seg[i].equals(segSub[j])) {
				return false;
			}
		}
		return true;
	}

	public static int containsSubstr(String[] seg, String[] segSub) {
		return containsSubstr(seg, segSub, 0);
	}

	public static int containsSubstr(String[] seg, String[] segSub, int start) {
		for (int i = start; i <= seg.length - segSub.length; ++i) {
			if (equals(seg, segSub, i))
				return i;
		}
		return -1;
	}

	static public <_Ty> boolean equals(_Ty a[], _Ty b[]) {
		if (a == null)
			return b == null;

		if (a.length != b.length) {
			return false;
		}

		for (int i = 0; i < b.length; ++i) {
			if (!equals(a[i], b[i])) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public boolean equals(Object a, Object b) {
		if (a == null)
			return b == null;
		if (a instanceof Map) {
			if (b instanceof Map)
				return equals((Map) a, (Map) b);
			else
				return false;
		}
		if (a instanceof List) {
			if (b instanceof List)
				return equals((List) a, (List) b);
			else
				return false;
		}
		if (a instanceof Set) {
			if (b instanceof Set)
				return equals((Set) a, (Set) b);
			else
				return false;
		}
		if (a instanceof int[]) {
			if (b instanceof int[])
				return equals((int[]) a, (int[]) b);
			else
				return false;
		}
		if (a instanceof double[]) {
			if (b instanceof double[])
				return equals((double[]) a, (double[]) b);
			else
				return false;
		}
		if (a instanceof String[]) {
			if (b instanceof String[])
				return equals((String[]) a, (String[]) b);
			else
				return false;
		}
		return a.equals(b);
	}

	static public <_Ty> boolean equals(List<_Ty> a, List<_Ty> b) {
		if (a == null)
			return b == null;

		if (a.size() != b.size()) {
			return false;
		}

		for (int i = 0; i < b.size(); ++i) {
			if (!equals(a.get(i), b.get(i))) {
				return false;
			}
		}
		return true;
	}

	static public <_Ty> boolean equals(Set<_Ty> a, Set<_Ty> b) {
		if (a == null)
			return b == null;

		if (a.size() != b.size()) {
			return false;
		}
		return a.containsAll(b);
	}

	static public <K, V> boolean equals(Map<K, V> a, Map<K, V> b) {
		if (a == null)
			return b == null;

		if (a.size() != b.size()) {
			return false;
		}

		for (Map.Entry<K, V> entry : a.entrySet()) {
			if (!equals(entry.getValue(), b.get(entry.getKey())))
				return false;
		}

		return true;
	}

	static public boolean equals(int a[], int b[]) {
		if (a == null)
			return b == null;
		if (a.length != b.length) {
			return false;
		}

		for (int i = 0; i < b.length; ++i) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	static public boolean equals(boolean a[], boolean b[]) {
		if (a == null)
			return b == null;
		if (a.length != b.length) {
			return false;
		}

		for (int i = 0; i < b.length; ++i) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	static public boolean equals(double a[], double b[]) {
		if (a == null)
			return b == null;

		if (a.length != b.length) {
			return false;
		}

		for (int i = 0; i < b.length; ++i) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	static public double min(DoubleMatrix x, int row_index) {
		double v = Double.POSITIVE_INFINITY;
		for (int i = row_index, end = row_index + x.columns * x.rows; i < end; i += x.rows) {
			double xi = x.get(i);
			if (xi == xi && xi < v) {
				v = xi;
			}
		}
		return v;
	}

	static public int argmin(DoubleMatrix x, int row_index) {
		double v = Double.POSITIVE_INFINITY;
		int argmin = -1;
		for (int i = row_index, end = row_index + x.columns * x.rows, j = 0; i < end; i += x.rows, ++j) {
			double xi = x.get(i);
			if (xi == xi && xi < v) {
				v = xi;
				argmin = j;
			}
		}
		return argmin;
	}

	static public int[] argmin(DoubleMatrix x) {
		int[] arr = new int[x.rows];
		for (int i = 0; i < x.rows; ++i) {
			arr[i] = argmin(x, i);
		}
		return arr;
	}

	static public DoubleMatrix min(DoubleMatrix x) {
		double[] arr = new double[x.rows];
		for (int i = 0; i < x.rows; ++i) {
			arr[i] = min(x, i);
		}
		// return a row vector!
		return new DoubleMatrix(1, x.rows, arr);
	}

	static public String jsonify(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static public <T> T dejsonify(String json, Class<T> valueType) {
		try {
			return new ObjectMapper().readValue(json, valueType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	static public <String> String[] tuple(String... arr) {
		return arr;
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	static public <String> List<String> list(String... arr) {
		ArrayList<String> list = new ArrayList<String>(arr.length);
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}

		return list;
	}

	public static <_Ty> void swap(_Ty a[], int i, int j) {
		_Ty tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	public static void swap(int a[], int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	public static void swap(double a[], int i, int j) {
		double tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	public static <_Ty> void swap(List<_Ty> a, int i, int j) {
		_Ty tmp = a.get(i);
		a.set(i, a.get(j));
		a.set(j, tmp);
	}

	static public <T> int indexOf(T[] elementData, T o) {
		return indexOf(elementData, o, 0);
	}

	static public int indexOf(int[] elementData, int o) {
		return indexOf(elementData, o, 0);
	}

	static public <T> boolean contains(T[] elementData, T o) {
		return indexOf(elementData, o) >= 0;
	}

	static public <T> int indexOf(T[] elementData, T o, int index) {
		if (o == null) {
			for (int i = index; i < elementData.length; i++)
				if (elementData[i] == null)
					return i;
		} else {
			for (int i = index; i < elementData.length; i++)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
	}

	static public int indexOf(int[] elementData, int o, int index) {
		for (int i = index; i < elementData.length; i++)
			if (o == elementData[i])
				return i;

		return -1;
	}

	public static int maxPrintability = 25;

	public static <T> String toString(T[] value, String delimiter, String brackets) {
		return toString(value, delimiter, brackets, maxPrintability);
	}

	public static String toString(Object data) {
		if (data == null) {
			return null;
		} else if (data instanceof String[]) {
			return toString((String[]) data);
		} else if (data instanceof String[][]) {
			return toString((String[][]) data);
		} else if (data instanceof int[][]) {
			return toString((int[][]) data);
		} else if (data instanceof int[]) {
			return toString((int[]) data);
		} else if (data instanceof float[]) {
			return toString((float[]) data);
		} else if (data instanceof double[]) {
			return toString((double[]) data);
		} else if (data instanceof Object[]) {
			return toString((Object[]) data);
		} else {
			return data.toString();
		}
	}

	public static <T> String toString(T[] value, String delimiter, String brackets, int maxPrintability) {
		if (value == null)
			return null;

		int length = value.length;
		if (length == 0)
			return brackets == null ? "" : brackets;

		if (maxPrintability <= 0)
			maxPrintability = Integer.MAX_VALUE;
		if (length > maxPrintability) {
			length = maxPrintability;
		}

		StringBuilder s = new StringBuilder();
		if (brackets != null && brackets.length() == 2)
			s.append(brackets.charAt(0));
		int i = 0;

		for (i = 0; i < length; ++i) {
			T t = value[i];
			s.append(toString(t));
			if (i == length - 1)
				break;

			if (delimiter != null)
				s.append(delimiter);
			else
				s.append(" ");
		}

		if (value.length > maxPrintability)
			s.append("... (length = " + value.length + ")");
		if (brackets != null && brackets.length() == 2)
			s.append(brackets.charAt(1));
		return s.toString();
	}

	public static <T> String toString(T[] value) {
		return toString(value, ", ", null);
	}

	public static String toString(int[] value, String delimiter) {
		return toString(value, delimiter, null, maxPrintability);
	}

	public static String toString(int[] value, String delimiter, String brackets, int maxPrintability) {
		if (value == null)
			return null;

		int length = value.length;
		if (length == 0)
			return brackets == null ? "" : brackets;
		if (maxPrintability <= 0)
			maxPrintability = Integer.MAX_VALUE;
		if (length > maxPrintability) {
			length = maxPrintability;
		}

		String s = "";
		if (brackets != null && brackets.length() == 2)
			s += brackets.charAt(0);
		int i = 0;

		for (i = 0; i < length; ++i) {
			int t = value[i];
			s += t;
			if (i == length - 1)
				break;

			if (delimiter != null)
				s += delimiter;
			else
				s += " ";

		}

		if (value.length > maxPrintability)
			s += "... (length = " + value.length + ")";
		if (brackets != null && brackets.length() == 2)
			s += brackets.charAt(1);
		return s;
	}

	public static String toString(int[] value, String delimiter, String brackets) {
		if (value == null)
			return null;

		int length = value.length;
		if (length == 0)
			return brackets == null ? "" : brackets;
		if (maxPrintability <= 0)
			maxPrintability = Integer.MAX_VALUE;
		if (length > maxPrintability) {
			length = maxPrintability;
		}

		String s = "";
		if (brackets != null && brackets.length() == 2)
			s += brackets.charAt(0);
		int i = 0;

		for (i = 0; i < length; ++i) {
			int t = value[i];
			s += t;
			if (i == length - 1)
				break;

			if (delimiter != null)
				s += delimiter;
			else
				s += " ";

		}

		if (value.length > maxPrintability)
			s += "... (length = " + value.length + ")";
		if (brackets != null && brackets.length() == 2)
			s += brackets.charAt(1);
		return s;
	}

	public static String toString(double[] value, String delimiter, String brackets) {
		if (value == null)
			return null;

		int length = value.length;
		if (length == 0)
			return brackets == null ? "" : brackets;
		if (maxPrintability <= 0)
			maxPrintability = Integer.MAX_VALUE;
		if (length > maxPrintability) {
			length = maxPrintability;
		}

		String s = "";
		if (brackets != null && brackets.length() == 2)
			s += brackets.charAt(0);
		int i = 0;

		for (i = 0; i < length; ++i) {
			double t = value[i];
			s += t;
			if (i == length - 1)
				break;

			if (delimiter != null)
				s += delimiter;
			else
				s += " ";

		}

		if (value.length > maxPrintability)
			s += "... (length = " + value.length + ")";
		if (brackets != null && brackets.length() == 2)
			s += brackets.charAt(1);
		return s;
	}

	public static String toString(int[] value) {
		return toString(value, ", ", "[]", maxPrintability);
	}

	public static String toString(byte[] value) {
		if (value == null) {
			return null;
		}
		if (value.length == 0) {
			return "[]";
		}

		String s = "[";
		int length = value.length;
		if (maxPrintability <= 0)
			maxPrintability = Integer.MAX_VALUE;
		if (value.length > maxPrintability) {
			length = maxPrintability;
		}

		for (int i = 0; i < length; ++i) {
			s += value[i];
			s += ", ";
		}

		if (value.length > maxPrintability)
			return s += "... (length = " + value.length + ")]";
		else
			return s.substring(0, s.length() - 2) + "]";
	}

	public static String toString(float[] value) {
		if (value == null) {
			return null;
		}
		if (value.length == 0) {
			return "[]";
		}

		String s = "[";

		int length = value.length;
		if (maxPrintability <= 0)
			maxPrintability = Integer.MAX_VALUE;
		if (value.length > maxPrintability) {
			length = maxPrintability;
		}

		for (int i = 0; i < length; ++i) {
			s += value[i];
			s += ", ";
		}

		if (value.length > maxPrintability)
			return s += "... (length = " + value.length + ")]";
		else
			return s.substring(0, s.length() - 2) + "]";
	}

	public static String toString(double[] value) {
		return toString(value, ", ", "[]");
	}

	static public class Couplet<_Kty, _Ty> implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		static public class Integer implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Integer() {
				this(0, 0);
			}

			public Integer(int x, int y) {
				this.x = x;
				this.y = y;
			}

			public int x, y;
		}

		static public class Double implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Double() {
				this(0, 0);
			}

			public Double(double x, double y) {
				this.x = x;
				this.y = y;
			}

			public double x, y;
		}

		public Couplet(Couplet<_Kty, _Ty> rhs) {// copy constructor;
			x = rhs.x;
			y = rhs.y;
		}

		public Couplet(_Kty x, _Ty y) {
			this.x = x;
			this.y = y;
		}

		public Couplet() {
		}

		public void set(Couplet<_Kty, _Ty> rhs) {
			x = rhs.x;
			y = rhs.y;
		}

		public void set(_Kty x, _Ty y) {
			this.x = x;
			this.y = y;
		}

		public void swap(Couplet<_Kty, _Ty> rhs) {
			Couplet<_Kty, _Ty> tmp = new Couplet<_Kty, _Ty>(this);
			set(rhs);
			rhs.set(tmp);
		}

		public _Kty x;
		public _Ty y;

		public String toString() {
			String str = "";

			if (x instanceof String[]) {
				str += Utility.toString((String[]) x);
				str += "\n";
			} else
				str += x;
			str += "->";
			if (y instanceof String[]) {
				str += Utility.toString((String[]) y);
				str += "\n";
			} else
				str += y;
			return str;
		}

	}

	public static int strlen(String value) {
		int length = 0;
		for (int i = 0; i < value.length(); ++i) {
			char ch = value.charAt(i);
			if ((ch & 0xff80) != 0)
				length += 2;
			else
				++length;
		}
		return length;
	}

	public static String toString(int size, char ch) {
		// if (size < 0){
		// log.info("java.lang.NegativeArraySizeException");
		// return "";
		// }
		char arr[] = new char[size];
		java.util.Arrays.fill(arr, ch);
		return new String(arr);
	}

	static public String lineSeparator = "\n";

	@SuppressWarnings("serial")
	public static class TextTreeNode extends Couplet<TextTreeNode[], TextTreeNode[]> {
		// objects hold a formatted label string and the level,column
		// coordinates for a shadow tree node
		public String value; // formatted node value
		int i, j;
		static final boolean debug = false;

		public TextTreeNode(String value) {
			this.value = value;
		}

		static int max_width(TextTreeNode[] list) {
			int length = 0;
			for (TextTreeNode x : list) {
				int width = x.max_width();
				if (width > length) {
					length = width;
				}
			}
			return length;
		}

		public int max_width() {
			int width = strlen(value);
			if (x != null) {
				int width_x = max_width(x);
				if (width_x > width)
					width = width_x;
			}
			if (y != null) {
				int width_y = max_width(y);
				if (width_y > width)
					width = width_y;
			}
			return width;
		}

		void hierarchize() {
			hierarchize(0, 0);
		}

		static void hierarchize(TextTreeNode list[], int level, int... column) {
			for (TextTreeNode x : list) {
				x.hierarchize(level, column);
			}
		}

		TextTreeNode right_hand() {
			if (y != null)
				return Utility.last(y);
			return this;
		}

		TextTreeNode left_hand() {
			if (x != null)
				return x[0];
			return this;
		}

		TextTreeNode last_kinder() {
			if (y != null)
				return Utility.last(y);
			if (x != null)
				return Utility.last(x);
			return null;
		}

		TextTreeNode left_most() {
			if (x != null)
				return x[0].left_most();
			return this;
		}

		TextTreeNode first_kinder() {
			if (x != null)
				return x[0];
			if (y != null)
				return y[0];
			return null;
		}

		void offset(int dj) {
			this.j += dj;
			if (debug)
				this.value = String.valueOf(j);
			if (x != null)
				offset(x, dj);
			if (y != null)
				offset(y, dj);
		}

		static void offset(TextTreeNode x[], int dj) {
			for (TextTreeNode node : x) {
				node.offset(dj);
			}
		}

		void shrink(TextTreeNode x[]) {
			if (x.length <= 1)
				return;
			TextTreeNode curr, prev;

			prev = x[0];
			for (int i = 1; i < x.length; i++) {
				curr = x[i];
				int offset = shift(prev, curr);
				if (offset > 0)
					x[i].offset(-offset);

				prev = x[i];
			}
		}

		static int size(TextTreeNode[] list) {
			int size = 0;
			for (TextTreeNode x : list) {
				size += x.size();
			}
			return size;
		}

		int size() {
			int size = 1;
			if (x != null)
				size += size(x);
			if (y != null)
				size += size(y);
			return size;
		}

		static int shift(TextTreeNode prev, TextTreeNode curr) {
			int offset = curr.left_hand().j - prev.right_hand().j;

			do {
				prev = prev.last_kinder();
				curr = curr.first_kinder();
				if (prev == null || curr == null)
					break;
				offset = Math.min(offset, curr.left_hand().j - prev.right_hand().j);
			} while (true);
			--offset;
			return offset;
		}

		void shrink(TextTreeNode parent) {
			if (x != null)
				for (TextTreeNode node : this.x) {
					node.shrink(this);
				}
			if (y != null)
				for (TextTreeNode node : this.y) {
					node.shrink(this);
				}

			if (x != null) {
				shrink(x);
				if (y != null) {

					TextTreeNode prev = Utility.last(x);
					TextTreeNode curr = y[0];
					int offset = Math.min(this.j - prev.j - 1, shift(prev, curr));

					if (offset > 0) {
						curr.offset(-offset);
						this.j -= offset;
						if (debug)
							this.value = String.valueOf(this.j);
					}

					shrink(y);

				}
			} else {
				if (y != null) {
					shrink(y);
					int diff = y[0].j - this.j;
					if (diff > 1) {
						TextTreeNode left_most = y[0].left_most();
						int left_most_j = left_most.j;
						if (left_most_j > this.j) {
							diff = this.j - left_most_j;
							offset(y, diff);
							if (parent != null) {
								if (parent.hasLeftKinder(this)) {
									parent.j += diff;
									if (debug)
										parent.value = String.valueOf(parent.j);
								}
								offset(parent.rightSiblings(this), diff);
							}
						}
					}
				}
			}
		}

		boolean hasLeftKinder(TextTreeNode kinder) {
			return x != null && Utility.indexOf(x, kinder) >= 0;
		}

		TextTreeNode[] rightSiblings(TextTreeNode kinder) {
			if (x != null) {
				int index = Utility.indexOf(x, kinder);
				if (index >= 0) {
					if (y != null)
						return Utility.copier(Arrays.copyOfRange(x, index + 1, x.length), y);
					return Arrays.copyOfRange(x, index + 1, x.length);
				}
			}
			int index = Utility.indexOf(y, kinder);
			assert index >= 0;
			return Arrays.copyOfRange(y, index + 1, y.length);

		}

		void hierarchize(int level, int... column) {
			if (x != null)
				hierarchize(x, level + 1, column);
			// allocate node for left child at next level in tree; attach node
			i = level;
			j = column[0]++; // update column to next cell in the table
			if (debug)
				value = String.valueOf(j);

			if (y != null)
				hierarchize(y, level + 1, column);
		}

		// the font type should be simsun;
		public String toString() {
			return toString(max_width(), false);
		}

		public String toString(int max_width, boolean shrink) {
			StringBuffer cout = new StringBuffer();
			int currLevel = 0;
			int currCol = 0;

			// build the shadow tree
			hierarchize();
			if (shrink)
				this.shrink((TextTreeNode) null);
			// final int colWidth = Math.max(max_width, max_width()) + 1;
			final int colWidth = max_width;

			// use during the level order scan of the shadow tree
			TextTreeNode currNode;
			//
			// store siblings of each nodeShadow object in a queue so that they
			// are visited in order at the next level of the tree
			Queue<TextTreeNode> q = new LinkedList<TextTreeNode>();
			//
			// insert the root in the queue and set current level to 0
			q.add(this);
			//
			// continue the iterative process until the queue
			// is empty
			while (!q.isEmpty()) {
				// delete front node from queue and make it the
				// current node
				currNode = q.poll();

				if (currNode.i > currLevel) {
					// if level changes, output a newline
					currLevel = currNode.i;
					currCol = 0;
					cout.append(lineSeparator);
				}

				char ch;
				if (currNode.x != null) {
					assert currNode.x.length > 0;
					for (TextTreeNode t : currNode.x)
						q.add(t);
					TextTreeNode head = currNode.x[0];
					// the string is right-aligned / right-justified, that's why
					// there a series of leading ' ';
					int dif = colWidth - strlen(head.value);// for leading ' 's

//					if ((head.j - currCol) * colWidth + dif >= 0)
					cout.append(Utility.toString((head.j - currCol) * colWidth + dif, ' '));
//					if ((currNode.j - head.j) * colWidth - dif >= 0)
					cout.append(Utility.toString((currNode.j - head.j) * colWidth - dif, '_'));
//					else {
//						System.out.println("error occurs!");
//					}

					ch = '_';
				} else {
//					if (currNode.j >= currCol)
					cout.append(Utility.toString((currNode.j - currCol) * colWidth, ' '));
//					else {
//						System.out.println("error occurs!");
//					}

					ch = ' ';
				}

				// for leading white spaces;
				cout.append(Utility.toString(colWidth - strlen(currNode.value), ch) + currNode.value);

				currCol = currNode.j;
				if (currNode.y != null) {
					for (TextTreeNode t : currNode.y)
						q.add(t);

					TextTreeNode last = currNode.y[currNode.y.length - 1];
					cout.append(Utility.toString((last.j - currCol) * colWidth, '_'));

					currCol = last.j;
				}

				++currCol;
			}
			cout.append(lineSeparator);

			return cout.toString();
		}
	}

	public static class Printer implements Closeable {
		public Printer(String file) throws FileNotFoundException {
			out = System.out;
			ps = new PrintStream(file);
			System.setOut(ps);
		}

		PrintStream out;
		PrintStream ps;

		public void close() {
			ps.close();
			System.setOut(out);

		}
	}

	public static char last(String str) {
		return str.charAt(str.length() - 1);
	}

	public static <T> T last(T[] str) {
		return str[str.length - 1];
	}

	public static int last(int[] str) {
		return str[str.length - 1];
	}

	public static <T> T last(List<T> str) {
		return str.get(str.size() - 1);
	}

	public static <T> T last(List<T> str, T newValue) {
		return str.set(str.size() - 1, newValue);
	}

	static public <_Ty> _Ty[] copier(_Ty[] adverb, _Ty element) {
		@SuppressWarnings("unchecked")
		_Ty[] adverbNew = (_Ty[]) Array.newInstance(adverb.getClass().getComponentType(), adverb.length + 1);
		System.arraycopy(adverb, 0, adverbNew, 0, adverb.length);
		adverbNew[adverb.length] = element;
		return adverbNew;
	}

	static public int[] copier(int[] adverb, int element) {
		int[] adverbNew = new int[adverb.length + 1];
		System.arraycopy(adverb, 0, adverbNew, 0, adverb.length);
		adverbNew[adverb.length] = element;
		return adverbNew;
	}

	static public <_Ty> _Ty[] copierSauf(_Ty[] adverb, int i) {
		@SuppressWarnings("unchecked")
		_Ty[] adverbNew = (_Ty[]) Array.newInstance(adverb.getClass().getComponentType(), adverb.length - 1);
		int index = 0;
		for (int j = 0; j < adverb.length; ++j) {
			if (i != j)
				adverbNew[index++] = adverb[j];
		}
		return adverbNew;
	}

	static public int[] copierSauf(int[] adverb, int i) {
		int[] adverbNew = new int[adverb.length - 1];
		int index = 0;
		for (int j = 0; j < adverb.length; ++j) {
			if (i != j)
				adverbNew[index++] = adverb[j];
		}
		return adverbNew;
	}

	@SuppressWarnings("unchecked")
	static public <_Ty> _Ty[] copier(_Ty[] a, _Ty[] b) {
		_Ty[] c = (_Ty[]) Array.newInstance(a.getClass().getComponentType(), a.length + b.length);
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	@SuppressWarnings("unchecked")
	static public <_Ty> _Ty[] remove_null(_Ty[] a) {
		int length = a.length;
		for (_Ty e : a) {
			if (e == null) {
				--length;
			}
		}
		if (length == 0)
			return null;
		if (length == a.length)
			return a;

		_Ty[] arr = (_Ty[]) Array.newInstance(a.getClass().getComponentType(), length);
		int i = 0;
		for (_Ty e : a) {
			if (e != null) {
				arr[i++] = e;
			}
		}

		return arr;
	}

	// post-condition: return a value in the range of [0, length]; the value
	// returned is no less than _Val;
	public static <_Ty> int binary_search(Vector<_Ty> arr, _Ty value, Comparator<_Ty> comparator) {
		int begin = 0, end = arr.size();
		for (;;) {
			int mid = (begin + end) >> 1;
			if (begin == end)
				return mid;
			int ret = comparator.compare(arr.get(mid), value);
			if (ret < 0)
				begin = mid + 1;
			else if (ret > 0)
				end = mid;
			else
				return mid;
		}
	}

	// post-condition: return a value in the range of [0, length]; the value
	// returned is no less than _Val;
	public static <_Ty> int binary_search(_Ty[] arr, _Ty value, Comparator<_Ty> comparator) {
		int begin = 0, end = arr.length;
		for (;;) {
			int mid = (begin + end) >> 1;
			if (begin == end)
				return mid;
			int ret = comparator.compare(arr[mid], value);
			if (ret < 0)
				begin = mid + 1;
			else if (ret > 0)
				end = mid;
			else
				return mid;
		}
	}

	static public class CharArray implements Iterable<Character> {

		CharArray(String str) {
			this.str = str;
		}

		String str;

		class CharIterator implements Iterator<Character> {
			int i = -1;

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return ++i < str.length();
			}

			@Override
			public Character next() {
				// TODO Auto-generated method stub
				return str.charAt(i);
			}
		}

		@Override
		public Iterator<Character> iterator() {
			// TODO Auto-generated method stub
			return new CharIterator();
		}

	}

	static CharArray toCharArray(String str) {
		return new CharArray(str);
	}

	public static void testCharArray() throws IOException {
		String text = new Utility.Text(Utility.corpusDirectory() + "ahocorasick/text.txt").fetchContent();
		text += text + text;
		long start = System.currentTimeMillis();
		for (char ch : text.toCharArray()) {
			++ch;
		}
		System.out.println("time cost = " + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		for (char ch : toCharArray(text)) {
			++ch;
		}
		System.out.println("time cost = " + (System.currentTimeMillis() - start));
	}

	/*
	 * quote for javaScript or python language
	 */
	public static String quote(String param) {
		return param.replace("\\", "\\\\").replace("'", "\\'");
	}

	/*
	 * quote for html language within the input tag
	 */
	public static String quote_html(String param) {
		return param.replace("&", "&amp;").replace("'", "&apos;").replace("\\", "\\\\");
	}

	/*
	 * quote for html language as text node
	 */
	public static String str_html(String param) {
		return param.replace("&", "&amp;").replaceAll("<(?=[a-zA-Z!/])", "&lt;");
	}

	/*
	 * quote for mysql language for query
	 */
	public static String quote_mysql(String param) {
		return param.replace("'", "''").replace("\\", "\\\\");
	}

	static public void printJavaInfo() {
		System.out.println("java版本号：" + System.getProperty("java.version")); // java版本号
		System.out.println("Java提供商名称：" + System.getProperty("java.vendor")); // Java提供商名称
		System.out.println("Java提供商网站：" + System.getProperty("java.vendor.url")); // Java提供商网站
		System.out.println("jre目录：" + System.getProperty("java.home")); // Java，哦，应该是jre目录
		System.out.println("Java虚拟机规范版本号：" + System.getProperty("java.vm.specification.version")); // Java虚拟机规范版本号
		System.out.println("Java虚拟机规范提供商：" + System.getProperty("java.vm.specification.vendor")); // Java虚拟机规范提供商
		System.out.println("Java虚拟机规范名称：" + System.getProperty("java.vm.specification.name")); // Java虚拟机规范名称
		System.out.println("Java虚拟机版本号：" + System.getProperty("java.vm.version")); // Java虚拟机版本号
		System.out.println("Java虚拟机提供商：" + System.getProperty("java.vm.vendor")); // Java虚拟机提供商
		System.out.println("Java虚拟机名称：" + System.getProperty("java.vm.name")); // Java虚拟机名称
		System.out.println("Java规范版本号：" + System.getProperty("java.specification.version")); // Java规范版本号
		System.out.println("Java规范提供商：" + System.getProperty("java.specification.vendor")); // Java规范提供商
		System.out.println("Java规范名称：" + System.getProperty("java.specification.name")); // Java规范名称
		System.out.println("Java类版本号：" + System.getProperty("java.class.version")); // Java类版本号
		System.out.println("Java类路径：" + System.getProperty("java.class.path")); // Java类路径
		System.out.println("Java lib路径：" + System.getProperty("java.library.path")); // Java lib路径
		System.out.println("Java输入输出临时路径：" + System.getProperty("java.io.tmpdir")); // Java输入输出临时路径
		System.out.println("Java编译器：" + System.getProperty("java.compiler")); // Java编译器
		System.out.println("Java执行路径：" + System.getProperty("java.ext.dirs")); // Java执行路径
		System.out.println("操作系统名称：" + System.getProperty("os.name")); // 操作系统名称
		System.out.println("操作系统的架构：" + System.getProperty("os.arch")); // 操作系统的架构
		System.out.println("操作系统版本号：" + System.getProperty("os.version")); // 操作系统版本号
		System.out.println("文件分隔符：" + System.getProperty("file.separator")); // 文件分隔符
		System.out.println("路径分隔符：" + System.getProperty("path.separator")); // 路径分隔符
		System.out.println("直线分隔符：" + System.getProperty("line.separator")); // 直线分隔符
		System.out.println("操作系统用户名：" + System.getProperty("user.name")); // 用户名
		System.out.println("操作系统用户的主目录：" + System.getProperty("user.home")); // 用户的主目录
		System.out.println("当前程序所在目录：" + System.getProperty("user.dir")); // 当前程序所在目录
	}

	public static String word_boundary(String regex) {
//		^ 取反，&& 逻辑与 （并且）
//		[^456] 匹配一个 非4非5非6的任意字符，可以匹配：a、x、1、8、好、中……
//		[a-o&&[def]] 等价于[def]，可以匹配：d、e、f
//		[a-d&&[^bc]] 等价于 [ad]，可以匹配：a、d
//		\w == "[一-龥a-zA-Zａ-ｚＡ-Ｚ0-9０-９_]"
		regex = regex.replace("\\w", "[\\pL\\pM\\p{Nd}\\p{Nl}\\p{Pc}[\\p{InEnclosedAlphanumerics}&&\\p{So}]]");
		regex = regex.replace("\\W", "[^\\pL\\pM\\p{Nd}\\p{Nl}\\p{Pc}[\\p{InEnclosedAlphanumerics}&&\\p{So}]]");
		return regex;
	}

	public static String rtrim(String str) {
		int num = str.length();
		for (int i = num - 1; i > -1; i--) {
			if (!(str.substring(i, i + 1).equals(" "))) {
				return str.substring(0, i + 1);
			}
		}
		return str;
	}

	public static String ltrim(String str) {
		int num = str.length();
		for (int i = 0; i < num; ++i) {
			if (!Character.isSpaceChar(str.charAt(i))) {
				return str.substring(i);
			}
		}
		return "";
	}

	public static String[] flatten(String[][] text) {
		int length = 0;
		for (String[] sentence : text) {
			length += sentence.length;
		}
		String[] result = new String[length];

		int index = 0;
		for (String[] sentence : text) {
			for (String word : sentence) {
				result[index++] = word;
			}
		}
		return result;
	}

	public static class RegexIterator implements Iterator<String[]>, Iterable<String[]> {
		Matcher matcher;
		// int beginIndex = 0;

		public RegexIterator(String str, String regex) {
			matcher = Pattern.compile(regex).matcher(str);
		}

		@Override
		public boolean hasNext() {
			return matcher.find();
		}

		@Override
		public java.lang.String[] next() {
			// if (beginIndex != matcher.start()) {
			// String res = "parsing error for " + str;
			// res += "\n" + "regex = " + regex;
			// res += "\n" + "regex = " + regex;
			// res += "\n" + "str + beginIndex = " + str.substring(beginIndex);
			// res += "\n" + "str + matcher.start() = " + str.substring(matcher.start());
			// throw new Exception(res);
			// }
			String[] group = new String[matcher.groupCount()];
			for (int i = 0; i < group.length; ++i)
				group[i] = matcher.group(i + 1);
			// x.add(group);
			// beginIndex = matcher.end();

			return group;
		}

		@Override
		public RegexIterator iterator() {
			// TODO Auto-generated method stub
			return this;
		}
	}

	public static RegexIterator regex(String str, String regex) throws Exception {
		return new RegexIterator(str, regex);
	}

	public static String[] regexSingleton(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		String[] x = null;
		int beginIndex = 0;
		if (matcher.find()) {
			if (beginIndex != matcher.start()) {
				return x;
			}
			x = new String[matcher.groupCount() + 1];
			for (int i = 0; i < x.length; ++i)
				x[i] = matcher.group(i);
		}
		return x;
	}

	public interface Replacer {
		String replace(String str);
	}

	public static String replace(String str, String regex, Replacer replacer) throws Exception {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		String tmp = "";
		int beginIndex = 0;
		while (matcher.find()) {
			if (beginIndex != matcher.start()) {
				tmp += str.substring(beginIndex, matcher.start());
			}
			tmp += replacer.replace(matcher.group(0));
			beginIndex = matcher.end();
		}
		if (beginIndex == 0)
			return str;
		tmp += str.substring(beginIndex);
		return tmp;
	}

}