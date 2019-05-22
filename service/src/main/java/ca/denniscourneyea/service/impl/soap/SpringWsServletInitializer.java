package ca.denniscourneyea.service.impl.soap;

import org.springframework.ws.transport.http.support.AbstractAnnotationConfigMessageDispatcherServletInitializer;

/*
Code based servlet initialization requires Tomcat 7 or another container that supports Servlet 3.0 or later. The
abstract super class is provided by Spring to facilitate integrating a Spring MVC dispatcher servlet initialized in code
and Spring Java configuration.
*/
public class SpringWsServletInitializer extends AbstractAnnotationConfigMessageDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { SpringWsConfiguration.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { SpringWsConfiguration.SOAP_SUB_CONTEXT + "/*", "*.wsdl" };
    }

}
