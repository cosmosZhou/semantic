

// APT-generated file.

package org.carrot2.source.ambient;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.ambient.FubDocumentSource} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.ambient.FubDocumentSource
 */
public final class FubDocumentSourceDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.ambient.FubDocumentSource";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "FubDocumentSource";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "A base document source for test collections developed at Fondazione Ugo Bordoni";
    
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
        ownAttrs.add(attributes.documents);
        ownAttrs.add(attributes.topicIds);
        ownAttrs.add(attributes.query);
        ownAttrs.add(attributes.minTopicSize);
        ownAttrs.add(attributes.includeDocumentsWithoutTopic);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.ambient.FubDocumentSourceDescriptor.attributes.documents);
        allAttrs.add(org.carrot2.source.ambient.FubDocumentSourceDescriptor.attributes.topicIds);
        allAttrs.add(org.carrot2.source.ambient.FubDocumentSourceDescriptor.attributes.query);
        allAttrs.add(org.carrot2.source.ambient.FubDocumentSourceDescriptor.attributes.minTopicSize);
        allAttrs.add(org.carrot2.source.ambient.FubDocumentSourceDescriptor.attributes.includeDocumentsWithoutTopic);

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
     * Constants for all attribute keys of the {@link org.carrot2.source.ambient.FubDocumentSource} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.ambient.FubDocumentSource#documents}. */
        public static final String DOCUMENTS = "documents";
        /** Attribute key for: {@link org.carrot2.source.ambient.FubDocumentSource#topicIds}. */
        public static final String TOPIC_IDS = "FubDocumentSource.topicIds";
        /** Attribute key for: {@link org.carrot2.source.ambient.FubDocumentSource#query}. */
        public static final String QUERY = "query";
        /** Attribute key for: {@link org.carrot2.source.ambient.FubDocumentSource#minTopicSize}. */
        public static final String MIN_TOPIC_SIZE = "FubDocumentSource.minTopicSize";
        /** Attribute key for: {@link org.carrot2.source.ambient.FubDocumentSource#includeDocumentsWithoutTopic}. */
        public static final String INCLUDE_DOCUMENTS_WITHOUT_TOPIC = "FubDocumentSource.includeDocumentsWithoutTopic";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.ambient.FubDocumentSource} component.
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
                "org.carrot2.source.ambient.FubDocumentSource",
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
         *          */
        public final AttributeInfo topicIds = 
            new AttributeInfo(
                "FubDocumentSource.topicIds",
                "org.carrot2.source.ambient.FubDocumentSource",
                "topicIds",
"Topics and subtopics covered in the output documents. The set is computed for the\noutput {@link org.carrot2.source.ambient.FubDocumentSource#documents} and it may vary for the same main topic based e.g. on the\nrequested number of requested results or {@link org.carrot2.source.ambient.FubDocumentSource#minTopicSize}.",
null,
"Topics and subtopics covered in the output documents",
"The set is computed for the output <code>org.carrot2.source.ambient.FubDocumentSource.documents</code> and it may vary for the same main topic based e.g. on the requested number of requested results or <code>org.carrot2.source.ambient.FubDocumentSource.minTopicSize</code>.",
"Topic ID",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#query
         */
        public final AttributeInfo query = 
            new AttributeInfo(
                "query",
                "org.carrot2.source.ambient.FubDocumentSource",
                "query",
null,
null,
null,
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.query
            );

        /**
         *          */
        public final AttributeInfo minTopicSize = 
            new AttributeInfo(
                "FubDocumentSource.minTopicSize",
                "org.carrot2.source.ambient.FubDocumentSource",
                "minTopicSize",
"Minimum topic size. Documents belonging to a topic with fewer documents than\nminimum topic size will not be returned.",
null,
"Minimum topic size",
"Documents belonging to a topic with fewer documents than minimum topic size will not be returned.",
"Filtering",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo includeDocumentsWithoutTopic = 
            new AttributeInfo(
                "FubDocumentSource.includeDocumentsWithoutTopic",
                "org.carrot2.source.ambient.FubDocumentSource",
                "includeDocumentsWithoutTopic",
"Include documents without topics.",
null,
"Include documents without topics",
null,
"Filtering",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.ambient.FubDocumentSource} component. You can use this
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
         * @see org.carrot2.source.ambient.FubDocumentSource#documents 
         */
@SuppressWarnings("unchecked")        public java.util.List<org.carrot2.core.Document> documents()
        {
            return (java.util.List<org.carrot2.core.Document>) map.get("documents");
        }

        

        

        

        

        /**
         * Topics and subtopics covered in the output documents. The set is computed for the
output {@link org.carrot2.source.ambient.FubDocumentSource#documents} and it may vary for the same main topic based e.g. on the
requested number of requested results or {@link org.carrot2.source.ambient.FubDocumentSource#minTopicSize}.
         * 
         * @see org.carrot2.source.ambient.FubDocumentSource#topicIds 
         */
@SuppressWarnings("unchecked")        public java.util.Set<java.lang.Object> topicIds()
        {
            return (java.util.Set<java.lang.Object>) map.get("FubDocumentSource.topicIds");
        }

        

        

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.ambient.FubDocumentSource#query 
         */
        public java.lang.String query()
        {
            return (java.lang.String) map.get("query");
        }

        

        /**
         * Minimum topic size. Documents belonging to a topic with fewer documents than
minimum topic size will not be returned.
         * 
         * @see org.carrot2.source.ambient.FubDocumentSource#minTopicSize 
         */
        public AttributeBuilder minTopicSize(int value)
        {
            map.put("FubDocumentSource.minTopicSize", value);
            return this;
        }

        

        

        /**
         * Minimum topic size. Documents belonging to a topic with fewer documents than
minimum topic size will not be returned.
         * 
         * @see org.carrot2.source.ambient.FubDocumentSource#minTopicSize 
         */
        public AttributeBuilder minTopicSize(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("FubDocumentSource.minTopicSize", value);
            return this;
        }

        

        

        /**
         * Include documents without topics.
         * 
         * @see org.carrot2.source.ambient.FubDocumentSource#includeDocumentsWithoutTopic 
         */
        public AttributeBuilder includeDocumentsWithoutTopic(boolean value)
        {
            map.put("FubDocumentSource.includeDocumentsWithoutTopic", value);
            return this;
        }

        

        

        /**
         * Include documents without topics.
         * 
         * @see org.carrot2.source.ambient.FubDocumentSource#includeDocumentsWithoutTopic 
         */
        public AttributeBuilder includeDocumentsWithoutTopic(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("FubDocumentSource.includeDocumentsWithoutTopic", value);
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
