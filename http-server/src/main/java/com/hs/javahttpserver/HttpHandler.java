/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hs.javahttpserver;

import models.*;

/**
 * Represents HTTP Handler base class
 * @author Sona.Hakobyan
 */
public interface HttpHandler {

    /**
     * Handles HTTP request
     * @param httpRequest the HTTP request
     * @return 
     */
    public MyHttpResponse handle(MyHttpRequest httpRequest);
}
