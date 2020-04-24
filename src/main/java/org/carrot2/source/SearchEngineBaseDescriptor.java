

// APT-generated file.

package org.carrot2.source;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.SearchEngineBase} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.SearchEngineBase
 */
public final class SearchEngineBaseDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.SearchEngineBase";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "SearchEngineBase";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "A base class facilitating implementation of <code>IDocumentSource</code>s wrapping external search engines with remote/ network-based interfaces";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "The base class defines the common attribute fields used by more specific base classes and concrete implementations.";

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
        ownAttrs.add(attributes.start);
        ownAttrs.add(attributes.results);
        ownAttrs.add(attributes.query);
        ownAttrs.add(attributes.resultsTotal);
        ownAttrs.add(attributes.documents);
        ownAttrs.add(attributes.compressed);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
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
     * Constants for all attribute keys of the {@link org.carrot2.source.SearchEngineBase} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.SearchEngineBase#start}. */
        public static final String START = "start";
        /** Attribute key for: {@link org.carrot2.source.SearchEngineBase#results}. */
        public static final String RESULTS = "results";
        /** Attribute key for: {@link org.carrot2.source.SearchEngineBase#query}. */
        public static final String QUERY = "query";
        /** Attribute key for: {@link org.carrot2.source.SearchEngineBase#resultsTotal}. */
        public static final String RESULTS_TOTAL = "results-total";
        /** Attribute key for: {@link org.carrot2.source.SearchEngineBase#documents}. */
        public static final String DOCUMENTS = "documents";
        /** Attribute key for: {@link org.carrot2.source.SearchEngineBase#compressed}. */
        public static final String COMPRESSED = "SearchEngineBase.compressed";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.SearchEngineBase} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#start
         */
        public final AttributeInfo start = 
            new AttributeInfo(
                "start",
                "org.carrot2.source.SearchEngineBase",
                "start",
null,
null,
null,
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.start
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#results
         */
        public final AttributeInfo results = 
            new AttributeInfo(
                "results",
                "org.carrot2.source.SearchEngineBase",
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
         * @see org.carrot2.core.attribute.CommonAttributes#query
         */
        public final AttributeInfo query = 
            new AttributeInfo(
                "query",
                "org.carrot2.source.SearchEngineBase",
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
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#resultsTotal
         */
        public final AttributeInfo resultsTotal = 
            new AttributeInfo(
                "results-total",
                "org.carrot2.source.SearchEngineBase",
                "resultsTotal",
null,
null,
null,
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.resultsTotal
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#documents
         */
        public final AttributeInfo documents = 
            new AttributeInfo(
                "documents",
                "org.carrot2.source.SearchEngineBase",
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
        public final AttributeInfo compressed = 
            new AttributeInfo(
                "SearchEngineBase.compressed",
                "org.carrot2.source.SearchEngineBase",
                "compressed",
"Indicates whether the search engine returned a compressed result stream.",
"Compression used",
"Indicates whether the search engine returned a compressed result stream",
null,
"Search result information",
null,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.SearchEngineBase} component. You can use this
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
         * @see org.carrot2.source.SearchEngineBase#start 
         */
        public AttributeBuilder start(int value)
        {
            map.put("start", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.SearchEngineBase#start 
         */
        public AttributeBuilder start(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("start", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.SearchEngineBase#results 
         */
        public AttributeBuilder results(int value)
        {
            map.put("results", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.SearchEngineBase#results 
         */
        public AttributeBuilder results(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("results", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.SearchEngineBase#query 
         */
        public AttributeBuilder query(java.lang.String value)
        {
            map.put("query", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.SearchEngineBase#query 
         */
        public AttributeBuilder query(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("query", value);
            return this;
        }

        

        

        

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.SearchEngineBase#resultsTotal 
         */
        public long resultsTotal()
        {
            return (java.lang.Long) map.get("results-total");
        }

        

        

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.SearchEngineBase#documents 
         */
@SuppressWarnings("unchecked")        public java.util.Collection<org.carrot2.core.Document> documents()
        {
            return (java.util.Collection<org.carrot2.core.Document>) map.get("documents");
        }

        

        

        

        

        /**
         * Indicates whether the search engine returned a compressed result stream.
         * 
         * @see org.carrot2.source.SearchEngineBase#compressed 
         */
        public boolean compressed()
        {
            return (java.lang.Boolean) map.get("SearchEngineBase.compressed");
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
