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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Logger;
import org.jblas.DoubleMatrix;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.Utility.Couplet;
import com.util.Utility.LNodeShadow;

public class Utility {
	public static void main(String[] args) throws Exception {
		char[] arr = new char[16];
		System.out.println(arr.length);
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
			double dif = System.currentTimeMillis() - start;
			dif /= 1000;
			log.info("Total Time duration " + dif + " seconds or " + (dif / 60) + " minutes");
			start();
		}
	}

	public static Logger log = Logger.getLogger(Utility.class);

	static public String convertSegmentationToOriginal(String[] arr) {
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

		return str.trim().split("\\s+");
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
		return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= 'ａ' && ch <= 'ｚ' || ch >= 'Ａ' && ch <= 'Ｚ';
	}

	public static final String EnglishPunctuation = ",.:;!?()\\[\\]{}'\"=<>";
	public static final String ChinesePunctuation = "，。：；！？（）「」『』【】～‘’′”“《》、…．·";
	public static final String sPunctuation = EnglishPunctuation + ChinesePunctuation;

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

	static public String jsonify(Object object) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(object);
	}

	static public <T> T dejsonify(String json, Class<T> valueType) throws IOException {
		return new ObjectMapper().readValue(json, valueType);
	}

	static public <String> String[] tuple(String... arr) {
		return arr;
	}

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

	public static int length(String value) {
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

	public static class LNodeShadow extends Couplet<LNodeShadow[], LNodeShadow[]> {
		// objects hold a formatted label string and the level,column
		// coordinates for a shadow tree node
		public String value; // formatted node value
		int i, j;

		LNodeShadow() {
		}

		public LNodeShadow(String value) {
			this.value = value;
		}

		static int max_width(LNodeShadow[] list) {
			int length = 0;
			for (LNodeShadow x : list) {
				int width = x.max_width();
				if (width > length) {
					length = width;
				}
			}
			return length;
		}

		public int max_width() {
			int width = length(value);
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

		static void hierarchize(LNodeShadow list[], int level, int... column) {
			for (LNodeShadow x : list) {
				x.hierarchize(level, column);
			}
		}

		// static int size(List<LNodeShadow> list){
		// int size = 0;
		// for (LNodeShadow x : list){
		// size += x.size();
		// }
		// return size;
		// }
		//
		// int size(){
		// int size = 1;
		// if (x != null)
		// size += size(x);
		// if (y != null)
		// size += size(y);
		// return size;
		// }
		//
		// static int sizeInbeween(List<LNodeShadow> list){
		// int size = list.size();
		// if (size == 1)
		// return size;
		// int i = 0;
		// size += size(list.get(i).y);
		//
		// for (++i; i < list.size() - 1; ++i){
		// size += list.get(i).size();
		// }
		// size += size(list.get(i).x);
		// return size;
		// }
		//
		void hierarchize(int level, int... column) {
			if (x != null)
				hierarchize(x, level + 1, column);
			// allocate node for left child at next level in tree; attach node
			i = level;
			j = column[0]++; // update column to next cell in the table

			if (y != null)
				hierarchize(y, level + 1, column);
		}

		// the font type should be simsun;
		public String toString() {
			return toString(max_width());
		}

		public String toString(int max_width) {
			String cout = "";
			int currLevel = 0;
			int currCol = 0;

			// build the shadow tree
			hierarchize();
			// final int colWidth = Math.max(max_width, max_width()) + 1;
			final int colWidth = max_width;

			// use during the level order scan of the shadow tree
			LNodeShadow currNode;
			//
			// store siblings of each nodeShadow object in a queue so that they
			// are visited in order at the next level of the tree
			Queue<LNodeShadow> q = new LinkedList<LNodeShadow>();
			//
			// insert the root in the queue and set current level to 0
			q.add(this);
			//
			// continue the iterative process until the queue
			// is empty
			while (q.size() != 0) {
				// delete front node from queue and make it the
				// current node
				currNode = q.poll();

				if (currNode.i > currLevel) {
					// if level changes, output a newline
					currLevel = currNode.i;
					currCol = 0;
					cout += lineSeparator;
				}

				char ch;
				if (currNode.x != null) {
					assert currNode.x.length > 0;
					for (LNodeShadow t : currNode.x)
						q.add(t);
					LNodeShadow head = currNode.x[0];
					// the string is right-aligned / right-justified, that's why
					// there a series of leading ' ';
					int dif = colWidth - length(head.value);// for leading ' 's
					cout += Utility.toString((head.j - currCol) * colWidth + dif, ' ');
					cout += Utility.toString((currNode.j - head.j) * colWidth - dif, '_');

					ch = '_';
				} else {
					cout += Utility.toString((currNode.j - currCol) * colWidth, ' ');

					ch = ' ';
				}

				// for leading white spaces;
				cout += Utility.toString(colWidth - length(currNode.value), ch) + currNode.value;

				currCol = currNode.j;
				if (currNode.y != null) {
					for (LNodeShadow t : currNode.y)
						q.add(t);

					LNodeShadow last = currNode.y[currNode.y.length - 1];
					cout += Utility.toString((last.j - currCol) * colWidth, '_');

					currCol = last.j;
				}

				++currCol;
			}
			cout += lineSeparator;

			return cout;
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

}