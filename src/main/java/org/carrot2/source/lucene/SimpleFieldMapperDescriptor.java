

// APT-generated file.

package org.carrot2.source.lucene;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.lucene.SimpleFieldMapper} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.lucene.SimpleFieldMapper
 */
public final class SimpleFieldMapperDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.lucene.SimpleFieldMapper";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "A simple <code>IFieldMapper</code> with one-to-one mapping between the default title, url and summary fields";
    
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
        ownAttrs.add(attributes.titleField);
        ownAttrs.add(attributes.contentField);
        ownAttrs.add(attributes.urlField);
        ownAttrs.add(attributes.searchFields);
        ownAttrs.add(attributes.formatter);
        ownAttrs.add(attributes.contextFragments);
        ownAttrs.add(attributes.fragmentJoin);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.lucene.SimpleFieldMapperDescriptor.attributes.titleField);
        allAttrs.add(org.carrot2.source.lucene.SimpleFieldMapperDescriptor.attributes.contentField);
        allAttrs.add(org.carrot2.source.lucene.SimpleFieldMapperDescriptor.attributes.urlField);
        allAttrs.add(org.carrot2.source.lucene.SimpleFieldMapperDescriptor.attributes.searchFields);
        allAttrs.add(org.carrot2.source.lucene.SimpleFieldMapperDescriptor.attributes.formatter);
        allAttrs.add(org.carrot2.source.lucene.SimpleFieldMapperDescriptor.attributes.contextFragments);
        allAttrs.add(org.carrot2.source.lucene.SimpleFieldMapperDescriptor.attributes.fragmentJoin);

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
     * Constants for all attribute keys of the {@link org.carrot2.source.lucene.SimpleFieldMapper} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.lucene.SimpleFieldMapper#titleField}. */
        public static final String TITLE_FIELD = "org.carrot2.source.lucene.SimpleFieldMapper.titleField";
        /** Attribute key for: {@link org.carrot2.source.lucene.SimpleFieldMapper#contentField}. */
        public static final String CONTENT_FIELD = "org.carrot2.source.lucene.SimpleFieldMapper.contentField";
        /** Attribute key for: {@link org.carrot2.source.lucene.SimpleFieldMapper#urlField}. */
        public static final String URL_FIELD = "org.carrot2.source.lucene.SimpleFieldMapper.urlField";
        /** Attribute key for: {@link org.carrot2.source.lucene.SimpleFieldMapper#searchFields}. */
        public static final String SEARCH_FIELDS = "org.carrot2.source.lucene.SimpleFieldMapper.searchFields";
        /** Attribute key for: {@link org.carrot2.source.lucene.SimpleFieldMapper#formatter}. */
        public static final String FORMATTER = "org.carrot2.source.lucene.SimpleFieldMapper.formatter";
        /** Attribute key for: {@link org.carrot2.source.lucene.SimpleFieldMapper#contextFragments}. */
        public static final String CONTEXT_FRAGMENTS = "org.carrot2.source.lucene.SimpleFieldMapper.contextFragments";
        /** Attribute key for: {@link org.carrot2.source.lucene.SimpleFieldMapper#fragmentJoin}. */
        public static final String FRAGMENT_JOIN = "org.carrot2.source.lucene.SimpleFieldMapper.fragmentJoin";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.lucene.SimpleFieldMapper} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo titleField = 
            new AttributeInfo(
                "org.carrot2.source.lucene.SimpleFieldMapper.titleField",
                "org.carrot2.source.lucene.SimpleFieldMapper",
                "titleField",
"Document title field name.",
"Document title field",
"Document title field name",
null,
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo contentField = 
            new AttributeInfo(
                "org.carrot2.source.lucene.SimpleFieldMapper.contentField",
                "org.carrot2.source.lucene.SimpleFieldMapper",
                "contentField",
"Document content field name.",
"Document content field",
"Document content field name",
null,
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo urlField = 
            new AttributeInfo(
                "org.carrot2.source.lucene.SimpleFieldMapper.urlField",
                "org.carrot2.source.lucene.SimpleFieldMapper",
                "urlField",
"Document URL field name.",
"Document URL field",
"Document URL field name",
null,
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo searchFields = 
            new AttributeInfo(
                "org.carrot2.source.lucene.SimpleFieldMapper.searchFields",
                "org.carrot2.source.lucene.SimpleFieldMapper",
                "searchFields",
"Index search field names. If not specified, title and content fields are used.",
"Search fields",
"Index search field names",
"If not specified, title and content fields are used.",
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo formatter = 
            new AttributeInfo(
                "org.carrot2.source.lucene.SimpleFieldMapper.formatter",
                "org.carrot2.source.lucene.SimpleFieldMapper",
                "formatter",
"Snippet formatter for the highlighter. Highlighter is not used if <code>null</code>.",
"Formatter",
"Snippet formatter for the highlighter",
"Highlighter is not used if <code>null</code>.",
"Highlighter",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo contextFragments = 
            new AttributeInfo(
                "org.carrot2.source.lucene.SimpleFieldMapper.contextFragments",
                "org.carrot2.source.lucene.SimpleFieldMapper",
                "contextFragments",
"Number of context fragments for the highlighter.",
"Context fragments",
"Number of context fragments for the highlighter",
null,
"Highlighter",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo fragmentJoin = 
            new AttributeInfo(
                "org.carrot2.source.lucene.SimpleFieldMapper.fragmentJoin",
                "org.carrot2.source.lucene.SimpleFieldMapper",
                "fragmentJoin",
"A string used to join context fragments when highlighting.",
"Join string",
"A string used to join context fragments when highlighting",
null,
"Highlighter",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.lucene.SimpleFieldMapper} component. You can use this
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
         * Document title field name.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#titleField 
         */
        public AttributeBuilder titleField(java.lang.String value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.titleField", value);
            return this;
        }

        

        

        /**
         * Document title field name.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#titleField 
         */
        public AttributeBuilder titleField(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.titleField", value);
            return this;
        }

        

        

        /**
         * Document content field name.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#contentField 
         */
        public AttributeBuilder contentField(java.lang.String value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.contentField", value);
            return this;
        }

        

        

        /**
         * Document content field name.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#contentField 
         */
        public AttributeBuilder contentField(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.contentField", value);
            return this;
        }

        

        

        /**
         * Document URL field name.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#urlField 
         */
        public AttributeBuilder urlField(java.lang.String value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.urlField", value);
            return this;
        }

        

        

        /**
         * Document URL field name.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#urlField 
         */
        public AttributeBuilder urlField(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.urlField", value);
            return this;
        }

        

        

        /**
         * Index search field names. If not specified, title and content fields are used.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#searchFields 
         */
        public AttributeBuilder searchFields(java.util.List<java.lang.String> value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.searchFields", value);
            return this;
        }

        

        

        /**
         * Index search field names. If not specified, title and content fields are used.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#searchFields 
         */
        public AttributeBuilder searchFields(IObjectFactory<? extends java.util.List<java.lang.String>> value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.searchFields", value);
            return this;
        }

        

        

        /**
         * Snippet formatter for the highlighter. Highlighter is not used if <code>null</code>.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#formatter 
         */
        public AttributeBuilder formatter(org.apache.lucene.search.highlight.Formatter value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.formatter", value);
            return this;
        }

        

        /**
         * Snippet formatter for the highlighter. Highlighter is not used if <code>null</code>.
         *
         * A class that extends org.apache.lucene.search.highlight.Formatter or appropriate IObjectFactory.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#formatter
         */
        public AttributeBuilder formatter(Class<?> clazz)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.formatter", clazz);
            return this;
        }

        

        /**
         * Snippet formatter for the highlighter. Highlighter is not used if <code>null</code>.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#formatter 
         */
        public AttributeBuilder formatter(IObjectFactory<? extends org.apache.lucene.search.highlight.Formatter> value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.formatter", value);
            return this;
        }

        

        

        /**
         * Number of context fragments for the highlighter.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#contextFragments 
         */
        public AttributeBuilder contextFragments(int value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.contextFragments", value);
            return this;
        }

        

        

        /**
         * Number of context fragments for the highlighter.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#contextFragments 
         */
        public AttributeBuilder contextFragments(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.contextFragments", value);
            return this;
        }

        

        

        /**
         * A string used to join context fragments when highlighting.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#fragmentJoin 
         */
        public AttributeBuilder fragmentJoin(java.lang.String value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.fragmentJoin", value);
            return this;
        }

        

        

        /**
         * A string used to join context fragments when highlighting.
         * 
         * @see org.carrot2.source.lucene.SimpleFieldMapper#fragmentJoin 
         */
        public AttributeBuilder fragmentJoin(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("org.carrot2.source.lucene.SimpleFieldMapper.fragmentJoin", value);
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
