

// APT-generated file.

package org.carrot2.clustering.synthetic;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm
 */
public final class ByFieldClusteringAlgorithmDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "ByAttributeClusteringAlgorithm";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Clusters documents into a flat structure based on the values of some field of the documents";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "By Attribute Clustering";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "By default the <code>Document.SOURCES</code> field is used.";

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
        ownAttrs.add(attributes.fieldName);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithmDescriptor.attributes.documents);
        allAttrs.add(org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithmDescriptor.attributes.clusters);
        allAttrs.add(org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithmDescriptor.attributes.fieldName);

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
     * Constants for all attribute keys of the {@link org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm#documents}. */
        public static final String DOCUMENTS = "documents";
        /** Attribute key for: {@link org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm#clusters}. */
        public static final String CLUSTERS = "clusters";
        /** Attribute key for: {@link org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm#fieldName}. */
        public static final String FIELD_NAME = "ByAttributeClusteringAlgorithm.fieldName";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm} component.
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
                "org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm",
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
                "org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm",
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
        public final AttributeInfo fieldName = 
            new AttributeInfo(
                "ByAttributeClusteringAlgorithm.fieldName",
                "org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm",
                "fieldName",
"Name of the field to cluster by. Each non-null scalar field value with distinct\nhash code will give rise to a single cluster, named using the\nvalue returned by {@link org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm#buildClusterLabel(Object)}. If the field value is a collection,\nthe document will be assigned to all clusters corresponding to the values in the\ncollection. Note that arrays will not be 'unfolded' in this way.",
"Field name",
"Name of the field to cluster by",
"Each non-null scalar field value with distinct hash code will give rise to a single cluster, named using the value returned by <code>org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm.buildClusterLabel(Object)</code>. If the field value is a collection, the document will be assigned to all clusters corresponding to the values in the collection. Note that arrays will not be 'unfolded' in this way.",
"Fields",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm} component. You can use this
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
         * Documents to cluster.
         * 
         * @see org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm#documents 
         */
        public AttributeBuilder documents(java.util.List<org.carrot2.core.Document> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        /**
         * Documents to cluster.
         * 
         * @see org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm#documents 
         */
        public AttributeBuilder documents(IObjectFactory<? extends java.util.List<org.carrot2.core.Document>> value)
        {
            map.put("documents", value);
            return this;
        }

        

        

        

        

        

        /**
         * Clusters created by the algorithm.
         * 
         * @see org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm#clusters 
         */
@SuppressWarnings("unchecked")        public java.util.List<org.carrot2.core.Cluster> clusters()
        {
            return (java.util.List<org.carrot2.core.Cluster>) map.get("clusters");
        }

        

        /**
         * Name of the field to cluster by. Each non-null scalar field value with distinct
hash code will give rise to a single cluster, named using the
value returned by {@link org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm#buildClusterLabel(Object)}. If the field value is a collection,
the document will be assigned to all clusters corresponding to the values in the
collection. Note that arrays will not be 'unfolded' in this way.
         * 
         * @see org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm#fieldName 
         */
        public AttributeBuilder fieldName(java.lang.String value)
        {
            map.put("ByAttributeClusteringAlgorithm.fieldName", value);
            return this;
        }

        

        

        /**
         * Name of the field to cluster by. Each non-null scalar field value with distinct
hash code will give rise to a single cluster, named using the
value returned by {@link org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm#buildClusterLabel(Object)}. If the field value is a collection,
the document will be assigned to all clusters corresponding to the values in the
collection. Note that arrays will not be 'unfolded' in this way.
         * 
         * @see org.carrot2.clustering.synthetic.ByFieldClusteringAlgorithm#fieldName 
         */
        public AttributeBuilder fieldName(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("ByAttributeClusteringAlgorithm.fieldName", value);
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