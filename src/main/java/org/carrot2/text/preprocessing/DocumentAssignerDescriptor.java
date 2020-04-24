

// APT-generated file.

package org.carrot2.text.preprocessing;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.text.preprocessing.DocumentAssigner} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.text.preprocessing.DocumentAssigner
 */
public final class DocumentAssignerDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.text.preprocessing.DocumentAssigner";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "DocumentAssigner";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Assigns document to label candidates";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "For each label candidate from <code>AllLabels.featureIndex</code> an <code>BitSet</code> with the assigned documents is constructed. The assignment algorithm is rather simple: in order to be assigned to a label, a document must contain at least one occurrence of each non-stop word from the label. <p> This class saves the following results to the <code>PreprocessingContext</code> : <ul> <li><code>AllLabels.documentIndices</code></li> </ul> <p> This class requires that <code>Tokenizer</code>, <code>CaseNormalizer</code>, <code>StopListMarker</code>, <code>PhraseExtractor</code> and <code>LabelFilterProcessor</code> be invoked first.";

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
        ownAttrs.add(attributes.exactPhraseAssignment);
        ownAttrs.add(attributes.minClusterSize);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.text.preprocessing.DocumentAssignerDescriptor.attributes.exactPhraseAssignment);
        allAttrs.add(org.carrot2.text.preprocessing.DocumentAssignerDescriptor.attributes.minClusterSize);

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
     * Constants for all attribute keys of the {@link org.carrot2.text.preprocessing.DocumentAssigner} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.text.preprocessing.DocumentAssigner#exactPhraseAssignment}. */
        public static final String EXACT_PHRASE_ASSIGNMENT = "DocumentAssigner.exactPhraseAssignment";
        /** Attribute key for: {@link org.carrot2.text.preprocessing.DocumentAssigner#minClusterSize}. */
        public static final String MIN_CLUSTER_SIZE = "DocumentAssigner.minClusterSize";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.text.preprocessing.DocumentAssigner} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo exactPhraseAssignment = 
            new AttributeInfo(
                "DocumentAssigner.exactPhraseAssignment",
                "org.carrot2.text.preprocessing.DocumentAssigner",
                "exactPhraseAssignment",
"Only exact phrase assignments. Assign only documents that contain the label in its\noriginal form, including the order of words. Enabling this option will cause less\ndocuments to be put in clusters, which result in higher precision of assignment,\nbut also a larger \"Other Topics\" group. Disabling this option will cause more\ndocuments to be put in clusters, which will make the \"Other Topics\" cluster\nsmaller, but also lower the precision of cluster-document assignments.",
"Exact phrase assignment",
"Only exact phrase assignments",
"Assign only documents that contain the label in its original form, including the order of words. Enabling this option will cause less documents to be put in clusters, which result in higher precision of assignment, but also a larger \"Other Topics\" group. Disabling this option will cause more documents to be put in clusters, which will make the \"Other Topics\" cluster smaller, but also lower the precision of cluster-document assignments.",
"Preprocessing",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo minClusterSize = 
            new AttributeInfo(
                "DocumentAssigner.minClusterSize",
                "org.carrot2.text.preprocessing.DocumentAssigner",
                "minClusterSize",
"Determines the minimum number of documents in each cluster.",
"Minimum cluster size",
"Determines the minimum number of documents in each cluster",
null,
"Preprocessing",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.text.preprocessing.DocumentAssigner} component. You can use this
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
         * Only exact phrase assignments. Assign only documents that contain the label in its
original form, including the order of words. Enabling this option will cause less
documents to be put in clusters, which result in higher precision of assignment,
but also a larger "Other Topics" group. Disabling this option will cause more
documents to be put in clusters, which will make the "Other Topics" cluster
smaller, but also lower the precision of cluster-document assignments.
         * 
         * @see org.carrot2.text.preprocessing.DocumentAssigner#exactPhraseAssignment 
         */
        public AttributeBuilder exactPhraseAssignment(boolean value)
        {
            map.put("DocumentAssigner.exactPhraseAssignment", value);
            return this;
        }

        

        

        /**
         * Only exact phrase assignments. Assign only documents that contain the label in its
original form, including the order of words. Enabling this option will cause less
documents to be put in clusters, which result in higher precision of assignment,
but also a larger "Other Topics" group. Disabling this option will cause more
documents to be put in clusters, which will make the "Other Topics" cluster
smaller, but also lower the precision of cluster-document assignments.
         * 
         * @see org.carrot2.text.preprocessing.DocumentAssigner#exactPhraseAssignment 
         */
        public AttributeBuilder exactPhraseAssignment(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("DocumentAssigner.exactPhraseAssignment", value);
            return this;
        }

        

        

        /**
         * Determines the minimum number of documents in each cluster.
         * 
         * @see org.carrot2.text.preprocessing.DocumentAssigner#minClusterSize 
         */
        public AttributeBuilder minClusterSize(int value)
        {
            map.put("DocumentAssigner.minClusterSize", value);
            return this;
        }

        

        

        /**
         * Determines the minimum number of documents in each cluster.
         * 
         * @see org.carrot2.text.preprocessing.DocumentAssigner#minClusterSize 
         */
        public AttributeBuilder minClusterSize(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("DocumentAssigner.minClusterSize", value);
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
