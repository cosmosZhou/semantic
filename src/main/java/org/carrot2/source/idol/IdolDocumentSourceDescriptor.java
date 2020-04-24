

// APT-generated file.

package org.carrot2.source.idol;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.idol.IdolDocumentSource} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.idol.IdolDocumentSource
 */
public final class IdolDocumentSourceDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.idol.IdolDocumentSource";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "IdolDocumentSource";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "A <code>IDocumentSource</code> fetching <code>Document</code>s (search results) from an IDOL Search Engine";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "Please note that you will need to install an XSLT stylesheet in your IDOL instance that transforms the search results into the OpenSearch format. The XSLT stylesheet is available under the <tt>org.carrot2.source.idol</tt> package, next to the binaries of this class. <p> Based on code donated by Julien Nioche. Autonomy IDOL support contributed by James Sealey. </p>";

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
        ownAttrs.add(attributes.idolServerName);
        ownAttrs.add(attributes.idolServerPort);
        ownAttrs.add(attributes.xslTemplateName);
        ownAttrs.add(attributes.otherSearchAttributes);
        ownAttrs.add(attributes.resultsPerPage);
        ownAttrs.add(attributes.minScore);
        ownAttrs.add(attributes.maximumResults);
        ownAttrs.add(attributes.userAgent);
        ownAttrs.add(attributes.userName);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.idol.IdolDocumentSourceDescriptor.attributes.idolServerName);
        allAttrs.add(org.carrot2.source.idol.IdolDocumentSourceDescriptor.attributes.idolServerPort);
        allAttrs.add(org.carrot2.source.idol.IdolDocumentSourceDescriptor.attributes.xslTemplateName);
        allAttrs.add(org.carrot2.source.idol.IdolDocumentSourceDescriptor.attributes.otherSearchAttributes);
        allAttrs.add(org.carrot2.source.idol.IdolDocumentSourceDescriptor.attributes.resultsPerPage);
        allAttrs.add(org.carrot2.source.idol.IdolDocumentSourceDescriptor.attributes.minScore);
        allAttrs.add(org.carrot2.source.idol.IdolDocumentSourceDescriptor.attributes.maximumResults);
        allAttrs.add(org.carrot2.source.idol.IdolDocumentSourceDescriptor.attributes.userAgent);
        allAttrs.add(org.carrot2.source.idol.IdolDocumentSourceDescriptor.attributes.userName);
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
     * Constants for all attribute keys of the {@link org.carrot2.source.idol.IdolDocumentSource} component.
     */
    public static class Keys  extends org.carrot2.source.MultipageSearchEngineDescriptor.Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.idol.IdolDocumentSource#idolServerName}. */
        public static final String IDOL_SERVER_NAME = "IdolDocumentSource.idolServerName";
        /** Attribute key for: {@link org.carrot2.source.idol.IdolDocumentSource#idolServerPort}. */
        public static final String IDOL_SERVER_PORT = "IdolDocumentSource.idolServerPort";
        /** Attribute key for: {@link org.carrot2.source.idol.IdolDocumentSource#xslTemplateName}. */
        public static final String XSL_TEMPLATE_NAME = "IdolDocumentSource.xslTemplateName";
        /** Attribute key for: {@link org.carrot2.source.idol.IdolDocumentSource#otherSearchAttributes}. */
        public static final String OTHER_SEARCH_ATTRIBUTES = "IdolDocumentSource.otherSearchAttributes";
        /** Attribute key for: {@link org.carrot2.source.idol.IdolDocumentSource#resultsPerPage}. */
        public static final String RESULTS_PER_PAGE = "IdolDocumentSource.resultsPerPage";
        /** Attribute key for: {@link org.carrot2.source.idol.IdolDocumentSource#minScore}. */
        public static final String MIN_SCORE = "IdolDocumentSource.minScore";
        /** Attribute key for: {@link org.carrot2.source.idol.IdolDocumentSource#maximumResults}. */
        public static final String MAXIMUM_RESULTS = "IdolDocumentSource.maximumResults";
        /** Attribute key for: {@link org.carrot2.source.idol.IdolDocumentSource#userAgent}. */
        public static final String USER_AGENT = "IdolDocumentSource.userAgent";
        /** Attribute key for: {@link org.carrot2.source.idol.IdolDocumentSource#userName}. */
        public static final String USER_NAME = "IdolDocumentSource.userName";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.idol.IdolDocumentSource} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo idolServerName = 
            new AttributeInfo(
                "IdolDocumentSource.idolServerName",
                "org.carrot2.source.idol.IdolDocumentSource",
                "idolServerName",
"URL of the IDOL Server.",
"IDOL server address",
"URL of the IDOL Server",
null,
"Service",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo idolServerPort = 
            new AttributeInfo(
                "IdolDocumentSource.idolServerPort",
                "org.carrot2.source.idol.IdolDocumentSource",
                "idolServerPort",
"IDOL Server Port.",
"IDOL server port",
"IDOL Server Port",
null,
"Service",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo xslTemplateName = 
            new AttributeInfo(
                "IdolDocumentSource.xslTemplateName",
                "org.carrot2.source.idol.IdolDocumentSource",
                "xslTemplateName",
"IDOL XSL Template Name. The Reference of an IDOL XSL template that outputs the\nresults in OpenSearch format.",
"IDOL XSL template name",
"IDOL XSL Template Name",
"The Reference of an IDOL XSL template that outputs the results in OpenSearch format.",
"Service",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo otherSearchAttributes = 
            new AttributeInfo(
                "IdolDocumentSource.otherSearchAttributes",
                "org.carrot2.source.idol.IdolDocumentSource",
                "otherSearchAttributes",
"Any other search attributes (separated by &amp;) from the Autonomy Query Search\nAPI's Ensure all the attributes are entered to satisfy XSL that will be applied.",
"Other IDOLSearch attributes",
"Any other search attributes (separated by &amp;) from the Autonomy Query Search API's Ensure all the attributes are entered to satisfy XSL that will be applied",
null,
"Service",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo resultsPerPage = 
            new AttributeInfo(
                "IdolDocumentSource.resultsPerPage",
                "org.carrot2.source.idol.IdolDocumentSource",
                "resultsPerPage",
"Results per page. The number of results per page the document source will expect\nthe feed to return.",
"Results per page",
"Results per page",
"The number of results per page the document source will expect the feed to return.",
"Service",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo minScore = 
            new AttributeInfo(
                "IdolDocumentSource.minScore",
                "org.carrot2.source.idol.IdolDocumentSource",
                "minScore",
"Minimum IDOL Score. The minimum score of the results returned by IDOL.",
"Minimum score",
"Minimum IDOL Score",
"The minimum score of the results returned by IDOL.",
"Service",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo maximumResults = 
            new AttributeInfo(
                "IdolDocumentSource.maximumResults",
                "org.carrot2.source.idol.IdolDocumentSource",
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
        public final AttributeInfo userAgent = 
            new AttributeInfo(
                "IdolDocumentSource.userAgent",
                "org.carrot2.source.idol.IdolDocumentSource",
                "userAgent",
"User agent header. The contents of the User-Agent HTTP header to use when making\nrequests to the feed URL. If empty or <code>null</code> value is provided, the\nfollowing User-Agent will be sent:\n<code>Rome Client (http://tinyurl.com/64t5n) Ver: UNKNOWN</code>.",
"User agent",
"User agent header",
"The contents of the User-Agent HTTP header to use when making requests to the feed URL. If empty or <code>null</code> value is provided, the following User-Agent will be sent: <code>Rome Client (http://tinyurl.com/64t5n) Ver: UNKNOWN</code>.",
"Service",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo userName = 
            new AttributeInfo(
                "IdolDocumentSource.userName",
                "org.carrot2.source.idol.IdolDocumentSource",
                "userName",
"User name to use for authentication.",
"User name",
"User name to use for authentication",
null,
"Service",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.idol.IdolDocumentSource} component. You can use this
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
         * URL of the IDOL Server.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#idolServerName 
         */
        public AttributeBuilder idolServerName(java.lang.String value)
        {
            map.put("IdolDocumentSource.idolServerName", value);
            return this;
        }

        

        

        /**
         * URL of the IDOL Server.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#idolServerName 
         */
        public AttributeBuilder idolServerName(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("IdolDocumentSource.idolServerName", value);
            return this;
        }

        

        

        /**
         * IDOL Server Port.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#idolServerPort 
         */
        public AttributeBuilder idolServerPort(int value)
        {
            map.put("IdolDocumentSource.idolServerPort", value);
            return this;
        }

        

        

        /**
         * IDOL Server Port.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#idolServerPort 
         */
        public AttributeBuilder idolServerPort(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("IdolDocumentSource.idolServerPort", value);
            return this;
        }

        

        

        /**
         * IDOL XSL Template Name. The Reference of an IDOL XSL template that outputs the
results in OpenSearch format.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#xslTemplateName 
         */
        public AttributeBuilder xslTemplateName(java.lang.String value)
        {
            map.put("IdolDocumentSource.xslTemplateName", value);
            return this;
        }

        

        

        /**
         * IDOL XSL Template Name. The Reference of an IDOL XSL template that outputs the
results in OpenSearch format.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#xslTemplateName 
         */
        public AttributeBuilder xslTemplateName(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("IdolDocumentSource.xslTemplateName", value);
            return this;
        }

        

        

        /**
         * Any other search attributes (separated by &amp;) from the Autonomy Query Search
API's Ensure all the attributes are entered to satisfy XSL that will be applied.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#otherSearchAttributes 
         */
        public AttributeBuilder otherSearchAttributes(java.lang.String value)
        {
            map.put("IdolDocumentSource.otherSearchAttributes", value);
            return this;
        }

        

        

        /**
         * Any other search attributes (separated by &amp;) from the Autonomy Query Search
API's Ensure all the attributes are entered to satisfy XSL that will be applied.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#otherSearchAttributes 
         */
        public AttributeBuilder otherSearchAttributes(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("IdolDocumentSource.otherSearchAttributes", value);
            return this;
        }

        

        

        /**
         * Results per page. The number of results per page the document source will expect
the feed to return.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#resultsPerPage 
         */
        public AttributeBuilder resultsPerPage(int value)
        {
            map.put("IdolDocumentSource.resultsPerPage", value);
            return this;
        }

        

        

        /**
         * Results per page. The number of results per page the document source will expect
the feed to return.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#resultsPerPage 
         */
        public AttributeBuilder resultsPerPage(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("IdolDocumentSource.resultsPerPage", value);
            return this;
        }

        

        

        /**
         * Minimum IDOL Score. The minimum score of the results returned by IDOL.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#minScore 
         */
        public AttributeBuilder minScore(int value)
        {
            map.put("IdolDocumentSource.minScore", value);
            return this;
        }

        

        

        /**
         * Minimum IDOL Score. The minimum score of the results returned by IDOL.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#minScore 
         */
        public AttributeBuilder minScore(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("IdolDocumentSource.minScore", value);
            return this;
        }

        

        

        /**
         * Maximum number of results. The maximum number of results the document source can
deliver.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#maximumResults 
         */
        public AttributeBuilder maximumResults(int value)
        {
            map.put("IdolDocumentSource.maximumResults", value);
            return this;
        }

        

        

        /**
         * Maximum number of results. The maximum number of results the document source can
deliver.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#maximumResults 
         */
        public AttributeBuilder maximumResults(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("IdolDocumentSource.maximumResults", value);
            return this;
        }

        

        

        /**
         * User agent header. The contents of the User-Agent HTTP header to use when making
requests to the feed URL. If empty or <code>null</code> value is provided, the
following User-Agent will be sent:
<code>Rome Client (http://tinyurl.com/64t5n) Ver: UNKNOWN</code>.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#userAgent 
         */
        public AttributeBuilder userAgent(java.lang.String value)
        {
            map.put("IdolDocumentSource.userAgent", value);
            return this;
        }

        

        

        /**
         * User agent header. The contents of the User-Agent HTTP header to use when making
requests to the feed URL. If empty or <code>null</code> value is provided, the
following User-Agent will be sent:
<code>Rome Client (http://tinyurl.com/64t5n) Ver: UNKNOWN</code>.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#userAgent 
         */
        public AttributeBuilder userAgent(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("IdolDocumentSource.userAgent", value);
            return this;
        }

        

        

        /**
         * User name to use for authentication.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#userName 
         */
        public AttributeBuilder userName(java.lang.String value)
        {
            map.put("IdolDocumentSource.userName", value);
            return this;
        }

        

        

        /**
         * User name to use for authentication.
         * 
         * @see org.carrot2.source.idol.IdolDocumentSource#userName 
         */
        public AttributeBuilder userName(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("IdolDocumentSource.userName", value);
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
