package com.neeraj.mybank;

import com.neeraj.mybank.context.BankingConfiguration;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;

public class ApplicationLauncher {

    public static void main(String[] args) throws LifecycleException {
        /*
         *  Run `java -jar -Dserver.port=8090 {jarname}.jar' & Tomcat boots up on port 8090, instead of 8080.
         *  Default to port 8080, if no system property is set.
         */
        int serverPort = 8080;
        String portProperty = System.getProperty("server.port");
        if (portProperty != null) {
            serverPort = Integer.parseInt(portProperty);
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(serverPort);
        tomcat.getConnector();

        Context tomcatCtx = tomcat.addContext("", null);

        WebApplicationContext appCtx = createContext(tomcatCtx.getServletContext());

        DispatcherServlet dispatcherServlet = new DispatcherServlet(appCtx);

        Wrapper servletWrapper = Tomcat.addServlet(tomcatCtx, "dispatcherServlet", dispatcherServlet);
        servletWrapper.setLoadOnStartup(1);
        servletWrapper.addMapping("/*");

        tomcat.start();
    }

    private static WebApplicationContext createContext(ServletContext servletContext){
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(BankingConfiguration.class);
        ctx.setServletContext(servletContext);
        ctx.refresh();
        ctx.registerShutdownHook();
        return ctx;
    }
}
