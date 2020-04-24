

// APT-generated file.

package org.carrot2.clustering.stc;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.clustering.stc.STCClusteringAlgorithm} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.clustering.stc.STCClusteringAlgorithm
 */
public final class STCClusteringAlgorithmDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.clustering.stc.STCClusteringAlgorithm";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "STCClusteringAlgorithm";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Suffix Tree Clustering (STC) algorithm";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "STC Clustering";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "Pretty much as described in: <i>Oren Zamir, Oren Etzioni, Grouper: A Dynamic Clustering Interface to Web Search Results, 1999.</i> Some liberties were taken wherever STC's description was not clear enough or where we thought some improvements could be made.";

    /**
     * Attributes of the component. Note that only statically reachable fields are included.
     * Additional attributes may be available at run time. 
     */
    public final static Attributes attributes; 

    /**
     * Attributes declared directly by the component.
     */
    private final static Set<AttributeInfo> ownAttributes;

    /**
     * Attributes declared by the component or its superclasses.
     */
    private final static Set<AttributeInfo> allAttributes;

    /**
     * Attributes declared by the component or its superclasses, lookup dictionary 
     * by attribute key.
     */
    private final static Map<String, AttributeInfo> allAttributesByKey;

    /**
     * Attributes declared by the component or its superclasses, lookup dictionary by 
     * attribute's field name.
     */
    private final static Map<String, AttributeInfo> allAttributesByFieldName;

    /**
     * Static initializer for internal collections.
     */
    static
    {
        attributes = new Attributes();

        final Set<AttributeInfo> ownAttrs = new HashSet<AttributeInfo>();
        ownAttrs.add(attributes.query);
        ownAttrs.add(attributes.documents);
        ownAttrs.add(attributes.clusters);
        ownAttrs.add(attributes.ignoreWordIfInFewerDocs);
        ownAttrs.add(attributes.ignoreWordIfInHigherDocsPercent);
        ownAttrs.add(attributes.minBaseClusterScore);
        ownAttrs.add(attributes.maxBaseClusters);
        ownAttrs.add(attributes.minBaseClusterSize);
        ownAttrs.add(attributes.maxClusters);
        ownAttrs.add(attributes.mergeThreshold);
        ownAttrs.add(attributes.maxPhraseOverlap);
        ownAttrs.add(attributes.mostGeneralPhraseCoverage);
        ownAttrs.add(attributes.maxDescPhraseLength);
        ownAttrs.add(attributes.maxPhrases);
        ownAttrs.add(attributes.singleTermBoost);
        ownAttrs.add(attributes.optimalPhraseLength);
        ownAttrs.add(attributes.optimalPhraseLengthDev);
        ownAttrs.add(attributes.documentCountBoost);
        ownAttrs.add(attributes.preprocessingPipeline);
        ownAttrs.add(attributes.scoreWeight);
        ownAttrs.add(attributes.mergeStemEquivalentBaseClusters);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.query);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.documents);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.clusters);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.ignoreWordIfInFewerDocs);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.ignoreWordIfInHigherDocsPercent);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.minBaseClusterScore);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.maxBaseClusters);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.minBaseClusterSize);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.maxClusters);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.mergeThreshold);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.maxPhraseOverlap);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.mostGeneralPhraseCoverage);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.maxDescPhraseLength);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.maxPhrases);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.singleTermBoost);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.optimalPhraseLength);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.optimalPhraseLengthDev);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.documentCountBoost);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.preprocessingPipeline);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.scoreWeight);
        allAttrs.add(org.carrot2.clustering.stc.STCClusteringAlgorithmDescriptor.attributes.mergeStemEquivalentBaseClusters);

        allAttributes = Collections.unmodifiableSet(allAttrs);
        ownAttributes = Collections.unmodifiableSet(ownAttrs);
        
        final Map<String, AttributeInfo> allAttrsByKey = new HashMap<String, AttributeInfo>();
        final Map<String, AttributeInfo> allAttrsByFieldName = new HashMap<String, AttributeInfo>();
        for (AttributeInfo ai : allAttrs)
        {
            allAttrsByKey.put(ai.key, ai);
            allAttrsByFieldName.put(ai.fieldName, ai);
        }

        allAttributesByKey = Collections.unmodifiableMap(allAttrsByKey);
        allAttributesByFieldName = Collections.unmodifiableMap(allAttrsByFieldName);
    }

    
    /* Attribute keys. */

    /**
     * Constants for all attribute keys of the {@link org.carrot2.clustering.stc.STCClusteringAlgorithm} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#query}. */
        public static final String QUERY = "query";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#documents}. */
        public static final String DOCUMENTS = "documents";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#clusters}. */
        public static final String CLUSTERS = "clusters";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#ignoreWordIfInFewerDocs}. */
        public static final String IGNORE_WORD_IF_IN_FEWER_DOCS = "STCClusteringAlgorithm.ignoreWordIfInFewerDocs";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#ignoreWordIfInHigherDocsPercent}. */
        public static final String IGNORE_WORD_IF_IN_HIGHER_DOCS_PERCENT = "STCClusteringAlgorithm.ignoreWordIfInHigherDocsPercent";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#minBaseClusterScore}. */
        public static final String MIN_BASE_CLUSTER_SCORE = "STCClusteringAlgorithm.minBaseClusterScore";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#maxBaseClusters}. */
        public static final String MAX_BASE_CLUSTERS = "STCClusteringAlgorithm.maxBaseClusters";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#minBaseClusterSize}. */
        public static final String MIN_BASE_CLUSTER_SIZE = "STCClusteringAlgorithm.minBaseClusterSize";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#maxClusters}. */
        public static final String MAX_CLUSTERS = "STCClusteringAlgorithm.maxClusters";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#mergeThreshold}. */
        public static final String MERGE_THRESHOLD = "STCClusteringAlgorithm.mergeThreshold";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#maxPhraseOverlap}. */
        public static final String MAX_PHRASE_OVERLAP = "STCClusteringAlgorithm.maxPhraseOverlap";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#mostGeneralPhraseCoverage}. */
        public static final String MOST_GENERAL_PHRASE_COVERAGE = "STCClusteringAlgorithm.mostGeneralPhraseCoverage";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#maxDescPhraseLength}. */
        public static final String MAX_DESC_PHRASE_LENGTH = "STCClusteringAlgorithm.maxDescPhraseLength";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#maxPhrases}. */
        public static final String MAX_PHRASES = "STCClusteringAlgorithm.maxPhrases";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#singleTermBoost}. */
        public static final String SINGLE_TERM_BOOST = "STCClusteringAlgorithm.singleTermBoost";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#optimalPhraseLength}. */
        public static final String OPTIMAL_PHRASE_LENGTH = "STCClusteringAlgorithm.optimalPhraseLength";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#optimalPhraseLengthDev}. */
        public static final String OPTIMAL_PHRASE_LENGTH_DEV = "STCClusteringAlgorithm.optimalPhraseLengthDev";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#documentCountBoost}. */
        public static final String DOCUMENT_COUNT_BOOST = "STCClusteringAlgorithm.documentCountBoost";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#preprocessingPipeline}. */
        public static final String PREPROCESSING_PIPELINE = "STCClusteringAlgorithm.preprocessingPipeline";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#scoreWeight}. */
        public static final String SCORE_WEIGHT = "STCClusteringAlgorithm.scoreWeight";
        /** Attribute key for: {@link org.carrot2.clustering.stc.STCClusteringAlgorithm#mergeStemEquivalentBaseClusters}. */
        public static final String MERGE_STEM_EQUIVALENT_BASE_CLUSTERS = "STCClusteringAlgorithm.mergeStemEquivalentBaseClusters";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.clustering.stc.STCClusteringAlgorithm} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#query
         */
        public final AttributeInfo query = 
            new AttributeInfo(
                "query",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "query",
"Query that produced the documents. The query will help the algorithm to create\nbetter clusters. Therefore, providing the query is optional but desirable.",
null,
"Query that produced the documents",
"The query will help the algorithm to create better clusters. Therefore, providing the query is optional but desirable.",
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.query
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#documents
         */
        public final AttributeInfo documents = 
            new AttributeInfo(
                "documents",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "documents",
"Documents to cluster.",
null,
"Documents to cluster",
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.documents
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#clusters
         */
        public final AttributeInfo clusters = 
            new AttributeInfo(
                "clusters",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "clusters",
"Clusters created by the algorithm.",
null,
"Clusters created by the algorithm",
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.clusters
            );

        /**
         *          */
        public final AttributeInfo ignoreWordIfInFewerDocs = 
            new AttributeInfo(
                "STCClusteringAlgorithm.ignoreWordIfInFewerDocs",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "ignoreWordIfInFewerDocs",
"Minimum word-document recurrences.",
null,
"Minimum word-document recurrences",
null,
"Word filtering",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo ignoreWordIfInHigherDocsPercent = 
            new AttributeInfo(
                "STCClusteringAlgorithm.ignoreWordIfInHigherDocsPercent",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "ignoreWordIfInHigherDocsPercent",
"Maximum word-document ratio. A number between 0 and 1, if a word exists in more\nsnippets than this ratio, it is ignored.",
null,
"Maximum word-document ratio",
"A number between 0 and 1, if a word exists in more snippets than this ratio, it is ignored.",
"Word filtering",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo minBaseClusterScore = 
            new AttributeInfo(
                "STCClusteringAlgorithm.minBaseClusterScore",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "minBaseClusterScore",
"Minimum base cluster score.",
null,
"Minimum base cluster score",
null,
"Base clusters",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo maxBaseClusters = 
            new AttributeInfo(
                "STCClusteringAlgorithm.maxBaseClusters",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "maxBaseClusters",
"Maximum base clusters count. Trims the base cluster array after N-th position for\nthe merging phase.",
null,
"Maximum base clusters count",
"Trims the base cluster array after N-th position for the merging phase.",
"Base clusters",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo minBaseClusterSize = 
            new AttributeInfo(
                "STCClusteringAlgorithm.minBaseClusterSize",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "minBaseClusterSize",
"Minimum documents per base cluster.",
null,
"Minimum documents per base cluster",
null,
"Base clusters",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo maxClusters = 
            new AttributeInfo(
                "STCClusteringAlgorithm.maxClusters",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "maxClusters",
"Maximum final clusters.",
null,
"Maximum final clusters",
null,
"Merging and output",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo mergeThreshold = 
            new AttributeInfo(
                "STCClusteringAlgorithm.mergeThreshold",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "mergeThreshold",
"Base cluster merge threshold.",
null,
"Base cluster merge threshold",
null,
"Merging and output",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo maxPhraseOverlap = 
            new AttributeInfo(
                "STCClusteringAlgorithm.maxPhraseOverlap",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "maxPhraseOverlap",
"Maximum cluster phrase overlap.",
null,
"Maximum cluster phrase overlap",
null,
"Labels",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo mostGeneralPhraseCoverage = 
            new AttributeInfo(
                "STCClusteringAlgorithm.mostGeneralPhraseCoverage",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "mostGeneralPhraseCoverage",
"Minimum general phrase coverage. Minimum phrase coverage to appear in cluster\ndescription.",
null,
"Minimum general phrase coverage",
"Minimum phrase coverage to appear in cluster description.",
"Labels",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo maxDescPhraseLength = 
            new AttributeInfo(
                "STCClusteringAlgorithm.maxDescPhraseLength",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "maxDescPhraseLength",
"Maximum words per label. Base clusters formed by phrases with more words than this\nratio are trimmed.",
null,
"Maximum words per label",
"Base clusters formed by phrases with more words than this ratio are trimmed.",
"Labels",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo maxPhrases = 
            new AttributeInfo(
                "STCClusteringAlgorithm.maxPhrases",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "maxPhrases",
"Maximum phrases per label. Maximum number of phrases from base clusters promoted\nto the cluster's label.",
null,
"Maximum phrases per label",
"Maximum number of phrases from base clusters promoted to the cluster's label.",
"Labels",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo singleTermBoost = 
            new AttributeInfo(
                "STCClusteringAlgorithm.singleTermBoost",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "singleTermBoost",
"Single term boost. A factor in calculation of the base cluster score. If greater\nthen zero, single-term base clusters are assigned this value regardless of the\npenalty function.",
null,
"Single term boost",
"A factor in calculation of the base cluster score. If greater then zero, single-term base clusters are assigned this value regardless of the penalty function.",
"Base clusters",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo optimalPhraseLength = 
            new AttributeInfo(
                "STCClusteringAlgorithm.optimalPhraseLength",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "optimalPhraseLength",
"Optimal label length. A factor in calculation of the base cluster score.",
null,
"Optimal label length",
"A factor in calculation of the base cluster score.",
"Base clusters",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo optimalPhraseLengthDev = 
            new AttributeInfo(
                "STCClusteringAlgorithm.optimalPhraseLengthDev",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "optimalPhraseLengthDev",
"Phrase length tolerance. A factor in calculation of the base cluster score.",
null,
"Phrase length tolerance",
"A factor in calculation of the base cluster score.",
"Base clusters",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo documentCountBoost = 
            new AttributeInfo(
                "STCClusteringAlgorithm.documentCountBoost",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "documentCountBoost",
"Document count boost. A factor in calculation of the base cluster score, boosting\nthe score depending on the number of documents found in the base cluster.",
null,
"Document count boost",
"A factor in calculation of the base cluster score, boosting the score depending on the number of documents found in the base cluster.",
"Base clusters",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo preprocessingPipeline = 
            new AttributeInfo(
                "STCClusteringAlgorithm.preprocessingPipeline",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "preprocessingPipeline",
"Common preprocessing tasks handler.",
null,
"Common preprocessing tasks handler",
null,
null,
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo scoreWeight = 
            new AttributeInfo(
                "STCClusteringAlgorithm.scoreWeight",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "scoreWeight",
"Balance between cluster score and size during cluster sorting. Value equal to 0.0\nwill sort clusters based only on cluster size. Value equal to 1.0\nwill sort clusters based only on cluster score.",
"Size-Score sorting ratio",
"Balance between cluster score and size during cluster sorting",
"Value equal to 0.0 will sort clusters based only on cluster size. Value equal to 1.0 will sort clusters based only on cluster score.",
"Clusters",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo mergeStemEquivalentBaseClusters = 
            new AttributeInfo(
                "STCClusteringAlgorithm.mergeStemEquivalentBaseClusters",
                "org.carrot2.clustering.stc.STCClusteringAlgorithm",
                "mergeStemEquivalentBaseClusters",
"Merge all stem-equivalent base clusters before running the merge phase.",
"Merge all stem-equivalent phrases when discovering base clusters",
"Merge all stem-equivalent base clusters before running the merge phase",
null,
"Clusters",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );


        /**
         * Attributes of the nested {@link org.carrot2.text.clustering.MultilingualClustering} component.
         */
        public final org.carrot2.text.clustering.MultilingualClusteringDescriptor.Attributes multilingualClustering =
            org.carrot2.text.clustering.MultilingualClusteringDescriptor.attributes;

    }

    /**
     * Attribute map builder for the  {@link org.carrot2.clustering.stc.STCClusteringAlgorithm} component. You can use this
     * builder as a type-safe alternative to populating the attribute map using attribute keys.
     */
    public static class AttributeBuilder 
    {
        /** The attribute map populated by this builder. */
        public final Map<String, Object> map;

        /**
         * Creates a builder backed by the provided map.
         */
        protected AttributeBuilder(Map<String, Object> map)
        {

            this.map = map;
        }


        

        /**
         * Query that produced the documents. The query will help the algorithm to create
better clusters. Therefore, providing the query is optional but desirable.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#query 
         */
        public AttributeBuilder query(java.lang.String value)
        {
            map.put("query", value);
            return this;
        }

        

        

        /**
         * Query that produced the documents. The query will help the algorithm to create
better clusters. Therefore, providing the query is optional but desirable.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#query 
         */
        public AttributeBuilder query(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("query", value);
            return this;
        }

        

        

        /**
         * Documents to cluster.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#documents 
         */
        public AttributeBuilder documents(java.util.List<org.carrot2.core.Document> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        /**
         * Documents to cluster.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#documents 
         */
        public AttributeBuilder documents(IObjectFactory<? extends java.util.List<org.carrot2.core.Document>> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        

        

        

        /**
         * Clusters created by the algorithm.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#clusters 
         */
@SuppressWarnings("unchecked")        public java.util.List<org.carrot2.core.Cluster> clusters()
        {
            return (java.util.List<org.carrot2.core.Cluster>) map.get("clusters");
        }

        

        /**
         * Minimum word-document recurrences.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#ignoreWordIfInFewerDocs 
         */
        public AttributeBuilder ignoreWordIfInFewerDocs(int value)
        {
            map.put("STCClusteringAlgorithm.ignoreWordIfInFewerDocs", value);
            return this;
        }

        

        

        /**
         * Minimum word-document recurrences.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#ignoreWordIfInFewerDocs 
         */
        public AttributeBuilder ignoreWordIfInFewerDocs(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("STCClusteringAlgorithm.ignoreWordIfInFewerDocs", value);
            return this;
        }

        

        

        /**
         * Maximum word-document ratio. A number between 0 and 1, if a word exists in more
snippets than this ratio, it is ignored.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#ignoreWordIfInHigherDocsPercent 
         */
        public AttributeBuilder ignoreWordIfInHigherDocsPercent(double value)
        {
            map.put("STCClusteringAlgorithm.ignoreWordIfInHigherDocsPercent", value);
            return this;
        }

        

        

        /**
         * Maximum word-document ratio. A number between 0 and 1, if a word exists in more
snippets than this ratio, it is ignored.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#ignoreWordIfInHigherDocsPercent 
         */
        public AttributeBuilder ignoreWordIfInHigherDocsPercent(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("STCClusteringAlgorithm.ignoreWordIfInHigherDocsPercent", value);
            return this;
        }

        

        

        /**
         * Minimum base cluster score.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#minBaseClusterScore 
         */
        public AttributeBuilder minBaseClusterScore(double value)
        {
            map.put("STCClusteringAlgorithm.minBaseClusterScore", value);
            return this;
        }

        

        

        /**
         * Minimum base cluster score.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#minBaseClusterScore 
         */
        public AttributeBuilder minBaseClusterScore(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("STCClusteringAlgorithm.minBaseClusterScore", value);
            return this;
        }

        

        

        /**
         * Maximum base clusters count. Trims the base cluster array after N-th position for
the merging phase.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#maxBaseClusters 
         */
        public AttributeBuilder maxBaseClusters(int value)
        {
            map.put("STCClusteringAlgorithm.maxBaseClusters", value);
            return this;
        }

        

        

        /**
         * Maximum base clusters count. Trims the base cluster array after N-th position for
the merging phase.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#maxBaseClusters 
         */
        public AttributeBuilder maxBaseClusters(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("STCClusteringAlgorithm.maxBaseClusters", value);
            return this;
        }

        

        

        /**
         * Minimum documents per base cluster.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#minBaseClusterSize 
         */
        public AttributeBuilder minBaseClusterSize(int value)
        {
            map.put("STCClusteringAlgorithm.minBaseClusterSize", value);
            return this;
        }

        

        

        /**
         * Minimum documents per base cluster.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#minBaseClusterSize 
         */
        public AttributeBuilder minBaseClusterSize(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("STCClusteringAlgorithm.minBaseClusterSize", value);
            return this;
        }

        

        

        /**
         * Maximum final clusters.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#maxClusters 
         */
        public AttributeBuilder maxClusters(int value)
        {
            map.put("STCClusteringAlgorithm.maxClusters", value);
            return this;
        }

        

        

        /**
         * Maximum final clusters.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#maxClusters 
         */
        public AttributeBuilder maxClusters(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("STCClusteringAlgorithm.maxClusters", value);
            return this;
        }

        

        

        /**
         * Base cluster merge threshold.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#mergeThreshold 
         */
        public AttributeBuilder mergeThreshold(double value)
        {
            map.put("STCClusteringAlgorithm.mergeThreshold", value);
            return this;
        }

        

        

        /**
         * Base cluster merge threshold.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#mergeThreshold 
         */
        public AttributeBuilder mergeThreshold(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("STCClusteringAlgorithm.mergeThreshold", value);
            return this;
        }

        

        

        /**
         * Maximum cluster phrase overlap.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#maxPhraseOverlap 
         */
        public AttributeBuilder maxPhraseOverlap(double value)
        {
            map.put("STCClusteringAlgorithm.maxPhraseOverlap", value);
            return this;
        }

        

        

        /**
         * Maximum cluster phrase overlap.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#maxPhraseOverlap 
         */
        public AttributeBuilder maxPhraseOverlap(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("STCClusteringAlgorithm.maxPhraseOverlap", value);
            return this;
        }

        

        

        /**
         * Minimum general phrase coverage. Minimum phrase coverage to appear in cluster
description.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#mostGeneralPhraseCoverage 
         */
        public AttributeBuilder mostGeneralPhraseCoverage(double value)
        {
            map.put("STCClusteringAlgorithm.mostGeneralPhraseCoverage", value);
            return this;
        }

        

        

        /**
         * Minimum general phrase coverage. Minimum phrase coverage to appear in cluster
description.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#mostGeneralPhraseCoverage 
         */
        public AttributeBuilder mostGeneralPhraseCoverage(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("STCClusteringAlgorithm.mostGeneralPhraseCoverage", value);
            return this;
        }

        

        

        /**
         * Maximum words per label. Base clusters formed by phrases with more words than this
ratio are trimmed.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#maxDescPhraseLength 
         */
        public AttributeBuilder maxDescPhraseLength(int value)
        {
            map.put("STCClusteringAlgorithm.maxDescPhraseLength", value);
            return this;
        }

        

        

        /**
         * Maximum words per label. Base clusters formed by phrases with more words than this
ratio are trimmed.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#maxDescPhraseLength 
         */
        public AttributeBuilder maxDescPhraseLength(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("STCClusteringAlgorithm.maxDescPhraseLength", value);
            return this;
        }

        

        

        /**
         * Maximum phrases per label. Maximum number of phrases from base clusters promoted
to the cluster's label.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#maxPhrases 
         */
        public AttributeBuilder maxPhrases(int value)
        {
            map.put("STCClusteringAlgorithm.maxPhrases", value);
            return this;
        }

        

        

        /**
         * Maximum phrases per label. Maximum number of phrases from base clusters promoted
to the cluster's label.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#maxPhrases 
         */
        public AttributeBuilder maxPhrases(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("STCClusteringAlgorithm.maxPhrases", value);
            return this;
        }

        

        

        /**
         * Single term boost. A factor in calculation of the base cluster score. If greater
then zero, single-term base clusters are assigned this value regardless of the
penalty function.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#singleTermBoost 
         */
        public AttributeBuilder singleTermBoost(double value)
        {
            map.put("STCClusteringAlgorithm.singleTermBoost", value);
            return this;
        }

        

        

        /**
         * Single term boost. A factor in calculation of the base cluster score. If greater
then zero, single-term base clusters are assigned this value regardless of the
penalty function.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#singleTermBoost 
         */
        public AttributeBuilder singleTermBoost(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("STCClusteringAlgorithm.singleTermBoost", value);
            return this;
        }

        

        

        /**
         * Optimal label length. A factor in calculation of the base cluster score.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#optimalPhraseLength 
         */
        public AttributeBuilder optimalPhraseLength(int value)
        {
            map.put("STCClusteringAlgorithm.optimalPhraseLength", value);
            return this;
        }

        

        

        /**
         * Optimal label length. A factor in calculation of the base cluster score.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#optimalPhraseLength 
         */
        public AttributeBuilder optimalPhraseLength(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("STCClusteringAlgorithm.optimalPhraseLength", value);
            return this;
        }

        

        

        /**
         * Phrase length tolerance. A factor in calculation of the base cluster score.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#optimalPhraseLengthDev 
         */
        public AttributeBuilder optimalPhraseLengthDev(double value)
        {
            map.put("STCClusteringAlgorithm.optimalPhraseLengthDev", value);
            return this;
        }

        

        

        /**
         * Phrase length tolerance. A factor in calculation of the base cluster score.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#optimalPhraseLengthDev 
         */
        public AttributeBuilder optimalPhraseLengthDev(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("STCClusteringAlgorithm.optimalPhraseLengthDev", value);
            return this;
        }

        

        

        /**
         * Document count boost. A factor in calculation of the base cluster score, boosting
the score depending on the number of documents found in the base cluster.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#documentCountBoost 
         */
        public AttributeBuilder documentCountBoost(double value)
        {
            map.put("STCClusteringAlgorithm.documentCountBoost", value);
            return this;
        }

        

        

        /**
         * Document count boost. A factor in calculation of the base cluster score, boosting
the score depending on the number of documents found in the base cluster.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#documentCountBoost 
         */
        public AttributeBuilder documentCountBoost(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("STCClusteringAlgorithm.documentCountBoost", value);
            return this;
        }

        

        

        /**
         * Common preprocessing tasks handler.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#preprocessingPipeline 
         */
        public AttributeBuilder preprocessingPipeline(org.carrot2.text.preprocessing.pipeline.IPreprocessingPipeline value)
        {
            map.put("STCClusteringAlgorithm.preprocessingPipeline", value);
            return this;
        }

        

        /**
         * Common preprocessing tasks handler.
         *
         * A class that extends org.carrot2.text.preprocessing.pipeline.IPreprocessingPipeline or appropriate IObjectFactory.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#preprocessingPipeline
         */
        public AttributeBuilder preprocessingPipeline(Class<?> clazz)
        {
            map.put("STCClusteringAlgorithm.preprocessingPipeline", clazz);
            return this;
        }

        

        /**
         * Common preprocessing tasks handler.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#preprocessingPipeline 
         */
        public AttributeBuilder preprocessingPipeline(IObjectFactory<? extends org.carrot2.text.preprocessing.pipeline.IPreprocessingPipeline> value)
        {
            map.put("STCClusteringAlgorithm.preprocessingPipeline", value);
            return this;
        }

        

        

        /**
         * Balance between cluster score and size during cluster sorting. Value equal to 0.0
will sort clusters based only on cluster size. Value equal to 1.0
will sort clusters based only on cluster score.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#scoreWeight 
         */
        public AttributeBuilder scoreWeight(double value)
        {
            map.put("STCClusteringAlgorithm.scoreWeight", value);
            return this;
        }

        

        

        /**
         * Balance between cluster score and size during cluster sorting. Value equal to 0.0
will sort clusters based only on cluster size. Value equal to 1.0
will sort clusters based only on cluster score.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#scoreWeight 
         */
        public AttributeBuilder scoreWeight(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("STCClusteringAlgorithm.scoreWeight", value);
            return this;
        }

        

        

        /**
         * Merge all stem-equivalent base clusters before running the merge phase.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#mergeStemEquivalentBaseClusters 
         */
        public AttributeBuilder mergeStemEquivalentBaseClusters(boolean value)
        {
            map.put("STCClusteringAlgorithm.mergeStemEquivalentBaseClusters", value);
            return this;
        }

        

        

        /**
         * Merge all stem-equivalent base clusters before running the merge phase.
         * 
         * @see org.carrot2.clustering.stc.STCClusteringAlgorithm#mergeStemEquivalentBaseClusters 
         */
        public AttributeBuilder mergeStemEquivalentBaseClusters(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("STCClusteringAlgorithm.mergeStemEquivalentBaseClusters", value);
            return this;
        }

        

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.text.clustering.MultilingualClustering} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.text.clustering.MultilingualClusteringDescriptor.AttributeBuilder multilingualClustering()
        {
            return org.carrot2.text.clustering.MultilingualClusteringDescriptor.attributeBuilder(map);
        }

    }

    /**
     * Creates an attribute map builder for the component. You can use this
     * builder as a type-safe alternative to populating the attribute map using attribute keys.
     * 
     * @param attributeValues An existing map which should be used to collect attribute values. 
     *        Attribute values set by this builder will be added to the provided map, overwriting
     *        previously defined mappings, if any.
     */
    public static AttributeBuilder attributeBuilder(Map<String, Object> attributeValues)
    {
        return new AttributeBuilder(attributeValues);
    }
    
    /* IBindableDescriptor */

    @Override 
    public String getPrefix()
    {
        return prefix;
    }

    @Override 
    public String getTitle()
    {
        return title;
    }
    
    @Override 
    public String getLabel()      
    { 
        return label;
    }
    
    @Override 
    public String getDescription() 
    { 
        return description; 
    }

    @Override 
    public Set<AttributeInfo> getOwnAttributes()
    { 
        return ownAttributes;
    }

    @Override 
    public Set<AttributeInfo> getAttributes()
    {
        return allAttributes;
    }

    @Override 
    public Map<String, AttributeInfo> getAttributesByKey()
    {
        return allAttributesByKey;
    }

    @Override 
    public Map<String, AttributeInfo> getAttributesByFieldName()
    {
        return allAttributesByFieldName;
    }
}
