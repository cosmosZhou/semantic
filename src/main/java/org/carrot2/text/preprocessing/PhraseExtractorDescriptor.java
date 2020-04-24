

// APT-generated file.

package org.carrot2.text.preprocessing;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.text.preprocessing.PhraseExtractor} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.text.preprocessing.PhraseExtractor
 */
public final class PhraseExtractorDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.text.preprocessing.PhraseExtractor";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "PhraseExtractor";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Extracts frequent phrases from the provided document";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "A frequent phrase is a sequence of words that appears in the documents more than once. This phrase extractor aggregates different inflection variants of phrase words into one phrase, returning the most frequent variant. For example, if phrase <i>computing science</i> appears 2 times and <i>computer sciences</i> appears 4 times, the latter will be returned with aggregated frequency of 6. <p> This class saves the following results to the <code>PreprocessingContext</code>: <ul> <li><code>AllPhrases.wordIndices</code></li> <li><code>AllPhrases.tf</code></li> <li><code>AllPhrases.tfByDocument</code></li> <li><code>AllTokens.suffixOrder</code></li> <li><code>AllTokens.lcp</code></li> </ul> <p> This class requires that <code>Tokenizer</code>, <code>CaseNormalizer</code> and <code>LanguageModelStemmer</code> be invoked first.";

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
        ownAttrs.add(attributes.dfThreshold);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.text.preprocessing.PhraseExtractorDescriptor.attributes.dfThreshold);

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
     * Constants for all attribute keys of the {@link org.carrot2.text.preprocessing.PhraseExtractor} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.text.preprocessing.PhraseExtractor#dfThreshold}. */
        public static final String DF_THRESHOLD = "PhraseExtractor.dfThreshold";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.text.preprocessing.PhraseExtractor} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo dfThreshold = 
            new AttributeInfo(
                "PhraseExtractor.dfThreshold",
                "org.carrot2.text.preprocessing.PhraseExtractor",
                "dfThreshold",
"Phrase Document Frequency threshold. Phrases appearing in fewer than\n<code>dfThreshold</code> documents will be ignored.",
"Phrase document frequency threshold",
"Phrase Document Frequency threshold",
"Phrases appearing in fewer than <code>dfThreshold</code> documents will be ignored.",
"Phrase extraction",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.text.preprocessing.PhraseExtractor} component. You can use this
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
         * Phrase Document Frequency threshold. Phrases appearing in fewer than
<code>dfThreshold</code> documents will be ignored.
         * 
         * @see org.carrot2.text.preprocessing.PhraseExtractor#dfThreshold 
         */
        public AttributeBuilder dfThreshold(int value)
        {
            map.put("PhraseExtractor.dfThreshold", value);
            return this;
        }

        

        

        /**
         * Phrase Document Frequency threshold. Phrases appearing in fewer than
<code>dfThreshold</code> documents will be ignored.
         * 
         * @see org.carrot2.text.preprocessing.PhraseExtractor#dfThreshold 
         */
        public AttributeBuilder dfThreshold(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("PhraseExtractor.dfThreshold", value);
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
