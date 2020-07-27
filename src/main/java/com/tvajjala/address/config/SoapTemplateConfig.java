package com.tvajjala.address.config;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.axiom.AxiomSoapMessageFactory;
import org.springframework.ws.soap.client.core.SoapFaultMessageResolver;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.xml.soap.MessageFactory.newInstance;

/**
 * This configuration will lets you prepare required beans to invoke soap -ws
 * <p>
 * https://docs.spring.io/spring-ws/site/reference/html/client.html
 * <p>
 * https://docs.spring.io/spring-ws/site/reference/html/common.html
 * <p>
 * http://wiki.eclipse.org/EclipseLink/Examples
 *
 * @author ThirupathiReddy Vajjala
 */
@Configuration
public class SoapTemplateConfig {


    private static final Logger LOGGER = LoggerFactory.getLogger(SoapTemplateConfig.class);


    @Bean
    public WebServiceTemplate cityStateTemplate() throws Exception {

        return webServiceTemplate("city");
    }

    @Bean
    public WebServiceTemplate alternateCityTemplate() throws Exception {

        return webServiceTemplate("alternate");
    }


    /**
     * The WebServiceTemplate is the core class for soap-side Web service access in Spring-WS. It contains methods
     * for sending Source objects, and receiving response messages as either Source or Result. Additionally, it can
     * marshal objects to XML before sending them across a transport, and un-marshal any response XML into an object
     * again.
     *
     * @return WebServiceTemplate {@link WebServiceTemplate}
     */
    public WebServiceTemplate webServiceTemplate(String bindingPath) throws Exception {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate(axiomSoapMessageFactory());
        // message resolver
        webServiceTemplate.setFaultMessageResolver(new SoapFaultMessageResolver());
        // message sender
        webServiceTemplate.setMessageSender(httpComponentsMessageSender());
        webServiceTemplate.setMarshaller(jaxb2Marshaller(bindingPath));
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller(bindingPath));
        webServiceTemplate.setInterceptors(new ClientInterceptor[]{new CustomClientInterceptor()});
        //webServiceTemplate.afterPropertiesSet();
        return webServiceTemplate;

    }


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


    /**
     * The SaajSoapMessageFactory uses the SOAP with Attachments API for Java to create SoapMessage implementations.
     * <p>
     * SAAJ is based on DOM, the Document Object Model. This means that all SOAP messages are stored in memory. For
     * larger SOAP messages, this may not be very performant. In that case, the AxiomSoapMessageFactory might be more
     * applicable.
     *
     * @return SaajSoapMessageFactory {@link SaajSoapMessageFactory}
     */
    @Bean
    SaajSoapMessageFactory saajSoapMessageFactory() throws Exception {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(newInstance());
        // messageFactory.afterPropertiesSet();
        return messageFactory;
    }


}
