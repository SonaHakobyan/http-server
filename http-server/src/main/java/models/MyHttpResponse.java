/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.*;

/**
 * Represents HTTP response model
 *
 * @author Sona.Hakobyan
 */
public class MyHttpResponse {

    /**
     * The response status code
     */
    private String statusCode;

    /**
     * The response version
     */
    private String version;

    /**
     * The response body
     */
    private byte[] body;

    /**
     * The map of response headers
     */
    private final HashMap<String, ArrayList<String>> headers;

    /**
     * Creates an instance of HTTP response
     */
    public MyHttpResponse() {
        this.headers = new HashMap<>();
        this.body = new byte[]{};
    }

     /**
     * Returns response status code
     *
     * @return
     */
    public String getStatusCode() {
        return this.statusCode;
    }
    
    /**
     * Sets response statusCode
     *
     * @param statusCode
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Returns request version
     *
     * @return
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Sets request version
     *
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }

     /**
     * Returns request body
     *
     * @return
     */
    public byte[] getBody() {
        return this.body;
    }
    
    /**
     * Sets request body
     *
     * @param body
     */
    public void setBody(byte[] body) {
        this.body = body;
    }
    
    /**
     * Returns response headers
     *
     * @return
     */
    public HashMap<String, ArrayList<String>> getHeaders() {
        return this.headers;
    }
}
