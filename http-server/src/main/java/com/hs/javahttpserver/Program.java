/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.hs.javahttpserver;

import client.BooksController;

/**
 *
 * @author Sona.Hakobyan
 */
public class Program {

    public static void main(String[] args) {

        // create http server
        var httpServer = new MyHttpServer();

        // add API handlers
        httpServer.getHandlers().add(new APIHandler("GET", "^/api/books$", BooksController::getAll));
        httpServer.getHandlers().add(new APIHandler("GET", "^/api/book/[0-9]*$", BooksController::get));
        httpServer.getHandlers().add(new APIHandler("POST", "^/api/book$", BooksController::post));
        httpServer.getHandlers().add(new APIHandler("PUT", "^/api/book$", BooksController::put));
        httpServer.getHandlers().add(new APIHandler("DELETE", "^/api/book/[0-9]*$", BooksController::delete));

        // add website handler
        httpServer.getHandlers().add(new StaticWebsiteHandler("C:\\Users\\Sona.Hakobyan\\source\\repos\\react\\library\\build"));

        // run http server on 8080 port
        httpServer.run(8080);
    }
}
