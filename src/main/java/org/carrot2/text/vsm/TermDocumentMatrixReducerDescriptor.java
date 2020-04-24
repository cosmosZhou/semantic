

// APT-generated file.

package org.carrot2.text.vsm;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.text.vsm.TermDocumentMatrixReducer} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.text.vsm.TermDocumentMatrixReducer
 */
public final class TermDocumentMatrixReducerDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.text.vsm.TermDocumentMatrixReducer";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "TermDocumentMatrixReducer";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Reduces the dimensionality of a term-document matrix using a matrix factorization algorithm";
    
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
        ownAttrs.add(attributes.factorizationFactory);
        ownAttrs.add(attributes.factorizationQuality);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.text.vsm.TermDocumentMatrixReducerDescriptor.attributes.factorizationFactory);
        allAttrs.add(org.carrot2.text.vsm.TermDocumentMatrixReducerDescriptor.attributes.factorizationQuality);

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
     * Constants for all attribute keys of the {@link org.carrot2.text.vsm.TermDocumentMatrixReducer} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.text.vsm.TermDocumentMatrixReducer#factorizationFactory}. */
        public static final String FACTORIZATION_FACTORY = "TermDocumentMatrixReducer.factorizationFactory";
        /** Attribute key for: {@link org.carrot2.text.vsm.TermDocumentMatrixReducer#factorizationQuality}. */
        public static final String FACTORIZATION_QUALITY = "TermDocumentMatrixReducer.factorizationQuality";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.text.vsm.TermDocumentMatrixReducer} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo factorizationFactory = 
            new AttributeInfo(
                "TermDocumentMatrixReducer.factorizationFactory",
                "org.carrot2.text.vsm.TermDocumentMatrixReducer",
                "factorizationFactory",
"Factorization method. The method to be used to factorize the term-document matrix\nand create base vectors that will give rise to cluster labels.",
"Factorization method",
"Factorization method",
"The method to be used to factorize the term-document matrix and create base vectors that will give rise to cluster labels.",
"Matrix model",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo factorizationQuality = 
            new AttributeInfo(
                "TermDocumentMatrixReducer.factorizationQuality",
                "org.carrot2.text.vsm.TermDocumentMatrixReducer",
                "factorizationQuality",
"Factorization quality. The number of iterations of matrix factorization to perform.\nThe higher the required quality, the more time-consuming clustering.",
"Factorization quality",
"Factorization quality",
"The number of iterations of matrix factorization to perform. The higher the required quality, the more time-consuming clustering.",
"Matrix model",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.text.vsm.TermDocumentMatrixReducer} component. You can use this
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
         * Factorization method. The method to be used to factorize the term-document matrix
and create base vectors that will give rise to cluster labels.
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixReducer#factorizationFactory 
         */
        public AttributeBuilder factorizationFactory(org.carrot2.matrix.factorization.IMatrixFactorizationFactory value)
        {
            map.put("TermDocumentMatrixReducer.factorizationFactory", value);
            return this;
        }

        

        /**
         * Factorization method. The method to be used to factorize the term-document matrix
and create base vectors that will give rise to cluster labels.
         *
         * A class that extends org.carrot2.matrix.factorization.IMatrixFactorizationFactory or appropriate IObjectFactory.
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixReducer#factorizationFactory
         */
        public AttributeBuilder factorizationFactory(Class<?> clazz)
        {
            map.put("TermDocumentMatrixReducer.factorizationFactory", clazz);
            return this;
        }

        

        /**
         * Factorization method. The method to be used to factorize the term-document matrix
and create base vectors that will give rise to cluster labels.
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixReducer#factorizationFactory 
         */
        public AttributeBuilder factorizationFactory(IObjectFactory<? extends org.carrot2.matrix.factorization.IMatrixFactorizationFactory> value)
        {
            map.put("TermDocumentMatrixReducer.factorizationFactory", value);
            return this;
        }

        

        

        /**
         * Factorization quality. The number of iterations of matrix factorization to perform.
The higher the required quality, the more time-consuming clustering.
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixReducer#factorizationQuality 
         */
        public AttributeBuilder factorizationQuality(org.carrot2.matrix.factorization.IterationNumberGuesser.FactorizationQuality value)
        {
            map.put("TermDocumentMatrixReducer.factorizationQuality", value);
            return this;
        }

        

        

        /**
         * Factorization quality. The number of iterations of matrix factorization to perform.
The higher the required quality, the more time-consuming clustering.
         * 
         * @see org.carrot2.text.vsm.TermDocumentMatrixReducer#factorizationQuality 
         */
        public AttributeBuilder factorizationQuality(IObjectFactory<? extends org.carrot2.matrix.factorization.IterationNumberGuesser.FactorizationQuality> value)
        {
            map.put("TermDocumentMatrixReducer.factorizationQuality", value);
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
