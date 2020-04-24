

// APT-generated file.

package org.carrot2.clustering.lingo;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.clustering.lingo.LingoClusteringAlgorithm} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.clustering.lingo.LingoClusteringAlgorithm
 */
public final class LingoClusteringAlgorithmDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.clustering.lingo.LingoClusteringAlgorithm";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "LingoClusteringAlgorithm";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Lingo clustering algorithm";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "Lingo Clustering";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "Implementation as described in: <i> \"Stanis\u0142aw Osi\u0144ski, Dawid Weiss: A Concept-Driven Algorithm for Clustering Search Results. IEEE Intelligent Systems, May/June, 3 (vol. 20), 2005, pp. 48\u201454.\"</i>.";

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
        ownAttrs.add(attributes.scoreWeight);
        ownAttrs.add(attributes.desiredClusterCountBase);
        ownAttrs.add(attributes.preprocessingPipeline);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.clustering.lingo.LingoClusteringAlgorithmDescriptor.attributes.query);
        allAttrs.add(org.carrot2.clustering.lingo.LingoClusteringAlgorithmDescriptor.attributes.documents);
        allAttrs.add(org.carrot2.clustering.lingo.LingoClusteringAlgorithmDescriptor.attributes.clusters);
        allAttrs.add(org.carrot2.clustering.lingo.LingoClusteringAlgorithmDescriptor.attributes.scoreWeight);
        allAttrs.add(org.carrot2.clustering.lingo.LingoClusteringAlgorithmDescriptor.attributes.desiredClusterCountBase);
        allAttrs.add(org.carrot2.clustering.lingo.LingoClusteringAlgorithmDescriptor.attributes.preprocessingPipeline);

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
     * Constants for all attribute keys of the {@link org.carrot2.clustering.lingo.LingoClusteringAlgorithm} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.clustering.lingo.LingoClusteringAlgorithm#query}. */
        public static final String QUERY = "query";
        /** Attribute key for: {@link org.carrot2.clustering.lingo.LingoClusteringAlgorithm#documents}. */
        public static final String DOCUMENTS = "documents";
        /** Attribute key for: {@link org.carrot2.clustering.lingo.LingoClusteringAlgorithm#clusters}. */
        public static final String CLUSTERS = "clusters";
        /** Attribute key for: {@link org.carrot2.clustering.lingo.LingoClusteringAlgorithm#scoreWeight}. */
        public static final String SCORE_WEIGHT = "LingoClusteringAlgorithm.scoreWeight";
        /** Attribute key for: {@link org.carrot2.clustering.lingo.LingoClusteringAlgorithm#desiredClusterCountBase}. */
        public static final String DESIRED_CLUSTER_COUNT_BASE = "LingoClusteringAlgorithm.desiredClusterCountBase";
        /** Attribute key for: {@link org.carrot2.clustering.lingo.LingoClusteringAlgorithm#preprocessingPipeline}. */
        public static final String PREPROCESSING_PIPELINE = "LingoClusteringAlgorithm.preprocessingPipeline";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.clustering.lingo.LingoClusteringAlgorithm} component.
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
                "org.carrot2.clustering.lingo.LingoClusteringAlgorithm",
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
                "org.carrot2.clustering.lingo.LingoClusteringAlgorithm",
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
                "org.carrot2.clustering.lingo.LingoClusteringAlgorithm",
                "clusters",
