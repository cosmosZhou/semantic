

// APT-generated file.

package org.carrot2.source.ambient;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.ambient.AmbientDocumentSource} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.ambient.AmbientDocumentSource
 */
public final class AmbientDocumentSourceDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.ambient.AmbientDocumentSource";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "AmbientDocumentSource";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Serves documents from the Ambient test set";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "Ambient (AMBIgous ENTries) is a data set designed for evaluating subtopic information retrieval. It consists of 44 topics, each with a set of subtopics and a list of 100 ranked documents. For more information, please see <a href=\"http://credo.fub.it/ambient/\">Ambient home page</a>.";

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
        ownAttrs.add(attributes.topic);
        ownAttrs.add(attributes.results);
        ownAttrs.add(attributes.resultsTotal);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.ambient.AmbientDocumentSourceDescriptor.attributes.topic);
        allAttrs.add(org.carrot2.source.ambient.AmbientDocumentSourceDescriptor.attributes.results);
        allAttrs.add(org.carrot2.source.ambient.AmbientDocumentSourceDescriptor.attributes.resultsTotal);
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
     * Constants for all attribute keys of the {@link org.carrot2.source.ambient.AmbientDocumentSource} component.
     */
    public static class Keys  extends org.carrot2.source.ambient.FubDocumentSourceDescriptor.Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.ambient.AmbientDocumentSource#topic}. */
        public static final String TOPIC = "AmbientDocumentSource.topic";
        /** Attribute key for: {@link org.carrot2.source.ambient.AmbientDocumentSource#results}. */
        public static final String RESULTS = "results";
        /** Attribute key for: {@link org.carrot2.source.ambient.AmbientDocumentSource#resultsTotal}. */
        public static final String RESULTS_TOTAL = "results-total";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.ambient.AmbientDocumentSource} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo topic = 
            new AttributeInfo(
                "AmbientDocumentSource.topic",
                "org.carrot2.source.ambient.AmbientDocumentSource",
                "topic",
"Ambient Topic. The Ambient Topic to load documents from.",
null,
"Ambient Topic",
"The Ambient Topic to load documents from.",
"Topic ID",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#results
         */
        public final AttributeInfo results = 
            new AttributeInfo(
                "results",
                "org.carrot2.source.ambient.AmbientDocumentSource",
                "results",
null,
null,
null,
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.results
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#resultsTotal
         */
        public final AttributeInfo resultsTotal = 
            new AttributeInfo(
                "results-total",
                "org.carrot2.source.ambient.AmbientDocumentSource",
                "resultsTotal",
null,
null,
null,
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.resultsTotal
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.ambient.AmbientDocumentSource} component. You can use this
     * builder as a type-safe alternative to populating the attribute map using attribute keys.
     */
    public static class AttributeBuilder  extends org.carrot2.source.ambient.FubDocumentSourceDescriptor.AttributeBuilder 
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
         * Ambient Topic. The Ambient Topic to load documents from.
         * 
         * @see org.carrot2.source.ambient.AmbientDocumentSource#topic 
         */
        public AttributeBuilder topic(org.carrot2.source.ambient.AmbientDocumentSource.AmbientTopic value)
        {
            map.put("AmbientDocumentSource.topic", value);
            return this;
        }

        

        

        /**
         * Ambient Topic. The Ambient Topic to load documents from.
         * 
         * @see org.carrot2.source.ambient.AmbientDocumentSource#topic 
         */
        public AttributeBuilder topic(IObjectFactory<? extends org.carrot2.source.ambient.AmbientDocumentSource.AmbientTopic> value)
        {
            map.put("AmbientDocumentSource.topic", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.ambient.AmbientDocumentSource#results 
         */
        public AttributeBuilder results(int value)
        {
            map.put("results", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.ambient.AmbientDocumentSource#results 
         */
        public AttributeBuilder results(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("results", value);
            return this;
        }

        

        

        

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.ambient.AmbientDocumentSource#resultsTotal 
         */
        public long resultsTotal()
        {
            return (java.lang.Long) map.get("results-total");
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
