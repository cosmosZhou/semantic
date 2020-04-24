

// APT-generated file.

package org.carrot2.clustering.lingo;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.clustering.lingo.ClusterBuilder} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.clustering.lingo.ClusterBuilder
 */
public final class ClusterBuilderDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.clustering.lingo.ClusterBuilder";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "LingoClusteringAlgorithm";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Builds cluster labels based on the reduced term-document matrix and assigns documents to the labels";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "";

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
        ownAttrs.add(attributes.phraseLabelBoost);
        ownAttrs.add(attributes.phraseLengthPenaltyStart);
        ownAttrs.add(attributes.phraseLengthPenaltyStop);
        ownAttrs.add(attributes.clusterMergingThreshold);
        ownAttrs.add(attributes.labelAssigner);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.clustering.lingo.ClusterBuilderDescriptor.attributes.phraseLabelBoost);
        allAttrs.add(org.carrot2.clustering.lingo.ClusterBuilderDescriptor.attributes.phraseLengthPenaltyStart);
        allAttrs.add(org.carrot2.clustering.lingo.ClusterBuilderDescriptor.attributes.phraseLengthPenaltyStop);
        allAttrs.add(org.carrot2.clustering.lingo.ClusterBuilderDescriptor.attributes.clusterMergingThreshold);
        allAttrs.add(org.carrot2.clustering.lingo.ClusterBuilderDescriptor.attributes.labelAssigner);

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
     * Constants for all attribute keys of the {@link org.carrot2.clustering.lingo.ClusterBuilder} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.clustering.lingo.ClusterBuilder#phraseLabelBoost}. */
        public static final String PHRASE_LABEL_BOOST = "LingoClusteringAlgorithm.phraseLabelBoost";
        /** Attribute key for: {@link org.carrot2.clustering.lingo.ClusterBuilder#phraseLengthPenaltyStart}. */
        public static final String PHRASE_LENGTH_PENALTY_START = "LingoClusteringAlgorithm.phraseLengthPenaltyStart";
        /** Attribute key for: {@link org.carrot2.clustering.lingo.ClusterBuilder#phraseLengthPenaltyStop}. */
        public static final String PHRASE_LENGTH_PENALTY_STOP = "LingoClusteringAlgorithm.phraseLengthPenaltyStop";
        /** Attribute key for: {@link org.carrot2.clustering.lingo.ClusterBuilder#clusterMergingThreshold}. */
        public static final String CLUSTER_MERGING_THRESHOLD = "LingoClusteringAlgorithm.clusterMergingThreshold";
        /** Attribute key for: {@link org.carrot2.clustering.lingo.ClusterBuilder#labelAssigner}. */
        public static final String LABEL_ASSIGNER = "LingoClusteringAlgorithm.labelAssigner";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.clustering.lingo.ClusterBuilder} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo phraseLabelBoost = 
            new AttributeInfo(
                "LingoClusteringAlgorithm.phraseLabelBoost",
                "org.carrot2.clustering.lingo.ClusterBuilder",
                "phraseLabelBoost",
"Phrase label boost. The weight of multi-word labels relative to one-word labels.\nLow values will result in more one-word labels being produced, higher values will\nfavor multi-word labels.",
"Phrase label boost",
"Phrase label boost",
"The weight of multi-word labels relative to one-word labels. Low values will result in more one-word labels being produced, higher values will favor multi-word labels.",
"Labels",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo phraseLengthPenaltyStart = 
            new AttributeInfo(
                "LingoClusteringAlgorithm.phraseLengthPenaltyStart",
                "org.carrot2.clustering.lingo.ClusterBuilder",
                "phraseLengthPenaltyStart",
"Phrase length penalty start. The phrase length at which the overlong multi-word\nlabels should start to be penalized. Phrases of length smaller than\n<code>phraseLengthPenaltyStart</code> will not be penalized.",
"Phrase length penalty start",
"Phrase length penalty start",
"The phrase length at which the overlong multi-word labels should start to be penalized. Phrases of length smaller than <code>phraseLengthPenaltyStart</code> will not be penalized.",
"Labels",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo phraseLengthPenaltyStop = 
            new AttributeInfo(
                "LingoClusteringAlgorithm.phraseLengthPenaltyStop",
                "org.carrot2.clustering.lingo.ClusterBuilder",
                "phraseLengthPenaltyStop",
"Phrase length penalty stop. The phrase length at which the overlong multi-word\nlabels should be removed completely. Phrases of length larger than\n<code>phraseLengthPenaltyStop</code> will be removed.",
"Phrase length penalty stop",
"Phrase length penalty stop",
"The phrase length at which the overlong multi-word labels should be removed completely. Phrases of length larger than <code>phraseLengthPenaltyStop</code> will be removed.",
"Labels",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo clusterMergingThreshold = 
            new AttributeInfo(
                "LingoClusteringAlgorithm.clusterMergingThreshold",
                "org.carrot2.clustering.lingo.ClusterBuilder",
                "clusterMergingThreshold",
"Cluster merging threshold. The percentage overlap between two cluster's documents\nrequired for the clusters to be merged into one clusters. Low values will result in\nmore aggressive merging, which may lead to irrelevant documents in clusters. High\nvalues will result in fewer clusters being merged, which may lead to very similar\nor duplicated clusters.",
"Cluster merging threshold",
"Cluster merging threshold",
"The percentage overlap between two cluster's documents required for the clusters to be merged into one clusters. Low values will result in more aggressive merging, which may lead to irrelevant documents in clusters. High values will result in fewer clusters being merged, which may lead to very similar or duplicated clusters.",
"Clusters",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo labelAssigner = 
            new AttributeInfo(
                "LingoClusteringAlgorithm.labelAssigner",
                "org.carrot2.clustering.lingo.ClusterBuilder",
                "labelAssigner",
"Cluster label assignment method.",
"Cluster label assignment method",
"Cluster label assignment method",
null,
"Labels",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.clustering.lingo.ClusterBuilder} component. You can use this
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
         * Phrase label boost. The weight of multi-word labels relative to one-word labels.
Low values will result in more one-word labels being produced, higher values will
favor multi-word labels.
         * 
         * @see org.carrot2.clustering.lingo.ClusterBuilder#phraseLabelBoost 
         */
        public AttributeBuilder phraseLabelBoost(double value)
        {
            map.put("LingoClusteringAlgorithm.phraseLabelBoost", value);
            return this;
        }

        

        

        /**
         * Phrase label boost. The weight of multi-word labels relative to one-word labels.
Low values will result in more one-word labels being produced, higher values will
favor multi-word labels.
         * 
         * @see org.carrot2.clustering.lingo.ClusterBuilder#phraseLabelBoost 
         */
        public AttributeBuilder phraseLabelBoost(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("LingoClusteringAlgorithm.phraseLabelBoost", value);
            return this;
        }

        

        

        /**
         * Phrase length penalty start. The phrase length at which the overlong multi-word
labels should start to be penalized. Phrases of length smaller than
<code>phraseLengthPenaltyStart</code> will not be penalized.
         * 
         * @see org.carrot2.clustering.lingo.ClusterBuilder#phraseLengthPenaltyStart 
         */
        public AttributeBuilder phraseLengthPenaltyStart(int value)
        {
            map.put("LingoClusteringAlgorithm.phraseLengthPenaltyStart", value);
            return this;
        }

        

        

        /**
         * Phrase length penalty start. The phrase length at which the overlong multi-word
labels should start to be penalized. Phrases of length smaller than
<code>phraseLengthPenaltyStart</code> will not be penalized.
         * 
         * @see org.carrot2.clustering.lingo.ClusterBuilder#phraseLengthPenaltyStart 
         */
        public AttributeBuilder phraseLengthPenaltyStart(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("LingoClusteringAlgorithm.phraseLengthPenaltyStart", value);
            return this;
        }

        

        

        /**
         * Phrase length penalty stop. The phrase length at which the overlong multi-word
labels should be removed completely. Phrases of length larger than
<code>phraseLengthPenaltyStop</code> will be removed.
         * 
         * @see org.carrot2.clustering.lingo.ClusterBuilder#phraseLengthPenaltyStop 
         */
        public AttributeBuilder phraseLengthPenaltyStop(int value)
        {
            map.put("LingoClusteringAlgorithm.phraseLengthPenaltyStop", value);
            return this;
        }

        

        

        /**
         * Phrase length penalty stop. The phrase length at which the overlong multi-word
labels should be removed completely. Phrases of length larger than
<code>phraseLengthPenaltyStop</code> will be removed.
         * 
         * @see org.carrot2.clustering.lingo.ClusterBuilder#phraseLengthPenaltyStop 
         */
        public AttributeBuilder phraseLengthPenaltyStop(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("LingoClusteringAlgorithm.phraseLengthPenaltyStop", value);
            return this;
        }

        

        

        /**
         * Cluster merging threshold. The percentage overlap between two cluster's documents
required for the clusters to be merged into one clusters. Low values will result in
more aggressive merging, which may lead to irrelevant documents in clusters. High
values will result in fewer clusters being merged, which may lead to very similar
or duplicated clusters.
         * 
         * @see org.carrot2.clustering.lingo.ClusterBuilder#clusterMergingThreshold 
         */
        public AttributeBuilder clusterMergingThreshold(double value)
        {
            map.put("LingoClusteringAlgorithm.clusterMergingThreshold", value);
            return this;
        }

        

        

        /**
         * Cluster merging threshold. The percentage overlap between two cluster's documents
required for the clusters to be merged into one clusters. Low values will result in
more aggressive merging, which may lead to irrelevant documents in clusters. High
values will result in fewer clusters being merged, which may lead to very similar
or duplicated clusters.
         * 
         * @see org.carrot2.clustering.lingo.ClusterBuilder#clusterMergingThreshold 
         */
        public AttributeBuilder clusterMergingThreshold(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("LingoClusteringAlgorithm.clusterMergingThreshold", value);
            return this;
        }

        

        

        /**
         * Cluster label assignment method.
         * 
         * @see org.carrot2.clustering.lingo.ClusterBuilder#labelAssigner 
         */
        public AttributeBuilder labelAssigner(org.carrot2.clustering.lingo.ILabelAssigner value)
        {
            map.put("LingoClusteringAlgorithm.labelAssigner", value);
            return this;
        }

        

        /**
         * Cluster label assignment method.
         *
         * A class that extends org.carrot2.clustering.lingo.ILabelAssigner or appropriate IObjectFactory.
         * 
         * @see org.carrot2.clustering.lingo.ClusterBuilder#labelAssigner
         */
        public AttributeBuilder labelAssigner(Class<?> clazz)
        {
            map.put("LingoClusteringAlgorithm.labelAssigner", clazz);
            return this;
        }

        

        /**
         * Cluster label assignment method.
         * 
         * @see org.carrot2.clustering.lingo.ClusterBuilder#labelAssigner 
         */
        public AttributeBuilder labelAssigner(IObjectFactory<? extends org.carrot2.clustering.lingo.ILabelAssigner> value)
        {
            map.put("LingoClusteringAlgorithm.labelAssigner", value);
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
