

// APT-generated file.

package org.carrot2.source.opensearch;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.opensearch.OpenSearchDocumentSource} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.opensearch.OpenSearchDocumentSource
 */
public final class OpenSearchDocumentSourceDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.opensearch.OpenSearchDocumentSource";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "OpenSearchDocumentSource";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "A <code>IDocumentSource</code> fetching <code>Document</code>s (search results) from an OpenSearch feed";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "<p> Based on code donated by Julien Nioche.";

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
        ownAttrs.add(attributes.feedUrlTemplate);
        ownAttrs.add(attributes.resultsPerPage);
        ownAttrs.add(attributes.maximumResults);
        ownAttrs.add(attributes.feedUrlParams);
        ownAttrs.add(attributes.userAgent);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.opensearch.OpenSearchDocumentSourceDescriptor.attributes.feedUrlTemplate);
        allAttrs.add(org.carrot2.source.opensearch.OpenSearchDocumentSourceDescriptor.attributes.resultsPerPage);
        allAttrs.add(org.carrot2.source.opensearch.OpenSearchDocumentSourceDescriptor.attributes.maximumResults);
        allAttrs.add(org.carrot2.source.opensearch.OpenSearchDocumentSourceDescriptor.attributes.feedUrlParams);
        allAttrs.add(org.carrot2.source.opensearch.OpenSearchDocumentSourceDescriptor.attributes.userAgent);
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
     * Constants for all attribute keys of the {@link org.carrot2.source.opensearch.OpenSearchDocumentSource} component.
     */
    public static class Keys  extends org.carrot2.source.MultipageSearchEngineDescriptor.Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.opensearch.OpenSearchDocumentSource#feedUrlTemplate}. */
        public static final String FEED_URL_TEMPLATE = "OpenSearchDocumentSource.feedUrlTemplate";
        /** Attribute key for: {@link org.carrot2.source.opensearch.OpenSearchDocumentSource#resultsPerPage}. */
        public static final String RESULTS_PER_PAGE = "OpenSearchDocumentSource.resultsPerPage";
        /** Attribute key for: {@link org.carrot2.source.opensearch.OpenSearchDocumentSource#maximumResults}. */
        public static final String MAXIMUM_RESULTS = "OpenSearchDocumentSource.maximumResults";
        /** Attribute key for: {@link org.carrot2.source.opensearch.OpenSearchDocumentSource#feedUrlParams}. */
        public static final String FEED_URL_PARAMS = "OpenSearchDocumentSource.feedUrlParams";
        /** Attribute key for: {@link org.carrot2.source.opensearch.OpenSearchDocumentSource#userAgent}. */
        public static final String USER_AGENT = "OpenSearchDocumentSource.userAgent";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.opensearch.OpenSearchDocumentSource} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo feedUrlTemplate = 
            new AttributeInfo(
                "OpenSearchDocumentSource.feedUrlTemplate",
                "org.carrot2.source.opensearch.OpenSearchDocumentSource",
                "feedUrlTemplate",
"URL to fetch the search feed from. The URL template can contain variable place\nholders as defined by the OpenSearch specification that will be replaced during\nruntime. The format of the place holder is <code>${variable}</code>. The following\nvariables are supported:\n<ul>\n<li><code>searchTerms</code> will be replaced by the query</li>\n<li><code>startIndex</code> index of the first result to be searched. Mutually \nexclusive with <code>startPage</code></li>\n<li><code>startPage</code> index of the first result\nto be searched. Mutually exclusive with <code>startIndex</code>.</li>\n<li><code>count</code> the number of search results per page</li>\n</ul>\n\n<p>Example URL feed templates for public services:</p>\n<dl>\n<dt>nature.com</dt>\n<dd><code>http://www.nature.com/opensearch/request?interface=opensearch&amp;operation=searchRetrieve&amp;query=${searchTerms}&amp;startRecord=${startIndex}&amp;maximumRecords=${count}&amp;httpAccept=application/rss%2Bxml</code></dd>\n<dt>indeed.com</dt>\n<dd><code>http://www.indeed.com/opensearch?q=${searchTerms}&amp;start=${startIndex}&amp;limit=${count}</code></dd>\n</dl>",
"Feed URL template",
"URL to fetch the search feed from",
"The URL template can contain variable place holders as defined by the OpenSearch specification that will be replaced during runtime. The format of the place holder is <code>${variable}</code>. The following variables are supported: <ul> <li><code>searchTerms</code> will be replaced by the query</li> <li><code>startIndex</code> index of the first result to be searched. Mutually exclusive with <code>startPage</code></li> <li><code>startPage</code> index of the first result to be searched. Mutually exclusive with <code>startIndex</code>.</li> <li><code>count</code> the number of search results per page</li> </ul> <p>Example URL feed templates for public services:</p> <dl> <dt>nature.com</dt> <dd><code>http://www.nature.com/opensearch/request?interface=opensearch&amp;operation=searchRetrieve&amp;query=${searchTerms}&amp;startRecord=${startIndex}&amp;maximumRecords=${count}&amp;httpAccept=application/rss%2Bxml</code></dd> <dt>indeed.com</dt> <dd><code>http://www.indeed.com/opensearch?q=${searchTerms}&amp;start=${startIndex}&amp;limit=${count}</code></dd> </dl>",
"Service",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo resultsPerPage = 
            new AttributeInfo(
                "OpenSearchDocumentSource.resultsPerPage",
                "org.carrot2.source.opensearch.OpenSearchDocumentSource",
                "resultsPerPage",
"Results per page. The number of results per page the document source will expect\nthe feed to return.",
"Results per page",
"Results per page",
"The number of results per page the document source will expect the feed to return.",
"Service",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo maximumResults = 
            new AttributeInfo(
                "OpenSearchDocumentSource.maximumResults",
                "org.carrot2.source.opensearch.OpenSearchDocumentSource",
                "maximumResults",
"Maximum number of results. The maximum number of results the document source can\ndeliver.",
"Maximum results",
"Maximum number of results",
"The maximum number of results the document source can deliver.",
"Service",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo feedUrlParams = 
            new AttributeInfo(
                "OpenSearchDocumentSource.feedUrlParams",
                "org.carrot2.source.opensearch.OpenSearchDocumentSource",
                "feedUrlParams",
"Additional parameters to be appended to {@link org.carrot2.source.opensearch.OpenSearchDocumentSource#feedUrlTemplate} on each request.",
"Feed URL parameters",
"Additional parameters to be appended to <code>org.carrot2.source.opensearch.OpenSearchDocumentSource.feedUrlTemplate</code> on each request",
null,
"Service",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo userAgent = 
            new AttributeInfo(
                "OpenSearchDocumentSource.userAgent",
                "org.carrot2.source.opensearch.OpenSearchDocumentSource",
                "userAgent",
"User agent header. The contents of the User-Agent HTTP header to use when making\nrequests to the feed URL. If empty or <code>null</code> value is provided,\nthe following User-Agent will be sent: <code>Rome Client (http://tinyurl.com/64t5n) \nVer: UNKNOWN</code>.",
"User agent",
"User agent header",
"The contents of the User-Agent HTTP header to use when making requests to the feed URL. If empty or <code>null</code> value is provided, the following User-Agent will be sent: <code>Rome Client (http://tinyurl.com/64t5n) Ver: UNKNOWN</code>.",
"Service",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.opensearch.OpenSearchDocumentSource} component. You can use this
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
         * URL to fetch the search feed from. The URL template can contain variable place
holders as defined by the OpenSearch specification that will be replaced during
runtime. The format of the place holder is <code>${variable}</code>. The following
variables are supported:
<ul>
<li><code>searchTerms</code> will be replaced by the query</li>
<li><code>startIndex</code> index of the first result to be searched. Mutually 
exclusive with <code>startPage</code></li>
<li><code>startPage</code> index of the first result
to be searched. Mutually exclusive with <code>startIndex</code>.</li>
<li><code>count</code> the number of search results per page</li>
</ul>

<p>Example URL feed templates for public services:</p>
<dl>
<dt>nature.com</dt>
<dd><code>http://www.nature.com/opensearch/request?interface=opensearch&amp;operation=searchRetrieve&amp;query=${searchTerms}&amp;startRecord=${startIndex}&amp;maximumRecords=${count}&amp;httpAccept=application/rss%2Bxml</code></dd>
<dt>indeed.com</dt>
<dd><code>http://www.indeed.com/opensearch?q=${searchTerms}&amp;start=${startIndex}&amp;limit=${count}</code></dd>
</dl>
         * 
         * @see org.carrot2.source.opensearch.OpenSearchDocumentSource#feedUrlTemplate 
         */
        public AttributeBuilder feedUrlTemplate(java.lang.String value)
        {
            map.put("OpenSearchDocumentSource.feedUrlTemplate", value);
            return this;
        }

        

        

        /**
         * URL to fetch the search feed from. The URL template can contain variable place
holders as defined by the OpenSearch specification that will be replaced during
runtime. The format of the place holder is <code>${variable}</code>. The following
variables are supported:
<ul>
<li><code>searchTerms</code> will be replaced by the query</li>
<li><code>startIndex</code> index of the first result to be searched. Mutually 
exclusive with <code>startPage</code></li>
<li><code>startPage</code> index of the first result
to be searched. Mutually exclusive with <code>startIndex</code>.</li>
<li><code>count</code> the number of search results per page</li>
</ul>

<p>Example URL feed templates for public services:</p>
<dl>
<dt>nature.com</dt>
<dd><code>http://www.nature.com/opensearch/request?interface=opensearch&amp;operation=searchRetrieve&amp;query=${searchTerms}&amp;startRecord=${startIndex}&amp;maximumRecords=${count}&amp;httpAccept=application/rss%2Bxml</code></dd>
<dt>indeed.com</dt>
<dd><code>http://www.indeed.com/opensearch?q=${searchTerms}&amp;start=${startIndex}&amp;limit=${count}</code></dd>
</dl>
         * 
         * @see org.carrot2.source.opensearch.OpenSearchDocumentSource#feedUrlTemplate 
         */
        public AttributeBuilder feedUrlTemplate(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("OpenSearchDocumentSource.feedUrlTemplate", value);
            return this;
        }

        

        

        /**
         * Results per page. The number of results per page the document source will expect
the feed to return.
         * 
         * @see org.carrot2.source.opensearch.OpenSearchDocumentSource#resultsPerPage 
         */
        public AttributeBuilder resultsPerPage(int value)
        {
            map.put("OpenSearchDocumentSource.resultsPerPage", value);
            return this;
        }

        

        

        /**
         * Results per page. The number of results per page the document source will expect
the feed to return.
         * 
         * @see org.carrot2.source.opensearch.OpenSearchDocumentSource#resultsPerPage 
         */
        public AttributeBuilder resultsPerPage(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("OpenSearchDocumentSource.resultsPerPage", value);
            return this;
        }

        

        

        /**
         * Maximum number of results. The maximum number of results the document source can
deliver.
         * 
         * @see org.carrot2.source.opensearch.OpenSearchDocumentSource#maximumResults 
         */
        public AttributeBuilder maximumResults(int value)
        {
            map.put("OpenSearchDocumentSource.maximumResults", value);
            return this;
        }

        

        

        /**
         * Maximum number of results. The maximum number of results the document source can
deliver.
         * 
         * @see org.carrot2.source.opensearch.OpenSearchDocumentSource#maximumResults 
         */
        public AttributeBuilder maximumResults(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("OpenSearchDocumentSource.maximumResults", value);
            return this;
        }

        

        

        /**
         * Additional parameters to be appended to {@link org.carrot2.source.opensearch.OpenSearchDocumentSource#feedUrlTemplate} on each request.
         * 
         * @see org.carrot2.source.opensearch.OpenSearchDocumentSource#feedUrlParams 
         */
        public AttributeBuilder feedUrlParams(java.util.Map<java.lang.String,java.lang.String> value)
        {
            map.put("OpenSearchDocumentSource.feedUrlParams", value);
            return this;
        }

        

        

        /**
         * Additional parameters to be appended to {@link org.carrot2.source.opensearch.OpenSearchDocumentSource#feedUrlTemplate} on each request.
         * 
         * @see org.carrot2.source.opensearch.OpenSearchDocumentSource#feedUrlParams 
         */
        public AttributeBuilder feedUrlParams(IObjectFactory<? extends java.util.Map<java.lang.String,java.lang.String>> value)
        {
            map.put("OpenSearchDocumentSource.feedUrlParams", value);
            return this;
        }

        

        

        /**
         * User agent header. The contents of the User-Agent HTTP header to use when making
requests to the feed URL. If empty or <code>null</code> value is provided,
the following User-Agent will be sent: <code>Rome Client (http://tinyurl.com/64t5n) 
Ver: UNKNOWN</code>.
         * 
         * @see org.carrot2.source.opensearch.OpenSearchDocumentSource#userAgent 
         */
        public AttributeBuilder userAgent(java.lang.String value)
        {
            map.put("OpenSearchDocumentSource.userAgent", value);
            return this;
        }

        

        

        /**
         * User agent header. The contents of the User-Agent HTTP header to use when making
requests to the feed URL. If empty or <code>null</code> value is provided,
the following User-Agent will be sent: <code>Rome Client (http://tinyurl.com/64t5n) 
Ver: UNKNOWN</code>.
         * 
         * @see org.carrot2.source.opensearch.OpenSearchDocumentSource#userAgent 
         */
        public AttributeBuilder userAgent(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("OpenSearchDocumentSource.userAgent", value);
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
