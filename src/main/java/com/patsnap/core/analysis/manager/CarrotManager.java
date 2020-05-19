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
import org.apache.solr.client.solrj.SolrServerException;
import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.clustering.stc.STCClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.IDocumentSource;
import org.carrot2.core.LanguageCode;
import org.carrot2.core.ProcessingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.patsnap.core.analysis.bo.CarrotClusterBo;
import com.patsnap.core.analysis.bo.CarrotPatentInputBo;
import com.patsnap.core.analysis.bo.CarrotPhraseBo;
import com.util.HttpClient;
import com.util.MongoDB;
import com.util.MySQL;
import com.util.Native;
import com.util.Utility;
import com.util.Utility.Timer;

/**
 * Manager for Carrot2 text processes
 *
 * @author zhangyan on 2019/11/13
 */
public class CarrotManager {
	private static final Logger log = LoggerFactory.getLogger(CarrotManager.class);

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
	private Controller getController(int depth) {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		System.out.println("availableProcessors = " + availableProcessors);

//		Controller controller = ControllerFactory.create(availableProcessors, IDocumentSource.class,
//				LingoClusteringAlgorithm.class);		
//		final Map<String, Object> attributes = new HashMap<String, Object>();
//		LingoClusteringAlgorithmDescriptor.attributeBuilder(attributes);
//		MultilingualClusteringDescriptor.attributeBuilder(attributes)
//				.languageAggregationStrategy(MultilingualClustering.LanguageAggregationStrategy.FLATTEN_ALL);		
//		controller.init(attributes);

		Controller controller = ControllerFactory.create(availableProcessors, IDocumentSource.class,
				STCClusteringAlgorithm.class);

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
			log.warn("Patent list to process is larger than {}, will limit it", MAX_DOCUMENT_COUNT);
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
		log.info("build {} Carrot documents from {} patents", documents.size(), patents.size());
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
		log.info("call depth one controller to process {} documents", documents.size());
		return depthOneController.process(documents, null, STCClusteringAlgorithm.class);
//		return depthOneController.process(documents, null, LingoClusteringAlgorithm.class);
	}

	/**
	 * Depth two result process, this is used for sunburst chart
	 *
	 * @param documents list of carrot Document
	 * @return result of processing
	 */
	private ProcessingResult processDepthTwoResult(List<Document> documents) {
		log.info("call depth two controller to process {} documents", documents.size());
		return depthTwoController.process(documents, null, LingoClusteringAlgorithm.class);
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
			log.debug("carrot cluster {} has {} sub-clusters", cluster.getId(), subClusters.size());
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
			log.error("Carrot result is empty for processing result");
//			throw new RuntimeException("carrot result is empty");
		}

