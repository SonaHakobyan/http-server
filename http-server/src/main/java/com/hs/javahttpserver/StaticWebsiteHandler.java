/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hs.javahttpserver;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import models.*;

/**
 * Represents a custom static website handler
 *
 * @author Sona.Hakobyan
 */
public class StaticWebsiteHandler implements HttpHandler {

    /**
     * The root path of static website sources
     */
    private final String path;

    /**
     * Creates an instance of static website handler
     *
     * @param path The root path of static website sources
     */
    public StaticWebsiteHandler(String path) {
        this.path = path;
    }

    /**
     * Processes HTTP request and returns response back
     *
     * @param httpRequest The HTTP request
     * @return
     */
    @Override
    public MyHttpResponse handle(MyHttpRequest httpRequest) {
        // safety check
        if (httpRequest == null || httpRequest.getPath() == null) {
            return null;
        }

        // consider 'index.html' as default
        var requestedPath = httpRequest.getPath();
        if (requestedPath.equals("/")) {
            requestedPath = "index.html";
        }

        // create the absolute path
        var absolutePath = Paths.get(this.path, requestedPath);

        // file doesn't exist
        if (!Files.exists(absolutePath)) {
            return null;
        }

        // the http response
        var httpResponse = new MyHttpResponse();

        // add version and status code
        httpResponse.setVersion(httpRequest.getVersion());
        httpResponse.setStatusCode("200 OK");

        try {
            // try read all the bytes
            var messageBody = Files.readAllBytes(absolutePath);

            // process message body if exists
            if (messageBody.length > 0) {
                httpResponse.setBody(messageBody);

                // add content length
                httpResponse.getHeaders().put("Content-Length", new ArrayList<String>() {
                    {
                        add(String.valueOf(messageBody.length));
                    }
                });
            }

            // resolve content-type
            var contentType = Files.probeContentType(absolutePath) != null
                    ? Files.probeContentType(absolutePath) : "application/octet-stream";

            // add to headers
            httpResponse.getHeaders().put("Content-Type", new ArrayList<String>() {
                {
                    add(contentType);
                }
            });
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return httpResponse;
    }
}
