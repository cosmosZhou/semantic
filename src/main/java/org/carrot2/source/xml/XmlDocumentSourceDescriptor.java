

// APT-generated file.

package org.carrot2.source.xml;

//Imported for JavaDoc references mostly.
import org.carrot2.util.attribute.*;

import java.util.*;

/**
 * Metadata and attributes of the {@link org.carrot2.source.xml.XmlDocumentSource} component. You can use 
 * this descriptor to obtain metadata, such as human readable name and description, about the component 
 * as a whole as well as about its attributes. Using the {@link #attributeBuilder(Map)}
 * you can obtain a builder for type-safe generation of the attribute maps. Please see the
 * <a href="{@docRoot}/overview-summary.html#setting-attributes">main overview</a> for a complete code example. 
 * 
 * Generated from org.carrot2.source.xml.XmlDocumentSource
 */
public final class XmlDocumentSourceDescriptor implements IBindableDescriptor
{
    /**
     * The component class for which this descriptor was generated. 
     */
    public final String bindableClassName = "org.carrot2.source.xml.XmlDocumentSource";

    /**
     * Attribute prefix used by the component.
     */
    public final String prefix = "XmlDocumentSource";

    /**
     * A one sentence summary of the component. It could be presented as a header of the tool
     * tip of the corresponding UI component.
     */
    public final String title = "Fetches documents from XML files and streams";
    
    /**
     * A short label for the component. It can be presented as a label of the
     * corresponding UI component.
     */
    public final String label = "";

    /**
     * A longer, possibly multi sentence, description of the component. It could be presented
     * as a body of the tool tip of the corresponding UI component.
     */
    public final String description = "For additional flexibility, an XSLT stylesheet can be applied to the XML stream before it is deserialized into Carrot2 data.";

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
        ownAttrs.add(attributes.xml);
        ownAttrs.add(attributes.xslt);
        ownAttrs.add(attributes.xmlParameters);
        ownAttrs.add(attributes.xsltParameters);
        ownAttrs.add(attributes.query);
        ownAttrs.add(attributes.results);
        ownAttrs.add(attributes.readClusters);
        ownAttrs.add(attributes.readAll);
        ownAttrs.add(attributes.title);
        ownAttrs.add(attributes.documents);
        ownAttrs.add(attributes.clusters);

        final Set<AttributeInfo> allAttrs = new HashSet<AttributeInfo>();
        allAttrs.add(org.carrot2.source.xml.XmlDocumentSourceDescriptor.attributes.xml);
        allAttrs.add(org.carrot2.source.xml.XmlDocumentSourceDescriptor.attributes.xslt);
        allAttrs.add(org.carrot2.source.xml.XmlDocumentSourceDescriptor.attributes.xmlParameters);
        allAttrs.add(org.carrot2.source.xml.XmlDocumentSourceDescriptor.attributes.xsltParameters);
        allAttrs.add(org.carrot2.source.xml.XmlDocumentSourceDescriptor.attributes.query);
        allAttrs.add(org.carrot2.source.xml.XmlDocumentSourceDescriptor.attributes.results);
        allAttrs.add(org.carrot2.source.xml.XmlDocumentSourceDescriptor.attributes.readClusters);
        allAttrs.add(org.carrot2.source.xml.XmlDocumentSourceDescriptor.attributes.readAll);
        allAttrs.add(org.carrot2.source.xml.XmlDocumentSourceDescriptor.attributes.title);
        allAttrs.add(org.carrot2.source.xml.XmlDocumentSourceDescriptor.attributes.documents);
        allAttrs.add(org.carrot2.source.xml.XmlDocumentSourceDescriptor.attributes.clusters);

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
     * Constants for all attribute keys of the {@link org.carrot2.source.xml.XmlDocumentSource} component.
     */
    public static class Keys 
    {
        protected Keys() {} 

