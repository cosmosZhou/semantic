

// APT-generated file.

package org.carrot2.clustering.kmeans;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm
 */
public final class BisectingKMeansClusteringAlgorithmDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "BisectingKMeansClusteringAlgorithm";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "A very simple implementation of bisecting k-means clustering";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "Unlike other algorithms in Carrot2, this one creates hard clusterings (one document belongs only to one cluster). On the other hand, the clusters are labeled only with individual words that may not always fully correspond to all documents in the cluster.";

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
        ownAttrs.add(attributes.documents);
        ownAttrs.add(attributes.clusters);
        ownAttrs.add(attributes.clusterCount);
        ownAttrs.add(attributes.maxIterations);
        ownAttrs.add(attributes.useDimensionalityReduction);
        ownAttrs.add(attributes.partitionCount);
        ownAttrs.add(attributes.labelCount);
        ownAttrs.add(attributes.preprocessingPipeline);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithmDescriptor.attributes.documents);
        allAttrs.add(org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithmDescriptor.attributes.clusters);
        allAttrs.add(org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithmDescriptor.attributes.clusterCount);
        allAttrs.add(org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithmDescriptor.attributes.maxIterations);
        allAttrs.add(org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithmDescriptor.attributes.useDimensionalityReduction);
        allAttrs.add(org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithmDescriptor.attributes.partitionCount);
        allAttrs.add(org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithmDescriptor.attributes.labelCount);
        allAttrs.add(org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithmDescriptor.attributes.preprocessingPipeline);

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
     * Constants for all attribute keys of the {@link org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#documents}. */
        public static final String DOCUMENTS = "documents";
        /** Attribute key for: {@link org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#clusters}. */
        public static final String CLUSTERS = "clusters";
        /** Attribute key for: {@link org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#clusterCount}. */
        public static final String CLUSTER_COUNT = "BisectingKMeansClusteringAlgorithm.clusterCount";
        /** Attribute key for: {@link org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#maxIterations}. */
        public static final String MAX_ITERATIONS = "BisectingKMeansClusteringAlgorithm.maxIterations";
        /** Attribute key for: {@link org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#useDimensionalityReduction}. */
        public static final String USE_DIMENSIONALITY_REDUCTION = "BisectingKMeansClusteringAlgorithm.useDimensionalityReduction";
        /** Attribute key for: {@link org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#partitionCount}. */
        public static final String PARTITION_COUNT = "BisectingKMeansClusteringAlgorithm.partitionCount";
        /** Attribute key for: {@link org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#labelCount}. */
        public static final String LABEL_COUNT = "BisectingKMeansClusteringAlgorithm.labelCount";
        /** Attribute key for: {@link org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#preprocessingPipeline}. */
        public static final String PREPROCESSING_PIPELINE = "BisectingKMeansClusteringAlgorithm.preprocessingPipeline";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#documents
         */
        public final AttributeInfo documents = 
            new AttributeInfo(
                "documents",
                "org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm",
                "documents",
null,
null,
null,
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
                "org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm",
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
        public final AttributeInfo clusterCount = 
            new AttributeInfo(
                "BisectingKMeansClusteringAlgorithm.clusterCount",
                "org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm",
                "clusterCount",
"The number of clusters to create. The algorithm will create at most the specified\nnumber of clusters.",
"Cluster count",
"The number of clusters to create",
"The algorithm will create at most the specified number of clusters.",
"Clusters",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo maxIterations = 
            new AttributeInfo(
                "BisectingKMeansClusteringAlgorithm.maxIterations",
                "org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm",
                "maxIterations",
"The maximum number of k-means iterations to perform.",
"Maximum iterations",
"The maximum number of k-means iterations to perform",
null,
"K-means",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo useDimensionalityReduction = 
            new AttributeInfo(
                "BisectingKMeansClusteringAlgorithm.useDimensionalityReduction",
                "org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm",
                "useDimensionalityReduction",
"Use dimensionality reduction. If <code>true</code>, k-means will be applied on the\ndimensionality-reduced term-document matrix with the number of dimensions being\nequal to twice the number of requested clusters. If the number of dimensions is \nlower than the number of input documents, reduction will not be performed.\nIf <code>false</code>, the k-means will\nbe performed directly on the original term-document matrix.",
"Use dimensionality reduction",
"Use dimensionality reduction",
"If <code>true</code>, k-means will be applied on the dimensionality-reduced term-document matrix with the number of dimensions being equal to twice the number of requested clusters. If the number of dimensions is lower than the number of input documents, reduction will not be performed. If <code>false</code>, the k-means will be performed directly on the original term-document matrix.",
"K-means",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo partitionCount = 
            new AttributeInfo(
                "BisectingKMeansClusteringAlgorithm.partitionCount",
                "org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm",
                "partitionCount",
"Partition count. The number of partitions to create at each k-means clustering\niteration.",
"Partition count",
"Partition count",
"The number of partitions to create at each k-means clustering iteration.",
"K-means",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo labelCount = 
            new AttributeInfo(
                "BisectingKMeansClusteringAlgorithm.labelCount",
                "org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm",
                "labelCount",
"Label count. The minimum number of labels to return for each cluster.",
"Label count",
"Label count",
"The minimum number of labels to return for each cluster.",
"Clusters",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo preprocessingPipeline = 
            new AttributeInfo(
                "BisectingKMeansClusteringAlgorithm.preprocessingPipeline",
                "org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm",
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
     * Attribute map builder for the  {@link org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm} component. You can use this
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
         * 
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#documents 
         */
        public AttributeBuilder documents(java.util.List<org.carrot2.core.Document> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#documents 
         */
        public AttributeBuilder documents(IObjectFactory<? extends java.util.List<org.carrot2.core.Document>> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        

        

        

        /**
         * 
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#clusters 
         */
@SuppressWarnings("unchecked")        public java.util.List<org.carrot2.core.Cluster> clusters()
        {
            return (java.util.List<org.carrot2.core.Cluster>) map.get("clusters");
        }

        

        /**
         * The number of clusters to create. The algorithm will create at most the specified
number of clusters.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#clusterCount 
         */
        public AttributeBuilder clusterCount(int value)
        {
            map.put("BisectingKMeansClusteringAlgorithm.clusterCount", value);
            return this;
        }

        

        

        /**
         * The number of clusters to create. The algorithm will create at most the specified
number of clusters.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#clusterCount 
         */
        public AttributeBuilder clusterCount(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("BisectingKMeansClusteringAlgorithm.clusterCount", value);
            return this;
        }

        

        

        /**
         * The maximum number of k-means iterations to perform.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#maxIterations 
         */
        public AttributeBuilder maxIterations(int value)
        {
            map.put("BisectingKMeansClusteringAlgorithm.maxIterations", value);
            return this;
        }

        

        

        /**
         * The maximum number of k-means iterations to perform.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#maxIterations 
         */
        public AttributeBuilder maxIterations(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("BisectingKMeansClusteringAlgorithm.maxIterations", value);
            return this;
        }

        

        

        /**
         * Use dimensionality reduction. If <code>true</code>, k-means will be applied on the
dimensionality-reduced term-document matrix with the number of dimensions being
equal to twice the number of requested clusters. If the number of dimensions is 
lower than the number of input documents, reduction will not be performed.
If <code>false</code>, the k-means will
be performed directly on the original term-document matrix.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#useDimensionalityReduction 
         */
        public AttributeBuilder useDimensionalityReduction(boolean value)
        {
            map.put("BisectingKMeansClusteringAlgorithm.useDimensionalityReduction", value);
            return this;
        }

        

        

        /**
         * Use dimensionality reduction. If <code>true</code>, k-means will be applied on the
dimensionality-reduced term-document matrix with the number of dimensions being
equal to twice the number of requested clusters. If the number of dimensions is 
lower than the number of input documents, reduction will not be performed.
If <code>false</code>, the k-means will
be performed directly on the original term-document matrix.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#useDimensionalityReduction 
         */
        public AttributeBuilder useDimensionalityReduction(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("BisectingKMeansClusteringAlgorithm.useDimensionalityReduction", value);
            return this;
        }

        

        

        /**
         * Partition count. The number of partitions to create at each k-means clustering
iteration.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#partitionCount 
         */
        public AttributeBuilder partitionCount(int value)
        {
            map.put("BisectingKMeansClusteringAlgorithm.partitionCount", value);
            return this;
        }

        

        

        /**
         * Partition count. The number of partitions to create at each k-means clustering
iteration.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#partitionCount 
         */
        public AttributeBuilder partitionCount(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("BisectingKMeansClusteringAlgorithm.partitionCount", value);
            return this;
        }

        

        

        /**
         * Label count. The minimum number of labels to return for each cluster.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#labelCount 
         */
        public AttributeBuilder labelCount(int value)
        {
            map.put("BisectingKMeansClusteringAlgorithm.labelCount", value);
            return this;
        }

        

        

        /**
         * Label count. The minimum number of labels to return for each cluster.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#labelCount 
         */
        public AttributeBuilder labelCount(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("BisectingKMeansClusteringAlgorithm.labelCount", value);
            return this;
        }

        

        

        /**
         * Common preprocessing tasks handler.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#preprocessingPipeline 
         */
        public AttributeBuilder preprocessingPipeline(org.carrot2.text.preprocessing.pipeline.IPreprocessingPipeline value)
        {
            map.put("BisectingKMeansClusteringAlgorithm.preprocessingPipeline", value);
            return this;
        }

        

        /**
         * Common preprocessing tasks handler.
         *
         * A class that extends org.carrot2.text.preprocessing.pipeline.IPreprocessingPipeline or appropriate IObjectFactory.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#preprocessingPipeline
         */
        public AttributeBuilder preprocessingPipeline(Class<?> clazz)
        {
            map.put("BisectingKMeansClusteringAlgorithm.preprocessingPipeline", clazz);
            return this;
        }

        

        /**
         * Common preprocessing tasks handler.
         * 
         * @see org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm#preprocessingPipeline 
         */
        public AttributeBuilder preprocessingPipeline(IObjectFactory<? extends org.carrot2.text.preprocessing.pipeline.IPreprocessingPipeline> value)
        {
            map.put("BisectingKMeansClusteringAlgorithm.preprocessingPipeline", value);
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
