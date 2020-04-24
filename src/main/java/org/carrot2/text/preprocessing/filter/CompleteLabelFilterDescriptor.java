

// APT-generated file.

package org.carrot2.text.preprocessing.filter;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.text.preprocessing.filter.CompleteLabelFilter} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.text.preprocessing.filter.CompleteLabelFilter
 */
public final class CompleteLabelFilterDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.text.preprocessing.filter.CompleteLabelFilter";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "CompleteLabelFilter";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "A filter that removes \"incomplete\" labels";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "<p> See <a href=\"http://project.carrot2.org/publications/osinski-2003-lingo.pdf\">this document</a>, page 31 for a definition of a complete phrase.";

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
        ownAttrs.add(attributes.enabled);
        ownAttrs.add(attributes.labelOverrideThreshold);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.text.preprocessing.filter.CompleteLabelFilterDescriptor.attributes.enabled);
        allAttrs.add(org.carrot2.text.preprocessing.filter.CompleteLabelFilterDescriptor.attributes.labelOverrideThreshold);

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
     * Constants for all attribute keys of the {@link org.carrot2.text.preprocessing.filter.CompleteLabelFilter} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.text.preprocessing.filter.CompleteLabelFilter#enabled}. */
        public static final String ENABLED = "CompleteLabelFilter.enabled";
        /** Attribute key for: {@link org.carrot2.text.preprocessing.filter.CompleteLabelFilter#labelOverrideThreshold}. */
        public static final String LABEL_OVERRIDE_THRESHOLD = "CompleteLabelFilter.labelOverrideThreshold";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.text.preprocessing.filter.CompleteLabelFilter} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo enabled = 
            new AttributeInfo(
                "CompleteLabelFilter.enabled",
                "org.carrot2.text.preprocessing.filter.CompleteLabelFilter",
                "enabled",
"Remove truncated phrases. Tries to remove \"incomplete\" cluster labels. For example,\nin a collection of documents related to <i>Data Mining</i>, the phrase\n<i>Conference on Data</i> is incomplete in a sense that most likely it should be\n<i>Conference on Data Mining</i> or even <i>Conference on Data Mining in Large\nDatabases</i>. When truncated phrase removal is enabled, the algorithm would try to\nremove the \"incomplete\" phrases like the former one and leave only the more\ninformative variants.",
"Remove truncated phrases",
"Remove truncated phrases",
"Tries to remove \"incomplete\" cluster labels. For example, in a collection of documents related to <i>Data Mining</i>, the phrase <i>Conference on Data</i> is incomplete in a sense that most likely it should be <i>Conference on Data Mining</i> or even <i>Conference on Data Mining in Large Databases</i>. When truncated phrase removal is enabled, the algorithm would try to remove the \"incomplete\" phrases like the former one and leave only the more informative variants.",
"Labels",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo labelOverrideThreshold = 
            new AttributeInfo(
                "CompleteLabelFilter.labelOverrideThreshold",
                "org.carrot2.text.preprocessing.filter.CompleteLabelFilter",
                "labelOverrideThreshold",
"Truncated label threshold. Determines the strength of the truncated label filter.\nThe lowest value means strongest truncated labels elimination, which may lead to\noverlong cluster labels and many unclustered documents. The highest value\neffectively disables the filter, which may result in short or truncated labels.",
"Truncated label threshold",
"Truncated label threshold",
"Determines the strength of the truncated label filter. The lowest value means strongest truncated labels elimination, which may lead to overlong cluster labels and many unclustered documents. The highest value effectively disables the filter, which may result in short or truncated labels.",
"Labels",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.text.preprocessing.filter.CompleteLabelFilter} component. You can use this
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
         * Remove truncated phrases. Tries to remove "incomplete" cluster labels. For example,
in a collection of documents related to <i>Data Mining</i>, the phrase
<i>Conference on Data</i> is incomplete in a sense that most likely it should be
<i>Conference on Data Mining</i> or even <i>Conference on Data Mining in Large
Databases</i>. When truncated phrase removal is enabled, the algorithm would try to
remove the "incomplete" phrases like the former one and leave only the more
informative variants.
         * 
         * @see org.carrot2.text.preprocessing.filter.CompleteLabelFilter#enabled 
         */
        public AttributeBuilder enabled(boolean value)
        {
            map.put("CompleteLabelFilter.enabled", value);
            return this;
        }

        

        

        /**
         * Remove truncated phrases. Tries to remove "incomplete" cluster labels. For example,
in a collection of documents related to <i>Data Mining</i>, the phrase
<i>Conference on Data</i> is incomplete in a sense that most likely it should be
<i>Conference on Data Mining</i> or even <i>Conference on Data Mining in Large
Databases</i>. When truncated phrase removal is enabled, the algorithm would try to
remove the "incomplete" phrases like the former one and leave only the more
informative variants.
         * 
         * @see org.carrot2.text.preprocessing.filter.CompleteLabelFilter#enabled 
         */
        public AttributeBuilder enabled(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("CompleteLabelFilter.enabled", value);
            return this;
        }

        

        

        /**
         * Truncated label threshold. Determines the strength of the truncated label filter.
The lowest value means strongest truncated labels elimination, which may lead to
overlong cluster labels and many unclustered documents. The highest value
effectively disables the filter, which may result in short or truncated labels.
         * 
         * @see org.carrot2.text.preprocessing.filter.CompleteLabelFilter#labelOverrideThreshold 
         */
        public AttributeBuilder labelOverrideThreshold(double value)
        {
            map.put("CompleteLabelFilter.labelOverrideThreshold", value);
            return this;
        }

        

        

        /**
         * Truncated label threshold. Determines the strength of the truncated label filter.
The lowest value means strongest truncated labels elimination, which may lead to
overlong cluster labels and many unclustered documents. The highest value
effectively disables the filter, which may result in short or truncated labels.
         * 
         * @see org.carrot2.text.preprocessing.filter.CompleteLabelFilter#labelOverrideThreshold 
         */
        public AttributeBuilder labelOverrideThreshold(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("CompleteLabelFilter.labelOverrideThreshold", value);
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
