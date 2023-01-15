package org.web.cofigMVC;


import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.io.File;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private int maxUploadSize = 1024*1024;

    private File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppContext.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration){
        registration.setMultipartConfig(getMultipartConfigElement());
    }

    private MultipartConfigElement getMultipartConfigElement(){
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                maxUploadSize,maxUploadSize*2,maxUploadSize/2);
        return multipartConfigElement;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        super.onStartup(servletContext);
        DelegatingFilterProxy userFilter = new DelegatingFilterProxy();
        userFilter.setTargetBeanName("");

        servletContext.addFilter("UserFilter", userFilter.getClass())
                .addMappingForUrlPatterns(null, false,"/user/users");
    }
}
