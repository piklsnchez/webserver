module Webserver {
    requires java.logging;
    requires java.ws.rs;
    requires javax.inject;
    requires javax.servlet.api;
    requires javax.annotation.api;
    requires grizzly.http.server;
    requires grizzly.http;
    requires grizzly.http.servlet;
    requires grizzly.websockets;
    requires grizzly.framework;
    requires jersey.container.grizzly2.http;
    requires jersey.server;
    requires jersey.client;
    requires jersey.media.jaxb;
    requires jersey.common;
//    requires jersey.hk;
    requires osgi.resource.locator;
    requires hk2.locator;
    requires hk2.api;
    requires hk2.utils;
    requires aopalliance.repackaged;
    requires validation.api;
    requires javassist;
}
