

// APT-generated file.

package org.carrot2.text.vsm;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.text.vsm.TermDocumentMatrixBuilder} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.text.vsm.TermDocumentMatrixBuilder
 */
public final class TermDocumentMatrixBuilderDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.text.vsm.TermDocumentMatrixBuilder";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "TermDocumentMatrixBuilder";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Builds a term document matrix based on the provided <code>PreprocessingContext</code>";
    
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
        ownAttrs.add(attributes.titleWordsBoost);
        ownAttrs.add(attributes.maximumMatrixSize);
        ownAttrs.add(attributes.maxWordDf);
        ownAttrs.add(attributes.termWeighting);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.text.vsm.TermDocumentMatrixBuilderDescriptor.attributes.titleWordsBoost);
        allAttrs.add(org.carrot2.text.vsm.TermDocumentMatrixBuilderDescriptor.attributes.maximumMatrixSize);
        allAttrs.add(org.carrot2.text.vsm.TermDocumentMatrixBuilderDescriptor.attributes.maxWordDf);
        allAttrs.add(org.carrot2.text.vsm.TermDocumentMatrixBuilderDescriptor.attributes.termWeighting);

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
     * Constants for all attribute keys of the {@link org.carrot2.text.vsm.TermDocumentMatrixBuilder} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.text.vsm.TermDocumentMatrixBuilder#titleWordsBoost}. */
        public static final String TITLE_WORDS_BOOST = "TermDocumentMatrixBuilder.titleWordsBoost";
        /** Attribute key for: {@link org.carrot2.text.vsm.TermDocumentMatrixBuilder#maximumMatrixSize}. */
        public static final String MAXIMUM_MATRIX_SIZE = "TermDocumentMatrixBuilder.maximumMatrixSize";
        /** Attribute key for: {@link org.carrot2.text.vsm.TermDocumentMatrixBuilder#maxWordDf}. */
        public static final String MAX_WORD_DF = "TermDocumentMatrixBuilder.maxWordDf";
        /** Attribute key for: {@link org.carrot2.text.vsm.TermDocumentMatrixBuilder#termWeighting}. */
        public static final String TERM_WEIGHTING = "TermDocumentMatrixBuilder.termWeighting";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.text.vsm.TermDocumentMatrixBuilder} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo titleWordsBoost = 
            new AttributeInfo(
                "TermDocumentMatrixBuilder.titleWordsBoost",
                "org.carrot2.text.vsm.TermDocumentMatrixBuilder",
                "titleWordsBoost",
"Title word boost. Gives more weight to words that appeared in\n{@link org.carrot2.core.Document#TITLE} fields.",
null,
"Title word boost",
"Gives more weight to words that appeared in <code>org.carrot2.core.Document.TITLE</code> fields.",
"Labels",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo maximumMatrixSize = 
            new AttributeInfo(
                "TermDocumentMatrixBuilder.maximumMatrixSize",
                "org.carrot2.text.vsm.TermDocumentMatrixBuilder",
                "maximumMatrixSize",
"Maximum matrix size. The maximum number of the term-document matrix elements. The\nlarger the size, the more accurate, time- and memory-consuming clustering.",
null,
"Maximum matrix size",
"The maximum number of the term-document matrix elements. The larger the size, the more accurate, time- and memory-consuming clustering.",
"Matrix model",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo maxWordDf = 
            new AttributeInfo(
                "TermDocumentMatrixBuilder.maxWordDf",
                "org.carrot2.text.vsm.TermDocumentMatrixBuilder",
                "maxWordDf",
"Maximum word document frequency. The maximum document frequency allowed for words\nas a fraction of all documents. Words with document frequency larger than\n<code>maxWordDf</code> will be ignored. For example, when <code>maxWordDf</code> is\n<code>0.4</code>, words appearing in more than 40% of documents will be be ignored.\nA value of <code>1.0</code> means that all words will be taken into\naccount, no matter in how many documents they appear.\n<p>\nThis attribute may be useful when certain words appear in most of the input\ndocuments (e.g. company name from header or footer) and such words dominate the\ncluster labels. In such case, setting <code>maxWordDf</code> to a value lower than\n<code>1.0</code>, e.g. <code>0.9</code> may improve the clusters. \n</p>\n<p>\nAnother useful application of this attribute is when there is a need to generate\nonly very specific clusters, i.e. clusters containing small numbers of documents.\nThis can be achieved by setting <code>maxWordDf</code> to extremely low values,\ne.g. <code>0.1</code> or <code>0.05</code>.\n</p>",
null,
"Maximum word document frequency",
"The maximum document frequency allowed for words as a fraction of all documents. Words with document frequency larger than <code>maxWordDf</code> will be ignored. For example, when <code>maxWordDf</code> is <code>0.4</code>, words appearing in more than 40% of documents will be be ignored. A value of <code>1.0</code> means that all words will be taken into account, no matter in how many documents they appear. <p> This attribute may be useful when certain words appear in most of the input documents (e.g. company name from header or footer) and such words dominate the cluster labels. In such case, setting <code>maxWordDf</code> to a value lower than <code>1.0</code>, e.g. <code>0.9</code> may improve the clusters. </p> <p> Another useful application of this attribute is when there is a need to generate only very specific clusters, i.e. clusters containing small numbers of documents. This can be achieved by setting <code>maxWordDf</code> to extremely low values, e.g. <code>0.1</code> or <code>0.05</code>. </p>",
"Matrix model",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo termWeighting = 
            new AttributeInfo(
                "TermDocumentMatrixBuilder.termWeighting",
                "org.carrot2.text.vsm.TermDocumentMatrixBuilder",
                "termWeighting",
"Term weighting. The method for calculating weight of words in the term-document\nmatrices.",
null,
"Term weighting",
"The method for calculating weight of words in the term-document matrices.",
"Matrix model",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.text.vsm.TermDocumentMatrixBuilder} component. You can use this
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
         * Title word boost. Gives more weight to words that appeared in
{@link org.carrot2.core.Document#TITLE} fields.
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixBuilder#titleWordsBoost 
         */
        public AttributeBuilder titleWordsBoost(double value)
        {
            map.put("TermDocumentMatrixBuilder.titleWordsBoost", value);
            return this;
        }

        

        

        /**
         * Title word boost. Gives more weight to words that appeared in
{@link org.carrot2.core.Document#TITLE} fields.
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixBuilder#titleWordsBoost 
         */
        public AttributeBuilder titleWordsBoost(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("TermDocumentMatrixBuilder.titleWordsBoost", value);
            return this;
        }

        

        

        /**
         * Maximum matrix size. The maximum number of the term-document matrix elements. The
larger the size, the more accurate, time- and memory-consuming clustering.
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixBuilder#maximumMatrixSize 
         */
        public AttributeBuilder maximumMatrixSize(int value)
        {
            map.put("TermDocumentMatrixBuilder.maximumMatrixSize", value);
            return this;
        }

        

        

        /**
         * Maximum matrix size. The maximum number of the term-document matrix elements. The
larger the size, the more accurate, time- and memory-consuming clustering.
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixBuilder#maximumMatrixSize 
         */
        public AttributeBuilder maximumMatrixSize(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("TermDocumentMatrixBuilder.maximumMatrixSize", value);
            return this;
        }

        

        

        /**
         * Maximum word document frequency. The maximum document frequency allowed for words
as a fraction of all documents. Words with document frequency larger than
<code>maxWordDf</code> will be ignored. For example, when <code>maxWordDf</code> is
<code>0.4</code>, words appearing in more than 40% of documents will be be ignored.
A value of <code>1.0</code> means that all words will be taken into
account, no matter in how many documents they appear.
<p>
This attribute may be useful when certain words appear in most of the input
documents (e.g. company name from header or footer) and such words dominate the
cluster labels. In such case, setting <code>maxWordDf</code> to a value lower than
<code>1.0</code>, e.g. <code>0.9</code> may improve the clusters. 
</p>
<p>
Another useful application of this attribute is when there is a need to generate
only very specific clusters, i.e. clusters containing small numbers of documents.
This can be achieved by setting <code>maxWordDf</code> to extremely low values,
e.g. <code>0.1</code> or <code>0.05</code>.
</p>
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixBuilder#maxWordDf 
         */
        public AttributeBuilder maxWordDf(double value)
        {
            map.put("TermDocumentMatrixBuilder.maxWordDf", value);
            return this;
        }

        

        

        /**
         * Maximum word document frequency. The maximum document frequency allowed for words
as a fraction of all documents. Words with document frequency larger than
<code>maxWordDf</code> will be ignored. For example, when <code>maxWordDf</code> is
<code>0.4</code>, words appearing in more than 40% of documents will be be ignored.
A value of <code>1.0</code> means that all words will be taken into
account, no matter in how many documents they appear.
<p>
This attribute may be useful when certain words appear in most of the input
documents (e.g. company name from header or footer) and such words dominate the
cluster labels. In such case, setting <code>maxWordDf</code> to a value lower than
<code>1.0</code>, e.g. <code>0.9</code> may improve the clusters. 
</p>
<p>
Another useful application of this attribute is when there is a need to generate
only very specific clusters, i.e. clusters containing small numbers of documents.
This can be achieved by setting <code>maxWordDf</code> to extremely low values,
e.g. <code>0.1</code> or <code>0.05</code>.
</p>
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixBuilder#maxWordDf 
         */
        public AttributeBuilder maxWordDf(IObjectFactory<? extends java.lang.Double> value)
        {
            map.put("TermDocumentMatrixBuilder.maxWordDf", value);
            return this;
        }

        

        

        /**
         * Term weighting. The method for calculating weight of words in the term-document
matrices.
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixBuilder#termWeighting 
         */
        public AttributeBuilder termWeighting(org.carrot2.text.vsm.ITermWeighting value)
        {
            map.put("TermDocumentMatrixBuilder.termWeighting", value);
            return this;
        }

        

        /**
         * Term weighting. The method for calculating weight of words in the term-document
matrices.
         *
         * A class that extends org.carrot2.text.vsm.ITermWeighting or appropriate IObjectFactory.
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixBuilder#termWeighting
         */
        public AttributeBuilder termWeighting(Class<?> clazz)
        {
            map.put("TermDocumentMatrixBuilder.termWeighting", clazz);
            return this;
        }

        

        /**
         * Term weighting. The method for calculating weight of words in the term-document
matrices.
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixBuilder#termWeighting 
         */
        public AttributeBuilder termWeighting(IObjectFactory<? extends org.carrot2.text.vsm.ITermWeighting> value)
        {
            map.put("TermDocumentMatrixBuilder.termWeighting", value);
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
