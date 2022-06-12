package com.orioninc.logging;

import org.jboss.resteasy.spi.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/logs")
public class LoggingExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingExample.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLogs(@Context HttpRequest httpRequest) {
        LOGGER.error("Error level: {}", httpRequest.getHttpMethod());
        LOGGER.warn("Warn level: {}", httpRequest.getHttpMethod());
        LOGGER.info("Info level: {}", httpRequest.getHttpMethod());
        LOGGER.debug("Debug level: {}", httpRequest.getHttpMethod());
        LOGGER.trace("Trace level: {}", httpRequest.getHttpMethod());
        return "Check out log file!";
    }
}
