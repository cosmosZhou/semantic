

// APT-generated file.

package org.carrot2.source.etools;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.etools.EToolsDocumentSource} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.etools.EToolsDocumentSource
 */
public final class EToolsDocumentSourceDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.etools.EToolsDocumentSource";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "EToolsDocumentSource";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "A Carrot2 input component for the eTools service (https://www.etools.ch)";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "For commercial licensing of the eTools feed, please e-mail: <code>contact@comcepta.com</code>.";

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
        ownAttrs.add(attributes.serviceUrlBase);
        ownAttrs.add(attributes.country);
        ownAttrs.add(attributes.language);
        ownAttrs.add(attributes.timeout);
        ownAttrs.add(attributes.dataSources);
        ownAttrs.add(attributes.safeSearch);
        ownAttrs.add(attributes.site);
        ownAttrs.add(attributes.partnerId);
        ownAttrs.add(attributes.customerId);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.etools.EToolsDocumentSourceDescriptor.attributes.serviceUrlBase);
        allAttrs.add(org.carrot2.source.etools.EToolsDocumentSourceDescriptor.attributes.country);
        allAttrs.add(org.carrot2.source.etools.EToolsDocumentSourceDescriptor.attributes.language);
        allAttrs.add(org.carrot2.source.etools.EToolsDocumentSourceDescriptor.attributes.timeout);
        allAttrs.add(org.carrot2.source.etools.EToolsDocumentSourceDescriptor.attributes.dataSources);
        allAttrs.add(org.carrot2.source.etools.EToolsDocumentSourceDescriptor.attributes.safeSearch);
        allAttrs.add(org.carrot2.source.etools.EToolsDocumentSourceDescriptor.attributes.site);
        allAttrs.add(org.carrot2.source.etools.EToolsDocumentSourceDescriptor.attributes.partnerId);
        allAttrs.add(org.carrot2.source.etools.EToolsDocumentSourceDescriptor.attributes.customerId);
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
     * Constants for all attribute keys of the {@link org.carrot2.source.etools.EToolsDocumentSource} component.
     */
    public static class Keys  extends org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBaseDescriptor.Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.etools.EToolsDocumentSource#serviceUrlBase}. */
        public static final String SERVICE_URL_BASE = "EToolsDocumentSource.serviceUrlBase";
        /** Attribute key for: {@link org.carrot2.source.etools.EToolsDocumentSource#country}. */
        public static final String COUNTRY = "EToolsDocumentSource.country";
        /** Attribute key for: {@link org.carrot2.source.etools.EToolsDocumentSource#language}. */
        public static final String LANGUAGE = "EToolsDocumentSource.language";
        /** Attribute key for: {@link org.carrot2.source.etools.EToolsDocumentSource#timeout}. */
        public static final String TIMEOUT = "EToolsDocumentSource.timeout";
        /** Attribute key for: {@link org.carrot2.source.etools.EToolsDocumentSource#dataSources}. */
        public static final String DATA_SOURCES = "EToolsDocumentSource.dataSources";
        /** Attribute key for: {@link org.carrot2.source.etools.EToolsDocumentSource#safeSearch}. */
        public static final String SAFE_SEARCH = "EToolsDocumentSource.safeSearch";
        /** Attribute key for: {@link org.carrot2.source.etools.EToolsDocumentSource#site}. */
        public static final String SITE = "EToolsDocumentSource.site";
        /** Attribute key for: {@link org.carrot2.source.etools.EToolsDocumentSource#partnerId}. */
        public static final String PARTNER_ID = "EToolsDocumentSource.partnerId";
        /** Attribute key for: {@link org.carrot2.source.etools.EToolsDocumentSource#customerId}. */
        public static final String CUSTOMER_ID = "EToolsDocumentSource.customerId";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.etools.EToolsDocumentSource} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo serviceUrlBase = 
            new AttributeInfo(
                "EToolsDocumentSource.serviceUrlBase",
                "org.carrot2.source.etools.EToolsDocumentSource",
                "serviceUrlBase",
"Base URL for the eTools service",
"Service URL",
"Base URL for the eTools service",
null,
"Service",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo country = 
            new AttributeInfo(
                "EToolsDocumentSource.country",
                "org.carrot2.source.etools.EToolsDocumentSource",
                "country",
"Determines the country of origin for the returned search results.",
"Country",
"Determines the country of origin for the returned search results",
null,
"Filtering",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo language = 
            new AttributeInfo(
                "EToolsDocumentSource.language",
                "org.carrot2.source.etools.EToolsDocumentSource",
                "language",
"Determines the language of the returned search results.",
"Language",
"Determines the language of the returned search results",
null,
"Filtering",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo timeout = 
            new AttributeInfo(
                "EToolsDocumentSource.timeout",
                "org.carrot2.source.etools.EToolsDocumentSource",
                "timeout",
"Maximum time in milliseconds to wait for all data sources to return results.",
"Timeout",
"Maximum time in milliseconds to wait for all data sources to return results",
null,
"Service",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo dataSources = 
            new AttributeInfo(
                "EToolsDocumentSource.dataSources",
                "org.carrot2.source.etools.EToolsDocumentSource",
                "dataSources",
"Determines which data sources to search.",
"Data sources",
"Determines which data sources to search",
null,
"Service",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo safeSearch = 
            new AttributeInfo(
                "EToolsDocumentSource.safeSearch",
                "org.carrot2.source.etools.EToolsDocumentSource",
                "safeSearch",
"If enabled, excludes offensive content from the results.",
"Safe search",
"If enabled, excludes offensive content from the results",
null,
"Filtering",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo site = 
            new AttributeInfo(
                "EToolsDocumentSource.site",
                "org.carrot2.source.etools.EToolsDocumentSource",
                "site",
"Site URL or comma-separated list of site site URLs to which the returned results\nshould be restricted. For example: <tt>wikipedia.org</tt> or\n<tt>en.wikipedia.org,de.wikipedia.org</tt>. Very larger lists of site restrictions\n(larger than 2000 characters) may result in a processing exception.",
"Site restriction",
"Site URL or comma-separated list of site site URLs to which the returned results should be restricted",
"For example: <tt>wikipedia.org</tt> or <tt>en.wikipedia.org,de.wikipedia.org</tt>. Very larger lists of site restrictions (larger than 2000 characters) may result in a processing exception.",
"Filtering",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo partnerId = 
            new AttributeInfo(
                "EToolsDocumentSource.partnerId",
                "org.carrot2.source.etools.EToolsDocumentSource",
                "partnerId",
"eTools partner identifier. If you have commercial arrangements with eTools, specify\nyour partner id here.",
"Partner ID",
"eTools partner identifier",
"If you have commercial arrangements with eTools, specify your partner id here.",
"Service",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo customerId = 
            new AttributeInfo(
                "EToolsDocumentSource.customerId",
                "org.carrot2.source.etools.EToolsDocumentSource",
                "customerId",
"eTools customer identifier. For commercial use of eTools, please e-mail: \n<code>contact@comcepta.com</code> to obtain your customer identifier.",
"Customer ID",
"eTools customer identifier",
"For commercial use of eTools, please e-mail: <code>contact@comcepta.com</code> to obtain your customer identifier.",
"Service",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.etools.EToolsDocumentSource} component. You can use this
     * builder as a type-safe alternative to populating the attribute map using attribute keys.
     */
    public static class AttributeBuilder  extends org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBaseDescriptor.AttributeBuilder 
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
         * Base URL for the eTools service
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#serviceUrlBase 
         */
        public AttributeBuilder serviceUrlBase(java.lang.String value)
        {
            map.put("EToolsDocumentSource.serviceUrlBase", value);
            return this;
        }

        

        

        /**
         * Base URL for the eTools service
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#serviceUrlBase 
         */
        public AttributeBuilder serviceUrlBase(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("EToolsDocumentSource.serviceUrlBase", value);
            return this;
        }

        

        

        /**
         * Determines the country of origin for the returned search results.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#country 
         */
        public AttributeBuilder country(org.carrot2.source.etools.EToolsDocumentSource.Country value)
        {
            map.put("EToolsDocumentSource.country", value);
            return this;
        }

        

        

        /**
         * Determines the country of origin for the returned search results.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#country 
         */
        public AttributeBuilder country(IObjectFactory<? extends org.carrot2.source.etools.EToolsDocumentSource.Country> value)
        {
            map.put("EToolsDocumentSource.country", value);
            return this;
        }

        

        

        /**
         * Determines the language of the returned search results.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#language 
         */
        public AttributeBuilder language(org.carrot2.source.etools.EToolsDocumentSource.Language value)
        {
            map.put("EToolsDocumentSource.language", value);
            return this;
        }

        

        

        /**
         * Determines the language of the returned search results.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#language 
         */
        public AttributeBuilder language(IObjectFactory<? extends org.carrot2.source.etools.EToolsDocumentSource.Language> value)
        {
            map.put("EToolsDocumentSource.language", value);
            return this;
        }

        

        

        /**
         * Maximum time in milliseconds to wait for all data sources to return results.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#timeout 
         */
        public AttributeBuilder timeout(int value)
        {
            map.put("EToolsDocumentSource.timeout", value);
            return this;
        }

        

        

        /**
         * Maximum time in milliseconds to wait for all data sources to return results.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#timeout 
         */
        public AttributeBuilder timeout(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("EToolsDocumentSource.timeout", value);
            return this;
        }

        

        

        /**
         * Determines which data sources to search.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#dataSources 
         */
        public AttributeBuilder dataSources(org.carrot2.source.etools.EToolsDocumentSource.DataSources value)
        {
            map.put("EToolsDocumentSource.dataSources", value);
            return this;
        }

        

        

        /**
         * Determines which data sources to search.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#dataSources 
         */
        public AttributeBuilder dataSources(IObjectFactory<? extends org.carrot2.source.etools.EToolsDocumentSource.DataSources> value)
        {
            map.put("EToolsDocumentSource.dataSources", value);
            return this;
        }

        

        

        /**
         * If enabled, excludes offensive content from the results.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#safeSearch 
         */
        public AttributeBuilder safeSearch(boolean value)
        {
            map.put("EToolsDocumentSource.safeSearch", value);
            return this;
        }

        

        

        /**
         * If enabled, excludes offensive content from the results.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#safeSearch 
         */
        public AttributeBuilder safeSearch(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("EToolsDocumentSource.safeSearch", value);
            return this;
        }

        

        

        /**
         * Site URL or comma-separated list of site site URLs to which the returned results
should be restricted. For example: <tt>wikipedia.org</tt> or
<tt>en.wikipedia.org,de.wikipedia.org</tt>. Very larger lists of site restrictions
(larger than 2000 characters) may result in a processing exception.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#site 
         */
        public AttributeBuilder site(java.lang.String value)
        {
            map.put("EToolsDocumentSource.site", value);
            return this;
        }

        

        

        /**
         * Site URL or comma-separated list of site site URLs to which the returned results
should be restricted. For example: <tt>wikipedia.org</tt> or
<tt>en.wikipedia.org,de.wikipedia.org</tt>. Very larger lists of site restrictions
(larger than 2000 characters) may result in a processing exception.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#site 
         */
        public AttributeBuilder site(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("EToolsDocumentSource.site", value);
            return this;
        }

        

        

        /**
         * eTools partner identifier. If you have commercial arrangements with eTools, specify
your partner id here.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#partnerId 
         */
        public AttributeBuilder partnerId(java.lang.String value)
        {
            map.put("EToolsDocumentSource.partnerId", value);
            return this;
        }

        

        

        /**
         * eTools partner identifier. If you have commercial arrangements with eTools, specify
your partner id here.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#partnerId 
         */
        public AttributeBuilder partnerId(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("EToolsDocumentSource.partnerId", value);
            return this;
        }

        

        

        /**
         * eTools customer identifier. For commercial use of eTools, please e-mail: 
<code>contact@comcepta.com</code> to obtain your customer identifier.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#customerId 
         */
        public AttributeBuilder customerId(java.lang.String value)
        {
            map.put("EToolsDocumentSource.customerId", value);
            return this;
        }

        

        

        /**
         * eTools customer identifier. For commercial use of eTools, please e-mail: 
<code>contact@comcepta.com</code> to obtain your customer identifier.
         * 
         * @see org.carrot2.source.etools.EToolsDocumentSource#customerId 
         */
        public AttributeBuilder customerId(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("EToolsDocumentSource.customerId", value);
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
