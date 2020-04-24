

// APT-generated file.

package org.carrot2.text.linguistic;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.text.linguistic.DefaultLexicalDataFactory} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.text.linguistic.DefaultLexicalDataFactory
 */
public final class DefaultLexicalDataFactoryDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.text.linguistic.DefaultLexicalDataFactory";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "The default management of lexical resources";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "Resources are read from disk, cached and shared between <b>all</b> threads using this class. Additional attributes control resource reloading and merging: <code>org.carrot2.text.linguistic.DefaultLexicalDataFactory.resourceLookup</code>, <code>org.carrot2.text.linguistic.DefaultLexicalDataFactory.reloadResources</code>, <code>org.carrot2.text.linguistic.DefaultLexicalDataFactory.mergeResources</code>.";

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
        ownAttrs.add(attributes.reloadResources);
        ownAttrs.add(attributes.mergeResources);
        ownAttrs.add(attributes.resourceLookup);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.text.linguistic.DefaultLexicalDataFactoryDescriptor.attributes.reloadResources);
        allAttrs.add(org.carrot2.text.linguistic.DefaultLexicalDataFactoryDescriptor.attributes.mergeResources);
        allAttrs.add(org.carrot2.text.linguistic.DefaultLexicalDataFactoryDescriptor.attributes.resourceLookup);

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
     * Constants for all attribute keys of the {@link org.carrot2.text.linguistic.DefaultLexicalDataFactory} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.text.linguistic.DefaultLexicalDataFactory#reloadResources}. */
        public static final String RELOAD_RESOURCES = "reload-resources";
        /** Attribute key for: {@link org.carrot2.text.linguistic.DefaultLexicalDataFactory#mergeResources}. */
        public static final String MERGE_RESOURCES = "merge-resources";
        /** Attribute key for: {@link org.carrot2.text.linguistic.DefaultLexicalDataFactory#resourceLookup}. */
        public static final String RESOURCE_LOOKUP = "resource-lookup";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.text.linguistic.DefaultLexicalDataFactory} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         * 
         * 
         * @see org.carrot2.text.linguistic.LexicalDataLoader#reloadResources
         */
        public final AttributeInfo reloadResources = 
            new AttributeInfo(
                "reload-resources",
                "org.carrot2.text.linguistic.DefaultLexicalDataFactory",
                "reloadResources",
null,
null,
null,
null,
null,
null,
org.carrot2.text.linguistic.LexicalDataLoaderDescriptor.attributes.reloadResources
            );

        /**
         *          */
        public final AttributeInfo mergeResources = 
            new AttributeInfo(
                "merge-resources",
                "org.carrot2.text.linguistic.DefaultLexicalDataFactory",
                "mergeResources",
"Merges stop words and stop labels from all known languages. If set to\n<code>false</code>, only stop words and stop labels of the active language will be\nused. If set to <code>true</code>, stop words from all {@link org.carrot2.core.LanguageCode}s will\nbe used together and stop labels from all languages will be used together, no\nmatter the active language. Lexical resource merging is useful when clustering data\nin a mix of different languages and should increase clustering quality in such\nsettings.",
"Merge lexical resources",
"Merges stop words and stop labels from all known languages",
"If set to <code>false</code>, only stop words and stop labels of the active language will be used. If set to <code>true</code>, stop words from all <code>org.carrot2.core.LanguageCode</code>s will be used together and stop labels from all languages will be used together, no matter the active language. Lexical resource merging is useful when clustering data in a mix of different languages and should increase clustering quality in such settings.",
"Preprocessing",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         * 
         * 
         * @see org.carrot2.text.linguistic.LexicalDataLoader#resourceLookup
         */
        public final AttributeInfo resourceLookup = 
            new AttributeInfo(
                "resource-lookup",
                "org.carrot2.text.linguistic.DefaultLexicalDataFactory",
                "resourceLookup",
null,
null,
null,
null,
null,
null,
org.carrot2.text.linguistic.LexicalDataLoaderDescriptor.attributes.resourceLookup
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.text.linguistic.DefaultLexicalDataFactory} component. You can use this
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
         * @see org.carrot2.text.linguistic.DefaultLexicalDataFactory#reloadResources 
         */
        public AttributeBuilder reloadResources(boolean value)
        {
            map.put("reload-resources", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.text.linguistic.DefaultLexicalDataFactory#reloadResources 
         */
        public AttributeBuilder reloadResources(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("reload-resources", value);
            return this;
        }

        

        

        /**
         * Merges stop words and stop labels from all known languages. If set to
<code>false</code>, only stop words and stop labels of the active language will be
used. If set to <code>true</code>, stop words from all {@link org.carrot2.core.LanguageCode}s will
be used together and stop labels from all languages will be used together, no
matter the active language. Lexical resource merging is useful when clustering data
in a mix of different languages and should increase clustering quality in such
settings.
         * 
         * @see org.carrot2.text.linguistic.DefaultLexicalDataFactory#mergeResources 
         */
        public AttributeBuilder mergeResources(boolean value)
        {
            map.put("merge-resources", value);
            return this;
        }

        

        

        /**
         * Merges stop words and stop labels from all known languages. If set to
<code>false</code>, only stop words and stop labels of the active language will be
used. If set to <code>true</code>, stop words from all {@link org.carrot2.core.LanguageCode}s will
be used together and stop labels from all languages will be used together, no
matter the active language. Lexical resource merging is useful when clustering data
in a mix of different languages and should increase clustering quality in such
settings.
         * 
         * @see org.carrot2.text.linguistic.DefaultLexicalDataFactory#mergeResources 
         */
        public AttributeBuilder mergeResources(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("merge-resources", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.text.linguistic.DefaultLexicalDataFactory#resourceLookup 
         */
        public AttributeBuilder resourceLookup(org.carrot2.util.resource.ResourceLookup value)
        {
            map.put("resource-lookup", value);
            return this;
        }

        

        /**
         * 
         *
         * A class that extends org.carrot2.util.resource.ResourceLookup or appropriate IObjectFactory.
         * 
         * @see org.carrot2.text.linguistic.DefaultLexicalDataFactory#resourceLookup
         */
        public AttributeBuilder resourceLookup(Class<?> clazz)
        {
            map.put("resource-lookup", clazz);
            return this;
        }

        

        /**
         * 
         * 
         * @see org.carrot2.text.linguistic.DefaultLexicalDataFactory#resourceLookup 
         */
        public AttributeBuilder resourceLookup(IObjectFactory<? extends org.carrot2.util.resource.ResourceLookup> value)
        {
            map.put("resource-lookup", value);
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
