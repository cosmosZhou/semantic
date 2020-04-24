

// APT-generated file.

package org.carrot2.output.metrics;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.output.metrics.PrecisionRecallMetric} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.output.metrics.PrecisionRecallMetric
 */
public final class PrecisionRecallMetricDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.output.metrics.PrecisionRecallMetric";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Computes precision, recall and F-metric for all partitions against the provided clusters";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "<p> Metrics will be calculated only if all input documents have non-blank <code>Document.PARTITIONS</code>. </p>";

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
        ownAttrs.add(attributes.weightedAveragePrecision);
        ownAttrs.add(attributes.weightedAverageRecall);
        ownAttrs.add(attributes.weightedAverageFMeasure);
        ownAttrs.add(attributes.precisionByPartition);
        ownAttrs.add(attributes.recallByPartition);
        ownAttrs.add(attributes.fMeasureByPartition);
        ownAttrs.add(attributes.enabled);
        ownAttrs.add(attributes.documents);
        ownAttrs.add(attributes.clusters);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.output.metrics.PrecisionRecallMetricDescriptor.attributes.weightedAveragePrecision);
        allAttrs.add(org.carrot2.output.metrics.PrecisionRecallMetricDescriptor.attributes.weightedAverageRecall);
        allAttrs.add(org.carrot2.output.metrics.PrecisionRecallMetricDescriptor.attributes.weightedAverageFMeasure);
        allAttrs.add(org.carrot2.output.metrics.PrecisionRecallMetricDescriptor.attributes.precisionByPartition);
        allAttrs.add(org.carrot2.output.metrics.PrecisionRecallMetricDescriptor.attributes.recallByPartition);
        allAttrs.add(org.carrot2.output.metrics.PrecisionRecallMetricDescriptor.attributes.fMeasureByPartition);
        allAttrs.add(org.carrot2.output.metrics.PrecisionRecallMetricDescriptor.attributes.enabled);
        allAttrs.add(org.carrot2.output.metrics.PrecisionRecallMetricDescriptor.attributes.documents);
        allAttrs.add(org.carrot2.output.metrics.PrecisionRecallMetricDescriptor.attributes.clusters);
        allAttrs.add(org.carrot2.output.metrics.IdealPartitioningBasedMetricDescriptor.attributes.partitionIdFieldName);

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
     * Constants for all attribute keys of the {@link org.carrot2.output.metrics.PrecisionRecallMetric} component.
     */
    public static class Keys  extends org.carrot2.output.metrics.IdealPartitioningBasedMetricDescriptor.Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.output.metrics.PrecisionRecallMetric#weightedAveragePrecision}. */
        public static final String WEIGHTED_AVERAGE_PRECISION = "org.carrot2.output.metrics.PrecisionRecallMetric.weightedAveragePrecision";
        /** Attribute key for: {@link org.carrot2.output.metrics.PrecisionRecallMetric#weightedAverageRecall}. */
        public static final String WEIGHTED_AVERAGE_RECALL = "org.carrot2.output.metrics.PrecisionRecallMetric.weightedAverageRecall";
        /** Attribute key for: {@link org.carrot2.output.metrics.PrecisionRecallMetric#weightedAverageFMeasure}. */
        public static final String WEIGHTED_AVERAGE_F_MEASURE = "org.carrot2.output.metrics.PrecisionRecallMetric.weightedAverageFMeasure";
        /** Attribute key for: {@link org.carrot2.output.metrics.PrecisionRecallMetric#precisionByPartition}. */
        public static final String PRECISION_BY_PARTITION = "org.carrot2.output.metrics.PrecisionRecallMetric.precisionByPartition";
        /** Attribute key for: {@link org.carrot2.output.metrics.PrecisionRecallMetric#recallByPartition}. */
        public static final String RECALL_BY_PARTITION = "org.carrot2.output.metrics.PrecisionRecallMetric.recallByPartition";
        /** Attribute key for: {@link org.carrot2.output.metrics.PrecisionRecallMetric#fMeasureByPartition}. */
        public static final String F_MEASURE_BY_PARTITION = "org.carrot2.output.metrics.PrecisionRecallMetric.fMeasureByPartition";
        /** Attribute key for: {@link org.carrot2.output.metrics.PrecisionRecallMetric#enabled}. */
        public static final String ENABLED = "org.carrot2.output.metrics.PrecisionRecallMetric.enabled";
        /** Attribute key for: {@link org.carrot2.output.metrics.PrecisionRecallMetric#documents}. */
        public static final String DOCUMENTS = "documents";
        /** Attribute key for: {@link org.carrot2.output.metrics.PrecisionRecallMetric#clusters}. */
        public static final String CLUSTERS = "clusters";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.output.metrics.PrecisionRecallMetric} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo weightedAveragePrecision = 
            new AttributeInfo(
                "org.carrot2.output.metrics.PrecisionRecallMetric.weightedAveragePrecision",
                "org.carrot2.output.metrics.PrecisionRecallMetric",
                "weightedAveragePrecision",
"Average precision of the whole cluster set, weighted by cluster size.",
null,
"Average precision of the whole cluster set, weighted by cluster size",
null,
null,
null,
null
            );

        /**
         *          */
        public final AttributeInfo weightedAverageRecall = 
            new AttributeInfo(
                "org.carrot2.output.metrics.PrecisionRecallMetric.weightedAverageRecall",
                "org.carrot2.output.metrics.PrecisionRecallMetric",
                "weightedAverageRecall",
"Average recall of the whole cluster set, weighted by cluster size.",
null,
"Average recall of the whole cluster set, weighted by cluster size",
null,
null,
null,
null
            );

        /**
         *          */
        public final AttributeInfo weightedAverageFMeasure = 
            new AttributeInfo(
                "org.carrot2.output.metrics.PrecisionRecallMetric.weightedAverageFMeasure",
                "org.carrot2.output.metrics.PrecisionRecallMetric",
                "weightedAverageFMeasure",
"Average F-measure of the whole cluster set, weighted by cluster size.",
null,
"Average F-measure of the whole cluster set, weighted by cluster size",
null,
null,
null,
null
            );

        /**
         *          */
        public final AttributeInfo precisionByPartition = 
            new AttributeInfo(
                "org.carrot2.output.metrics.PrecisionRecallMetric.precisionByPartition",
                "org.carrot2.output.metrics.PrecisionRecallMetric",
                "precisionByPartition",
"Precision by partition.",
null,
"Precision by partition",
null,
null,
null,
null
            );

        /**
         *          */
        public final AttributeInfo recallByPartition = 
            new AttributeInfo(
                "org.carrot2.output.metrics.PrecisionRecallMetric.recallByPartition",
                "org.carrot2.output.metrics.PrecisionRecallMetric",
                "recallByPartition",
"Recall by partition.",
null,
"Recall by partition",
null,
null,
null,
null
            );

        /**
         *          */
        public final AttributeInfo fMeasureByPartition = 
            new AttributeInfo(
                "org.carrot2.output.metrics.PrecisionRecallMetric.fMeasureByPartition",
                "org.carrot2.output.metrics.PrecisionRecallMetric",
                "fMeasureByPartition",
"F-measure by partition.",
null,
"F-measure by partition",
null,
null,
null,
null
            );

        /**
         *          */
        public final AttributeInfo enabled = 
            new AttributeInfo(
                "org.carrot2.output.metrics.PrecisionRecallMetric.enabled",
                "org.carrot2.output.metrics.PrecisionRecallMetric",
                "enabled",
"Calculate F-measure.",
null,
"Calculate F-measure",
null,
null,
null,
null
            );

        /**
         *          */
        public final AttributeInfo documents = 
            new AttributeInfo(
                "documents",
                "org.carrot2.output.metrics.PrecisionRecallMetric",
                "documents",
null,
null,
null,
null,
null,
null,
null
            );

        /**
         *          */
        public final AttributeInfo clusters = 
            new AttributeInfo(
                "clusters",
                "org.carrot2.output.metrics.PrecisionRecallMetric",
                "clusters",
null,
null,
null,
null,
null,
null,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.output.metrics.PrecisionRecallMetric} component. You can use this
     * builder as a type-safe alternative to populating the attribute map using attribute keys.
     */
    public static class AttributeBuilder  extends org.carrot2.output.metrics.IdealPartitioningBasedMetricDescriptor.AttributeBuilder 
    {
        /** The attribute map populated by this builder. */
        public final Map<String, Object> map;

        /**
         * Creates a builder backed by the provided map.
         */
        protected AttributeBuilder(Map<String, Object> map)
        {
 super(map); 
            this.map = map;
        }


        

        

        

        

        /**
         * Average precision of the whole cluster set, weighted by cluster size.
         * 
         * @see org.carrot2.output.metrics.PrecisionRecallMetric#weightedAveragePrecision 
         */
        public java.lang.Double weightedAveragePrecision()
        {
            return (java.lang.Double) map.get("org.carrot2.output.metrics.PrecisionRecallMetric.weightedAveragePrecision");
        }

        

        

        

        

        /**
         * Average recall of the whole cluster set, weighted by cluster size.
         * 
         * @see org.carrot2.output.metrics.PrecisionRecallMetric#weightedAverageRecall 
         */
        public java.lang.Double weightedAverageRecall()
        {
            return (java.lang.Double) map.get("org.carrot2.output.metrics.PrecisionRecallMetric.weightedAverageRecall");
        }

        

        

        

        

        /**
         * Average F-measure of the whole cluster set, weighted by cluster size.
         * 
         * @see org.carrot2.output.metrics.PrecisionRecallMetric#weightedAverageFMeasure 
         */
        public java.lang.Double weightedAverageFMeasure()
        {
            return (java.lang.Double) map.get("org.carrot2.output.metrics.PrecisionRecallMetric.weightedAverageFMeasure");
        }

        

        

        

        

        /**
         * Precision by partition.
         * 
         * @see org.carrot2.output.metrics.PrecisionRecallMetric#precisionByPartition 
         */
@SuppressWarnings("unchecked")        public java.util.Map<java.lang.Object,java.lang.Double> precisionByPartition()
        {
            return (java.util.Map<java.lang.Object,java.lang.Double>) map.get("org.carrot2.output.metrics.PrecisionRecallMetric.precisionByPartition");
        }

        

        

        

        

        /**
         * Recall by partition.
         * 
         * @see org.carrot2.output.metrics.PrecisionRecallMetric#recallByPartition 
         */
@SuppressWarnings("unchecked")        public java.util.Map<java.lang.Object,java.lang.Double> recallByPartition()
        {
            return (java.util.Map<java.lang.Object,java.lang.Double>) map.get("org.carrot2.output.metrics.PrecisionRecallMetric.recallByPartition");
        }

        

        

        

        

        /**
         * F-measure by partition.
         * 
         * @see org.carrot2.output.metrics.PrecisionRecallMetric#fMeasureByPartition 
         */
@SuppressWarnings("unchecked")        public java.util.Map<java.lang.Object,java.lang.Double> fMeasureByPartition()
        {
            return (java.util.Map<java.lang.Object,java.lang.Double>) map.get("org.carrot2.output.metrics.PrecisionRecallMetric.fMeasureByPartition");
        }

        

        /**
         * Calculate F-measure.
         * 
         * @see org.carrot2.output.metrics.PrecisionRecallMetric#enabled 
         */
        public AttributeBuilder enabled(boolean value)
        {
            map.put("org.carrot2.output.metrics.PrecisionRecallMetric.enabled", value);
            return this;
        }

        

        

        /**
         * Calculate F-measure.
         * 
         * @see org.carrot2.output.metrics.PrecisionRecallMetric#enabled 
         */
        public AttributeBuilder enabled(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("org.carrot2.output.metrics.PrecisionRecallMetric.enabled", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.output.metrics.PrecisionRecallMetric#documents 
         */
        public AttributeBuilder documents(java.util.List<org.carrot2.core.Document> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.output.metrics.PrecisionRecallMetric#documents 
         */
        public AttributeBuilder documents(IObjectFactory<? extends java.util.List<org.carrot2.core.Document>> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.output.metrics.PrecisionRecallMetric#clusters 
         */
        public AttributeBuilder clusters(java.util.List<org.carrot2.core.Cluster> value)
        {
            map.put("clusters", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.output.metrics.PrecisionRecallMetric#clusters 
         */
        public AttributeBuilder clusters(IObjectFactory<? extends java.util.List<org.carrot2.core.Cluster>> value)
        {
            map.put("clusters", value);
            return this;
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
