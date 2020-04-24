

// APT-generated file.

package org.carrot2.source.microsoft.v7;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.microsoft.v7.Bing7DocumentSource} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.microsoft.v7.Bing7DocumentSource
 */
public final class Bing7DocumentSourceDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.microsoft.v7.Bing7DocumentSource";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "Bing7DocumentSource";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "A <code>IDocumentSource</code> fetching web page search results from Bing, using Search API V7";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "<p>Important: there are limits for free use of the above API (beyond which it is a paid service).";

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
        ownAttrs.add(attributes.apiKey);
        ownAttrs.add(attributes.site);
        ownAttrs.add(attributes.market);
        ownAttrs.add(attributes.adult);
        ownAttrs.add(attributes.redirectStrategy);
        ownAttrs.add(attributes.respectRateLimits);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.microsoft.v7.Bing7DocumentSourceDescriptor.attributes.apiKey);
        allAttrs.add(org.carrot2.source.microsoft.v7.Bing7DocumentSourceDescriptor.attributes.site);
        allAttrs.add(org.carrot2.source.microsoft.v7.Bing7DocumentSourceDescriptor.attributes.market);
        allAttrs.add(org.carrot2.source.microsoft.v7.Bing7DocumentSourceDescriptor.attributes.adult);
        allAttrs.add(org.carrot2.source.microsoft.v7.Bing7DocumentSourceDescriptor.attributes.redirectStrategy);
        allAttrs.add(org.carrot2.source.microsoft.v7.Bing7DocumentSourceDescriptor.attributes.respectRateLimits);
        allAttrs.add(org.carrot2.source.MultipageSearchEngineDescriptor.attributes.searchMode);
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
     * Constants for all attribute keys of the {@link org.carrot2.source.microsoft.v7.Bing7DocumentSource} component.
     */
    public static class Keys  extends org.carrot2.source.MultipageSearchEngineDescriptor.Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.microsoft.v7.Bing7DocumentSource#apiKey}. */
        public static final String API_KEY = "Bing7DocumentSource.apiKey";
        /** Attribute key for: {@link org.carrot2.source.microsoft.v7.Bing7DocumentSource#site}. */
        public static final String SITE = "Bing7DocumentSource.site";
        /** Attribute key for: {@link org.carrot2.source.microsoft.v7.Bing7DocumentSource#market}. */
        public static final String MARKET = "Bing7DocumentSource.market";
        /** Attribute key for: {@link org.carrot2.source.microsoft.v7.Bing7DocumentSource#adult}. */
        public static final String ADULT = "Bing7DocumentSource.adult";
        /** Attribute key for: {@link org.carrot2.source.microsoft.v7.Bing7DocumentSource#redirectStrategy}. */
        public static final String REDIRECT_STRATEGY = "Bing7DocumentSource.redirectStrategy";
        /** Attribute key for: {@link org.carrot2.source.microsoft.v7.Bing7DocumentSource#respectRateLimits}. */
        public static final String RESPECT_RATE_LIMITS = "Bing7DocumentSource.respectRateLimits";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.microsoft.v7.Bing7DocumentSource} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo apiKey = 
            new AttributeInfo(
                "Bing7DocumentSource.apiKey",
                "org.carrot2.source.microsoft.v7.Bing7DocumentSource",
                "apiKey",
"The API key used to authenticate requests. You will have to provide your own API key.\nThere is a free monthly grace request limit.\n\n<p>By default takes the system property's value under key: <code>bing7.key</code>.</p>",
"Application API key",
"The API key used to authenticate requests",
"You will have to provide your own API key. There is a free monthly grace request limit. <p>By default takes the system property's value under key: <code>bing7.key</code>.</p>",
"Service",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo site = 
            new AttributeInfo(
                "Bing7DocumentSource.site",
                "org.carrot2.source.microsoft.v7.Bing7DocumentSource",
                "site",
"Site restriction to return value under a given URL. Example:\n<tt>http://www.wikipedia.org</tt> or simply <tt>wikipedia.org</tt>.",
"Site restriction",
"Site restriction to return value under a given URL",
"Example: <tt>http://www.wikipedia.org</tt> or simply <tt>wikipedia.org</tt>.",
"Filtering",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo market = 
            new AttributeInfo(
                "Bing7DocumentSource.market",
                "org.carrot2.source.microsoft.v7.Bing7DocumentSource",
                "market",
"Language and country/region information for the request.",
"Market",
"Language and country/region information for the request",
null,
"Filtering",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo adult = 
            new AttributeInfo(
                "Bing7DocumentSource.adult",
                "org.carrot2.source.microsoft.v7.Bing7DocumentSource",
                "adult",
"Adult search restriction (porn filter).",
"Safe search",
"Adult search restriction (porn filter)",
null,
"Filtering",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo redirectStrategy = 
            new AttributeInfo(
                "Bing7DocumentSource.redirectStrategy",
                "org.carrot2.source.microsoft.v7.Bing7DocumentSource",
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
         *          */
        public final AttributeInfo respectRateLimits = 
            new AttributeInfo(
                "Bing7DocumentSource.respectRateLimits",
                "org.carrot2.source.microsoft.v7.Bing7DocumentSource",
                "respectRateLimits",
"Respect official guidelines concerning rate limits. If set to false,\nrate limits are not observed.",
"Respect request rate limits",
"Respect official guidelines concerning rate limits",
"If set to false, rate limits are not observed.",
"Service",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.microsoft.v7.Bing7DocumentSource} component. You can use this
     * builder as a type-safe alternative to populating the attribute map using attribute keys.
     */
    public static class AttributeBuilder  extends org.carrot2.source.MultipageSearchEngineDescriptor.AttributeBuilder 
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
         * The API key used to authenticate requests. You will have to provide your own API key.
There is a free monthly grace request limit.

<p>By default takes the system property's value under key: <code>bing7.key</code>.</p>
         * 
         * @see org.carrot2.source.microsoft.v7.Bing7DocumentSource#apiKey 
         */
        public AttributeBuilder apiKey(java.lang.String value)
        {
            map.put("Bing7DocumentSource.apiKey", value);
            return this;
        }

        

        

        /**
         * The API key used to authenticate requests. You will have to provide your own API key.
There is a free monthly grace request limit.

<p>By default takes the system property's value under key: <code>bing7.key</code>.</p>
         * 
         * @see org.carrot2.source.microsoft.v7.Bing7DocumentSource#apiKey 
         */
        public AttributeBuilder apiKey(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("Bing7DocumentSource.apiKey", value);
            return this;
        }

        

        

        /**
         * Site restriction to return value under a given URL. Example:
<tt>http://www.wikipedia.org</tt> or simply <tt>wikipedia.org</tt>.
         * 
         * @see org.carrot2.source.microsoft.v7.Bing7DocumentSource#site 
         */
        public AttributeBuilder site(java.lang.String value)
        {
            map.put("Bing7DocumentSource.site", value);
            return this;
        }

        

        

        /**
         * Site restriction to return value under a given URL. Example:
<tt>http://www.wikipedia.org</tt> or simply <tt>wikipedia.org</tt>.
         * 
         * @see org.carrot2.source.microsoft.v7.Bing7DocumentSource#site 
         */
        public AttributeBuilder site(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("Bing7DocumentSource.site", value);
            return this;
        }

        

        

        /**
         * Language and country/region information for the request.
         * 
         * @see org.carrot2.source.microsoft.v7.Bing7DocumentSource#market 
         */
        public AttributeBuilder market(org.carrot2.source.microsoft.v7.MarketOption value)
        {
            map.put("Bing7DocumentSource.market", value);
            return this;
        }

        

        

        /**
         * Language and country/region information for the request.
         * 
         * @see org.carrot2.source.microsoft.v7.Bing7DocumentSource#market 
         */
        public AttributeBuilder market(IObjectFactory<? extends org.carrot2.source.microsoft.v7.MarketOption> value)
        {
            map.put("Bing7DocumentSource.market", value);
            return this;
        }

        

        

        /**
         * Adult search restriction (porn filter).
         * 
         * @see org.carrot2.source.microsoft.v7.Bing7DocumentSource#adult 
         */
        public AttributeBuilder adult(org.carrot2.source.microsoft.v7.AdultOption value)
        {
            map.put("Bing7DocumentSource.adult", value);
            return this;
        }

        

        

        /**
         * Adult search restriction (porn filter).
         * 
         * @see org.carrot2.source.microsoft.v7.Bing7DocumentSource#adult 
         */
        public AttributeBuilder adult(IObjectFactory<? extends org.carrot2.source.microsoft.v7.AdultOption> value)
        {
            map.put("Bing7DocumentSource.adult", value);
            return this;
        }

        

        

        /**
         * HTTP redirect response strategy (follow or throw an error).
         * 
         * @see org.carrot2.source.microsoft.v7.Bing7DocumentSource#redirectStrategy 
         */
        public AttributeBuilder redirectStrategy(org.carrot2.util.httpclient.HttpRedirectStrategy value)
        {
            map.put("Bing7DocumentSource.redirectStrategy", value);
            return this;
        }

        

        

        /**
         * HTTP redirect response strategy (follow or throw an error).
         * 
         * @see org.carrot2.source.microsoft.v7.Bing7DocumentSource#redirectStrategy 
         */
        public AttributeBuilder redirectStrategy(IObjectFactory<? extends org.carrot2.util.httpclient.HttpRedirectStrategy> value)
        {
            map.put("Bing7DocumentSource.redirectStrategy", value);
            return this;
        }

        

        

        /**
         * Respect official guidelines concerning rate limits. If set to false,
rate limits are not observed.
         * 
         * @see org.carrot2.source.microsoft.v7.Bing7DocumentSource#respectRateLimits 
         */
        public AttributeBuilder respectRateLimits(boolean value)
        {
            map.put("Bing7DocumentSource.respectRateLimits", value);
            return this;
        }

        

        

        /**
         * Respect official guidelines concerning rate limits. If set to false,
rate limits are not observed.
         * 
         * @see org.carrot2.source.microsoft.v7.Bing7DocumentSource#respectRateLimits 
         */
        public AttributeBuilder respectRateLimits(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("Bing7DocumentSource.respectRateLimits", value);
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