        /** Attribute key for: {@link org.carrot2.source.xml.XmlDocumentSource#xml}. */
        public static final String XML = "XmlDocumentSource.xml";
        /** Attribute key for: {@link org.carrot2.source.xml.XmlDocumentSource#xslt}. */
        public static final String XSLT = "XmlDocumentSource.xslt";
        /** Attribute key for: {@link org.carrot2.source.xml.XmlDocumentSource#xmlParameters}. */
        public static final String XML_PARAMETERS = "XmlDocumentSource.xmlParameters";
        /** Attribute key for: {@link org.carrot2.source.xml.XmlDocumentSource#xsltParameters}. */
        public static final String XSLT_PARAMETERS = "XmlDocumentSource.xsltParameters";
        /** Attribute key for: {@link org.carrot2.source.xml.XmlDocumentSource#query}. */
        public static final String QUERY = "query";
        /** Attribute key for: {@link org.carrot2.source.xml.XmlDocumentSource#results}. */
        public static final String RESULTS = "results";
        /** Attribute key for: {@link org.carrot2.source.xml.XmlDocumentSource#readClusters}. */
        public static final String READ_CLUSTERS = "XmlDocumentSource.readClusters";
        /** Attribute key for: {@link org.carrot2.source.xml.XmlDocumentSource#readAll}. */
        public static final String READ_ALL = "XmlDocumentSource.readAll";
        /** Attribute key for: {@link org.carrot2.source.xml.XmlDocumentSource#title}. */
        public static final String TITLE = "processing-result.title";
        /** Attribute key for: {@link org.carrot2.source.xml.XmlDocumentSource#documents}. */
        public static final String DOCUMENTS = "documents";
        /** Attribute key for: {@link org.carrot2.source.xml.XmlDocumentSource#clusters}. */
        public static final String CLUSTERS = "clusters";
    }


    /* Attribute descriptors. */

    /**
     * All attributes of the {@link org.carrot2.source.xml.XmlDocumentSource} component.
     */
    public static final class Attributes
    {
        private Attributes() { /* No public instances. */ }

