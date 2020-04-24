

// APT-generated file.

package org.carrot2.source.pubmed;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.pubmed.PubMedDocumentSource} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.pubmed.PubMedDocumentSource
 */
public final class PubMedDocumentSourceDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.pubmed.PubMedDocumentSource";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "PubMedDocumentSource";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Performs searches on the PubMed database using its on-line e-utilities: http://eutils.ncbi.nlm.nih.gov/entrez/query/static/eutils_help.html";
    
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
        ownAttrs.add(attributes.toolName);
        ownAttrs.add(attributes.maxResults);
        ownAttrs.add(attributes.redirectStrategy);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.pubmed.PubMedDocumentSourceDescriptor.attributes.toolName);
        allAttrs.add(org.carrot2.source.pubmed.PubMedDocumentSourceDescriptor.attributes.maxResults);
        allAttrs.add(org.carrot2.source.pubmed.PubMedDocumentSourceDescriptor.attributes.redirectStrategy);
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
     * Constants for all attribute keys of the {@link org.carrot2.source.pubmed.PubMedDocumentSource} component.
     */
    public static class Keys  extends org.carrot2.source.SimpleSearchEngineDescriptor.Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.pubmed.PubMedDocumentSource#toolName}. */
        public static final String TOOL_NAME = "PubMedDocumentSource.toolName";
        /** Attribute key for: {@link org.carrot2.source.pubmed.PubMedDocumentSource#maxResults}. */
        public static final String MAX_RESULTS = "PubMedDocumentSource.maxResults";
        /** Attribute key for: {@link org.carrot2.source.pubmed.PubMedDocumentSource#redirectStrategy}. */
        public static final String REDIRECT_STRATEGY = "PubMedDocumentSource.redirectStrategy";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.pubmed.PubMedDocumentSource} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo toolName = 
            new AttributeInfo(
                "PubMedDocumentSource.toolName",
                "org.carrot2.source.pubmed.PubMedDocumentSource",
                "toolName",
"Tool name, if registered.",
"EUtils Registered Tool Name",
"Tool name, if registered",
null,
"Search query",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo maxResults = 
            new AttributeInfo(
                "PubMedDocumentSource.maxResults",
                "org.carrot2.source.pubmed.PubMedDocumentSource",
                "maxResults",
"Maximum results to fetch. No more than the specified number of results\nwill be fetched from PubMed, regardless of the requested number of results.",
"Maximum results",
"Maximum results to fetch",
"No more than the specified number of results will be fetched from PubMed, regardless of the requested number of results.",
"Search query",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo redirectStrategy = 
            new AttributeInfo(
                "PubMedDocumentSource.redirectStrategy",
                "org.carrot2.source.pubmed.PubMedDocumentSource",
                "redirectStrategy",
"HTTP redirect response strategy (follow or throw an error).",
"HTTP redirect strategy",
"HTTP redirect response strategy (follow or throw an error)",
null,
"Service",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.pubmed.PubMedDocumentSource} component. You can use this
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
         * Tool name, if registered.
         * 
         * @see org.carrot2.source.pubmed.PubMedDocumentSource#toolName 
         */
        public AttributeBuilder toolName(java.lang.String value)
        {
            map.put("PubMedDocumentSource.toolName", value);
            return this;
        }

        

        

        /**
         * Tool name, if registered.
         * 
         * @see org.carrot2.source.pubmed.PubMedDocumentSource#toolName 
         */
        public AttributeBuilder toolName(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("PubMedDocumentSource.toolName", value);
            return this;
        }

        

        

        /**
         * Maximum results to fetch. No more than the specified number of results
will be fetched from PubMed, regardless of the requested number of results.
         * 
         * @see org.carrot2.source.pubmed.PubMedDocumentSource#maxResults 
         */
        public AttributeBuilder maxResults(int value)
        {
            map.put("PubMedDocumentSource.maxResults", value);
            return this;
        }

        

        

        /**
         * Maximum results to fetch. No more than the specified number of results
will be fetched from PubMed, regardless of the requested number of results.
         * 
         * @see org.carrot2.source.pubmed.PubMedDocumentSource#maxResults 
         */
        public AttributeBuilder maxResults(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("PubMedDocumentSource.maxResults", value);
            return this;
        }

        

        

        /**
         * HTTP redirect response strategy (follow or throw an error).
         * 
         * @see org.carrot2.source.pubmed.PubMedDocumentSource#redirectStrategy 
         */
        public AttributeBuilder redirectStrategy(org.carrot2.util.httpclient.HttpRedirectStrategy value)
        {
            map.put("PubMedDocumentSource.redirectStrategy", value);
            return this;
        }

        

        

        /**
         * HTTP redirect response strategy (follow or throw an error).
         * 
         * @see org.carrot2.source.pubmed.PubMedDocumentSource#redirectStrategy 
         */
        public AttributeBuilder redirectStrategy(IObjectFactory<? extends org.carrot2.util.httpclient.HttpRedirectStrategy> value)
        {
            map.put("PubMedDocumentSource.redirectStrategy", value);
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
