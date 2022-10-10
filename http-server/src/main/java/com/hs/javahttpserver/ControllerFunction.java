/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hs.javahttpserver;

import models.MyHttpRequest;
import models.MyHttpResponse;

/**
 * Represents a functional component
 * 
 * @author Sona.Hakobyan
 */
@FunctionalInterface
public interface ControllerFunction  {
    MyHttpResponse apply(MyHttpRequest httpRequest);
}
