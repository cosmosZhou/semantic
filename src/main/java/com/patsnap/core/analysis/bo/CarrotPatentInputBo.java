/*
 * Copyright (c) 2016 PatSnap Pte Ltd, All Rights Reserved.
 */

package com.patsnap.core.analysis.bo;

import org.carrot2.core.LanguageCode;

/**
 * Patent text input bo for Carrot
 *
 * @author zhangyan
 */
public class CarrotPatentInputBo {

	private static final long serialVersionUID = -7972331176225207830L;
	/**
	 * id of the patent
	 */
	public String patentId;
	/**
	 * patent title text
	 */
	public String title;
	/**
	 * patent abstract text
	 */
	public String abstraction;
	/**
	 * patent text language
	 */
	public LanguageCode language;

	private boolean empty = false;

	public boolean isEmpty() {
		return title == null || title.isEmpty() || abstraction == null || abstraction.isEmpty();
//		return title.isEmpty() && abstraction.isEmpty();
	}
}
