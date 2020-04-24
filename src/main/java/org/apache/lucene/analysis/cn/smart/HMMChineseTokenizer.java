/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lucene.analysis.cn.smart;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.cn.smart.hhmm.SegToken;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.analysis.util.SegmentingTokenizerBase;
import org.apache.lucene.util.AttributeFactory;

import com.util.Utility;

/**
 * Tokenizer for Chinese or mixed Chinese-English text.
 * <p>
 * The analyzer uses probabilistic knowledge to find the optimal word
 * segmentation for Simplified Chinese text. The text is first broken into
 * sentences, then each sentence is segmented into words.
 */
public class HMMChineseTokenizer extends SegmentingTokenizerBase {
	/** used for breaking the text into sentences */
	private static final BreakIterator sentenceProto = BreakIterator.getSentenceInstance(Locale.ROOT);

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
	private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
	private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);

	private final WordSegmenter wordSegmenter = new WordSegmenter();
	private Iterator<SegToken> tokens;

	/** Creates a new HMMChineseTokenizer */
	public HMMChineseTokenizer() {
		this(DEFAULT_TOKEN_ATTRIBUTE_FACTORY);
	}

	/** Creates a new HMMChineseTokenizer, supplying the AttributeFactory */
	public HMMChineseTokenizer(AttributeFactory factory) {
		super(factory, (BreakIterator) sentenceProto.clone());
	}

	@Override
	protected void setNextSentence(int sentenceStart, int sentenceEnd) {
		String sentence = new String(buffer, sentenceStart, sentenceEnd - sentenceStart);
//		System.out.println("analyzing sentence : \n" + sentence);
		tokens = wordSegmenter.segmentSentence(sentence, offset + sentenceStart).iterator();
	}

	@Override
	protected boolean incrementWord() {
		if (tokens == null || !tokens.hasNext()) {
//			System.out.println();
			return false;
		} else {
			SegToken token = tokens.next();
			clearAttributes();

//			System.out.print(new String(token.charArray) + ' ');

			termAtt.copyBuffer(token.charArray, 0, token.charArray.length);
			offsetAtt.setOffset(correctOffset(token.startOffset), correctOffset(token.endOffset));
			typeAtt.setType("word");
			return true;
		}
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		tokens = null;
	}

	static String regex = "[^;!?；！？…。\\r\\n]+[;!?；！？…。\\r\\n]*";

	public static String[] split_into_sentences(String document) {
		ArrayList<String> texts = new ArrayList<String>();

		Matcher m = Pattern.compile(regex).matcher(document);
		while (m.find()) {
			String line = m.group().trim();
//		        # if the current sentence is correct, skipping processing!
			if (!Pattern.compile("^[’”]").matcher(line).find() || texts.isEmpty()) {
//		# sentence boundary detected!            
				texts.add(line);
				continue;
			}
			if (Pattern.compile("^.[,)\\]}，）】》、的]").matcher(line).find()) {
//		#                 for the following '的 ' case, this sentence should belong to be previous one:
//		#                 ”的文字，以及选项“是”和“否”。
				if (line.substring(1, 3).equals("的确")) {
//		#                         for the following special case: 
//		#                         ”的确， GPS和其它基于卫星的定位系统为了商业、公共安全和国家安全的用 途而快速地发展。
					Utility.last(texts, Utility.last(texts) + line.charAt(0));

//		# sentence boundary detected! insert end of line here
					texts.add(Utility.ltrim(line.substring(1)));
					continue;
				}
//		#                 for the following comma case:
//		#                 ”,IEEE Jounalon Selected Areas in Communications,Vol.31,No.2,Feburary2013所述。
				Utility.last(texts, Utility.last(texts) + line);
				continue;
			}
			int boundary_index;
			Matcher _m = Pattern.compile("^.[;.!?:；。！？：…\\r\\n]+").matcher(line);
			if (_m.find())
				boundary_index = _m.end();
			else
				boundary_index = 1;
//		#                 considering the following complex case:                 
//		#                 ”!!!然后可以通过家长控制功能禁止观看。        
//		# sentence boundary detected! insert end of line here
			Utility.last(texts, Utility.last(texts) + line.substring(0, boundary_index));
			if (boundary_index < line.length())
				texts.add(Utility.ltrim(line.substring(boundary_index)));
		}
		return texts.toArray(new String[texts.size()]);
	}

	public static void main(String[] args) {
		String[] texts = { "第一段文字中有-\"hello word\"的内容吧。。。", "第二段文字中有‘hello word。…’的内容吧。。",
				"第三段文字中有(“hello word？。”)内容吧。。?", "第四段文字中有【（“hello word。”）】内容吧。。", "    第五段文字中有【hello word】内容吧。",
				"“第六段文字中有{hello word}内容吧。！”", "“第七段文字中有《hello word》内容吧。！？”！！！！", "第八段文字中有('hello word')内容吧。；",
				"    第九段文字中有‘hello word；’的内容吧。;", "‘第十段文字中有hello word的内容吧。’?", };

		String document = String.join("", texts);
		for (String line : split_into_sentences(document)) {
			System.out.println(line);
		}
	}
}
