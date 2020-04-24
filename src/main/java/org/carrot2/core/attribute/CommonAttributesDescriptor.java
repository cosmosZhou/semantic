

// APT-generated file.

package org.carrot2.core.attribute;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.core.attribute.CommonAttributes} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.core.attribute.CommonAttributes
 */
public final class CommonAttributesDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.core.attribute.CommonAttributes";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Attributes shared and inherited by many clustering algorithms";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "Extracted for consistency.";

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
        ownAttrs.add(attributes.start);
        ownAttrs.add(attributes.results);
        ownAttrs.add(attributes.query);
        ownAttrs.add(attributes.resultsTotal);
        ownAttrs.add(attributes.documents);
        ownAttrs.add(attributes.clusters);
        ownAttrs.add(attributes.processingTimeTotal);
        ownAttrs.add(attributes.processingTimeSource);
        ownAttrs.add(attributes.processingTimeAlgorithm);
        ownAttrs.add(attributes.processingResultTitle);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.start);
        allAttrs.add(org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.results);
        allAttrs.add(org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.query);
        allAttrs.add(org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.resultsTotal);
        allAttrs.add(org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.documents);
        allAttrs.add(org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.clusters);
        allAttrs.add(org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.processingTimeTotal);
        allAttrs.add(org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.processingTimeSource);
        allAttrs.add(org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.processingTimeAlgorithm);
        allAttrs.add(org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.processingResultTitle);

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
     * Constants for all attribute keys of the {@link org.carrot2.core.attribute.CommonAttributes} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.core.attribute.CommonAttributes#start}. */
        public static final String START = "start";
        /** Attribute key for: {@link org.carrot2.core.attribute.CommonAttributes#results}. */
        public static final String RESULTS = "results";
        /** Attribute key for: {@link org.carrot2.core.attribute.CommonAttributes#query}. */
        public static final String QUERY = "query";
        /** Attribute key for: {@link org.carrot2.core.attribute.CommonAttributes#resultsTotal}. */
        public static final String RESULTS_TOTAL = "results-total";
        /** Attribute key for: {@link org.carrot2.core.attribute.CommonAttributes#documents}. */
        public static final String DOCUMENTS = "documents";
        /** Attribute key for: {@link org.carrot2.core.attribute.CommonAttributes#clusters}. */
        public static final String CLUSTERS = "clusters";
        /** Attribute key for: {@link org.carrot2.core.attribute.CommonAttributes#processingTimeTotal}. */
        public static final String PROCESSING_TIME_TOTAL = "processing-time-total";
        /** Attribute key for: {@link org.carrot2.core.attribute.CommonAttributes#processingTimeSource}. */
        public static final String PROCESSING_TIME_SOURCE = "processing-time-source";
        /** Attribute key for: {@link org.carrot2.core.attribute.CommonAttributes#processingTimeAlgorithm}. */
        public static final String PROCESSING_TIME_ALGORITHM = "processing-time-algorithm";
        /** Attribute key for: {@link org.carrot2.core.attribute.CommonAttributes#processingResultTitle}. */
        public static final String PROCESSING_RESULT_TITLE = "processing-result.title";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.core.attribute.CommonAttributes} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo start = 
            new AttributeInfo(
                "start",
                "org.carrot2.core.attribute.CommonAttributes",
                "start",
"Index of the first document/ search result to fetch. The index starts at zero.",
"Start index",
"Index of the first document/ search result to fetch",
"The index starts at zero.",
"Search query",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo results = 
            new AttributeInfo(
                "results",
                "org.carrot2.core.attribute.CommonAttributes",
                "results",
"Maximum number of documents/ search results to fetch. The query hint can be used\nby clustering algorithms to avoid creating trivial clusters (combination of query words).",
"Results",
"Maximum number of documents/ search results to fetch",
"The query hint can be used by clustering algorithms to avoid creating trivial clusters (combination of query words).",
"Search query",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo query = 
            new AttributeInfo(
                "query",
                "org.carrot2.core.attribute.CommonAttributes",
                "query",
"Query to perform.",
"Query",
"Query to perform",
null,
"Search query",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo resultsTotal = 
            new AttributeInfo(
                "results-total",
                "org.carrot2.core.attribute.CommonAttributes",
                "resultsTotal",
"Estimated total number of matching documents.",
"Total results",
"Estimated total number of matching documents",
null,
"Search result information",
null,
null
            );

        /**
         *          */
        public final AttributeInfo documents = 
            new AttributeInfo(
                "documents",
                "org.carrot2.core.attribute.CommonAttributes",
                "documents",
"Documents returned by the search engine/ document retrieval system or\ndocuments passed as input to the clustering algorithm.",
"Documents",
"Documents returned by the search engine/ document retrieval system or documents passed as input to the clustering algorithm",
null,
"Documents",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo clusters = 
            new AttributeInfo(
                "clusters",
                "org.carrot2.core.attribute.CommonAttributes",
                "clusters",
"Clusters created by the clustering algorithm.",
"Clusters",
"Clusters created by the clustering algorithm",
null,
"Search result information",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo processingTimeTotal = 
            new AttributeInfo(
                "processing-time-total",
                "org.carrot2.core.attribute.CommonAttributes",
                "processingTimeTotal",
"Total processing time in milliseconds. A sum of processing times of all components in the chain.\nTotal processing time may be greater than the sum of\n{@link org.carrot2.core.attribute.CommonAttributes#processingTimeTotal} and {@link org.carrot2.core.attribute.CommonAttributes#processingTimeAlgorithm}.",
"Total processing time",
"Total processing time in milliseconds",
"A sum of processing times of all components in the chain. Total processing time may be greater than the sum of <code>org.carrot2.core.attribute.CommonAttributes.processingTimeTotal</code> and <code>org.carrot2.core.attribute.CommonAttributes.processingTimeAlgorithm</code>.",
"Search result information",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo processingTimeSource = 
            new AttributeInfo(
                "processing-time-source",
                "org.carrot2.core.attribute.CommonAttributes",
                "processingTimeSource",
"Data source processing time in milliseconds. A sum of processing times of all\n{@link org.carrot2.core.IDocumentSource}s in the chain, including the\n{@link org.carrot2.core.IProcessingComponent#beforeProcessing()} and\n{@link org.carrot2.core.IProcessingComponent#afterProcessing()} hooks.",
"Data source processing time",
"Data source processing time in milliseconds",
"A sum of processing times of all <code>org.carrot2.core.IDocumentSource</code>s in the chain, including the <code>org.carrot2.core.IProcessingComponent.beforeProcessing()</code> and <code>org.carrot2.core.IProcessingComponent.afterProcessing()</code> hooks.",
"Search result information",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo processingTimeAlgorithm = 
            new AttributeInfo(
                "processing-time-algorithm",
                "org.carrot2.core.attribute.CommonAttributes",
                "processingTimeAlgorithm",
"Algorithm processing time in milliseconds. A sum of processing times of all\n{@link org.carrot2.core.IClusteringAlgorithm}s in the chain, including the\n{@link org.carrot2.core.IProcessingComponent#beforeProcessing()} and\n{@link org.carrot2.core.IProcessingComponent#afterProcessing()} hooks.",
"Clustering algorithm processing time",
"Algorithm processing time in milliseconds",
"A sum of processing times of all <code>org.carrot2.core.IClusteringAlgorithm</code>s in the chain, including the <code>org.carrot2.core.IProcessingComponent.beforeProcessing()</code> and <code>org.carrot2.core.IProcessingComponent.afterProcessing()</code> hooks.",
"Search result information",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo processingResultTitle = 
            new AttributeInfo(
                "processing-result.title",
                "org.carrot2.core.attribute.CommonAttributes",
                "processingResultTitle",
"Processing result title. A typical title for a processing result will be the query\nused to fetch documents from that source. For certain document sources the query\nmay not be needed (on-disk XML, feed of syndicated news); in such cases, the input\ncomponent should set its title properly for visual interfaces such as the\nworkbench.",
"Title",
"Processing result title",
"A typical title for a processing result will be the query used to fetch documents from that source. For certain document sources the query may not be needed (on-disk XML, feed of syndicated news); in such cases, the input component should set its title properly for visual interfaces such as the workbench.",
"Search result information",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.core.attribute.CommonAttributes} component. You can use this
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
         * Index of the first document/ search result to fetch. The index starts at zero.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#start 
         */
        public AttributeBuilder start(int value)
        {
            map.put("start", value);
            return this;
        }

        

        

        /**
         * Index of the first document/ search result to fetch. The index starts at zero.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#start 
         */
        public AttributeBuilder start(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("start", value);
            return this;
        }

        

        

        /**
         * Maximum number of documents/ search results to fetch. The query hint can be used
by clustering algorithms to avoid creating trivial clusters (combination of query words).
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#results 
         */
        public AttributeBuilder results(int value)
        {
            map.put("results", value);
            return this;
        }

        

        

        /**
         * Maximum number of documents/ search results to fetch. The query hint can be used
by clustering algorithms to avoid creating trivial clusters (combination of query words).
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#results 
         */
        public AttributeBuilder results(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("results", value);
            return this;
        }

        

        

        /**
         * Query to perform.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#query 
         */
        public AttributeBuilder query(java.lang.String value)
        {
            map.put("query", value);
            return this;
        }

        

        

        /**
         * Query to perform.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#query 
         */
        public AttributeBuilder query(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("query", value);
            return this;
        }

        

        

        

        

        

        /**
         * Estimated total number of matching documents.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#resultsTotal 
         */
        public java.lang.Long resultsTotal()
        {
            return (java.lang.Long) map.get("results-total");
        }

        

        /**
         * Documents returned by the search engine/ document retrieval system or
documents passed as input to the clustering algorithm.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#documents 
         */
        public AttributeBuilder documents(java.util.List<org.carrot2.core.Document> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        /**
         * Documents returned by the search engine/ document retrieval system or
documents passed as input to the clustering algorithm.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#documents 
         */
        public AttributeBuilder documents(IObjectFactory<? extends java.util.List<org.carrot2.core.Document>> value)
        {
            map.put("documents", value);
            return this;
        }

        

        /**
         * Documents returned by the search engine/ document retrieval system or
documents passed as input to the clustering algorithm.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#documents 
         */
@SuppressWarnings("unchecked")        public java.util.List<org.carrot2.core.Document> documents()
        {
            return (java.util.List<org.carrot2.core.Document>) map.get("documents");
        }

        

        

        

        

        /**
         * Clusters created by the clustering algorithm.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#clusters 
         */
@SuppressWarnings("unchecked")        public java.util.List<org.carrot2.core.Cluster> clusters()
        {
            return (java.util.List<org.carrot2.core.Cluster>) map.get("clusters");
        }

        

        

        

        

        /**
         * Total processing time in milliseconds. A sum of processing times of all components in the chain.
Total processing time may be greater than the sum of
{@link org.carrot2.core.attribute.CommonAttributes#processingTimeTotal} and {@link org.carrot2.core.attribute.CommonAttributes#processingTimeAlgorithm}.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#processingTimeTotal 
         */
        public java.lang.Long processingTimeTotal()
        {
            return (java.lang.Long) map.get("processing-time-total");
        }

        

        

        

        

        /**
         * Data source processing time in milliseconds. A sum of processing times of all
{@link org.carrot2.core.IDocumentSource}s in the chain, including the
{@link org.carrot2.core.IProcessingComponent#beforeProcessing()} and
{@link org.carrot2.core.IProcessingComponent#afterProcessing()} hooks.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#processingTimeSource 
         */
        public java.lang.Long processingTimeSource()
        {
            return (java.lang.Long) map.get("processing-time-source");
        }

        

        

        

        

        /**
         * Algorithm processing time in milliseconds. A sum of processing times of all
{@link org.carrot2.core.IClusteringAlgorithm}s in the chain, including the
{@link org.carrot2.core.IProcessingComponent#beforeProcessing()} and
{@link org.carrot2.core.IProcessingComponent#afterProcessing()} hooks.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#processingTimeAlgorithm 
         */
        public java.lang.Long processingTimeAlgorithm()
        {
            return (java.lang.Long) map.get("processing-time-algorithm");
        }

        

        

        

        

        /**
         * Processing result title. A typical title for a processing result will be the query
used to fetch documents from that source. For certain document sources the query
may not be needed (on-disk XML, feed of syndicated news); in such cases, the input
component should set its title properly for visual interfaces such as the
workbench.
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#processingResultTitle 
         */
        public java.lang.String processingResultTitle()
        {
            return (java.lang.String) map.get("processing-result.title");
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
