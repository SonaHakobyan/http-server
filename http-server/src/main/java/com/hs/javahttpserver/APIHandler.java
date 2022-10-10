/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hs.javahttpserver;

import java.util.regex.*;
import models.*;

/**
 * The API handler
 * 
 * @author Sona.Hakobyan
 */
public class APIHandler implements HttpHandler {
    /**
     *  The HTTP request method type
     */
    private final String method;

   /**
    * The HTTP request path pattern
    */
    private final String pathPattern;

    /**
     * The controller function
     */
    private final ControllerFunction controllerFunction;

    /**
     * Generates the instance of API handler
     * @param method The HTTP method type
     * @param pathPattern The HTTP request path pattern
     * @param controllerFunction The controller function
     */
    public APIHandler(String method, String pathPattern, ControllerFunction controllerFunction) {
        this.method = method;
        this.pathPattern = pathPattern;
        this.controllerFunction = controllerFunction;
    }

   /**
    * Handles HTTP request
    * @param httpRequest the HTTP request
    * @return 
    */
    @Override
    public MyHttpResponse handle(MyHttpRequest httpRequest) {
        // safety check
        if (httpRequest == null) {
            return null;
        }

        // check method
        if (httpRequest.getMethod() == null || !httpRequest.getMethod().equals(this.method)) {
            return null;
        }

        // the regular expression to match specified path
        var pattern = Pattern.compile(this.pathPattern, Pattern.CASE_INSENSITIVE);

        // check path
        if (!pattern.matcher(httpRequest.getPath()).find()) {
            return null;
        }

        return this.controllerFunction.apply(httpRequest);
    }
}
