package com.deeplearning;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jblas.DoubleMatrix;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nlp.AhoCorasick;
import com.util.Native;
import com.util.Utility;

public class NERTaggerDict {

	/**
	 * 
	 */
	public static Map<String, NERTagger> dict;
	static {
		synchronized (NERTaggerDict.class) {
			System.out.println("initializing NERTaggerDict");
			dict = new HashMap<String, NERTagger>();
		}
	}

	public static NERTagger getTagger(String service) throws IOException {
		NERTagger tagger;
		if (!dict.containsKey(service)) {
			synchronized (dict) {

				Utility.BinaryReader dis = new Utility.BinaryReader(
						String.format(Utility.modelsDirectory() + "cn/ner/%s.bin", service));
				tagger = new NERTagger(dis);
				dict.put(service, tagger);

			}
		} else {
			tagger = dict.get(service);
		}
		return tagger;

	}

	public static int[] get_repertoire_code(String service, String text, Enum[] enums) throws Exception {
		HashMap<String, Object> intent = AhoCorasick.instance.ahocorasick(text, service);
		int[] repertoire_code = repertoire_code(dict2tags(text, enums[0], intent));
		return repertoire_code;
	}

	static public Map<String, Object> predict(String service, String text, boolean cpp) throws Exception {
		service = AhoCorasick.servicedic(service);

		Enum[] enums = get_enums(service);

		int[] repertoire_code = get_repertoire_code(service, text, enums);

		return toDict(enums, text,
				cpp ? Native.ner(service, text, repertoire_code) : getTagger(service).predict(text, repertoire_code));
	}

