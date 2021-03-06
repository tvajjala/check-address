== Spring-boot SOAP client

This tutorial walks you through the process of consuming a `SOAP-based web service` with Spring.

We will build a SOAP client that fetches city information based on `zipCode`.

Complete WSDL you can found  http://ws.cdyne.com/psaddress/addresslookup.asmx?wsdl[here]

From the above WSDL we will use following simple service calls

. `ReturnCityState` - This will return City and State Information
. `AlternateCities` - This will return alternate city names


Spring provides a mechanism to invoke soap based services using `WebServiceTemplate`

We need to Customise this component to improve performance, refer
        https://github.com/tvajjala/check-address/blob/master/src/main/java/com/tvajjala/address/config/SoapTemplateConfig.java[SoapTemplateConfig] for full configuration.


It required following components

. Message Sender
. Message Factory
. Marshaller


=== Message Sender (HTTP Transport)

There are two implementations of the `WebServiceMessageSender` interface for sending messages via HTTP.

.. The default implementation is the `HttpUrlConnectionMessageSender`, which uses the facilities provided by Java itself.
.. The alternative is the `HttpComponentsMessageSender`, which uses the `Apache HttpComponents HttpClient`.
Use the latter if you need more advanced and easy-to-use functionality (such as authentication, HTTP connection pooling, and so forth).


````java



    /**
     * Apache httpClient with default behaviour. customize as per need to handle https traffic
     *
     * @return httpClient
     */
    @Bean
    HttpClient httpClient() {
        return HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.DEFAULT)
                .addInterceptorFirst(httpRequestInterceptor())
                .build();
    }


    public HttpRequestInterceptor httpRequestInterceptor() {
        return (request, context) -> request.removeHeaders(HTTP.CONTENT_LEN);
    }


    /**
     * Transportation: HTTP (Other alternative Transportation like JMS, EMAIL, XMPP)
     * <p>
     * There are two implementations of the {@link org.springframework.ws.transport.WebServiceMessageSender}
     * interface for sending messages via HTTP.
     * 1. The default implementation is the
     * {@link org.springframework.ws.transport.http.HttpUrlConnectionMessageSender}, which uses the facilities
     * provided by Java itself.
     * <p>
     * 2.The alternative is the {@link HttpComponentsMessageSender}, which uses the Apache HttpComponents
     * {@link HttpClient}.
     * Use the latter if you need more advanced and easy-to-use functionality (such as authentication, HTTP connection
     * pooling, and so forth).
     *
     * @return HttpComponentsMessageSender {@link HttpComponentsMessageSender}
     */
    @Bean
    HttpComponentsMessageSender httpComponentsMessageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setHttpClient(httpClient());
        return httpComponentsMessageSender;
    }
````





=== Message Factory

Concrete message implementations are created by a `WebServiceMessageFactory`.

This factory can create an empty message, or read a message based on an input stream.

There are two concrete implementations of WebServiceMessageFactory;

.. one is based on SAAJ, the SOAP with Attachments API for Java,

.. the other based on Axis 2's AXIOM, the AXis Object Model.

*AxiomSoapMessageFactory*

The `AxiomSoapMessageFactory` uses the AXis 2 Object Model to create SoapMessage implementations.
AXIOM is based on StAX, the Streaming API for XML. StAX provides a pull-based mechanism for reading XML messages, which can be more efficient for larger messages.

To increase reading performance on the `AxiomSoapMessageFactory`, you can set the `payloadCaching` property to false (default is true).
This will read the contents of the SOAP body directly from the socket stream. When this setting is enabled, the payload can only be read once.
This means that you have to make sure that any pre-processing (logging etc.) of the message does not consume it.



````java

    /**
     * In addition to a message sender, the WebServiceTemplate requires a Web service message factory. There are two
     * message factories for SOAP: {@link SaajSoapMessageFactory} and
     * {@link AxiomSoapMessageFactory}. If no message factory is
     * specified (via the messageFactory property), Spring-WS will use the
     * {@link org.springframework.ws.soap.saaj.SaajSoapMessageFactory} by default.
     * <p>
     * The AxiomSoapMessageFactory uses the AXis 2 Object Model to create SoapMessage implementations. AXIOM is based
     * on StAX, the Streaming API for XML. StAX provides a pull-based mechanism for reading XML messages, which can
     * be more efficient for larger messages.
     *
     * @return AxiomSoapMessageFactory {@link AxiomSoapMessageFactory}
     */
  @Bean
    AxiomSoapMessageFactory axiomSoapMessageFactory() {
        AxiomSoapMessageFactory axiomSoapMessageFactory = new AxiomSoapMessageFactory();

        /*
         * To increase reading performance on the AxiomSoapMessageFactory, you can set the payloadCaching property to
         * false (default is true). This will read the contents of the SOAP body directly from the socket stream.
         * When this setting is enabled, the payload can only be read once. This means that you have to make sure
         * that any pre-processing (logging etc.) of the message does not consume it.
         */
        axiomSoapMessageFactory.setPayloadCaching(false);
        //axiomSoapMessageFactory.afterPropertiesSet();

        return axiomSoapMessageFactory;
    }
````


=== Sending and receiving POJOs - marshalling and un-marshalling

In order to facilitate the sending of plain Java objects, the WebServiceTemplate has a number of send(..) methods that take an Object as an argument for a message's data content. The method marshalSendAndReceive(..) in the WebServiceTemplate class delegates the conversion of the request object to XML to a Marshaller, and the conversion of the response XML to an object to an Unmarshaller.

To externalize the conversion logic we use `Eclipselink Moxy` Framework.


*Eclipselink Moxy*

Refer https://wiki.eclipse.org/EclipseLink/Examples document for more information.

````java
/**
     * Handles conversion of JavaObjects to XML vice versa. (uses MOXY to externalize this conversion).
     * <p>
     * In order to facilitate the sending of plain Java objects, the WebServiceTemplate has a number of send(..)
     * methods that take an Object as an argument for a message's data content. The method marshalSendAndReceive(..)
     * in the WebServiceTemplate class delegates the conversion of the request object to XML to a Marshaller, and the
     * conversion of the response XML to an object to an Unmarshaller.
     *
     * @return Jaxb2Marshaller {@link Jaxb2Marshaller}
     */
    public Jaxb2Marshaller jaxb2Marshaller(String path) throws IOException {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("bindings/" + path + "/**");

        Map<String, Object> properties = Collections.singletonMap(JAXBContextProperties.OXM_METADATA_SOURCE, Arrays.stream(resources).map(resource -> "bindings/" + path
                + "/" + resource.getFilename()).collect(Collectors.toList()));

        LOGGER.info("JaxbContextProperties {} ", properties);
        jaxb2Marshaller.setJaxbContextProperties(properties);
        //used to specify java classes to bound. since we are using Moxy we need to provide
        //jaxb.properties file folder - javax.xml.bind.context.factory=org.eclipse.persistence.jaxb.JAXBContextFactory
        jaxb2Marshaller.setContextPath("jaxb");//jaxb.context.path
        return jaxb2Marshaller;
    }
````

Client layer uses RxJava to make asynchronous calls and aggregates the result.

refer https://github.com/tvajjala/check-address.git[Github repository] for complete codebase.




*Summary*

. Invoking SOAP web service using webServiceTemplate
. Understanding the different messageFactories  (`Axiom` and SAAJ)
. Different message senders for different protocols ( `HTTP`, JMS etc)
. Integrating `Moxy` Marshaller framework( Java to XML conversion)
. Usage of RxJava for parallel calls




