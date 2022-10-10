/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.*;

/**
 * Represents HTTP request model
 *
 * @author Sona.Hakobyan
 */
public class MyHttpRequest {

    /**
     * The request method
     */
    private String method;

    /**
     * The request path
     */
    private String path;

    /**
     * The request version
     */
    private String version;

    /**
     * The request body
     */
    private byte[] body;

    /**
     * The map of request headers
     */
    private final HashMap<String, ArrayList<String>> headers;

    /**
     * Create an instance of HTTP request
     */
    public MyHttpRequest() {
        this.headers = new HashMap<>();
        this.body = new byte[]{};
    }

    /**
     * Returns request method
     *
     * @return
     */
    public String getMethod() {
        return this.method;
    }

    /**
     * Sets request method
     *
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Returns request path
     *
     * @return
     */
    public String getPath() {
        return this.path;
    }
    
    /**
     * Sets request path
     *
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
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
     * Returns response headers
     * @return 
     */
    public HashMap<String, ArrayList<String>> getHeaders(){
        return this.headers;
    }
}
