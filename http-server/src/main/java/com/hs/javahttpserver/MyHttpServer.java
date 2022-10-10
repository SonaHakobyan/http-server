/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hs.javahttpserver;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import models.*;

/**
 * A custom implementation of HTTP Server
 *
 * @author Sona.Hakobyan
 */
public class MyHttpServer {

    /**
     * Set of supported HTTP handlers
     */
    private final ArrayList<HttpHandler> handlers;

    /**
     * The executor service
     */
    private final ExecutorService executorService;

    /**
     * Creates an instance of HTTP Server
     */
    public MyHttpServer() {
        this.handlers = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(20);
    }

    /**
     * Returns set of handlers
     *
     * @return
     */
    public ArrayList<HttpHandler> getHandlers() {
        return this.handlers;
    }

    /**
     * Runs server to serve clients
     *
     * @param port The server port number
     */
    public void run(int port) {

        try {
            // craete a socket on given port
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                // ready to accept a client
                Socket client = serverSocket.accept();

                // ask executor service to serve the client
                this.executorService.submit(() -> {
                    // handle connection
                    this.handleConnection(client);
                });
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Handles client connection
     *
     * @param incomingClient The incoming client's socket
     */
    private void handleConnection(Socket incomingClient) {
        try {
            // use client and close after this method
            var client = incomingClient;

            // get input stream out of client 
            InputStream inputStream = client.getInputStream();

            // wrap stream into the reader and close when method is done
            var reader = new BufferedReader(new InputStreamReader(inputStream));

            // read http request message from reader
            var httpRequest = this.readHttpRequest(reader);

            // process http request and produce http response
            var httpResponse = this.processHttpRequest(httpRequest);

            // get output stream out of client 
            OutputStream outputStream = client.getOutputStream();

            // write back to the client the http response
            this.writeHttpResponse(httpResponse, outputStream);

            // close as it is done
            client.close();
            inputStream.close();
            outputStream.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Read and create new HTTP request message from reader
     *
     * @param reader The stream text reader
     * @return
     */
    private MyHttpRequest readHttpRequest(BufferedReader reader) {
        // the HTTP request instance
        var httpRequest = new MyHttpRequest();

        // determine if it is the first line
        var isFirstLine = true;

        // read stream value and store
        String currentLine;
        try {
            while ((currentLine = reader.readLine()) != null && !currentLine.equals("")) {
                // first line contains method, path and version
                if (isFirstLine) {
                    // no more the first
                    isFirstLine = false;

                    // store method, path and version
                    var args = currentLine.split(" ", 3);
                    httpRequest.setMethod(args[0]);
                    httpRequest.setPath(args[1]);
                    httpRequest.setVersion(args[2]);

                    continue;
                }

                // get header key-value pair
                var header = currentLine.split(":", 2);
                var headerKey = header[0].trim();
                var headerValue = header[1].trim();

                // store header elements
                if (httpRequest.getHeaders().containsKey(headerKey)) {
                    httpRequest.getHeaders().get(headerKey).add(headerValue);
                } else {
                    httpRequest.getHeaders().put(headerKey, new ArrayList<String>() {
                        {
                            add(headerValue);
                        }
                    });
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        // if request doesn't have body, it's done
        if (!currentLine.equals("") || !httpRequest.getHeaders().containsKey("Content-Length")) {
            return httpRequest;
        }

        // otherwise, get the length of a body 
        var contentLength = Integer.parseInt(httpRequest.getHeaders().get("Content-Length").stream().findFirst().orElse(""));

        // an array to store content
        var charArray = new char[contentLength];

        try {
            // read the content
            reader.read(charArray, 0, contentLength);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        // store content in body
        httpRequest.setBody(new String(charArray).getBytes());

        return httpRequest;
    }

    /**
     * Process HTTP request and return corresponding HTTP response
     *
     * @param httpRequest
     * @return
     */
    private MyHttpResponse processHttpRequest(MyHttpRequest httpRequest) {
        // process handlers
        for (var handler : this.handlers) {
            // get corresponding http response
            var httpResponse = handler.handle(httpRequest);

            // return http request
            if (httpResponse != null) {
                return httpResponse;
            }
        }

        // return 404 error
        return notFoundHttpResponse();
    }

    /**
     * Writes HTTP response to client output stream
     *
     * @param httpResponse the HTTP response
     * @param stream the output stream
     */
    private void writeHttpResponse(MyHttpResponse httpResponse, OutputStream stream) {
        // start building the response adding status line
        var builder = new StringBuilder(String.format("%s %s\n", httpResponse.getVersion(), httpResponse.getStatusCode()));

        // process headers
        if (httpResponse.getHeaders() != null) {
            for (var entry : httpResponse.getHeaders().entrySet()) {
                // get the key
                var key = entry.getKey();

                // get the values list
                var valuesList = entry.getValue();

                // process values
                for (var value : valuesList) {

                    // add to response
                    builder.append(String.format("%s %s\n", key, value));
                }
            }
        }

        // add message body
        if (httpResponse.getBody() != null && httpResponse.getBody().length > 0) {
            builder.append("\n");
        }

        // get response as set of bytes
        var responseBytes = builder.toString().getBytes(StandardCharsets.UTF_8);

        try {
            // write response into the sream
            stream.write(responseBytes, 0, responseBytes.length);

            if (httpResponse.getHeaders().containsKey("Content-Length")) {
                stream.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Returns 404 not found response
     *
     * @return
     */
    public static MyHttpResponse notFoundHttpResponse() {
        return new MyHttpResponse() {
            {
                setStatusCode("404 Not Found");
                setVersion("HTTP/1.1");
            }
        };
    }
}
