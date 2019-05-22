package ca.denniscourneyea.service.impl.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Configuration
@ComponentScan({"ca.denniscourneyea.service.impl.soap","ca.denniscourneyea.service.impl.common"})
class SpringWsConfiguration {

    static final String SOAP_SUB_CONTEXT = "/soap";
    static final String TARGET_NAMESPACE = "http://www.denniscourneyea.ca/spring-mvc-ws/v1";

    private final String locationUri;

    @Autowired
    public SpringWsConfiguration(ServletContext containerContext) {
        this.locationUri = containerContext.getContextPath() + SOAP_SUB_CONTEXT;
        System.out.println("Location Uri = " + locationUri);
    }

    @Bean
    public DefaultWsdl11Definition status() throws IOException, SAXException, ParserConfigurationException {
        Resource xsd = new ClassPathResource("/service-1.0.xsd");

        SimpleXsdSchema schema = new SimpleXsdSchema();
        schema.setXsd(xsd);
        schema.afterPropertiesSet();

        DefaultWsdl11Definition wsdlDefinition = new DefaultWsdl11Definition();
        wsdlDefinition.setTargetNamespace(TARGET_NAMESPACE);
        wsdlDefinition.setSchema(schema);
        wsdlDefinition.setPortTypeName("Status");
        wsdlDefinition.setServiceName("StatusService");
        wsdlDefinition.setLocationUri(locationUri);
        return wsdlDefinition;
    }

}