	static public double[][][] _predict(String service, String text, boolean cpp) throws Exception {
		service = AhoCorasick.servicedic(service);

		Enum[] enums = get_enums(service);

		int[] repertoire_code = get_repertoire_code(service, text, enums);

		if (cpp)
			return Native.NER(service, text, repertoire_code);
		else
			return getTagger(service)._predict(text, repertoire_code);
	}

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
	}

	enum enum_audio {
		other,

		_song, song,

		_name, name,

		_artist, artist,

		_singer, singer,

		_genre, genre,

		_tag, tag,

		_language, language,

		_album, album,

		_appname, appname,

		_mode, mode,

		_category, category,

		_type, type,

		_episode, episode,

		_billboard, billboard,
	}

	enum enum_map {
		other,

		_fromAddress, fromAddress,

		_fromCity, fromCity,

		_address, address,

		_district, district,

		_city, city,

		_province, province,

		_strategy, strategy,

		_appname, appname,

		_trafficType, trafficType,

		_code, code,

		_poi, poi,

		_category, category,

		_radius, radius,

		_price, price,
	}

	enum enum_call {

		other,

		_name, name,

		_number, number,

		_method, method,

		_content, content,

		_type, type,

		_relation, relation,
	}

	enum enum_news {
		other,

		_tag, tag,

		_location, location,

		_date, date,

		_name, name,

		_frequency, frequency,

		_station, station,
	}

	enum enum_stock {
		other, _id, id, _name, name, _exchange, exchange, _dish, dish, _type, type, _url, url,
	}

	enum enum_weather {
		other,

		_province, province,

		_city, city,

		_district, district,

		_date, date,

		_weatherinfo, weatherinfo,
	}

	enum enum_video {
		other,

		_category, category,

		_actor, actor,

		_director, director,

		_name, name,

		_season, season,

		_episode, episode,

		_channel, channel,

		_program, program,

		_type, type,

		_starttime, starttime,

		_endtime, endtime,
	}

	enum enum_train {
		other,

		_trainNo, trainNo,

		_flightNo, flightNo,

		_origin, origin,

		_destination, destination,

		_departDate, departDate,

		_departTime, departTime,

		_airlineCode, airlineCode,

		_trainType, trainType,

		_sort, sort,
	}

	enum enum_websearch {
		other,

		_domain, domain,

		_type, type,

		_text, text,

		_url, url,

		_name, name,
	}

	enum enum_cmd {
		other,

		_confirm, confirm,

		_operator, operator,

		_operand, operand,

		_name, name,

		_date, date,

		_event, event,

		_content, content,

		_repeatType, repeatType,
	}

	public static int[] repertoire_code(Enum[] array) {
		int[] list = new int[array.length];
		for (int i = 0; i < list.length; ++i) {
			list[i] = array[i].ordinal() + 1;
		}
		return list;

	}

	public static Enum[] dict2tags(String sent, Enum other, Map<String, Object> dic) throws JsonProcessingException {

		ArrayList<String[]> pair = new ArrayList<String[]>();

		for (Map.Entry<String, Object> p : dic.entrySet()) {
			String key = p.getKey();
			Object value = p.getValue();

			if (value instanceof ArrayList) {
				for (String v : (ArrayList<String>) value) {
					pair.add(Utility.tuple(key, v));
				}

			} else {
				pair.add(Utility.tuple(key, (String) value));
			}
		}

		Collections.sort(pair, new Comparator<String[]>() {

			@Override
			public int compare(String[] o1, String[] o2) {
				return Integer.compare(o2[1].length(), o1[1].length());
			}

		});

		for (int _ = 0; _ < 5; ++_) {
			Object res = trial(sent, other, pair);
			if (res instanceof Integer) {
				int i = (int) res;
				Utility.swap(pair, i, i - 1);
			} else {
				return (Enum[]) res;
			}

		}

		System.out.println("sent = " + sent);
		System.out.println("service = " + other);
		System.out.println("dic = " + Utility.jsonify(dic));

		return null;
	}

	public static Object trial(String sent, Enum other, ArrayList<String[]> pair) {
		Enum tags[] = new Enum[sent.length()];
		Arrays.fill(tags, other);

		for (int fail_index = 0; fail_index < pair.size(); ++fail_index) {
			String[] key_value = pair.get(fail_index);
			String key = key_value[0];
			String value = key_value[1];
			int index = sent.indexOf(value);
			while (tags[index] != other) {
				while (true) {
					++index;
					if (index == sent.length())
						return fail_index;
					if (tags[index] == other)
						break;
				}

				index = sent.indexOf(value, index);
				if (index < 0)
					return fail_index;

			}
			tags[index] = Enum.valueOf(other.getClass(), key);
			for (int i = index + 1; i < index + value.length(); ++i) {
				if (tags[i] != other)
					return fail_index;

				tags[i] = Enum.valueOf(other.getClass(), '_' + key);
			}

		}

		return tags;

	}

	public static Enum[] get_enums(String service) {
		Enum[] enums = null;
		switch (service) {
		case "map":
			enums = enum_map.values();
			break;
		case "audio":
			enums = enum_audio.values();
			break;
		case "call":
			enums = enum_call.values();
			break;
		case "news":
			enums = enum_news.values();
			break;
		case "stock":
			enums = enum_stock.values();
			break;
		case "weather":
			enums = enum_weather.values();
			break;
		case "video":
			enums = enum_video.values();
			break;
		case "train":
			enums = enum_train.values();
			break;
		case "websearch":
			enums = enum_websearch.values();
			break;
		case "cmd":
			enums = enum_cmd.values();
			break;
		}
		return enums;
	}

	public static Map<String, Object> toDict(Enum enums[], String predict_text, int[] argmax) {
		Map<String, Object> dic = new HashMap<String, Object>();
		StringBuffer sstr = new StringBuffer();
		assert argmax.length == predict_text.length();

		int i = 0;
		if (argmax[i] != 0)
			sstr.append(predict_text.charAt(i));

		for (++i; i <= argmax.length; ++i) {
			if (i == argmax.length || (argmax[i] & 1) == 0) {
				if (argmax[i - 1] != 0) {

					String tag = enums[argmax[i - 1]].name();

					if (tag.startsWith("_"))
						tag = tag.substring(1);

					if (dic.containsKey(tag)) {
						if (!(dic.get(tag) instanceof List)) {
							dic.put(tag, Utility.list(dic.get(tag)));
						}
						((List) dic.get(tag)).add(sstr.toString());
					} else
						dic.put(tag, sstr.toString());
				}
				sstr.setLength(0);
			}
			if (i < argmax.length && argmax[i] != 0)
				sstr.append(predict_text.charAt(i));
		}

		assert sstr.length() == 0;

		return dic;
	}

	public static Logger log = Logger.getLogger(NERTaggerDict.class);
}
