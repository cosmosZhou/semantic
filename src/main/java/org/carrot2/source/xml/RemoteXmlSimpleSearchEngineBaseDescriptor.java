

// APT-generated file.

package org.carrot2.source.xml;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase
 */
public final class RemoteXmlSimpleSearchEngineBaseDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "A base class for implementing data sources based on XML/XSLT";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "The XSLT stylesheet will be loaded once during component initialization and cached for all further requests.";

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
        ownAttrs.add(attributes.redirectStrategy);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBaseDescriptor.attributes.redirectStrategy);
        allAttrs.add(org.carrot2.source.SearchEngineBaseDescriptor.attributes.start);
        allAttrs.add(org.carrot2.source.SearchEngineBaseDescriptor.attributes.results);
        allAttrs.add(org.carrot2.source.SearchEngineBaseDescriptor.attributes.query);
        allAttrs.add(org.carrot2.source.SearchEngineBaseDescriptor.attributes.resultsTotal);
        allAttrs.add(org.carrot2.source.SearchEngineBaseDescriptor.attributes.documents);
        allAttrs.add(org.carrot2.source.SearchEngineBaseDescriptor.attributes.compressed);

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
     * Constants for all attribute keys of the {@link org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase} component.
     */
    public static class Keys  extends org.carrot2.source.SimpleSearchEngineDescriptor.Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase#redirectStrategy}. */
        public static final String REDIRECT_STRATEGY = "org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase.redirectStrategy";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo redirectStrategy = 
            new AttributeInfo(
                "org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase.redirectStrategy",
                "org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase",
                "redirectStrategy",
"HTTP redirect response strategy (follow or throw an error).",
"HTTP redirect strategy",
"HTTP redirect response strategy (follow or throw an error)",
null,
"Service",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );


        /**
         * Attributes of the nested {@link org.carrot2.source.xml.XmlDocumentSourceHelper} component.
         */
        public final org.carrot2.source.xml.XmlDocumentSourceHelperDescriptor.Attributes xmlDocumentSourceHelper =
            org.carrot2.source.xml.XmlDocumentSourceHelperDescriptor.attributes;

    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase} component. You can use this
     * builder as a type-safe alternative to populating the attribute map using attribute keys.
     */
    public static class AttributeBuilder  extends org.carrot2.source.SimpleSearchEngineDescriptor.AttributeBuilder 
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
         * HTTP redirect response strategy (follow or throw an error).
         * 
         * @see org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase#redirectStrategy 
         */
        public AttributeBuilder redirectStrategy(org.carrot2.util.httpclient.HttpRedirectStrategy value)
        {
            map.put("org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase.redirectStrategy", value);
            return this;
        }

        

        

        /**
         * HTTP redirect response strategy (follow or throw an error).
         * 
         * @see org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase#redirectStrategy 
         */
        public AttributeBuilder redirectStrategy(IObjectFactory<? extends org.carrot2.util.httpclient.HttpRedirectStrategy> value)
        {
            map.put("org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBase.redirectStrategy", value);
            return this;
        }

        

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.source.xml.XmlDocumentSourceHelper} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.source.xml.XmlDocumentSourceHelperDescriptor.AttributeBuilder xmlDocumentSourceHelper()
        {
            return org.carrot2.source.xml.XmlDocumentSourceHelperDescriptor.attributeBuilder(map);
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
