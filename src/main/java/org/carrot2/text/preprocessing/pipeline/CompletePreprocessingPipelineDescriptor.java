

// APT-generated file.

package org.carrot2.text.preprocessing.pipeline;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.text.preprocessing.pipeline.CompletePreprocessingPipeline} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.text.preprocessing.pipeline.CompletePreprocessingPipeline
 */
public final class CompletePreprocessingPipelineDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.text.preprocessing.pipeline.CompletePreprocessingPipeline";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "PreprocessingPipeline";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Performs a complete preprocessing on the provided documents";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "The preprocessing consists of the following steps: <ol> <li><code>Tokenizer.tokenize(PreprocessingContext)</code></li> <li><code>CaseNormalizer.normalize(PreprocessingContext)</code></li> <li><code>LanguageModelStemmer.stem(PreprocessingContext)</code></li> <li><code>StopListMarker.mark(PreprocessingContext)</code></li> <li><code>PhraseExtractor.extractPhrases(PreprocessingContext)</code></li> <li><code>LabelFilterProcessor.process(PreprocessingContext)</code></li> <li><code>DocumentAssigner.assign(PreprocessingContext)</code></li> </ol>";

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
     * Constants for all attribute keys of the {@link org.carrot2.text.preprocessing.pipeline.CompletePreprocessingPipeline} component.
     */
    public static class Keys  extends org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipelineDescriptor.Keys 
    {
        protected Keys() {} 

    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.text.preprocessing.pipeline.CompletePreprocessingPipeline} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }


        /**
         * Attributes of the nested {@link org.carrot2.text.preprocessing.PhraseExtractor} component.
         */
        public final org.carrot2.text.preprocessing.PhraseExtractorDescriptor.Attributes phraseExtractor =
            org.carrot2.text.preprocessing.PhraseExtractorDescriptor.attributes;

        /**
         * Attributes of the nested {@link org.carrot2.text.preprocessing.LabelFilterProcessor} component.
         */
        public final org.carrot2.text.preprocessing.LabelFilterProcessorDescriptor.Attributes labelFilterProcessor =
            org.carrot2.text.preprocessing.LabelFilterProcessorDescriptor.attributes;

        /**
         * Attributes of the nested {@link org.carrot2.text.preprocessing.DocumentAssigner} component.
         */
        public final org.carrot2.text.preprocessing.DocumentAssignerDescriptor.Attributes documentAssigner =
            org.carrot2.text.preprocessing.DocumentAssignerDescriptor.attributes;

    }

    /**
     * Attribute map builder for the  {@link org.carrot2.text.preprocessing.pipeline.CompletePreprocessingPipeline} component. You can use this
     * builder as a type-safe alternative to populating the attribute map using attribute keys.
     */
    public static class AttributeBuilder  extends org.carrot2.text.preprocessing.pipeline.BasicPreprocessingPipelineDescriptor.AttributeBuilder 
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
         * Returns an attribute builder for the nested
         * {@link org.carrot2.text.preprocessing.PhraseExtractor} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.text.preprocessing.PhraseExtractorDescriptor.AttributeBuilder phraseExtractor()
        {
            return org.carrot2.text.preprocessing.PhraseExtractorDescriptor.attributeBuilder(map);
        }

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.text.preprocessing.LabelFilterProcessor} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.text.preprocessing.LabelFilterProcessorDescriptor.AttributeBuilder labelFilterProcessor()
        {
            return org.carrot2.text.preprocessing.LabelFilterProcessorDescriptor.attributeBuilder(map);
        }

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.text.preprocessing.DocumentAssigner} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.text.preprocessing.DocumentAssignerDescriptor.AttributeBuilder documentAssigner()
        {
            return org.carrot2.text.preprocessing.DocumentAssignerDescriptor.attributeBuilder(map);
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
