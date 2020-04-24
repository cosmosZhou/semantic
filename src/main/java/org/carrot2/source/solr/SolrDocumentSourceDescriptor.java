

// APT-generated file.

package org.carrot2.source.solr;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.solr.SolrDocumentSource} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.solr.SolrDocumentSource
 */
public final class SolrDocumentSourceDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.solr.SolrDocumentSource";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "SolrDocumentSource";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Fetches documents from an instance of Solr";
    
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
        ownAttrs.add(attributes.serviceUrlBase);
        ownAttrs.add(attributes.solrFilterQuery);
        ownAttrs.add(attributes.solrTitleFieldName);
        ownAttrs.add(attributes.solrSummaryFieldName);
        ownAttrs.add(attributes.solrUrlFieldName);
        ownAttrs.add(attributes.solrIdFieldName);
        ownAttrs.add(attributes.solrXsltAdapter);
        ownAttrs.add(attributes.readClusters);
        ownAttrs.add(attributes.useHighlighterOutput);
        ownAttrs.add(attributes.copyFields);
        ownAttrs.add(attributes.clusters);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.solr.SolrDocumentSourceDescriptor.attributes.serviceUrlBase);
        allAttrs.add(org.carrot2.source.solr.SolrDocumentSourceDescriptor.attributes.solrFilterQuery);
        allAttrs.add(org.carrot2.source.solr.SolrDocumentSourceDescriptor.attributes.solrTitleFieldName);
        allAttrs.add(org.carrot2.source.solr.SolrDocumentSourceDescriptor.attributes.solrSummaryFieldName);
        allAttrs.add(org.carrot2.source.solr.SolrDocumentSourceDescriptor.attributes.solrUrlFieldName);
        allAttrs.add(org.carrot2.source.solr.SolrDocumentSourceDescriptor.attributes.solrIdFieldName);
        allAttrs.add(org.carrot2.source.solr.SolrDocumentSourceDescriptor.attributes.solrXsltAdapter);
        allAttrs.add(org.carrot2.source.solr.SolrDocumentSourceDescriptor.attributes.readClusters);
        allAttrs.add(org.carrot2.source.solr.SolrDocumentSourceDescriptor.attributes.useHighlighterOutput);
        allAttrs.add(org.carrot2.source.solr.SolrDocumentSourceDescriptor.attributes.copyFields);
        allAttrs.add(org.carrot2.source.solr.SolrDocumentSourceDescriptor.attributes.clusters);
        allAttrs.add(org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBaseDescriptor.attributes.redirectStrategy);
        allAttrs.add(org.carrot2.source.SearchEngineBaseDescriptor.attributes.start);
        allAttrs.add(org.carrot2.source.SearchEngineBaseDescriptor.attributes.results);
        allAttrs.add(org.carrot2.source.SearchEngineBaseDescriptor.attributes.query);
        allAttrs.add(org.carrot2.source.SearchEngineBaseDescriptor.attributes.resultsTotal);
        allAttrs.add(org.carrot2.source.SearchEngineBaseDescriptor.attributes.documents);
        allAttrs.add(org.carrot2.source.SearchEngineBaseDescriptor.attributes.compressed);

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
     * Constants for all attribute keys of the {@link org.carrot2.source.solr.SolrDocumentSource} component.
     */
    public static class Keys  extends org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBaseDescriptor.Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.solr.SolrDocumentSource#serviceUrlBase}. */
        public static final String SERVICE_URL_BASE = "SolrDocumentSource.serviceUrlBase";
        /** Attribute key for: {@link org.carrot2.source.solr.SolrDocumentSource#solrFilterQuery}. */
        public static final String SOLR_FILTER_QUERY = "SolrDocumentSource.solrFilterQuery";
        /** Attribute key for: {@link org.carrot2.source.solr.SolrDocumentSource#solrTitleFieldName}. */
        public static final String SOLR_TITLE_FIELD_NAME = "SolrDocumentSource.solrTitleFieldName";
        /** Attribute key for: {@link org.carrot2.source.solr.SolrDocumentSource#solrSummaryFieldName}. */
        public static final String SOLR_SUMMARY_FIELD_NAME = "SolrDocumentSource.solrSummaryFieldName";
        /** Attribute key for: {@link org.carrot2.source.solr.SolrDocumentSource#solrUrlFieldName}. */
        public static final String SOLR_URL_FIELD_NAME = "SolrDocumentSource.solrUrlFieldName";
        /** Attribute key for: {@link org.carrot2.source.solr.SolrDocumentSource#solrIdFieldName}. */
        public static final String SOLR_ID_FIELD_NAME = "SolrDocumentSource.solrIdFieldName";
        /** Attribute key for: {@link org.carrot2.source.solr.SolrDocumentSource#solrXsltAdapter}. */
        public static final String SOLR_XSLT_ADAPTER = "SolrDocumentSource.solrXsltAdapter";
        /** Attribute key for: {@link org.carrot2.source.solr.SolrDocumentSource#readClusters}. */
        public static final String READ_CLUSTERS = "SolrDocumentSource.readClusters";
        /** Attribute key for: {@link org.carrot2.source.solr.SolrDocumentSource#useHighlighterOutput}. */
        public static final String USE_HIGHLIGHTER_OUTPUT = "SolrDocumentSource.useHighlighterOutput";
        /** Attribute key for: {@link org.carrot2.source.solr.SolrDocumentSource#copyFields}. */
        public static final String COPY_FIELDS = "SolrDocumentSource.copyFields";
        /** Attribute key for: {@link org.carrot2.source.solr.SolrDocumentSource#clusters}. */
        public static final String CLUSTERS = "clusters";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.solr.SolrDocumentSource} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo serviceUrlBase = 
            new AttributeInfo(
                "SolrDocumentSource.serviceUrlBase",
                "org.carrot2.source.solr.SolrDocumentSource",
                "serviceUrlBase",
"Solr service URL base. The URL base can contain additional Solr parameters, \nfor example: <tt>http://localhost:8983/solr/select?fq=timestemp:[NOW-24HOUR TO NOW]</tt>",
"Service URL",
"Solr service URL base",
"The URL base can contain additional Solr parameters, for example: <tt>http://localhost:8983/solr/select?fq=timestemp:[NOW-24HOUR TO NOW]</tt>",
"Service",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo solrFilterQuery = 
            new AttributeInfo(
                "SolrDocumentSource.solrFilterQuery",
                "org.carrot2.source.solr.SolrDocumentSource",
                "solrFilterQuery",
"Filter query appended to {@link org.carrot2.source.solr.SolrDocumentSource#serviceUrlBase}.",
"Filter query",
"Filter query appended to <code>org.carrot2.source.solr.SolrDocumentSource.serviceUrlBase</code>",
null,
"Service",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo solrTitleFieldName = 
            new AttributeInfo(
                "SolrDocumentSource.solrTitleFieldName",
                "org.carrot2.source.solr.SolrDocumentSource",
                "solrTitleFieldName",
"Title field name. Name of the Solr field that will provide document titles.",
"Title field name",
"Title field name",
"Name of the Solr field that will provide document titles.",
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo solrSummaryFieldName = 
            new AttributeInfo(
                "SolrDocumentSource.solrSummaryFieldName",
                "org.carrot2.source.solr.SolrDocumentSource",
                "solrSummaryFieldName",
"Summary field name. Name of the Solr field that will provide document summary.",
"Summary field name",
"Summary field name",
"Name of the Solr field that will provide document summary.",
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo solrUrlFieldName = 
            new AttributeInfo(
                "SolrDocumentSource.solrUrlFieldName",
                "org.carrot2.source.solr.SolrDocumentSource",
                "solrUrlFieldName",
"URL field name. Name of the Solr field that will provide document URLs.",
"URL field name",
"URL field name",
"Name of the Solr field that will provide document URLs.",
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo solrIdFieldName = 
            new AttributeInfo(
                "SolrDocumentSource.solrIdFieldName",
                "org.carrot2.source.solr.SolrDocumentSource",
                "solrIdFieldName",
"Document identifier field name (specified in Solr schema). This field is necessary\nto connect Solr-side clusters or highlighter output to documents.",
"ID field name",
"Document identifier field name (specified in Solr schema)",
"This field is necessary to connect Solr-side clusters or highlighter output to documents.",
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo solrXsltAdapter = 
            new AttributeInfo(
                "SolrDocumentSource.solrXsltAdapter",
                "org.carrot2.source.solr.SolrDocumentSource",
                "solrXsltAdapter",
"Provides a custom XSLT stylesheet for converting from Solr's output to\nan XML format <a href=\"http://download.carrot2.org/head/manual/index.html#section.architecture.xml-formats\">\nparsed by Carrot2</a>. For performance reasons this attribute\ncan be provided at initialization time only (no processing-time overrides).",
"Custom XSLT adapter from Solr to Carrot2 format",
"Provides a custom XSLT stylesheet for converting from Solr's output to an XML format <a href=\"http://download.carrot2.org/head/manual/index.html.section.architecture.xml-formats\"> parsed by Carrot2</a>",
"For performance reasons this attribute can be provided at initialization time only (no processing-time overrides).",
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo readClusters = 
            new AttributeInfo(
                "SolrDocumentSource.readClusters",
                "org.carrot2.source.solr.SolrDocumentSource",
                "readClusters",
"If clusters are present in the Solr output they will be read and exposed to components\nfurther down the processing chain. Note that {@link org.carrot2.source.solr.SolrDocumentSource#solrIdFieldName} is required to match\ndocument references.",
"Read Solr clusters if present",
"If clusters are present in the Solr output they will be read and exposed to components further down the processing chain",
"Note that <code>org.carrot2.source.solr.SolrDocumentSource.solrIdFieldName</code> is required to match document references.",
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo useHighlighterOutput = 
            new AttributeInfo(
                "SolrDocumentSource.useHighlighterOutput",
                "org.carrot2.source.solr.SolrDocumentSource",
                "useHighlighterOutput",
"If highlighter fragments are present in the Solr output they will be used (and preferred) over full\nfield content. This may be used to decrease the memory required for clustering. In general if highlighter\nis used the contents of full fields won't be emitted from Solr though (because it makes little sense).\n\n<p>Setting this option to <code>false</code> will disable using the highlighter output\nentirely.</p>",
"Use highlighter output if present",
"If highlighter fragments are present in the Solr output they will be used (and preferred) over full field content",
"This may be used to decrease the memory required for clustering. In general if highlighter is used the contents of full fields won't be emitted from Solr though (because it makes little sense). <p>Setting this option to <code>false</code> will disable using the highlighter output entirely.</p>",
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo copyFields = 
            new AttributeInfo(
                "SolrDocumentSource.copyFields",
                "org.carrot2.source.solr.SolrDocumentSource",
                "copyFields",
"Copy Solr fields from the search result to Carrot2 {@link org.carrot2.core.Document} instances (as fields).",
"Copy Solr document fields",
"Copy Solr fields from the search result to Carrot2 <code>org.carrot2.core.Document</code> instances (as fields)",
null,
"Index field mapping",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo clusters = 
            new AttributeInfo(
                "clusters",
                "org.carrot2.source.solr.SolrDocumentSource",
                "clusters",
"If {@link org.carrot2.source.solr.SolrDocumentSource#readClusters} is <code>true</code> and clusters are present in the input\nXML, they will be deserialized and exposed to components further down the processing\nchain.",
"Clusters",
"If <code>org.carrot2.source.solr.SolrDocumentSource.readClusters</code> is <code>true</code> and clusters are present in the input XML, they will be deserialized and exposed to components further down the processing chain",
null,
"Search result information",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );


    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.solr.SolrDocumentSource} component. You can use this
     * builder as a type-safe alternative to populating the attribute map using attribute keys.
     */
    public static class AttributeBuilder  extends org.carrot2.source.xml.RemoteXmlSimpleSearchEngineBaseDescriptor.AttributeBuilder 
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
         * Solr service URL base. The URL base can contain additional Solr parameters, 
for example: <tt>http://localhost:8983/solr/select?fq=timestemp:[NOW-24HOUR TO NOW]</tt>
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#serviceUrlBase 
         */
        public AttributeBuilder serviceUrlBase(java.lang.String value)
        {
            map.put("SolrDocumentSource.serviceUrlBase", value);
            return this;
        }

        

        

        /**
         * Solr service URL base. The URL base can contain additional Solr parameters, 
for example: <tt>http://localhost:8983/solr/select?fq=timestemp:[NOW-24HOUR TO NOW]</tt>
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#serviceUrlBase 
         */
        public AttributeBuilder serviceUrlBase(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("SolrDocumentSource.serviceUrlBase", value);
            return this;
        }

        

        

        /**
         * Filter query appended to {@link org.carrot2.source.solr.SolrDocumentSource#serviceUrlBase}.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrFilterQuery 
         */
        public AttributeBuilder solrFilterQuery(java.lang.String value)
        {
            map.put("SolrDocumentSource.solrFilterQuery", value);
            return this;
        }

        

        

        /**
         * Filter query appended to {@link org.carrot2.source.solr.SolrDocumentSource#serviceUrlBase}.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrFilterQuery 
         */
        public AttributeBuilder solrFilterQuery(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("SolrDocumentSource.solrFilterQuery", value);
            return this;
        }

        

        

        /**
         * Title field name. Name of the Solr field that will provide document titles.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrTitleFieldName 
         */
        public AttributeBuilder solrTitleFieldName(java.lang.String value)
        {
            map.put("SolrDocumentSource.solrTitleFieldName", value);
            return this;
        }

        

        

        /**
         * Title field name. Name of the Solr field that will provide document titles.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrTitleFieldName 
         */
        public AttributeBuilder solrTitleFieldName(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("SolrDocumentSource.solrTitleFieldName", value);
            return this;
        }

        

        

        /**
         * Summary field name. Name of the Solr field that will provide document summary.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrSummaryFieldName 
         */
        public AttributeBuilder solrSummaryFieldName(java.lang.String value)
        {
            map.put("SolrDocumentSource.solrSummaryFieldName", value);
            return this;
        }

        

        

        /**
         * Summary field name. Name of the Solr field that will provide document summary.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrSummaryFieldName 
         */
        public AttributeBuilder solrSummaryFieldName(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("SolrDocumentSource.solrSummaryFieldName", value);
            return this;
        }

        

        

        /**
         * URL field name. Name of the Solr field that will provide document URLs.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrUrlFieldName 
         */
        public AttributeBuilder solrUrlFieldName(java.lang.String value)
        {
            map.put("SolrDocumentSource.solrUrlFieldName", value);
            return this;
        }

        

        

        /**
         * URL field name. Name of the Solr field that will provide document URLs.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrUrlFieldName 
         */
        public AttributeBuilder solrUrlFieldName(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("SolrDocumentSource.solrUrlFieldName", value);
            return this;
        }

        

        

        /**
         * Document identifier field name (specified in Solr schema). This field is necessary
to connect Solr-side clusters or highlighter output to documents.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrIdFieldName 
         */
        public AttributeBuilder solrIdFieldName(java.lang.String value)
        {
            map.put("SolrDocumentSource.solrIdFieldName", value);
            return this;
        }

        

        

        /**
         * Document identifier field name (specified in Solr schema). This field is necessary
to connect Solr-side clusters or highlighter output to documents.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrIdFieldName 
         */
        public AttributeBuilder solrIdFieldName(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("SolrDocumentSource.solrIdFieldName", value);
            return this;
        }

        

        

        /**
         * Provides a custom XSLT stylesheet for converting from Solr's output to
an XML format <a href="http://download.carrot2.org/head/manual/index.html#section.architecture.xml-formats">
parsed by Carrot2</a>. For performance reasons this attribute
can be provided at initialization time only (no processing-time overrides).
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrXsltAdapter 
         */
        public AttributeBuilder solrXsltAdapter(org.carrot2.util.resource.IResource value)
        {
            map.put("SolrDocumentSource.solrXsltAdapter", value);
            return this;
        }

        

        /**
         * Provides a custom XSLT stylesheet for converting from Solr's output to
an XML format <a href="http://download.carrot2.org/head/manual/index.html#section.architecture.xml-formats">
parsed by Carrot2</a>. For performance reasons this attribute
can be provided at initialization time only (no processing-time overrides).
         *
         * A class that extends org.carrot2.util.resource.IResource or appropriate IObjectFactory.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrXsltAdapter
         */
        public AttributeBuilder solrXsltAdapter(Class<?> clazz)
        {
            map.put("SolrDocumentSource.solrXsltAdapter", clazz);
            return this;
        }

        

        /**
         * Provides a custom XSLT stylesheet for converting from Solr's output to
an XML format <a href="http://download.carrot2.org/head/manual/index.html#section.architecture.xml-formats">
parsed by Carrot2</a>. For performance reasons this attribute
can be provided at initialization time only (no processing-time overrides).
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#solrXsltAdapter 
         */
        public AttributeBuilder solrXsltAdapter(IObjectFactory<? extends org.carrot2.util.resource.IResource> value)
        {
            map.put("SolrDocumentSource.solrXsltAdapter", value);
            return this;
        }

        

        

        /**
         * If clusters are present in the Solr output they will be read and exposed to components
further down the processing chain. Note that {@link org.carrot2.source.solr.SolrDocumentSource#solrIdFieldName} is required to match
document references.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#readClusters 
         */
        public AttributeBuilder readClusters(boolean value)
        {
            map.put("SolrDocumentSource.readClusters", value);
            return this;
        }

        

        

        /**
         * If clusters are present in the Solr output they will be read and exposed to components
further down the processing chain. Note that {@link org.carrot2.source.solr.SolrDocumentSource#solrIdFieldName} is required to match
document references.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#readClusters 
         */
        public AttributeBuilder readClusters(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("SolrDocumentSource.readClusters", value);
            return this;
        }

        

        

        /**
         * If highlighter fragments are present in the Solr output they will be used (and preferred) over full
field content. This may be used to decrease the memory required for clustering. In general if highlighter
is used the contents of full fields won't be emitted from Solr though (because it makes little sense).

<p>Setting this option to <code>false</code> will disable using the highlighter output
entirely.</p>
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#useHighlighterOutput 
         */
        public AttributeBuilder useHighlighterOutput(boolean value)
        {
            map.put("SolrDocumentSource.useHighlighterOutput", value);
            return this;
        }

        

        

        /**
         * If highlighter fragments are present in the Solr output they will be used (and preferred) over full
field content. This may be used to decrease the memory required for clustering. In general if highlighter
is used the contents of full fields won't be emitted from Solr though (because it makes little sense).

<p>Setting this option to <code>false</code> will disable using the highlighter output
entirely.</p>
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#useHighlighterOutput 
         */
        public AttributeBuilder useHighlighterOutput(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("SolrDocumentSource.useHighlighterOutput", value);
            return this;
        }

        

        

        /**
         * Copy Solr fields from the search result to Carrot2 {@link org.carrot2.core.Document} instances (as fields).
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#copyFields 
         */
        public AttributeBuilder copyFields(boolean value)
        {
            map.put("SolrDocumentSource.copyFields", value);
            return this;
        }

        

        

        /**
         * Copy Solr fields from the search result to Carrot2 {@link org.carrot2.core.Document} instances (as fields).
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#copyFields 
         */
        public AttributeBuilder copyFields(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("SolrDocumentSource.copyFields", value);
            return this;
        }

        

        

        /**
         * If {@link org.carrot2.source.solr.SolrDocumentSource#readClusters} is <code>true</code> and clusters are present in the input
XML, they will be deserialized and exposed to components further down the processing
chain.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#clusters 
         */
        public AttributeBuilder clusters(java.util.List<org.carrot2.core.Cluster> value)
        {
            map.put("clusters", value);
            return this;
        }

        

        

        /**
         * If {@link org.carrot2.source.solr.SolrDocumentSource#readClusters} is <code>true</code> and clusters are present in the input
XML, they will be deserialized and exposed to components further down the processing
chain.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#clusters 
         */
        public AttributeBuilder clusters(IObjectFactory<? extends java.util.List<org.carrot2.core.Cluster>> value)
        {
            map.put("clusters", value);
            return this;
        }

        

        /**
         * If {@link org.carrot2.source.solr.SolrDocumentSource#readClusters} is <code>true</code> and clusters are present in the input
XML, they will be deserialized and exposed to components further down the processing
chain.
         * 
         * @see org.carrot2.source.solr.SolrDocumentSource#clusters 
         */
@SuppressWarnings("unchecked")        public java.util.List<org.carrot2.core.Cluster> clusters()
        {
            return (java.util.List<org.carrot2.core.Cluster>) map.get("clusters");
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
