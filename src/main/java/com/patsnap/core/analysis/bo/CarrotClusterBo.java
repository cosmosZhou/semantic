package com.patsnap.core.analysis.bo;

import java.io.Serializable;
import java.util.List;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.util.Utility;

/**
 * BO of cluster result of carrot
 *
 * @author zhangyan on 2019/11/13
 */

public class CarrotClusterBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String id;

	public int size;

	public int getSize() {
		return size;
	}

	public int documentCount;

	public List<String> documentIds;

	public String documentCacheKey;

	public List<CarrotClusterBo> subClusters;

	public List<CarrotPhraseBo> phrases;

	private String caseCacheKey;

	private int caseSize;

	public CarrotClusterBo() {
	}

	public CarrotClusterBo(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		if (subClusters == null) {
			assert size == documentCount;
			return String.format("{id : %s, phrases = %s, size = %d, documentIds = %s}", id, phrases, size,
					documentIds);
		} else
			return String.format(
					"{id : %s, size = %d, documentCount = %d, documentIds = %s, documentCacheKey = %s, subClusters = %s, phrases = %s, caseCacheKey = %s, caseSize = %d}",
					id, size, documentCount, documentIds, documentCacheKey, subClusters, phrases, caseCacheKey,
					caseSize);
//		try {
//			return Utility.jsonify(this);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
	}
}
