package com.patsnap.core.analysis.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.IDocumentSource;
import org.carrot2.core.LanguageCode;
import org.carrot2.core.ProcessingResult;
import org.carrot2.text.clustering.MultilingualClustering;
import org.carrot2.text.clustering.MultilingualClusteringDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotsearch.lingo3g.Lingo3GClusteringAlgorithm;
import com.carrotsearch.lingo3g.Lingo3GClusteringAlgorithmDescriptor;
import com.google.common.collect.Maps;
import com.patsnap.core.analysis.bo.CarrotClusterBo;
import com.patsnap.core.analysis.bo.CarrotPatentInputBo;
import com.patsnap.core.analysis.bo.CarrotPhraseBo;
import com.patsnap.core.analysis.constants.PatentDataConstant;
import com.util.HttpClientGet;

/**
 * Manager for Carrot2 text processes
 *
 * @author zhangyan on 2019/11/13
 */
public class CarrotManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(CarrotManager.class);

	private static final int MAX_DOCUMENT_COUNT = 10000;

	private Controller depthOneController;

	private Controller depthTwoController;

//    @Value("${configs.com.patsnap.analysis.carrot.frequent.word}")
	private String FREQUENT_WORD_LIST = "\u73B0\u6709\u6280\u672F|\u6280\u672F\u9886\u57DF|\u6709\u76CA\u6548\u679C|\u7701\u7565\u89C6\u56FE|\u4EF0\u89C6\u56FE|\u66F4\u4F18\u9009|\u76F8\u8FDE\u63A5|\u56FE\u793A\u7B80\u5355\u8BF4\u660E|\u914D\u5236\u6210|\u524D\u8FF0\u6743\u5229|\u5373\u53EF\u5236|\u4EFB\u610F\u4E00\u9879|\u8BA1\u7B97\u51FA|\u53D1\u9001\u7ED9|\u8303\u56F4\u5185|\u8FDB\u4E00\u6B65|\u91CD\u8981\u6027|\u8ACB\u6C42\u9805|\u5176\u7279\u5F81\u5728\u4E8E|\u5176\u7279\u5F81\u662F|\u5176\u5305\u62EC|\u5176\u7279\u5F81\u5728|\u7B2C\u4E8C\u6B65|\u5176\u5236\u5907\u65B9\u6CD5\u662F|\u56FE\u5F0F\u7B80\u5355\u8BF4\u660E|\u5305\u62EC\u5982\u4E0B\u6B65\u9AA4|\u5305\u62EC\u4EE5\u4E0B\u6B65\u9AA4|\u4F7F\u7528\u65F6|\u8BE5\u65B9\u6CD5\u5305\u62EC|\u6240\u8FF0\u65B9\u6CD5\u5305\u62EC|\u7CFB\u5305\u542B|\u91C7\u7528\u672C\u53D1\u660E|\u901A\u8FC7\u672C\u53D1\u660E|\u65B9\u6CD5\u5305\u62EC|\u672C\u53D1\u660E\u7684\u6709\u76CA\u6548\u679C\u662F|\u672C\u5B9E\u7528\u65B0\u578B\u7684\u6709\u76CA\u6548\u679C\u662F|\u672C\u5B9E\u7528\u65B0\u578B\u7684\u4F18\u70B9\u5728\u4E8E|\u901A\u8FC7\u4E0A\u8FF0\u65B9\u5F0F|\u4E0E\u73B0\u6709\u6280\u672F\u76F8\u6BD4|\u70B9\u7684\u56FE\u7247\u6216\u7167\u7247|group consisting|mg/mL|group consisting|w/v|subject|thereof|\u5F0F\u52A0\u70ED\u7089|\u640F\u90E8\u4F4D|\u914D\u7F6E\u6210|\u8BBE\u5907\u76F8|\u6599\u673A\u6784|\u91CD\u91CF\u4EFD|\u7814\u78E8\u6210|\u7A33\u5B9A\u52420|\u56FE\u68484\u6700|\u7ACB\u4F53\u56FE1|\u914D\u7F6E|\u914D\u7F6E\u6210|current block|\u66F4\u5177\u4F53|\u5468\u671F\u5185|\u65F6\u95F4\u6BB5|Improvement|John|\u8BBE\u5907\u76F8|content item|first side|first image|\u7ECF\u641C\u7D22|home|smart|weight|\u6599\u673A\u6784|module|\u6750\u6599\u5236\u6210|unit|range|\u6210\u672C\u4F4E|\u6280\u672F\u6548\u679C|\u533A\u57DF\u5185|\u6E29\u5EA6t|\u6709\u5229\u4E8E\u63D0\u9AD8|\u76F8\u7ED3\u5408|\u9002\u7528\u8303\u56F4|\u73B0\u6709\u6280\u672F\u76F8\u6BD4|\u56FE\u68484\u6700|\u7528\u6237\u8BF4\u51FA|\u4EBA\u5458\u53CA\u65F6|\u5C42\u7684\u4E00\u4FA7|\u91CD\u91CF\u4EFD|\u7CBE\u786E\u63A7\u5236|\u751F\u957F\u51FA|\u7814\u78E8\u6210|\u8BBE\u7F6E\u6709\u6240|\u76F8\u90BB\u4E24|\u76F8\u90BB|\u4F59\u91CF\u4E3A\u6C34|\u4FDD\u62A4\u4E0B\u51B7\u5374|D\u6253\u5370|\u5E7F\u6CDB\u7684\u5E94\u7528\u573A\u666F|\u7B80\u5316\u4E86\u64CD\u4F5C\u6B65\u9AA4\u5927\u5927|\u7B80\u5355\u6761\u4EF6\u6E29\u548C|\u6781\u5927\u6539\u5584|\u663E\u8457\u6539\u5584|\u5E7F\u6CDB\u7528\u4E8E\u6C7D\u8F66|\u529F\u80FD\u5316|plurality|Adding|\u767E\u5206\u6BD4\u7684\u7EC4\u5206|\u5F62\u72B64\u6700|\u7A33\u5B9A\u52420|\u8F6C\u5316\u6548\u7387|\u5316\u77F3\u58A8\u70EF|sample|\u6210\u672C\u4F4E|\u66F4\u5177\u4F53|\u7ACB\u4F53\u56FE1|\u5B9E\u65BD\u65B9\u6848|\u90E810|\u9A8C\u8BC1\u5408\u683C|\u4E00\u90E8\u5206|\u5B9E\u7528\u65B0\u578B|\u5B9E\u65BD\u4F8B|\u6B21\u6570\u8D85\u8FC7\u4E00\u5B9A|\u8FF0\u624B\u673A|\u53D1\u660E\u5B9E\u65BD\u4F8B|\u79CD\u8BED\u8A00|\u57FA\u4E8E\u6587\u672C|\u65B9\u6CD5\u5305\u62EC|\u81F3\u5C11\u4E00\u4E2A|\u8FF0\u7B2C\u4E00|\u7B2C\u4E00\u548C\u7B2C\u4E8C|\u60C5\u51B5\u4E0B|\u8FD8\u5305\u62EC|All|\u79CD\u8BED\u8A00|\u8FF0\u624B\u673A";

