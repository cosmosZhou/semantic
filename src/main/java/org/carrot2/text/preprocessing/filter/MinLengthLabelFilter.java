
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2019, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package org.carrot2.text.preprocessing.filter;

import org.carrot2.core.LanguageCode;
import org.carrot2.core.attribute.Processing;
import org.carrot2.text.preprocessing.PreprocessingContext;
import org.carrot2.util.attribute.*;

/**
 * Accepts labels whose length in characters is greater or equal to the provided
 * value.
 */
@Bindable(prefix = "MinLengthLabelFilter")
public class MinLengthLabelFilter extends SingleLabelFilterBase {
	/**
	 * Remove labels shorter than 3 characters. Removes labels whose total length in
	 * characters, including spaces, is less than 3.
	 */
	@Input
	@Processing
	@Attribute
	@Label("Remove short labels")
	@Level(AttributeLevel.BASIC)
	@Group(DefaultGroups.LABELS)
	public boolean enabled = true;

	private final static int MIN_LENGTH = 3;

	@Override
	public boolean acceptPhrase(PreprocessingContext context, int phraseIndex) {
		final int[] wordIndices = context.allPhrases.wordIndices[phraseIndex];
		char[][] wordImage = context.allWords.image;

//		int wordIndex = 0;
//		int length = wordImage[wordIndices[wordIndex++]].length;
//		while (length < MIN_LENGTH && wordIndex < wordIndices.length) {		
//			length += wordImage[wordIndices[wordIndex]].length + 1 /* space */;
//			wordIndex++;
//		}

		int length = 0;
		for (int wordIndex = 0; wordIndex < wordIndices.length; ++wordIndex) {
			length += wordImage[wordIndices[wordIndex]].length + 1;
		}

		LanguageCode lang = context.language.getLanguageCode();
		switch (lang) {
		case CHINESE_SIMPLIFIED:
			break;
		default:
			length += wordIndices.length - 1;
		}
		
		return length >= MIN_LENGTH && withinMaxLength(lang, length);
	}

	@Override
	public boolean acceptWord(PreprocessingContext context, int wordIndex) {
		int length = context.allWords.image[wordIndex].length;
		return length >= MIN_LENGTH && withinMaxLength(context.language.getLanguageCode(), length);
	}

	static public boolean withinMaxLength(LanguageCode lang, int length) {
		switch (lang) {
		case CHINESE_SIMPLIFIED:
			return length <= 8;
		case ENGLISH:
			return length <= 20;
		default:
			return true;
		}
	}

	public boolean isEnabled() {
		return enabled;
	}
}
