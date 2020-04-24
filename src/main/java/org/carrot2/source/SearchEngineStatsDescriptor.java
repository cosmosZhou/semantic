

// APT-generated file.

package org.carrot2.source;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.SearchEngineStats} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.SearchEngineStats
 */
public final class SearchEngineStatsDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.SearchEngineStats";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "SearchEngineStats";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Usage statistics for an instance of <code>MultipageSearchEngine</code>";
    
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
        ownAttrs.add(attributes.queries);
        ownAttrs.add(attributes.pageRequests);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.SearchEngineStatsDescriptor.attributes.queries);
        allAttrs.add(org.carrot2.source.SearchEngineStatsDescriptor.attributes.pageRequests);

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
     * Constants for all attribute keys of the {@link org.carrot2.source.SearchEngineStats} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.SearchEngineStats#queries}. */
        public static final String QUERIES = "SearchEngineStats.queries";
        /** Attribute key for: {@link org.carrot2.source.SearchEngineStats#pageRequests}. */
        public static final String PAGE_REQUESTS = "SearchEngineStats.pageRequests";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.SearchEngineStats} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo queries = 
            new AttributeInfo(
                "SearchEngineStats.queries",
                "org.carrot2.source.SearchEngineStats",
                "queries",
"Number queries handled successfully by this data source.",
"Successful queries",
"Number queries handled successfully by this data source",
null,
"Search result information",
null,
null
            );

        /**
         *          */
        public final AttributeInfo pageRequests = 
            new AttributeInfo(
                "SearchEngineStats.pageRequests",
                "org.carrot2.source.SearchEngineStats",
                "pageRequests",
"Number of individual page requests issued by this data source.",
"Page requests",
"Number of individual page requests issued by this data source",
null,
"Search result information",
null,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.SearchEngineStats} component. You can use this
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
         * Number queries handled successfully by this data source.
         * 
         * @see org.carrot2.source.SearchEngineStats#queries 
         */
        public int queries()
        {
            return (java.lang.Integer) map.get("SearchEngineStats.queries");
        }

        

        

        

        

        /**
         * Number of individual page requests issued by this data source.
         * 
         * @see org.carrot2.source.SearchEngineStats#pageRequests 
         */
        public int pageRequests()
        {
            return (java.lang.Integer) map.get("SearchEngineStats.pageRequests");
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
