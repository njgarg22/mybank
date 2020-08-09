package com.neeraj.mybank;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import com.neeraj.mybank.web.TransactionServlet;

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
		
		Context ctx = tomcat.addContext("", null);
		Wrapper servletWrapper = Tomcat.addServlet(ctx, "TransactionServlet", new TransactionServlet());
		servletWrapper.setLoadOnStartup(1);
		servletWrapper.addMapping("/*");
		
		tomcat.start();
	}
}
