

// APT-generated file.

package org.carrot2.output.metrics;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.output.metrics.ContaminationMetric} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.output.metrics.ContaminationMetric
 */
public final class ContaminationMetricDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.output.metrics.ContaminationMetric";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Computes cluster contamination";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "If a cluster groups documents found in the same <code>Document.PARTITIONS</code>, its contamination is 0. If a cluster groups an equally distributed mix of all partitions, its contamination is 1.0. For a full definition, please see section 4.4.1 of <a href=\"http://project.carrot2.org/publications/osinski04-dimensionality.pdf\">this work</a>. <p> Contamination is calculated for top-level clusters only, taking into account documents from the cluster and all subclusters. Finally, contamination will be calculated only if all input documents have non-blank <code>Document.PARTITIONS</code>s. </p>";

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
        ownAttrs.add(attributes.weightedAverageContamination);
        ownAttrs.add(attributes.enabled);
        ownAttrs.add(attributes.documents);
        ownAttrs.add(attributes.clusters);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.output.metrics.ContaminationMetricDescriptor.attributes.weightedAverageContamination);
        allAttrs.add(org.carrot2.output.metrics.ContaminationMetricDescriptor.attributes.enabled);
        allAttrs.add(org.carrot2.output.metrics.ContaminationMetricDescriptor.attributes.documents);
        allAttrs.add(org.carrot2.output.metrics.ContaminationMetricDescriptor.attributes.clusters);
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
     * Constants for all attribute keys of the {@link org.carrot2.output.metrics.ContaminationMetric} component.
     */
    public static class Keys  extends org.carrot2.output.metrics.IdealPartitioningBasedMetricDescriptor.Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.output.metrics.ContaminationMetric#weightedAverageContamination}. */
        public static final String WEIGHTED_AVERAGE_CONTAMINATION = "org.carrot2.output.metrics.ContaminationMetric.weightedAverageContamination";
        /** Attribute key for: {@link org.carrot2.output.metrics.ContaminationMetric#enabled}. */
        public static final String ENABLED = "org.carrot2.output.metrics.ContaminationMetric.enabled";
        /** Attribute key for: {@link org.carrot2.output.metrics.ContaminationMetric#documents}. */
        public static final String DOCUMENTS = "documents";
        /** Attribute key for: {@link org.carrot2.output.metrics.ContaminationMetric#clusters}. */
        public static final String CLUSTERS = "clusters";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.output.metrics.ContaminationMetric} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo weightedAverageContamination = 
            new AttributeInfo(
                "org.carrot2.output.metrics.ContaminationMetric.weightedAverageContamination",
                "org.carrot2.output.metrics.ContaminationMetric",
                "weightedAverageContamination",
"Average contamination of the whole cluster set, weighted by the size of cluster.",
null,
"Average contamination of the whole cluster set, weighted by the size of cluster",
null,
null,
null,
null
            );

        /**
         *          */
        public final AttributeInfo enabled = 
            new AttributeInfo(
                "org.carrot2.output.metrics.ContaminationMetric.enabled",
                "org.carrot2.output.metrics.ContaminationMetric",
                "enabled",
"Calculate contamination metric.",
null,
"Calculate contamination metric",
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
                "org.carrot2.output.metrics.ContaminationMetric",
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
                "org.carrot2.output.metrics.ContaminationMetric",
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
     * Attribute map builder for the  {@link org.carrot2.output.metrics.ContaminationMetric} component. You can use this
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
         * Average contamination of the whole cluster set, weighted by the size of cluster.
         * 
         * @see org.carrot2.output.metrics.ContaminationMetric#weightedAverageContamination 
         */
        public double weightedAverageContamination()
        {
            return (java.lang.Double) map.get("org.carrot2.output.metrics.ContaminationMetric.weightedAverageContamination");
        }

        

        /**
         * Calculate contamination metric.
         * 
         * @see org.carrot2.output.metrics.ContaminationMetric#enabled 
         */
        public AttributeBuilder enabled(boolean value)
        {
            map.put("org.carrot2.output.metrics.ContaminationMetric.enabled", value);
            return this;
        }

        

        

        /**
         * Calculate contamination metric.
         * 
         * @see org.carrot2.output.metrics.ContaminationMetric#enabled 
         */
        public AttributeBuilder enabled(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("org.carrot2.output.metrics.ContaminationMetric.enabled", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.output.metrics.ContaminationMetric#documents 
         */
        public AttributeBuilder documents(java.util.List<org.carrot2.core.Document> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.output.metrics.ContaminationMetric#documents 
         */
        public AttributeBuilder documents(IObjectFactory<? extends java.util.List<org.carrot2.core.Document>> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.output.metrics.ContaminationMetric#clusters 
         */
        public AttributeBuilder clusters(java.util.List<org.carrot2.core.Cluster> value)
        {
            map.put("clusters", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.output.metrics.ContaminationMetric#clusters 
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
