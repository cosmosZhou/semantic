

// APT-generated file.

package org.carrot2.text.clustering;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.text.clustering.MultilingualClustering} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.text.clustering.MultilingualClustering
 */
public final class MultilingualClusteringDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.text.clustering.MultilingualClustering";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "MultilingualClustering";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "A helper for clustering multilingual collections of documents";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "The helper partitions the input documents by <code>org.carrot2.core.Document.LANGUAGE</code>, clusters each such monolingual partition separately and then aggregates the partial cluster lists based on the selected <code>LanguageAggregationStrategy</code>.";

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
        ownAttrs.add(attributes.languageAggregationStrategy);
        ownAttrs.add(attributes.defaultLanguage);
        ownAttrs.add(attributes.languageCounts);
        ownAttrs.add(attributes.majorityLanguage);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.text.clustering.MultilingualClusteringDescriptor.attributes.languageAggregationStrategy);
        allAttrs.add(org.carrot2.text.clustering.MultilingualClusteringDescriptor.attributes.defaultLanguage);
        allAttrs.add(org.carrot2.text.clustering.MultilingualClusteringDescriptor.attributes.languageCounts);
        allAttrs.add(org.carrot2.text.clustering.MultilingualClusteringDescriptor.attributes.majorityLanguage);

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
     * Constants for all attribute keys of the {@link org.carrot2.text.clustering.MultilingualClustering} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.text.clustering.MultilingualClustering#languageAggregationStrategy}. */
        public static final String LANGUAGE_AGGREGATION_STRATEGY = "MultilingualClustering.languageAggregationStrategy";
        /** Attribute key for: {@link org.carrot2.text.clustering.MultilingualClustering#defaultLanguage}. */
        public static final String DEFAULT_LANGUAGE = "MultilingualClustering.defaultLanguage";
        /** Attribute key for: {@link org.carrot2.text.clustering.MultilingualClustering#languageCounts}. */
        public static final String LANGUAGE_COUNTS = "MultilingualClustering.languageCounts";
        /** Attribute key for: {@link org.carrot2.text.clustering.MultilingualClustering#majorityLanguage}. */
        public static final String MAJORITY_LANGUAGE = "MultilingualClustering.majorityLanguage";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.text.clustering.MultilingualClustering} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo languageAggregationStrategy = 
            new AttributeInfo(
                "MultilingualClustering.languageAggregationStrategy",
                "org.carrot2.text.clustering.MultilingualClustering",
                "languageAggregationStrategy",
"Language aggregation strategy. Determines how clusters generated for individual\nlanguages should be combined to form the final result. Please see\n{@link org.carrot2.text.clustering.MultilingualClustering.LanguageAggregationStrategy} \nfor the list of available options.",
null,
"Language aggregation strategy",
"Determines how clusters generated for individual languages should be combined to form the final result. Please see <code>org.carrot2.text.clustering.MultilingualClustering.LanguageAggregationStrategy</code> for the list of available options.",
"Multilingual clustering",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo defaultLanguage = 
            new AttributeInfo(
                "MultilingualClustering.defaultLanguage",
                "org.carrot2.text.clustering.MultilingualClustering",
                "defaultLanguage",
"Default clustering language. The default language to use for documents with\nundefined {@link org.carrot2.core.Document#LANGUAGE}.",
null,
"Default clustering language",
"The default language to use for documents with undefined <code>org.carrot2.core.Document.LANGUAGE</code>.",
"Multilingual clustering",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo languageCounts = 
            new AttributeInfo(
                "MultilingualClustering.languageCounts",
                "org.carrot2.text.clustering.MultilingualClustering",
                "languageCounts",
"Document languages. The number of documents in each language. Empty string key means\nunknown language.",
null,
"Document languages",
"The number of documents in each language. Empty string key means unknown language.",
"Multilingual clustering",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo majorityLanguage = 
            new AttributeInfo(
                "MultilingualClustering.majorityLanguage",
                "org.carrot2.text.clustering.MultilingualClustering",
                "majorityLanguage",
"Majority language.\nIf {@link org.carrot2.text.clustering.MultilingualClustering#languageAggregationStrategy} is \n{@link org.carrot2.text.clustering.MultilingualClustering.LanguageAggregationStrategy#CLUSTER_IN_MAJORITY_LANGUAGE},\nthis attribute will provide the majority language that was used to cluster all the documents.\nIf the majority of the documents have undefined language, this attribute will be \nempty and the clustering will be performed in the {@link org.carrot2.text.clustering.MultilingualClustering#defaultLanguage}.",
null,
"Majority language",
"If <code>org.carrot2.text.clustering.MultilingualClustering.languageAggregationStrategy</code> is <code>org.carrot2.text.clustering.MultilingualClustering.LanguageAggregationStrategy.CLUSTER_IN_MAJORITY_LANGUAGE</code>, this attribute will provide the majority language that was used to cluster all the documents. If the majority of the documents have undefined language, this attribute will be empty and the clustering will be performed in the <code>org.carrot2.text.clustering.MultilingualClustering.defaultLanguage</code>.",
"Multilingual clustering",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.text.clustering.MultilingualClustering} component. You can use this
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
         * Language aggregation strategy. Determines how clusters generated for individual
languages should be combined to form the final result. Please see
{@link org.carrot2.text.clustering.MultilingualClustering.LanguageAggregationStrategy} 
for the list of available options.
         * 
         * @see org.carrot2.text.clustering.MultilingualClustering#languageAggregationStrategy 
         */
        public AttributeBuilder languageAggregationStrategy(org.carrot2.text.clustering.MultilingualClustering.LanguageAggregationStrategy value)
        {
            map.put("MultilingualClustering.languageAggregationStrategy", value);
            return this;
        }

        

        

        /**
         * Language aggregation strategy. Determines how clusters generated for individual
languages should be combined to form the final result. Please see
{@link org.carrot2.text.clustering.MultilingualClustering.LanguageAggregationStrategy} 
for the list of available options.
         * 
         * @see org.carrot2.text.clustering.MultilingualClustering#languageAggregationStrategy 
         */
        public AttributeBuilder languageAggregationStrategy(IObjectFactory<? extends org.carrot2.text.clustering.MultilingualClustering.LanguageAggregationStrategy> value)
        {
            map.put("MultilingualClustering.languageAggregationStrategy", value);
            return this;
        }

        

        

        /**
         * Default clustering language. The default language to use for documents with
undefined {@link org.carrot2.core.Document#LANGUAGE}.
         * 
         * @see org.carrot2.text.clustering.MultilingualClustering#defaultLanguage 
         */
        public AttributeBuilder defaultLanguage(org.carrot2.core.LanguageCode value)
        {
            map.put("MultilingualClustering.defaultLanguage", value);
            return this;
        }

        

        

        /**
         * Default clustering language. The default language to use for documents with
undefined {@link org.carrot2.core.Document#LANGUAGE}.
         * 
         * @see org.carrot2.text.clustering.MultilingualClustering#defaultLanguage 
         */
        public AttributeBuilder defaultLanguage(IObjectFactory<? extends org.carrot2.core.LanguageCode> value)
        {
            map.put("MultilingualClustering.defaultLanguage", value);
            return this;
        }

        

        

        

        

        

        /**
         * Document languages. The number of documents in each language. Empty string key means
unknown language.
         * 
         * @see org.carrot2.text.clustering.MultilingualClustering#languageCounts 
         */
@SuppressWarnings("unchecked")        public java.util.Map<java.lang.String,java.lang.Integer> languageCounts()
        {
            return (java.util.Map<java.lang.String,java.lang.Integer>) map.get("MultilingualClustering.languageCounts");
        }

        

        

        

        

        /**
         * Majority language.
If {@link org.carrot2.text.clustering.MultilingualClustering#languageAggregationStrategy} is 
{@link org.carrot2.text.clustering.MultilingualClustering.LanguageAggregationStrategy#CLUSTER_IN_MAJORITY_LANGUAGE},
this attribute will provide the majority language that was used to cluster all the documents.
If the majority of the documents have undefined language, this attribute will be 
empty and the clustering will be performed in the {@link org.carrot2.text.clustering.MultilingualClustering#defaultLanguage}.
         * 
         * @see org.carrot2.text.clustering.MultilingualClustering#majorityLanguage 
         */
        public java.lang.String majorityLanguage()
        {
            return (java.lang.String) map.get("MultilingualClustering.majorityLanguage");
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