        /**
         *          */
        public final AttributeInfo xml = 
            new AttributeInfo(
                "XmlDocumentSource.xml",
                "org.carrot2.source.xml.XmlDocumentSource",
                "xml",
"The resource to load XML data from. You can either create instances of\n{@link org.carrot2.util.resource.IResource} implementations directly or use \n{@link org.carrot2.util.resource.ResourceLookup} to look up\n{@link org.carrot2.util.resource.IResource} instances from a variety of locations.\n<p>\nOne special {@link org.carrot2.util.resource.IResource} implementation you can use is\n{@link org.carrot2.util.resource.URLResourceWithParams}. It allows you to specify attribute placeholders in\nthe URL that will be replaced with actual values at runtime. The placeholder format\nis <code>${attribute}</code>. The following common attributes will be substituted:\n</p>\n<ul>\n<li><code>query</code> will be replaced with the current query being processed. If\nthe query has not been provided, this attribute will fall back to an empty string.</li>\n<li><code>results</code> will be replaced with the number of results requested. If\nthe number of results has not been provided, this attribute will be substituted\nwith an empty string.</li>\n</ul>\n<p>\nAdditionally, custom placeholders can be used. Values for the custom placeholders\nshould be provided in the {@link org.carrot2.source.xml.XmlDocumentSource#xmlParameters} attribute.\n</p>",
"XML resource",
"The resource to load XML data from",
"You can either create instances of <code>org.carrot2.util.resource.IResource</code> implementations directly or use <code>org.carrot2.util.resource.ResourceLookup</code> to look up <code>org.carrot2.util.resource.IResource</code> instances from a variety of locations. <p> One special <code>org.carrot2.util.resource.IResource</code> implementation you can use is <code>org.carrot2.util.resource.URLResourceWithParams</code>. It allows you to specify attribute placeholders in the URL that will be replaced with actual values at runtime. The placeholder format is <code>${attribute}</code>. The following common attributes will be substituted: </p> <ul> <li><code>query</code> will be replaced with the current query being processed. If the query has not been provided, this attribute will fall back to an empty string.</li> <li><code>results</code> will be replaced with the number of results requested. If the number of results has not been provided, this attribute will be substituted with an empty string.</li> </ul> <p> Additionally, custom placeholders can be used. Values for the custom placeholders should be provided in the <code>org.carrot2.source.xml.XmlDocumentSource.xmlParameters</code> attribute. </p>",
"XML data",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo xslt = 
            new AttributeInfo(
                "XmlDocumentSource.xslt",
                "org.carrot2.source.xml.XmlDocumentSource",
                "xslt",
"The resource to load XSLT stylesheet from. The XSLT stylesheet is optional and is\nuseful when the source XML stream does not follow the Carrot2 format. The XSLT\ntransformation will be applied to the source XML stream, the transformed XML stream\nwill be deserialized into {@link org.carrot2.core.Document}s.\n<p>\nThe XSLT {@link org.carrot2.util.resource.IResource} can be provided both on initialization and processing\ntime. The stylesheet provided on initialization will be cached for the life time of\nthe component, while processing-time style sheets will be compiled every time\nprocessing is requested and will override the initialization-time stylesheet.\n</p>\n<p>\nTo pass additional parameters to the XSLT transformer, use the\n{@link org.carrot2.source.xml.XmlDocumentSource#xsltParameters} attribute.\n</p>",
"XSLT stylesheet",
"The resource to load XSLT stylesheet from",
"The XSLT stylesheet is optional and is useful when the source XML stream does not follow the Carrot2 format. The XSLT transformation will be applied to the source XML stream, the transformed XML stream will be deserialized into <code>org.carrot2.core.Document</code>s. <p> The XSLT <code>org.carrot2.util.resource.IResource</code> can be provided both on initialization and processing time. The stylesheet provided on initialization will be cached for the life time of the component, while processing-time style sheets will be compiled every time processing is requested and will override the initialization-time stylesheet. </p> <p> To pass additional parameters to the XSLT transformer, use the <code>org.carrot2.source.xml.XmlDocumentSource.xsltParameters</code> attribute. </p>",
"XML transformation",
org.carrot2.util.attribute.AttributeLevel.MEDIUM,
null
            );

        /**
         *          */
        public final AttributeInfo xmlParameters = 
            new AttributeInfo(
                "XmlDocumentSource.xmlParameters",
                "org.carrot2.source.xml.XmlDocumentSource",
                "xmlParameters",
"Values for custom placeholders in the XML URL. If the type of resource provided in\nthe {@link org.carrot2.source.xml.XmlDocumentSource#xml} attribute is {@link org.carrot2.util.resource.URLResourceWithParams}, this map provides\nvalues for custom placeholders found in the XML URL. Keys of the map correspond to\nplaceholder names, values of the map will be used to replace the placeholders.\nPlease see {@link org.carrot2.source.xml.XmlDocumentSource#xml} for the placeholder syntax.",
"XML parameters",
"Values for custom placeholders in the XML URL",
"If the type of resource provided in the <code>org.carrot2.source.xml.XmlDocumentSource.xml</code> attribute is <code>org.carrot2.util.resource.URLResourceWithParams</code>, this map provides values for custom placeholders found in the XML URL. Keys of the map correspond to placeholder names, values of the map will be used to replace the placeholders. Please see <code>org.carrot2.source.xml.XmlDocumentSource.xml</code> for the placeholder syntax.",
"XML data",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         *          */
        public final AttributeInfo xsltParameters = 
            new AttributeInfo(
                "XmlDocumentSource.xsltParameters",
                "org.carrot2.source.xml.XmlDocumentSource",
                "xsltParameters",
"Parameters to be passed to the XSLT transformer. Keys of the map will be used as\nparameter names, values of the map as parameter values.",
"XSLT parameters",
"Parameters to be passed to the XSLT transformer",
"Keys of the map will be used as parameter names, values of the map as parameter values.",
"XML transformation",
org.carrot2.util.attribute.AttributeLevel.ADVANCED,
null
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#query
         */
        public final AttributeInfo query = 
            new AttributeInfo(
                "query",
                "org.carrot2.source.xml.XmlDocumentSource",
                "query",
"After processing this field may hold the query read from the XML data, if any. For\nthe semantics of this field on input, see {@link org.carrot2.source.xml.XmlDocumentSource#xml}.",
null,
"After processing this field may hold the query read from the XML data, if any",
"For the semantics of this field on input, see <code>org.carrot2.source.xml.XmlDocumentSource.xml</code>.",
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.query
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#results
         */
        public final AttributeInfo results = 
            new AttributeInfo(
                "results",
                "org.carrot2.source.xml.XmlDocumentSource",
                "results",
"The maximum number of documents to read from the XML data if {@link org.carrot2.source.xml.XmlDocumentSource#readAll} is\n<code>false</code>.",
null,
"The maximum number of documents to read from the XML data if <code>org.carrot2.source.xml.XmlDocumentSource.readAll</code> is <code>false</code>",
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.results
            );

        /**
         *          */
        public final AttributeInfo readClusters = 
            new AttributeInfo(
                "XmlDocumentSource.readClusters",
                "org.carrot2.source.xml.XmlDocumentSource",
                "readClusters",
"If clusters are present in the input XML they will be read and exposed to components\nfurther down the processing chain.",
"Read clusters from input",
"If clusters are present in the input XML they will be read and exposed to components further down the processing chain",
null,
"XML transformation",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         *          */
        public final AttributeInfo readAll = 
            new AttributeInfo(
                "XmlDocumentSource.readAll",
                "org.carrot2.source.xml.XmlDocumentSource",
                "readAll",
"If <code>true</code>, all documents are read from the input XML stream, regardless\nof the limit set by {@link org.carrot2.source.xml.XmlDocumentSource#results}.",
"Read all documents",
"If <code>true</code>, all documents are read from the input XML stream, regardless of the limit set by <code>org.carrot2.source.xml.XmlDocumentSource.results</code>",
null,
"Search query",
org.carrot2.util.attribute.AttributeLevel.BASIC,
null
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#processingResultTitle
         */
        public final AttributeInfo title = 
            new AttributeInfo(
                "processing-result.title",
                "org.carrot2.source.xml.XmlDocumentSource",
                "title",
"The title (file name or query attribute, if present) for the search result fetched\nfrom the resource.",
null,
"The title (file name or query attribute, if present) for the search result fetched from the resource",
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.processingResultTitle
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#documents
         */
        public final AttributeInfo documents = 
            new AttributeInfo(
                "documents",
                "org.carrot2.source.xml.XmlDocumentSource",
                "documents",
"Documents read from the XML data.",
null,
"Documents read from the XML data",
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.documents
            );

        /**
         * 
         * 
         * @see org.carrot2.core.attribute.CommonAttributes#clusters
         */
        public final AttributeInfo clusters = 
            new AttributeInfo(
                "clusters",
                "org.carrot2.source.xml.XmlDocumentSource",
                "clusters",
"If {@link org.carrot2.source.xml.XmlDocumentSource#readClusters} is <code>true</code> and clusters are present in the input\nXML, they will be deserialized and exposed to components further down the processing\nchain.",
null,
"If <code>org.carrot2.source.xml.XmlDocumentSource.readClusters</code> is <code>true</code> and clusters are present in the input XML, they will be deserialized and exposed to components further down the processing chain",
null,
null,
null,
org.carrot2.core.attribute.CommonAttributesDescriptor.attributes.clusters
            );


        /**
         * Attributes of the nested {@link org.carrot2.source.xml.XmlDocumentSourceHelper} component.
         */
        public final org.carrot2.source.xml.XmlDocumentSourceHelperDescriptor.Attributes xmlDocumentSourceHelper =
            org.carrot2.source.xml.XmlDocumentSourceHelperDescriptor.attributes;

    }

    /**
     * Attribute map builder for the  {@link org.carrot2.source.xml.XmlDocumentSource} component. You can use this
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
         * The resource to load XML data from. You can either create instances of
{@link org.carrot2.util.resource.IResource} implementations directly or use 
{@link org.carrot2.util.resource.ResourceLookup} to look up
{@link org.carrot2.util.resource.IResource} instances from a variety of locations.
<p>
One special {@link org.carrot2.util.resource.IResource} implementation you can use is
{@link org.carrot2.util.resource.URLResourceWithParams}. It allows you to specify attribute placeholders in
the URL that will be replaced with actual values at runtime. The placeholder format
is <code>${attribute}</code>. The following common attributes will be substituted:
</p>
<ul>
<li><code>query</code> will be replaced with the current query being processed. If
the query has not been provided, this attribute will fall back to an empty string.</li>
<li><code>results</code> will be replaced with the number of results requested. If
the number of results has not been provided, this attribute will be substituted
with an empty string.</li>
</ul>
<p>
Additionally, custom placeholders can be used. Values for the custom placeholders
should be provided in the {@link org.carrot2.source.xml.XmlDocumentSource#xmlParameters} attribute.
</p>
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#xml 
         */
        public AttributeBuilder xml(org.carrot2.util.resource.IResource value)
        {
            map.put("XmlDocumentSource.xml", value);
            return this;
        }

        

        /**
         * The resource to load XML data from. You can either create instances of
{@link org.carrot2.util.resource.IResource} implementations directly or use 
{@link org.carrot2.util.resource.ResourceLookup} to look up
{@link org.carrot2.util.resource.IResource} instances from a variety of locations.
<p>
One special {@link org.carrot2.util.resource.IResource} implementation you can use is
{@link org.carrot2.util.resource.URLResourceWithParams}. It allows you to specify attribute placeholders in
the URL that will be replaced with actual values at runtime. The placeholder format
is <code>${attribute}</code>. The following common attributes will be substituted:
</p>
<ul>
<li><code>query</code> will be replaced with the current query being processed. If
the query has not been provided, this attribute will fall back to an empty string.</li>
<li><code>results</code> will be replaced with the number of results requested. If
the number of results has not been provided, this attribute will be substituted
with an empty string.</li>
</ul>
<p>
Additionally, custom placeholders can be used. Values for the custom placeholders
should be provided in the {@link org.carrot2.source.xml.XmlDocumentSource#xmlParameters} attribute.
</p>
         *
         * A class that extends org.carrot2.util.resource.IResource or appropriate IObjectFactory.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#xml
         */
        public AttributeBuilder xml(Class<?> clazz)
        {
            map.put("XmlDocumentSource.xml", clazz);
            return this;
        }

        

        /**
         * The resource to load XML data from. You can either create instances of
{@link org.carrot2.util.resource.IResource} implementations directly or use 
{@link org.carrot2.util.resource.ResourceLookup} to look up
{@link org.carrot2.util.resource.IResource} instances from a variety of locations.
<p>
One special {@link org.carrot2.util.resource.IResource} implementation you can use is
{@link org.carrot2.util.resource.URLResourceWithParams}. It allows you to specify attribute placeholders in
the URL that will be replaced with actual values at runtime. The placeholder format
is <code>${attribute}</code>. The following common attributes will be substituted:
</p>
<ul>
<li><code>query</code> will be replaced with the current query being processed. If
the query has not been provided, this attribute will fall back to an empty string.</li>
<li><code>results</code> will be replaced with the number of results requested. If
the number of results has not been provided, this attribute will be substituted
with an empty string.</li>
</ul>
<p>
Additionally, custom placeholders can be used. Values for the custom placeholders
should be provided in the {@link org.carrot2.source.xml.XmlDocumentSource#xmlParameters} attribute.
</p>
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#xml 
         */
        public AttributeBuilder xml(IObjectFactory<? extends org.carrot2.util.resource.IResource> value)
        {
            map.put("XmlDocumentSource.xml", value);
            return this;
        }

        

        

        /**
         * The resource to load XSLT stylesheet from. The XSLT stylesheet is optional and is
useful when the source XML stream does not follow the Carrot2 format. The XSLT
transformation will be applied to the source XML stream, the transformed XML stream
will be deserialized into {@link org.carrot2.core.Document}s.
<p>
The XSLT {@link org.carrot2.util.resource.IResource} can be provided both on initialization and processing
time. The stylesheet provided on initialization will be cached for the life time of
the component, while processing-time style sheets will be compiled every time
processing is requested and will override the initialization-time stylesheet.
</p>
<p>
To pass additional parameters to the XSLT transformer, use the
{@link org.carrot2.source.xml.XmlDocumentSource#xsltParameters} attribute.
</p>
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#xslt 
         */
        public AttributeBuilder xslt(org.carrot2.util.resource.IResource value)
        {
            map.put("XmlDocumentSource.xslt", value);
            return this;
        }

        

        /**
         * The resource to load XSLT stylesheet from. The XSLT stylesheet is optional and is
useful when the source XML stream does not follow the Carrot2 format. The XSLT
transformation will be applied to the source XML stream, the transformed XML stream
will be deserialized into {@link org.carrot2.core.Document}s.
<p>
The XSLT {@link org.carrot2.util.resource.IResource} can be provided both on initialization and processing
time. The stylesheet provided on initialization will be cached for the life time of
the component, while processing-time style sheets will be compiled every time
processing is requested and will override the initialization-time stylesheet.
</p>
<p>
To pass additional parameters to the XSLT transformer, use the
{@link org.carrot2.source.xml.XmlDocumentSource#xsltParameters} attribute.
</p>
         *
         * A class that extends org.carrot2.util.resource.IResource or appropriate IObjectFactory.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#xslt
         */
        public AttributeBuilder xslt(Class<?> clazz)
        {
            map.put("XmlDocumentSource.xslt", clazz);
            return this;
        }

        

        /**
         * The resource to load XSLT stylesheet from. The XSLT stylesheet is optional and is
useful when the source XML stream does not follow the Carrot2 format. The XSLT
transformation will be applied to the source XML stream, the transformed XML stream
will be deserialized into {@link org.carrot2.core.Document}s.
<p>
The XSLT {@link org.carrot2.util.resource.IResource} can be provided both on initialization and processing
time. The stylesheet provided on initialization will be cached for the life time of
the component, while processing-time style sheets will be compiled every time
processing is requested and will override the initialization-time stylesheet.
</p>
<p>
To pass additional parameters to the XSLT transformer, use the
{@link org.carrot2.source.xml.XmlDocumentSource#xsltParameters} attribute.
</p>
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#xslt 
         */
        public AttributeBuilder xslt(IObjectFactory<? extends org.carrot2.util.resource.IResource> value)
        {
            map.put("XmlDocumentSource.xslt", value);
            return this;
        }

        

        

        /**
         * Values for custom placeholders in the XML URL. If the type of resource provided in
the {@link org.carrot2.source.xml.XmlDocumentSource#xml} attribute is {@link org.carrot2.util.resource.URLResourceWithParams}, this map provides
values for custom placeholders found in the XML URL. Keys of the map correspond to
placeholder names, values of the map will be used to replace the placeholders.
Please see {@link org.carrot2.source.xml.XmlDocumentSource#xml} for the placeholder syntax.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#xmlParameters 
         */
        public AttributeBuilder xmlParameters(java.util.Map<java.lang.String,java.lang.String> value)
        {
            map.put("XmlDocumentSource.xmlParameters", value);
            return this;
        }

        

        

        /**
         * Values for custom placeholders in the XML URL. If the type of resource provided in
the {@link org.carrot2.source.xml.XmlDocumentSource#xml} attribute is {@link org.carrot2.util.resource.URLResourceWithParams}, this map provides
values for custom placeholders found in the XML URL. Keys of the map correspond to
placeholder names, values of the map will be used to replace the placeholders.
Please see {@link org.carrot2.source.xml.XmlDocumentSource#xml} for the placeholder syntax.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#xmlParameters 
         */
        public AttributeBuilder xmlParameters(IObjectFactory<? extends java.util.Map<java.lang.String,java.lang.String>> value)
        {
            map.put("XmlDocumentSource.xmlParameters", value);
            return this;
        }

        

        

        /**
         * Parameters to be passed to the XSLT transformer. Keys of the map will be used as
parameter names, values of the map as parameter values.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#xsltParameters 
         */
        public AttributeBuilder xsltParameters(java.util.Map<java.lang.String,java.lang.String> value)
        {
            map.put("XmlDocumentSource.xsltParameters", value);
            return this;
        }

        

        

        /**
         * Parameters to be passed to the XSLT transformer. Keys of the map will be used as
parameter names, values of the map as parameter values.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#xsltParameters 
         */
        public AttributeBuilder xsltParameters(IObjectFactory<? extends java.util.Map<java.lang.String,java.lang.String>> value)
        {
            map.put("XmlDocumentSource.xsltParameters", value);
            return this;
        }

        

        

        /**
         * After processing this field may hold the query read from the XML data, if any. For
the semantics of this field on input, see {@link org.carrot2.source.xml.XmlDocumentSource#xml}.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#query 
         */
        public AttributeBuilder query(java.lang.String value)
        {
            map.put("query", value);
            return this;
        }

        

        

        /**
         * After processing this field may hold the query read from the XML data, if any. For
the semantics of this field on input, see {@link org.carrot2.source.xml.XmlDocumentSource#xml}.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#query 
         */
        public AttributeBuilder query(IObjectFactory<? extends java.lang.String> value)
        {
            map.put("query", value);
            return this;
        }

        

        /**
         * After processing this field may hold the query read from the XML data, if any. For
the semantics of this field on input, see {@link org.carrot2.source.xml.XmlDocumentSource#xml}.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#query 
         */
        public java.lang.String query()
        {
            return (java.lang.String) map.get("query");
        }

        

        /**
         * The maximum number of documents to read from the XML data if {@link org.carrot2.source.xml.XmlDocumentSource#readAll} is
<code>false</code>.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#results 
         */
        public AttributeBuilder results(int value)
        {
            map.put("results", value);
            return this;
        }

        

        

        /**
         * The maximum number of documents to read from the XML data if {@link org.carrot2.source.xml.XmlDocumentSource#readAll} is
<code>false</code>.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#results 
         */
        public AttributeBuilder results(IObjectFactory<? extends java.lang.Integer> value)
        {
            map.put("results", value);
            return this;
        }

        

        

        /**
         * If clusters are present in the input XML they will be read and exposed to components
further down the processing chain.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#readClusters 
         */
        public AttributeBuilder readClusters(boolean value)
        {
            map.put("XmlDocumentSource.readClusters", value);
            return this;
        }

        

        

        /**
         * If clusters are present in the input XML they will be read and exposed to components
further down the processing chain.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#readClusters 
         */
        public AttributeBuilder readClusters(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("XmlDocumentSource.readClusters", value);
            return this;
        }

        

        

        /**
         * If <code>true</code>, all documents are read from the input XML stream, regardless
of the limit set by {@link org.carrot2.source.xml.XmlDocumentSource#results}.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#readAll 
         */
        public AttributeBuilder readAll(boolean value)
        {
            map.put("XmlDocumentSource.readAll", value);
            return this;
        }

        

        

        /**
         * If <code>true</code>, all documents are read from the input XML stream, regardless
of the limit set by {@link org.carrot2.source.xml.XmlDocumentSource#results}.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#readAll 
         */
        public AttributeBuilder readAll(IObjectFactory<? extends java.lang.Boolean> value)
        {
            map.put("XmlDocumentSource.readAll", value);
            return this;
        }

        

        

        

        

        

        /**
         * The title (file name or query attribute, if present) for the search result fetched
from the resource.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#title 
         */
        public java.lang.String title()
        {
            return (java.lang.String) map.get("processing-result.title");
        }

        

        

        

        

        /**
         * Documents read from the XML data.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#documents 
         */
@SuppressWarnings("unchecked")        public java.util.List<org.carrot2.core.Document> documents()
        {
            return (java.util.List<org.carrot2.core.Document>) map.get("documents");
        }

        

        /**
         * If {@link org.carrot2.source.xml.XmlDocumentSource#readClusters} is <code>true</code> and clusters are present in the input
XML, they will be deserialized and exposed to components further down the processing
chain.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#clusters 
         */
        public AttributeBuilder clusters(java.util.List<org.carrot2.core.Cluster> value)
        {
            map.put("clusters", value);
            return this;
        }

        

        

        /**
         * If {@link org.carrot2.source.xml.XmlDocumentSource#readClusters} is <code>true</code> and clusters are present in the input
XML, they will be deserialized and exposed to components further down the processing
chain.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#clusters 
         */
        public AttributeBuilder clusters(IObjectFactory<? extends java.util.List<org.carrot2.core.Cluster>> value)
        {
            map.put("clusters", value);
            return this;
        }

        

        /**
         * If {@link org.carrot2.source.xml.XmlDocumentSource#readClusters} is <code>true</code> and clusters are present in the input
XML, they will be deserialized and exposed to components further down the processing
chain.
         * 
         * @see org.carrot2.source.xml.XmlDocumentSource#clusters 
         */
@SuppressWarnings("unchecked")        public java.util.List<org.carrot2.core.Cluster> clusters()
        {
            return (java.util.List<org.carrot2.core.Cluster>) map.get("clusters");
        }

        /**
         * Returns an attribute builder for the nested
         * {@link org.carrot2.source.xml.XmlDocumentSourceHelper} component, backed by the same attribute map
         * as the current builder.
         */
        public org.carrot2.source.xml.XmlDocumentSourceHelperDescriptor.AttributeBuilder xmlDocumentSourceHelper()
        {
            return org.carrot2.source.xml.XmlDocumentSourceHelperDescriptor.attributeBuilder(map);
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