//    @Value("${configs.com.patsnap.analysis.carrot.frequent.word.contain}")
	private String FREQUENT_WORD_CONTAIN_LIST = "\u7533\u8BF7\u4E13\u5229\u8303\u56F4|\u8BF7\u6C42\u9879|\u6743\u5229\u8981\u6C42|claim";

	/**
	 * Set of frequent words to be filtered if exactly matched all word in the set
	 * is upper case for easily matching ignore case
	 */
	private static Set<String> frequentWordSet = new HashSet<>(128);

	/**
	 * Set of frequent words to be filtered if be contained
	 */
	private static List<String> frequentWordContainList;

//    @Autowired
//    private PatentCacheManager patentCacheManager;

//    @Override
	private CarrotManager() {
		// depth one carrot controller
		depthOneController = getController(1);

		// depth two carrot controller
		depthTwoController = getController(2);

		// init frequent words set
		if (StringUtils.isNotBlank(FREQUENT_WORD_LIST)) {
			for (String w : FREQUENT_WORD_LIST.split("\\|")) {
				frequentWordSet.add(w.toUpperCase(Locale.ENGLISH));
			}
		}

		frequentWordContainList = Arrays.asList(FREQUENT_WORD_CONTAIN_LIST.split("\\|")).stream()
				.map(String::toUpperCase).collect(Collectors.toList());
	}

	/**
	 * Get carrot2 controller with given depth and init attributes
	 *
	 * @param depth number of depth level
	 * @return carrot2 controller
	 */
	@SuppressWarnings("unchecked")
	private Controller getController(int depth) {
		Controller controller = ControllerFactory.create(8, IDocumentSource.class, Lingo3GClusteringAlgorithm.class);
		final Map<String, Object> attributes = Maps.newHashMap();
		Lingo3GClusteringAlgorithmDescriptor.attributeBuilder(attributes).attributes().maxHierarchyDepth(depth)
				.maxClusteringPassesTop(6);
		MultilingualClusteringDescriptor.attributeBuilder(attributes)
				.languageAggregationStrategy(MultilingualClustering.LanguageAggregationStrategy.FLATTEN_ALL);
		controller.init(attributes);
		return controller;
	}

	/**
	 * Convert patent texts to Lingo Documents
	 *
	 * @return List of Carrot2 Documents
	 */
	private List<Document> buildLingoDocument(List<CarrotPatentInputBo> patents) {
		List<Document> documents = new ArrayList<>(patents.size());

		if (patents.size() > MAX_DOCUMENT_COUNT) {
			LOGGER.warn("Patent list to process is larger than {}, will limit it", MAX_DOCUMENT_COUNT);
			patents = patents.stream().limit(MAX_DOCUMENT_COUNT).collect(Collectors.toList());
		}

		for (CarrotPatentInputBo patent : patents) {
			if (patent.isEmpty()) {
				continue;
			}
			String patentId = patent.patentId;
			// build document
			Document document = new Document(patent.title, patent.abstraction, patentId, patent.language, patentId);
			documents.add(document);
		}
		LOGGER.info("build {} Carrot documents from {} patents", documents.size(), patents.size());
		return documents;
	}

	/**
	 * Depth one result process, used for word clouds
	 *
	 * @param documents list of carrot Document
	 * @return result of processing
	 */
	private ProcessingResult processDepthOneResult(List<Document> documents) {
		// TODO: can query hint help improve clustering results?
		LOGGER.info("call depth one controller to process {} documents", documents.size());
		return depthOneController.process(documents, null, Lingo3GClusteringAlgorithm.class);
	}

	/**
	 * Depth two result process, this is used for sunburst chart
	 *
	 * @param documents list of carrot Document
	 * @return result of processing
	 */
	private ProcessingResult processDepthTwoResult(List<Document> documents) {
		LOGGER.info("call depth two controller to process {} documents", documents.size());
		return depthTwoController.process(documents, null, Lingo3GClusteringAlgorithm.class);
	}

	private CarrotClusterBo buildCarrotClusterBo(Cluster cluster) {
		// assemble cluster bo
		CarrotClusterBo clusterBo = new CarrotClusterBo(String.valueOf(cluster.getId()));
		clusterBo.size = cluster.size();

		clusterBo.phrases = transferCarrotPhrases(cluster.getPhrases());

		if (CollectionUtils.isEmpty(clusterBo.phrases)) {
			// here return clusterBo with empty phrases
			// no need to cache patent ids in the following steps
			return clusterBo;
		}

		List<String> documentIds = cluster.getAllDocuments().stream().map(Document::getStringId)
				.collect(Collectors.toList());
		clusterBo.documentCount = documentIds.size();
		// save patent_ids to redis
//		String cacheKey = patentCacheManager.cachePatentIds(documentIds);
		clusterBo.documentIds = documentIds;
//		clusterBo.documentCacheKey = cacheKey;
//		 handle phrases
		return clusterBo;
	}

	/**
	 * Check if a phrase need to be filtered
	 *
	 * @param phrase single carrot phrase string
	 * @return true if phrase is not within phrases to be stopped
	 */
	private boolean isNoStopPhrase(String phrase) {
		if (StringUtils.isBlank(phrase)) {
			return false;
		}
		String upperPhrase = phrase.toUpperCase(Locale.ENGLISH);
		if (frequentWordSet.contains(upperPhrase)) {
			return false;
		}

		for (String w : frequentWordContainList) {
			if (upperPhrase.contains(w)) {
				return false;
			}
		}

		return true;
	}

	private List<CarrotPhraseBo> transferCarrotPhrases(List<String> phrases) {
		// TODO: filter frequent word
		if (CollectionUtils.isNotEmpty(phrases)) {
			return phrases.stream().filter(this::isNoStopPhrase).map(CarrotPhraseBo::new).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	/**
	 * Deal with process result and build output result
	 */
	private List<CarrotClusterBo> parseProcessingResult(ProcessingResult processingResult) {
		int totalSize = 0;
		List<CarrotClusterBo> carrotClusterBoList = new ArrayList<>();
		for (Cluster cluster : processingResult.getClusters()) {
			if (cluster.isOtherTopics()) {
				// Indicates that the cluster is an <i>Other Topics</i> cluster. Such a cluster
				// contains documents that remain unclustered at given level of cluster
				// hierarchy.
				continue;
			}
			CarrotClusterBo carrotClusterBo = buildCarrotClusterBo(cluster);
			// phase should not be empty
			if (carrotClusterBo == null || CollectionUtils.isEmpty(carrotClusterBo.phrases)) {
				continue;
			}
			// update total size
			totalSize += carrotClusterBo.size;
			// handle sub clusters
			List<Cluster> subClusters = cluster.getSubclusters();
			List<CarrotClusterBo> subClusterBoList = new ArrayList<>(subClusters.size());
			LOGGER.debug("carrot cluster {} has {} sub-clusters", cluster.getId(), subClusters.size());
			for (Cluster subCluster : subClusters) {
				if (subCluster.isOtherTopics()) {
					continue;
				}
				CarrotClusterBo subClusterBo = buildCarrotClusterBo(subCluster);
				// some words filtered should not be add in the result.
				if (subClusterBo == null || CollectionUtils.isEmpty(subClusterBo.phrases)) {
					continue;
				}
				subClusterBoList.add(subClusterBo);
			}
			// sort by size && set sub cluster list
			if (CollectionUtils.isNotEmpty(subClusterBoList)) {
				subClusterBoList.sort(Comparator.comparing(CarrotClusterBo::getSize).reversed());
				carrotClusterBo.subClusters = subClusterBoList;
			}
			carrotClusterBoList.add(carrotClusterBo);
		}

		if (totalSize == 0 || CollectionUtils.isEmpty(carrotClusterBoList)) {
			LOGGER.error("Carrot result is empty for processing result");
			throw new RuntimeException("carrot result is empty");
		}

		LOGGER.info("build carrot cluster list of size: {}", carrotClusterBoList.size());
		return carrotClusterBoList;
	}

	@SuppressWarnings("unchecked")
	private List<CarrotPatentInputBo> buildCarrotPatentInputBo(Map<String, Object> patentDataMap) {
		List<CarrotPatentInputBo> carrotPatentInputBoList = new ArrayList<>();
		// sort order?
		List<String> patentIds = new ArrayList<>(patentDataMap.keySet());
		Collections.sort(patentIds);

		for (String patentId : patentIds) {
			CarrotPatentInputBo carrotPatentInputBo = new CarrotPatentInputBo();
			Map<String, Object> valueMap = (Map<String, Object>) patentDataMap.get(patentId);
			// determine patent text and language
//			String titleLang;
//			String abstLang;

			if (StringUtils.isEmpty((String) valueMap.get(PatentDataConstant.Fields.TITLE_TRAN_LANG))) {
				carrotPatentInputBo.title = (String) valueMap.get(PatentDataConstant.Fields.TITLE);
//				titleLang = (String) valueMap.get(PatentDataConstant.Fields.TITLE_LANG);
			} else {
				carrotPatentInputBo.title = (String) valueMap.get(PatentDataConstant.Fields.TITLE_TRAN);
//				titleLang = (String) valueMap.get(PatentDataConstant.Fields.TITLE_TRAN_LANG);
			}

			if (StringUtils.isEmpty((String) valueMap.get(PatentDataConstant.Fields.ABST_TRAN_LANG))) {
				carrotPatentInputBo.abstraction = (String) valueMap.get(PatentDataConstant.Fields.ABST);
//				abstLang = (String) valueMap.get(PatentDataConstant.Fields.ABST_LANG);
			} else {
				carrotPatentInputBo.abstraction = (String) valueMap.get(PatentDataConstant.Fields.ABST_TRAN);
//				abstLang = (String) valueMap.get(PatentDataConstant.Fields.ABST_TRAN_LANG);
			}
			// only set to chinese of title and abstraction text are all chinese
//			if ("CN".equals(titleLang.toUpperCase()) && StringUtils.isNotBlank(titleLang)
//					&& titleLang.equals(abstLang)) {
//				carrotPatentInputBo.language = LanguageCode.CHINESE_SIMPLIFIED;
//			} else {
			carrotPatentInputBo.language = LanguageCode.ENGLISH;
//			}

			carrotPatentInputBo.patentId = patentId;
			carrotPatentInputBoList.add(carrotPatentInputBo);
		}
		return carrotPatentInputBoList;
	}

	/**
	 * build word clouds for a list of patents
	 *
	 * @param patentDataMap map of patent_id to patent data
	 * @return list of carrot cluster
	 */
	List<CarrotClusterBo> clusterPatentsToWordClouds(Map<String, Object> patentDataMap) {
		List<CarrotPatentInputBo> patents = buildCarrotPatentInputBo(patentDataMap);
		ProcessingResult processingResult = processDepthOneResult(buildLingoDocument(patents));
		return parseProcessingResult(processingResult);
	}

	/**
	 * build sunbursts for a list of patents
	 *
	 * @param patentDataMap map of patent_id to patent dat
	 * @return list of carrot cluster
	 */
	List<CarrotClusterBo> clusterPatentsToSunburstChart(Map<String, Object> patentDataMap) {
		List<CarrotPatentInputBo> patents = buildCarrotPatentInputBo(patentDataMap);
		ProcessingResult processingResult = processDepthTwoResult(buildLingoDocument(patents));
		return parseProcessingResult(processingResult);
	}

	public static CarrotManager instance = new CarrotManager();

	public List<Map<String, Object>> getClusteringResult(String keyword) throws IOException {
		Map<String, Object> patentDataMap = HttpClientGet.solr_with_keyword(keyword);
		List<CarrotClusterBo> carrotResult = instance.clusterPatentsToWordClouds(patentDataMap);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (CarrotClusterBo cluster : carrotResult) {
			Map<String, Object> dict = new HashMap<String, Object>();
			dict.put("id", cluster.id);
			dict.put("documentCount", cluster.documentCount);
			dict.put("documentIds", cluster.documentIds);

			String[] phrases = new String[cluster.phrases.size()];
			int i = 0;
			for (CarrotPhraseBo phrase : cluster.phrases) {
				phrases[i++] = phrase.key;
			}
			dict.put("phrases", phrases);
			list.add(dict);
		}

		return list;
	}

	public static void main(String[] args) throws Exception {

		String keyword = "medicine";
//		Map<String, Object> patentDataMap = HttpClientGet.solr_with_keyword(keyword);
//		List<CarrotClusterBo> carrotResult = instance.clusterPatentsToWordClouds(patentDataMap);

		System.out.println("clustering with keyword: " + keyword);

		System.out.println(instance.getClusteringResult(keyword));

	}
}
