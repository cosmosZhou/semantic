package test;

import junit.framework.TestCase;

/**
 * @author hankcs
 */
public class TestJavaObject extends TestCase {

	class MyObject {

	}

	static class MyStaticObject {

	}

	public void test() throws Exception {
		String arr_String[] = new String[5];
		MyStaticObject arr_MyStaticObject[] = new MyStaticObject[5];
		MyObject arr_MyObject[] = new MyObject[5];
		int arr_int[] = new int[5];
		System.out.println(arr_MyObject);
		System.out.println(arr_MyStaticObject);
		System.out.println(arr_String);
		System.out.println(arr_int);
	}

}
