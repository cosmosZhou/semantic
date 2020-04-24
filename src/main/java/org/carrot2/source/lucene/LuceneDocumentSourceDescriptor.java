

// APT-generated file.

package org.carrot2.source.lucene;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.lucene.LuceneDocumentSource} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.lucene.LuceneDocumentSource
 */
public final class LuceneDocumentSourceDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.lucene.LuceneDocumentSource";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "LuceneDocumentSource";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "A <code>IDocumentSource</code> fetching <code>Document</code>s from a local Apache Lucene index";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "The index should be binary-compatible with the Lucene version actually imported by this plugin.";

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
        ownAttrs.add(attributes.results);
        ownAttrs.add(attributes.resultsTotal);
        ownAttrs.add(attributes.documents);
        ownAttrs.add(attributes.directory);
        ownAttrs.add(attributes.analyzer);
        ownAttrs.add(attributes.fieldMapper);
        ownAttrs.add(attributes.query);
        ownAttrs.add(attributes.keepLuceneDocuments);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.lucene.LuceneDocumentSourceDescriptor.attributes.results);
        allAttrs.add(org.carrot2.source.lucene.LuceneDocumentSourceDescriptor.attributes.resultsTotal);
        allAttrs.add(org.carrot2.source.lucene.LuceneDocumentSourceDescriptor.attributes.documents);
        allAttrs.add(org.carrot2.source.lucene.LuceneDocumentSourceDescriptor.attributes.directory);
        allAttrs.add(org.carrot2.source.lucene.LuceneDocumentSourceDescriptor.attributes.analyzer);
        allAttrs.add(org.carrot2.source.lucene.LuceneDocumentSourceDescriptor.attributes.fieldMapper);
        allAttrs.add(org.carrot2.source.lucene.LuceneDocumentSourceDescriptor.attributes.query);
        allAttrs.add(org.carrot2.source.lucene.LuceneDocumentSourceDescriptor.attributes.keepLuceneDocuments);

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
     * Constants for all attribute keys of the {@link org.carrot2.source.lucene.LuceneDocumentSource} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.lucene.LuceneDocumentSource#results}. */
        public static final String RESULTS = "results";
        /** Attribute key for: {@link org.carrot2.source.lucene.LuceneDocumentSource#resultsTotal}. */
        public static final String RESULTS_TOTAL = "results-total";
        /** Attribute key for: {@link org.carrot2.source.lucene.LuceneDocumentSource#documents}. */
        public static final String DOCUMENTS = "documents";
        /** Attribute key for: {@link org.carrot2.source.lucene.LuceneDocumentSource#directory}. */
        public static final String DIRECTORY = "LuceneDocumentSource.directory";
        /** Attribute key for: {@link org.carrot2.source.lucene.LuceneDocumentSource#analyzer}. */
        public static final String ANALYZER = "LuceneDocumentSource.analyzer";
        /** Attribute key for: {@link org.carrot2.source.lucene.LuceneDocumentSource#fieldMapper}. */
        public static final String FIELD_MAPPER = "LuceneDocumentSource.fieldMapper";
        /** Attribute key for: {@link org.carrot2.source.lucene.LuceneDocumentSource#query}. */
        public static final String QUERY = "query";
        /** Attribute key for: {@link org.carrot2.source.lucene.LuceneDocumentSource#keepLuceneDocuments}. */
        public static final String KEEP_LUCENE_DOCUMENTS = "LuceneDocumentSource.keepLuceneDocuments";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.lucene.LuceneDocumentSource} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#results
         */
        public final AttributeInfo results = 
            new AttributeInfo(
                "results",
                "org.carrot2.source.lucene.LuceneDocumentSource",
                "results",
null,
null,
null,
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.results
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#resultsTotal
         */
        public final AttributeInfo resultsTotal = 
            new AttributeInfo(
                "results-total",
                "org.carrot2.source.lucene.LuceneDocumentSource",
                "resultsTotal",
null,
null,
null,
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.resultsTotal
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#documents
         */
        public final AttributeInfo documents = 
            new AttributeInfo(
                "documents",
                "org.carrot2.source.lucene.LuceneDocumentSource",
                "documents",
null,
null,
null,
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.documents
            );

        /**
         *          */
        public final AttributeInfo directory = 
            new AttributeInfo(
                "LuceneDocumentSource.directory",
                "org.carrot2.source.lucene.LuceneDocumentSource",
                "directory",
"Search index {@link org.apache.lucene.store.Directory}. Must be unlocked for\nreading.",
"Index directory",
"Search index <code>org.apache.lucene.store.Directory</code>",
"Must be unlocked for reading.",
"Index properties",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo analyzer = 
            new AttributeInfo(
                "LuceneDocumentSource.analyzer",
                "org.carrot2.source.lucene.LuceneDocumentSource",
                "analyzer",
"{@link org.apache.lucene.analysis.Analyzer} used at indexing time. The same\nanalyzer should be used for querying.",
"Analyzer",
"<code>org.apache.lucene.analysis.Analyzer</code> used at indexing time",
"The same analyzer should be used for querying.",
"Index properties",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo fieldMapper = 
            new AttributeInfo(
                "LuceneDocumentSource.fieldMapper",
                "org.carrot2.source.lucene.LuceneDocumentSource",
                "fieldMapper",
"{@link IFieldMapper} provides the link between Carrot2\n{@link org.carrot2.core.Document} fields and Lucene index fields.",
"Field mapper",
"<code>IFieldMapper</code> provides the link between Carrot2 <code>org.carrot2.core.Document</code> fields and Lucene index fields",
null,
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo query = 
            new AttributeInfo(
                "query",
                "org.carrot2.source.lucene.LuceneDocumentSource",
                "query",
"A pre-parsed {@link org.apache.lucene.search.Query} object or a {@link String}\nparsed using the built-in classic QueryParser over a\nset of search fields returned from the {@link org.carrot2.source.lucene.LuceneDocumentSource#fieldMapper}.",
"Query",
"A pre-parsed <code>org.apache.lucene.search.Query</code> object or a <code>String</code> parsed using the built-in classic QueryParser over a set of search fields returned from the <code>org.carrot2.source.lucene.LuceneDocumentSource.fieldMapper</code>",
null,
"Search query",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo keepLuceneDocuments = 
            new AttributeInfo(
                "LuceneDocumentSource.keepLuceneDocuments",
                "org.carrot2.source.lucene.LuceneDocumentSource",
                "keepLuceneDocuments",
"Keeps references to Lucene document instances in Carrot2 documents. Please bear in\nmind two limitations:\n<ul>\n<li><strong>Lucene documents will not be serialized to XML/JSON.</strong>\nTherefore, they can only be accessed when invoking clustering through Carrot2 Java\nAPI. To pass some of the fields of Lucene documents to Carrot2 XML/JSON output,\nimplement a custom {@link IFieldMapper} that will store those fields as regular\nCarrot2 fields.</li>\n<li><strong>Increased memory usage</strong> when using a {@link org.carrot2.core.Controller}\n{@link org.carrot2.core.ControllerFactory#createCachingPooling(Class...) configured to cache} the\noutput from {@link LuceneDocumentSource}.</li>\n</ul>",
"Keep Lucene documents",
"Keeps references to Lucene document instances in Carrot2 documents",
"Please bear in mind two limitations: <ul> <li><strong>Lucene documents will not be serialized to XML/JSON.</strong> Therefore, they can only be accessed when invoking clustering through Carrot2 Java API. To pass some of the fields of Lucene documents to Carrot2 XML/JSON output, implement a custom <code>IFieldMapper</code> that will store those fields as regular Carrot2 fields.</li> <li><strong>Increased memory usage</strong> when using a <code>org.carrot2.core.Controller</code> <code>org.carrot2.core.ControllerFactory.createCachingPooling(Class...) configured to cache</code> the output from <code>LuceneDocumentSource</code>.</li> </ul>",
"Search result information",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.lucene.LuceneDocumentSource} component. You can use this
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
         * @see org.carrot2.source.lucene.LuceneDocumentSource#results 
         */
        public AttributeBuilder results(int value)
        {
            map.put("results", value);
            return this;
        }

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#results 
         */
        public AttributeBuilder results(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("results", value);
            return this;
        }

        

        

        

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#resultsTotal 
         */
        public long resultsTotal()
        {
            return (java.lang.Long) map.get("results-total");
        }

        

        

        

        

        /**
         * 
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#documents 
         */
@SuppressWarnings("unchecked")        public java.util.Collection<org.carrot2.core.Document> documents()
        {
            return (java.util.Collection<org.carrot2.core.Document>) map.get("documents");
        }

        

        /**
         * Search index {@link org.apache.lucene.store.Directory}. Must be unlocked for
reading.
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#directory 
         */
        public AttributeBuilder directory(org.apache.lucene.store.Directory value)
        {
            map.put("LuceneDocumentSource.directory", value);
            return this;
        }

        

        /**
         * Search index {@link org.apache.lucene.store.Directory}. Must be unlocked for
reading.
         *
         * A class that extends org.apache.lucene.store.Directory or appropriate IObjectFactory.
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#directory
         */
        public AttributeBuilder directory(Class<?> clazz)
        {
            map.put("LuceneDocumentSource.directory", clazz);
            return this;
        }

        

        /**
         * Search index {@link org.apache.lucene.store.Directory}. Must be unlocked for
reading.
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#directory 
         */
        public AttributeBuilder directory(IObjectFactory<? extends org.apache.lucene.store.Directory> value)
        {
            map.put("LuceneDocumentSource.directory", value);
            return this;
        }

        

        

        /**
         * {@link org.apache.lucene.analysis.Analyzer} used at indexing time. The same
analyzer should be used for querying.
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#analyzer 
         */
        public AttributeBuilder analyzer(org.apache.lucene.analysis.Analyzer value)
        {
            map.put("LuceneDocumentSource.analyzer", value);
            return this;
        }

        

        /**
         * {@link org.apache.lucene.analysis.Analyzer} used at indexing time. The same
analyzer should be used for querying.
         *
         * A class that extends org.apache.lucene.analysis.Analyzer or appropriate IObjectFactory.
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#analyzer
         */
        public AttributeBuilder analyzer(Class<?> clazz)
        {
            map.put("LuceneDocumentSource.analyzer", clazz);
            return this;
        }

        

        /**
         * {@link org.apache.lucene.analysis.Analyzer} used at indexing time. The same
analyzer should be used for querying.
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#analyzer 
         */
        public AttributeBuilder analyzer(IObjectFactory<? extends org.apache.lucene.analysis.Analyzer> value)
        {
            map.put("LuceneDocumentSource.analyzer", value);
            return this;
        }

        

        

        /**
         * {@link IFieldMapper} provides the link between Carrot2
{@link org.carrot2.core.Document} fields and Lucene index fields.
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#fieldMapper 
         */
        public AttributeBuilder fieldMapper(org.carrot2.source.lucene.IFieldMapper value)
        {
            map.put("LuceneDocumentSource.fieldMapper", value);
            return this;
        }

        

        /**
         * {@link IFieldMapper} provides the link between Carrot2
{@link org.carrot2.core.Document} fields and Lucene index fields.
         *
         * A class that extends org.carrot2.source.lucene.IFieldMapper or appropriate IObjectFactory.
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#fieldMapper
         */
        public AttributeBuilder fieldMapper(Class<?> clazz)
        {
            map.put("LuceneDocumentSource.fieldMapper", clazz);
            return this;
        }

        

        /**
         * {@link IFieldMapper} provides the link between Carrot2
{@link org.carrot2.core.Document} fields and Lucene index fields.
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#fieldMapper 
         */
        public AttributeBuilder fieldMapper(IObjectFactory<? extends org.carrot2.source.lucene.IFieldMapper> value)
        {
            map.put("LuceneDocumentSource.fieldMapper", value);
            return this;
        }

        

        

        /**
         * A pre-parsed {@link org.apache.lucene.search.Query} object or a {@link String}
parsed using the built-in classic QueryParser over a
set of search fields returned from the {@link org.carrot2.source.lucene.LuceneDocumentSource#fieldMapper}.
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#query 
         */
        public AttributeBuilder query(java.lang.Object value)
        {
            map.put("query", value);
            return this;
        }

        

        

        /**
         * A pre-parsed {@link org.apache.lucene.search.Query} object or a {@link String}
parsed using the built-in classic QueryParser over a
set of search fields returned from the {@link org.carrot2.source.lucene.LuceneDocumentSource#fieldMapper}.
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#query 
         */
        public AttributeBuilder query(IObjectFactory<? extends java.lang.Object> value)
        {
            map.put("query", value);
            return this;
        }

        

        

        /**
         * Keeps references to Lucene document instances in Carrot2 documents. Please bear in
mind two limitations:
<ul>
<li><strong>Lucene documents will not be serialized to XML/JSON.</strong>
Therefore, they can only be accessed when invoking clustering through Carrot2 Java
API. To pass some of the fields of Lucene documents to Carrot2 XML/JSON output,
implement a custom {@link IFieldMapper} that will store those fields as regular
Carrot2 fields.</li>
<li><strong>Increased memory usage</strong> when using a {@link org.carrot2.core.Controller}
{@link org.carrot2.core.ControllerFactory#createCachingPooling(Class...) configured to cache} the
output from {@link LuceneDocumentSource}.</li>
</ul>
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#keepLuceneDocuments 
         */
        public AttributeBuilder keepLuceneDocuments(boolean value)
        {
            map.put("LuceneDocumentSource.keepLuceneDocuments", value);
            return this;
        }

        

        

        /**
         * Keeps references to Lucene document instances in Carrot2 documents. Please bear in
mind two limitations:
<ul>
<li><strong>Lucene documents will not be serialized to XML/JSON.</strong>
Therefore, they can only be accessed when invoking clustering through Carrot2 Java
API. To pass some of the fields of Lucene documents to Carrot2 XML/JSON output,
implement a custom {@link IFieldMapper} that will store those fields as regular
Carrot2 fields.</li>
<li><strong>Increased memory usage</strong> when using a {@link org.carrot2.core.Controller}
{@link org.carrot2.core.ControllerFactory#createCachingPooling(Class...) configured to cache} the
output from {@link LuceneDocumentSource}.</li>
</ul>
         * 
         * @see org.carrot2.source.lucene.LuceneDocumentSource#keepLuceneDocuments 
         */
        public AttributeBuilder keepLuceneDocuments(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("LuceneDocumentSource.keepLuceneDocuments", value);
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
