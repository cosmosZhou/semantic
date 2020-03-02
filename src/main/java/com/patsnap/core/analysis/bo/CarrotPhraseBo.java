package com.patsnap.core.analysis.bo;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Base carrot phrase BO, can be used to extend
 *
 * @author zhangyan on 2019/11/13
 */

public class CarrotPhraseBo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String key;
    public String type;
    public String name;
    public String label;

    public CarrotPhraseBo() {
    }

    public CarrotPhraseBo(String key) {
        this(key, key, key, key);
    }

    public CarrotPhraseBo(String key, String type, String name, String label){
        this.key = key;
        this.type = type;
        this.name = name;
        this.label = label;
    }
    
    @Override
    public String toString() {
    	return key;
//    	return String.format("{key : %s, type : %s, name : %s, label : %s}", key, type, name, label);
    }
}