		log.info("build carrot cluster list of size: {}", carrotClusterBoList.size());
		return carrotClusterBoList;
	}

	private List<CarrotPatentInputBo> buildCarrotPatentInputBo(LanguageCode language,
			List<? extends Map<String, Object>> patentDataMap) {
		List<CarrotPatentInputBo> carrotPatentInputBoList = new ArrayList<>();

		for (Map<String, Object> dict : patentDataMap) {
			CarrotPatentInputBo carrotPatentInputBo = new CarrotPatentInputBo();

			carrotPatentInputBo.title = (String) dict.get("TTL");

			carrotPatentInputBo.abstraction = (String) dict.get("ABST");
			carrotPatentInputBo.language = language;

			carrotPatentInputBo.patentId = (String) dict.get("_id");
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
	List<CarrotClusterBo> clusterPatentsToWordClouds(LanguageCode language,
			List<? extends Map<String, Object>> patentDataMap) {
		List<CarrotPatentInputBo> patents = buildCarrotPatentInputBo(language, patentDataMap);
		ProcessingResult processingResult = processDepthOneResult(buildLingoDocument(patents));
		return parseProcessingResult(processingResult);
	}

	/**
	 * build sunbursts for a list of patents
	 *
	 * @param patentDataMap map of patent_id to patent dat
	 * @return list of carrot cluster
	 */
	List<CarrotClusterBo> clusterPatentsToSunburstChart(LanguageCode language,
			List<? extends Map<String, Object>> patentDataMap) {
		List<CarrotPatentInputBo> patents = buildCarrotPatentInputBo(language, patentDataMap);
		ProcessingResult processingResult = processDepthTwoResult(buildLingoDocument(patents));
		return parseProcessingResult(processingResult);
	}

	public static CarrotManager instance = new CarrotManager();

	public static class ClusteringResult {
		ClusteringResult(String[] list, Map<String, List<String>> map, Map<String, List<String>> cluster,
				int numFromSolr, double solr_duration, double clustering_duration, double postprocessing_duration,
				double lexicon_detection_duration) {
			this.list = list;
			this.cluster = cluster;

			this.map = map;
			this.numFromSolr = numFromSolr;
			this.solr_duration = solr_duration;
			this.clustering_duration = clustering_duration;
			this.postprocessing_duration = postprocessing_duration;
			this.hyponym_detection_duration = lexicon_detection_duration;
			for (int i = 0; i < cache.length; i++) {
				if (cache[i] == null) {
					cache[i] = map;
					this.cache_index = i;
					break;
				}
			}
		}

		public String[] list;
		public Map<String, List<String>> cluster = new HashMap<String, List<String>>();

		@JsonIgnore // field to ignore
		public Map<String, List<String>> map;

		public double solr_duration;
		public double clustering_duration;
		public double postprocessing_duration;
		public double hyponym_detection_duration;
		public int numFromSolr;
		public int cache_index;

		@SuppressWarnings("unchecked")
		public static Map<String, List<String>>[] cache = new HashMap[128];
	}

	public ClusteringResult getClusteringResult(String lang, String text, int rows)
			throws IOException, SolrServerException {
//		System.out.println("calling public ClusteringResult getClusteringResult(String language, String text, int rows)!");
		Timer timer = new Utility.Timer();
		List<? extends Map<String, Object>> patentDataMap = HttpClient.solr_with_keyword(lang, text, rows);
		double solr_duration = timer.report("HttpClient.solr_with_keyword(language, text, rows)");

		int numFromSolr = patentDataMap.size();

		List<String> list = new ArrayList<String>();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Map<String, List<String>> cluster = new HashMap<String, List<String>>();
		if (patentDataMap.isEmpty())
			return new ClusteringResult(new String[0], map, cluster, numFromSolr, solr_duration, 0, 0, 0);

//		lang = lang.toLowerCase();
		
		LanguageCode languageCode = LanguageCode.forISOCode(lang.equals("cn") ? "zh_cn" : lang);
		List<CarrotClusterBo> carrotResult = instance.clusterPatentsToWordClouds(languageCode, patentDataMap);

		for (CarrotClusterBo c : carrotResult) {
			for (CarrotPhraseBo phrase : c.phrases) {
				list.add(phrase.key);
				map.put(phrase.key, c.documentIds);
			}
		}

		double clustering_duration = timer.report("clusterPatentsToWordClouds");

		Set<String> setPhrases = new HashSet<String>();
		switch (languageCode) {
		case CHINESE_SIMPLIFIED:
			for (String phrase : list) {
				if (StopWordManager.Chinese.instance.isStopWord(phrase)) {
					map.remove(phrase);
					continue;
				}
				setPhrases.add(phrase);
			}
			break;
		case ENGLISH:
			for (String phrase : list) {
				if (StopWordManager.English.instance.isStopWord(phrase)) {
					map.remove(phrase);
					continue;
				}
				setPhrases.add(phrase);
			}
			break;
		default:
			break;
		}

		double postprocessing_duration = timer.report("Native.keyword(jstringArray)");

		List<String> listPhrases = new ArrayList<String>(setPhrases);
		listPhrases.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.compare(map.get(o2).size(), map.get(o1).size());
			}
		});

		if (listPhrases.size() > 50) {
			listPhrases = listPhrases.subList(0, 50);
		}

		String array[] = Utility.toArray(listPhrases);
		int frequency[] = new int[array.length];
		for (int i = 0; i < frequency.length; i++) {
			frequency[i] = map.get(array[i]).size();
		}

		double[][] embedding = new double[array.length][];
		
		Thread thread = MongoDB.instance.acquireWordEmbedding(lang, array, embedding);		

		int[] heads = Native.lexiconStructureWithEmbeddingCN(embedding, frequency);

		for (int child = 0; child < heads.length; ++child) {
			int parent = heads[child];
			if (parent < 0) {
				continue;
			}
			String incenter = array[parent];
			String childPhrase = array[child];
			if (!cluster.containsKey(incenter))
				cluster.put(incenter, new ArrayList<String>());
			cluster.get(incenter).add(childPhrase);
		}

		double lexicon_detection_duration = timer.report("lexicon_detection_duration");
		log.info("finishing analyzing keyword: " + text);

		ClusteringResult result = new ClusteringResult(array, map, cluster, numFromSolr, solr_duration,
				clustering_duration, postprocessing_duration, lexicon_detection_duration);
		if (thread != null)
			thread.start();
		return result;
	}

	public List<String> getClusteringResult(LanguageCode language, List<Map<String, Object>> patentDataMap)
			throws IOException {
		List<CarrotClusterBo> carrotResult = instance.clusterPatentsToWordClouds(language, patentDataMap);
		List<String> list = new ArrayList<String>();
		for (CarrotClusterBo cluster : carrotResult) {
			if (cluster.phrases.size() != 1) {
				log.info(String.format("cluster.phrases.size() = %d", cluster.phrases.size()));
			}
			if (!cluster.phrases.isEmpty())
				list.add(cluster.phrases.get(0).key);
		}

		return list;
	}

	public static void crawlFromSolr(String lang) throws Exception {
//		int sum_positive = 0;
//		int sum_negative = 0;
		HashSet<String> backup = new HashSet<String>();
		HashSet<String> analyzed = new HashSet<String>();
		String table = String.format("tbl_keyword_%s", lang);
		for (;;) {

			String keyword;
			do {
				if (backup.isEmpty()) {
					for (Map<String, Object> map : MySQL.instance.select(
							String.format("select text from %s where label = 1 order by rand() limit 1000", table))) {
						backup.add((String) map.get("text"));
					}
				}

				keyword = backup.iterator().next();
				backup.remove(keyword);
			} while (analyzed.contains(keyword));

			System.out.println("analyzing " + keyword);
			String list[] = instance.getClusteringResult(lang, keyword, 1000).list;

			for (String e : list) {
				int label = Native.keywordCN(e);

//				if (label == 1) {
//					++sum_positive;
//				} else {
//					++sum_negative;
//				}

				if (MySQL.instance.insert(table, e, label, 0)) {
					backup.add(e);
				}
			}
		}

	}

//cd D:\360\solution\semantic\target\classes
//java -classpath ../lib/*;./ com.patsnap.core.analysis.manager.CarrotManager
	public static void main(String[] args) throws Exception {
		System.out.println("args = " + String.join(", ", args));
		crawlFromSolr(args[0]);
	}
}
//WARN org.carrot2.text.linguistic.DefaultTokenizerFactory - Tokenizer for Chinese Simplified (zh_cn) is not available. This may degrade clustering quality of Chinese Simplified content. Cause: java.lang.NoClassDefFoundError: org/apache/lucene/analysis/cn/smart/SentenceTokenizer
//https://github.com/mimno/Mallet