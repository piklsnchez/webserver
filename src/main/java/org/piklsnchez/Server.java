package org.piklsnchez;

import java.util.stream.Stream;
import java.io.IOException;
import java.util.logging.Logger;
import java.net.URI;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

public class Server {
    private static final String CLASS = Server.class.getName();
    private static final Logger LOG = Logger.getLogger(CLASS);
    
    private static final String BASE_URI = "http://0.0.0.0:8080/";
    
    public static void main(String... args){
        ResourceConfig resourceConfig = new ResourceConfig().packages("org.piklsnchez.rest");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
        server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("."), "/static");
        try {
            server.start();
            System.out.println("Press any key to stop the server...");
            System.in.read();
            server.stop();
        } catch (Exception e) {
            LOG.severe(e.toString());
        }
    }
}