null,
null,
null,
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.clusters
            );

        /**
         *          */
        public final AttributeInfo scoreWeight = 
            new AttributeInfo(
                "LingoClusteringAlgorithm.scoreWeight",
                "org.carrot2.clustering.lingo.LingoClusteringAlgorithm",
                "scoreWeight",
"Balance between cluster score and size during cluster sorting. Value equal to 0.0\nwill cause Lingo to sort clusters based only on cluster size. Value equal to 1.0\nwill cause Lingo to sort clusters based only on cluster score.",
"Size-Score sorting ratio",
"Balance between cluster score and size during cluster sorting",
"Value equal to 0.0 will cause Lingo to sort clusters based only on cluster size. Value equal to 1.0 will cause Lingo to sort clusters based only on cluster score.",
"Clusters",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo desiredClusterCountBase = 
            new AttributeInfo(
                "LingoClusteringAlgorithm.desiredClusterCountBase",
                "org.carrot2.clustering.lingo.LingoClusteringAlgorithm",
                "desiredClusterCountBase",
"Desired cluster count base. Base factor used to calculate the number of clusters\nbased on the number of documents on input. The larger the value, the more clusters\nwill be created. The number of clusters created by the algorithm will be\nproportional to the cluster count base, but not in a linear way.",
"Cluster count base",
"Desired cluster count base",
"Base factor used to calculate the number of clusters based on the number of documents on input. The larger the value, the more clusters will be created. The number of clusters created by the algorithm will be proportional to the cluster count base, but not in a linear way.",
"Clusters",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo preprocessingPipeline = 
            new AttributeInfo(
                "LingoClusteringAlgorithm.preprocessingPipeline",
                "org.carrot2.clustering.lingo.LingoClusteringAlgorithm",
                "preprocessingPipeline",
"Common preprocessing tasks handler, contains bindable attributes.",
null,
"Common preprocessing tasks handler, contains bindable attributes",
null,
null,
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );


        /**
         * Attributes of the nested {@link org.carrot2.text.vsm.TermDocumentMatrixBuilder} component.
         */
        public final org.carrot2.text.vsm.TermDocumentMatrixBuilderDescriptor.Attributes matrixBuilder =
            org.carrot2.text.vsm.TermDocumentMatrixBuilderDescriptor.attributes;

        /**
         * Attributes of the nested {@link org.carrot2.text.vsm.TermDocumentMatrixReducer} component.
         */
        public final org.carrot2.text.vsm.TermDocumentMatrixReducerDescriptor.Attributes matrixReducer =
            org.carrot2.text.vsm.TermDocumentMatrixReducerDescriptor.attributes;

        /**
         * Attributes of the nested {@link org.carrot2.clustering.lingo.ClusterBuilder} component.
         */
        public final org.carrot2.clustering.lingo.ClusterBuilderDescriptor.Attributes clusterBuilder =
            org.carrot2.clustering.lingo.ClusterBuilderDescriptor.attributes;

        /**
         * Attributes of the nested {@link org.carrot2.text.preprocessing.LabelFormatter} component.
         */
        public final org.carrot2.text.preprocessing.LabelFormatterDescriptor.Attributes labelFormatter =
            org.carrot2.text.preprocessing.LabelFormatterDescriptor.attributes;

        /**
         * Attributes of the nested {@link org.carrot2.text.clustering.MultilingualClustering} component.
         */
        public final org.carrot2.text.clustering.MultilingualClusteringDescriptor.Attributes multilingualClustering =
            org.carrot2.text.clustering.MultilingualClusteringDescriptor.attributes;

    }

    /**
     * Attribute map builder for the  {@link org.carrot2.clustering.lingo.LingoClusteringAlgorithm} component. You can use this
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
         * @see org.carrot2.clustering.lingo.LingoClusteringAlgorithm#query 
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
         * @see org.carrot2.clustering.lingo.LingoClusteringAlgorithm#query 
         */
        public AttributeBuilder query(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("query", value);
            return this;
        }

        

        

        /**
         * Documents to cluster.
         * 
         * @see org.carrot2.clustering.lingo.LingoClusteringAlgorithm#documents 
         */
        public AttributeBuilder documents(java.util.List<org.carrot2.core.Document> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        /**
         * Documents to cluster.
         * 
         * @see org.carrot2.clustering.lingo.LingoClusteringAlgorithm#documents 
         */
        public AttributeBuilder documents(IObjectFactory<? extends java.util.List<org.carrot2.core.Document>> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        

        

        

        /**
         * 
         * 
         * @see org.carrot2.clustering.lingo.LingoClusteringAlgorithm#clusters 
         */
@SuppressWarnings("unchecked")        public java.util.List<org.carrot2.core.Cluster> clusters()
        {
            return (java.util.List<org.carrot2.core.Cluster>) map.get("clusters");
        }

        

        /**
         * Balance between cluster score and size during cluster sorting. Value equal to 0.0
will cause Lingo to sort clusters based only on cluster size. Value equal to 1.0
will cause Lingo to sort clusters based only on cluster score.
         * 
         * @see org.carrot2.clustering.lingo.LingoClusteringAlgorithm#scoreWeight 
         */
        public AttributeBuilder scoreWeight(double value)
        {
            map.put("LingoClusteringAlgorithm.scoreWeight", value);
            return this;
        }

        

        

        /**
         * Balance between cluster score and size during cluster sorting. Value equal to 0.0
will cause Lingo to sort clusters based only on cluster size. Value equal to 1.0
will cause Lingo to sort clusters based only on cluster score.
         * 
         * @see org.carrot2.clustering.lingo.LingoClusteringAlgorithm#scoreWeight 
         */
        public AttributeBuilder scoreWeight(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("LingoClusteringAlgorithm.scoreWeight", value);
            return this;
        }

        

        

        /**
         * Desired cluster count base. Base factor used to calculate the number of clusters
based on the number of documents on input. The larger the value, the more clusters
will be created. The number of clusters created by the algorithm will be
proportional to the cluster count base, but not in a linear way.
         * 
         * @see org.carrot2.clustering.lingo.LingoClusteringAlgorithm#desiredClusterCountBase 
         */
        public AttributeBuilder desiredClusterCountBase(int value)
        {
            map.put("LingoClusteringAlgorithm.desiredClusterCountBase", value);
            return this;
        }

        

        

        /**
         * Desired cluster count base. Base factor used to calculate the number of clusters
based on the number of documents on input. The larger the value, the more clusters
will be created. The number of clusters created by the algorithm will be
proportional to the cluster count base, but not in a linear way.
         * 
         * @see org.carrot2.clustering.lingo.LingoClusteringAlgorithm#desiredClusterCountBase 
         */
        public AttributeBuilder desiredClusterCountBase(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("LingoClusteringAlgorithm.desiredClusterCountBase", value);
            return this;
        }

        

        

        /**
         * Common preprocessing tasks handler, contains bindable attributes.
         * 
         * @see org.carrot2.clustering.lingo.LingoClusteringAlgorithm#preprocessingPipeline 
         */
        public AttributeBuilder preprocessingPipeline(org.carrot2.text.preprocessing.pipeline.IPreprocessingPipeline value)
        {
            map.put("LingoClusteringAlgorithm.preprocessingPipeline", value);
            return this;
        }

        

        /**
         * Common preprocessing tasks handler, contains bindable attributes.
         *
         * A class that extends org.carrot2.text.preprocessing.pipeline.IPreprocessingPipeline or appropriate IObjectFactory.
         * 
         * @see org.carrot2.clustering.lingo.LingoClusteringAlgorithm#preprocessingPipeline
         */
        public AttributeBuilder preprocessingPipeline(Class<?> clazz)
        {
            map.put("LingoClusteringAlgorithm.preprocessingPipeline", clazz);
            return this;
        }

        

        /**
         * Common preprocessing tasks handler, contains bindable attributes.
         * 
         * @see org.carrot2.clustering.lingo.LingoClusteringAlgorithm#preprocessingPipeline 
         */
        public AttributeBuilder preprocessingPipeline(IObjectFactory<? extends org.carrot2.text.preprocessing.pipeline.IPreprocessingPipeline> value)
        {
            map.put("LingoClusteringAlgorithm.preprocessingPipeline", value);
            return this;
        }

        

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.text.vsm.TermDocumentMatrixBuilder} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.text.vsm.TermDocumentMatrixBuilderDescriptor.AttributeBuilder matrixBuilder()
        {
            return org.carrot2.text.vsm.TermDocumentMatrixBuilderDescriptor.attributeBuilder(map);
        }

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.text.vsm.TermDocumentMatrixReducer} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.text.vsm.TermDocumentMatrixReducerDescriptor.AttributeBuilder matrixReducer()
        {
            return org.carrot2.text.vsm.TermDocumentMatrixReducerDescriptor.attributeBuilder(map);
        }

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.clustering.lingo.ClusterBuilder} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.clustering.lingo.ClusterBuilderDescriptor.AttributeBuilder clusterBuilder()
        {
            return org.carrot2.clustering.lingo.ClusterBuilderDescriptor.attributeBuilder(map);
        }

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.text.preprocessing.LabelFormatter} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.text.preprocessing.LabelFormatterDescriptor.AttributeBuilder labelFormatter()
        {
            return org.carrot2.text.preprocessing.LabelFormatterDescriptor.attributeBuilder(map);
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
