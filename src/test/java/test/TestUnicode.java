package test;

import junit.framework.TestCase;

/**
 * @author hankcs
 */
public class TestUnicode extends TestCase {
	int get_bits(byte ch, int start, int size, int shift) {
		return ((ch >> start) & ((1 << size) - 1)) << shift;
	}

	int get_bits(byte ch, int start, int size) {
		return (ch >> start) & ((1 << size) - 1);
	}

	int get_bits(byte ch, int start, int size, byte _ch) {
		return get_bits(ch, start, size, _ch, 8 - size);
	}

	int get_bits(byte ch, int start, int size, byte _ch, int _size) {
		return get_bits(_ch, 0, _size, size) + get_bits(ch, start, size);
	}

	int utf2unicode(byte[] pText) {
//		https://blog.csdn.net/qq_38279908/article/details/89329740
//		https://www.cnblogs.com/cfas/p/7931787.html
		// #include <codecvt> // std::codecvt_utf8
//		return std::wstring_convert<std::codecvt_utf8<wchar_t> >().from_bytes(str);
		int[] uchar = new int[4];

		switch (pText.length) {
		case 1:
			uchar[1] = 0;
			uchar[0] = pText[0];
			break;
		case 2:
//			U-000007FF: 110xxxxx 10xxxxxx
			uchar[1] = get_bits(pText[0], 2, 4);
			uchar[0] = get_bits(pText[1], 0, 6, pText[0]);
			break;
		case 3:
//			U-0000FFFF: 1110xxxx 10xxxxxx 10xxxxxx
			uchar[1] = get_bits(pText[1], 2, 4, pText[0]);
			uchar[0] = get_bits(pText[2], 0, 6, pText[1]);
			break;
		case 4:
			// U-001FFFFF: 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
			uchar[2] = get_bits(pText[1], 4, 2, pText[0], 4);
			uchar[1] = get_bits(pText[2], 2, 4, pText[1]);
			uchar[0] = get_bits(pText[3], 0, 6, pText[2]);
			break;
		case 5:
			// U-03FFFFFF: 111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
			uchar[3] = get_bits(pText[0], 0, 3);
			uchar[2] = get_bits(pText[2], 4, 2, pText[0]);
			uchar[1] = get_bits(pText[3], 2, 4, pText[2]);
			uchar[0] = get_bits(pText[4], 0, 6, pText[3]);
			break;
		case 6:
			// U-7FFFFFFF: 1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx
			uchar[3] = get_bits(pText[1], 0, 6, pText[0]);
			uchar[2] = get_bits(pText[3], 4, 2, pText[2]);
			uchar[1] = get_bits(pText[4], 2, 4, pText[3]);
			uchar[0] = get_bits(pText[5], 0, 6, pText[4]);
			break;
		}

		return uchar[0] + (uchar[1] << 8) + (uchar[2] << 16) + (uchar[3] << 24);
	}

	void print_binary(byte bytearray[]) {
		for (byte b : bytearray) {
			print_binary(b);
		}
		System.out.println();
	}

	void print_binary(byte b) {
		int unsigned = 256 + b;
		for (int i = 7; i >= 0; --i) {
			System.out.print(((unsigned & (1 << i)) == 0) ? 0 : 1);
		}
	}

	void print_binary(char b) {
		int unsigned = 65536 + b;
		for (int i = 15; i >= 0; --i) {
			System.out.print(((unsigned & (1 << i)) == 0) ? 0 : 1);
		}
	}

	void print_binary(char b, int tab) {
		int unsigned = 65536 + b;
		for (int i = 15; i >= 0; --i) {
			System.out.print(((unsigned & (1 << i)) == 0) ? 0 : 1);
			if (i == tab)
				System.out.print('\t');
		}
	}

	void print_binary(int unsigned) {
		for (int i = 31; i >= 0; --i) {
			System.out.print(((unsigned & (1 << i)) == 0) ? 0 : 1);
			if (i == 16)
				System.out.print('\t');
		}
		System.out.println();
	}

	char[] unicode2jchar(int unsigned) {
		char[] jchars = new char[2];
		jchars[0] = 0xd800;
		jchars[1] = 0xdc00;

		unsigned -= 65536;

		jchars[0] = (char) (jchars[0] | (unsigned >> 10));
		jchars[1] = (char) (jchars[1] | (unsigned & 0x03ff));

		return jchars;
	}

	void print_binary(String javaStr) {
		for (char ch : javaStr.toCharArray()) {
			print_binary(ch);
			System.out.print('\t');
		}
		System.out.println();
	}

	void print_binary(char[] jchars) {
		for (char ch : jchars) {
			print_binary(ch);
			System.out.print('\t');
		}
		System.out.println();
	}

	static boolean test_jchar2 = false;

	public void test_jchar2() throws Exception {

		byte[] bytearray = { (byte) 0xf0, (byte) 0x80, (byte) 0x80, (byte) 0x80 };

		if (!test_jchar2)
			return;

		for (int l = 0; l < 7; ++l) {
			bytearray[0] = (byte) (0xf0 + l);
			for (int i = 0; i < 64; ++i) {
				bytearray[1] = (byte) (0x80 + i);
				for (int j = 0; j < 64; ++j) {
					bytearray[2] = (byte) (0x80 + j);
					for (int k = 0; k < 64; ++k) {
						bytearray[3] = (byte) (0x80 + k);
						String javaStr = new String(bytearray, "utf8");
						if (javaStr.length() != 2 || javaStr.charAt(0) == 65536 - 3)
							continue;

						int unicode = utf2unicode(bytearray);
						char jchars[] = unicode2jchar(unicode);
						System.out.println("unicode");
						print_binary(unicode);

						System.out.println("java chars from unicode");

						print_binary(jchars);
						System.out.println("java chars");
						print_binary(javaStr);

						assertEquals(new String(jchars), javaStr);
					}
				}
			}
		}
	}

	public void test_jchar3() throws Exception {

		byte[] bytearray = { (byte) 0xf8, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0x80 };

		int []lengths = new int[6];
		for (int l = 0; l < 4; ++l) {
			bytearray[0] = (byte) (0xf8 + l);
			for (int i = 0; i < 64; ++i) {
				bytearray[1] = (byte) (0x80 + i);
				for (int j = 0; j < 64; ++j) {
					bytearray[2] = (byte) (0x80 + j);
					for (int k = 0; k < 64; ++k) {
						bytearray[3] = (byte) (0x80 + k);
						for (int m = 0; m < 64; ++m) {
							bytearray[4] = (byte) (0x80 + m);
							String javaStr = new String(bytearray, "utf8");
							
							++lengths[javaStr.length()];
							
							if (javaStr.length() != 3 || javaStr.charAt(0) == 65536 - 3)
								continue;

							int unicode = utf2unicode(bytearray);
							char jchars[] = unicode2jchar(unicode);
							System.out.println("unicode");
							print_binary(unicode);

							System.out.println("java chars from unicode");

							print_binary(jchars);
							System.out.println("java chars");
							print_binary(javaStr);

							assertEquals(new String(jchars), javaStr);
						}
					}
				}
			}
		}
		for (int i = 0; i < lengths.length; i++) {
			System.out.printf("length %d's count = %d\n", i, lengths[i]);	
		}	
	}
}
