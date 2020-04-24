

// APT-generated file.

package org.carrot2.text.preprocessing.pipeline;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline
 */
public final class BasicPreprocessingPipelineDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "PreprocessingPipeline";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Performs basic preprocessing steps on the provided documents";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "The preprocessing consists of the following steps: <ol> <li><code>Tokenizer.tokenize(PreprocessingContext)</code></li> <li><code>CaseNormalizer.normalize(PreprocessingContext)</code></li> <li><code>LanguageModelStemmer.stem(PreprocessingContext)</code></li> <li><code>StopListMarker.mark(PreprocessingContext)</code></li> </ol>";

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
        ownAttrs.add(attributes.tokenizerFactory);
        ownAttrs.add(attributes.stemmerFactory);
        ownAttrs.add(attributes.lexicalDataFactory);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipelineDescriptor.attributes.tokenizerFactory);
        allAttrs.add(org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipelineDescriptor.attributes.stemmerFactory);
        allAttrs.add(org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipelineDescriptor.attributes.lexicalDataFactory);

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
     * Constants for all attribute keys of the {@link org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline#tokenizerFactory}. */
        public static final String TOKENIZER_FACTORY = "PreprocessingPipeline.tokenizerFactory";
        /** Attribute key for: {@link org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline#stemmerFactory}. */
        public static final String STEMMER_FACTORY = "PreprocessingPipeline.stemmerFactory";
        /** Attribute key for: {@link org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline#lexicalDataFactory}. */
        public static final String LEXICAL_DATA_FACTORY = "PreprocessingPipeline.lexicalDataFactory";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo tokenizerFactory = 
            new AttributeInfo(
                "PreprocessingPipeline.tokenizerFactory",
                "org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline",
                "tokenizerFactory",
"Tokenizer factory. Creates the tokenizers to be used by the clustering algorithm.",
null,
"Tokenizer factory",
"Creates the tokenizers to be used by the clustering algorithm.",
"Preprocessing",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo stemmerFactory = 
            new AttributeInfo(
                "PreprocessingPipeline.stemmerFactory",
                "org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline",
                "stemmerFactory",
"Stemmer factory. Creates the stemmers to be used by the clustering algorithm.",
null,
"Stemmer factory",
"Creates the stemmers to be used by the clustering algorithm.",
"Preprocessing",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo lexicalDataFactory = 
            new AttributeInfo(
                "PreprocessingPipeline.lexicalDataFactory",
                "org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline",
                "lexicalDataFactory",
"Lexical data factory. Creates the lexical data to be used by the clustering\nalgorithm, including stop word and stop label dictionaries.",
null,
"Lexical data factory",
"Creates the lexical data to be used by the clustering algorithm, including stop word and stop label dictionaries.",
"Preprocessing",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );


        /**
         * Attributes of the nested {@link org.carrot2.text.preprocessing.Tokenizer} component.
         */
        public final org.carrot2.text.preprocessing.TokenizerDescriptor.Attributes tokenizer =
            org.carrot2.text.preprocessing.TokenizerDescriptor.attributes;

        /**
         * Attributes of the nested {@link org.carrot2.text.preprocessing.CaseNormalizer} component.
         */
        public final org.carrot2.text.preprocessing.CaseNormalizerDescriptor.Attributes caseNormalizer =
            org.carrot2.text.preprocessing.CaseNormalizerDescriptor.attributes;

        /**
         * Attributes of the nested {@link org.carrot2.text.preprocessing.LanguageModelStemmer} component.
         */
        public final org.carrot2.text.preprocessing.LanguageModelStemmerDescriptor.Attributes languageModelStemmer =
            org.carrot2.text.preprocessing.LanguageModelStemmerDescriptor.attributes;

        /**
         * Attributes of the nested {@link org.carrot2.text.preprocessing.StopListMarker} component.
         */
        public final org.carrot2.text.preprocessing.StopListMarkerDescriptor.Attributes stopListMarker =
            org.carrot2.text.preprocessing.StopListMarkerDescriptor.attributes;

    }

    /**
     * Attribute map builder for the  {@link org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline} component. You can use this
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
         * Tokenizer factory. Creates the tokenizers to be used by the clustering algorithm.
         * 
         * @see org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline#tokenizerFactory 
         */
        public AttributeBuilder tokenizerFactory(org.carrot2.text.linguistic.ITokenizerFactory value)
        {
            map.put("PreprocessingPipeline.tokenizerFactory", value);
            return this;
        }

        

        /**
         * Tokenizer factory. Creates the tokenizers to be used by the clustering algorithm.
         *
         * A class that extends org.carrot2.text.linguistic.ITokenizerFactory or appropriate IObjectFactory.
         * 
         * @see org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline#tokenizerFactory
         */
        public AttributeBuilder tokenizerFactory(Class<?> clazz)
        {
            map.put("PreprocessingPipeline.tokenizerFactory", clazz);
            return this;
        }

        

        /**
         * Tokenizer factory. Creates the tokenizers to be used by the clustering algorithm.
         * 
         * @see org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline#tokenizerFactory 
         */
        public AttributeBuilder tokenizerFactory(IObjectFactory<? extends org.carrot2.text.linguistic.ITokenizerFactory> value)
        {
            map.put("PreprocessingPipeline.tokenizerFactory", value);
            return this;
        }

        

        

        /**
         * Stemmer factory. Creates the stemmers to be used by the clustering algorithm.
         * 
         * @see org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline#stemmerFactory 
         */
        public AttributeBuilder stemmerFactory(org.carrot2.text.linguistic.IStemmerFactory value)
        {
            map.put("PreprocessingPipeline.stemmerFactory", value);
            return this;
        }

        

        /**
         * Stemmer factory. Creates the stemmers to be used by the clustering algorithm.
         *
         * A class that extends org.carrot2.text.linguistic.IStemmerFactory or appropriate IObjectFactory.
         * 
         * @see org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline#stemmerFactory
         */
        public AttributeBuilder stemmerFactory(Class<?> clazz)
        {
            map.put("PreprocessingPipeline.stemmerFactory", clazz);
            return this;
        }

        

        /**
         * Stemmer factory. Creates the stemmers to be used by the clustering algorithm.
         * 
         * @see org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline#stemmerFactory 
         */
        public AttributeBuilder stemmerFactory(IObjectFactory<? extends org.carrot2.text.linguistic.IStemmerFactory> value)
        {
            map.put("PreprocessingPipeline.stemmerFactory", value);
            return this;
        }

        

        

        /**
         * Lexical data factory. Creates the lexical data to be used by the clustering
algorithm, including stop word and stop label dictionaries.
         * 
         * @see org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline#lexicalDataFactory 
         */
        public AttributeBuilder lexicalDataFactory(org.carrot2.text.linguistic.ILexicalDataFactory value)
        {
            map.put("PreprocessingPipeline.lexicalDataFactory", value);
            return this;
        }

        

        /**
         * Lexical data factory. Creates the lexical data to be used by the clustering
algorithm, including stop word and stop label dictionaries.
         *
         * A class that extends org.carrot2.text.linguistic.ILexicalDataFactory or appropriate IObjectFactory.
         * 
         * @see org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline#lexicalDataFactory
         */
        public AttributeBuilder lexicalDataFactory(Class<?> clazz)
        {
            map.put("PreprocessingPipeline.lexicalDataFactory", clazz);
            return this;
        }

        

        /**
         * Lexical data factory. Creates the lexical data to be used by the clustering
algorithm, including stop word and stop label dictionaries.
         * 
         * @see org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipeline#lexicalDataFactory 
         */
        public AttributeBuilder lexicalDataFactory(IObjectFactory<? extends org.carrot2.text.linguistic.ILexicalDataFactory> value)
        {
            map.put("PreprocessingPipeline.lexicalDataFactory", value);
            return this;
        }

        

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.text.preprocessing.Tokenizer} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.text.preprocessing.TokenizerDescriptor.AttributeBuilder tokenizer()
        {
            return org.carrot2.text.preprocessing.TokenizerDescriptor.attributeBuilder(map);
        }

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.text.preprocessing.CaseNormalizer} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.text.preprocessing.CaseNormalizerDescriptor.AttributeBuilder caseNormalizer()
        {
            return org.carrot2.text.preprocessing.CaseNormalizerDescriptor.attributeBuilder(map);
        }

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.text.preprocessing.LanguageModelStemmer} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.text.preprocessing.LanguageModelStemmerDescriptor.AttributeBuilder languageModelStemmer()
        {
            return org.carrot2.text.preprocessing.LanguageModelStemmerDescriptor.attributeBuilder(map);
        }

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.text.preprocessing.StopListMarker} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.text.preprocessing.StopListMarkerDescriptor.AttributeBuilder stopListMarker()
        {
            return org.carrot2.text.preprocessing.StopListMarkerDescriptor.attributeBuilder(map);
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
