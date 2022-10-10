/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import models.*;

/**
 * The books controller
 *
 * @author Sona.Hakobyan
 */
public class BooksController {

    /**
     * The JSON object mapper instance
     */
    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Set of dummy books for demo
     */
    private final static ArrayList<Book> books = new ArrayList<>(List.of(
            new Book(1, "Ulysses", "James Joyce"),
            new Book(2, "Don Quixote", " Miguel de Cervantes"),
            new Book(3, "One Hundred Years of Solitude ", " Gabriel Garcia Marquez"),
            new Book(4, "The Great Gatsby", "F. Scott Fitzgerald"),
            new Book(5, "War and Peace", "Leo Tolstoy"),
            new Book(6, "Hamlet", "William Shakespeare"),
            new Book(7, "To the Lighthouse", "Virginia Woolf"),
            new Book(8, "Nineteen Eighty Four", "George Orwell")
    ));

    public static MyHttpResponse getAll(MyHttpRequest httpRequest) {
        try {
            // create and return http response and put books in a body
            return createHttpResponse(httpRequest, objectMapper.writeValueAsString(books));
        } catch (JsonProcessingException ex) {
            // return http response
            return createHttpResponse(httpRequest, ex.getMessage());
        }
    }

    /**
     * Returns book having given id
     *
     * @param httpRequest the HTTP request
     * @return
     */
    public static MyHttpResponse get(MyHttpRequest httpRequest) {
        // get book id from path
        var pathParts = httpRequest.getPath().split("/");
        var id = Integer.parseInt(pathParts[pathParts.length - 1]);

        // filter book by id
        var book = books.stream().filter(b -> b.getId() == id).findFirst();

        // book does not exist
        if (!book.isPresent()) {
            return createHttpResponse(httpRequest, "This book doesn't exist");
        }

        try {
            // return http response with book in a body
            return createHttpResponse(httpRequest, objectMapper.writeValueAsString(book.get()));
        } catch (JsonProcessingException ex) {
            // return http response
            return createHttpResponse(httpRequest, ex.getMessage());
        }
    }

    /**
     * Adds book to the list
     *
     * @param httpRequest the HTTP request
     * @return
     */
    public static MyHttpResponse post(MyHttpRequest httpRequest) {
        // try map body to book object
        Book book;
        try {
            book = objectMapper.readValue(httpRequest.getBody(), Book.class);
        } catch (IOException ex) {
            // return http response
            return createHttpResponse(httpRequest, ex.getMessage());
        }

        // book already exists
        if (books.stream().filter(b -> b.getId() == book.getId()).findFirst().isPresent()) {
            return createHttpResponse(httpRequest, "Book already exists");
        }

        // store the book
        books.add(book);

        // return http response
        return createHttpResponse(httpRequest, "Book added successfully");
    }

    /**
     * Puts the book in the list
     *
     * @param httpRequest the HTTP request
     * @return
     */
    public static MyHttpResponse put(MyHttpRequest httpRequest) {
        // try map body to book object
        Book book;
        try {
            book = objectMapper.readValue(httpRequest.getBody(), Book.class);
        } catch (IOException ex) {
            // return http response
            return createHttpResponse(httpRequest, ex.getMessage());
        }

        // filter book by id
        var existingBook = books.stream().filter(b -> b.getId() == book.getId()).findFirst();

        // book does not exist
        if (!existingBook.isPresent()) {
            return createHttpResponse(httpRequest, "This book doesn't exist");
        }

        // replace existing book with the new one
        books.set(books.indexOf(existingBook.get()), book);

        // return http response
        return createHttpResponse(httpRequest, "Book edited successfully");
    }

    /**
     * Deletes book from the list
     *
     * @param httpRequest the HTTP request
     * @return
     */
    public static MyHttpResponse delete(MyHttpRequest httpRequest) {
        // get book id from path
        var pathParts = httpRequest.getPath().split("/");
        var bookId = Integer.parseInt(pathParts[pathParts.length - 1]);

        // filter book by id
        var book = books.stream().filter(b -> b.getId() == bookId).findFirst();

        // book doesn't exists
        if (!book.isPresent()) {
            return createHttpResponse(httpRequest, "This book doesn't exist");
        }

        // remove the book
        books.remove(book.get());

        // create and return http response
        return createHttpResponse(httpRequest, "Book deleted successfully");
    }

    /**
     * Creates and returns an HTTP response
     *
     * @param httpRequest the HTTP request
     * @param body the response body
     * @return
     */
    private static MyHttpResponse createHttpResponse(MyHttpRequest httpRequest, String body) {
        // safety check
        if (httpRequest == null || body == null) {
            return null;
        }

        // the http response
        var httpResponse = new MyHttpResponse();

        // the response body
        var responseBody = body.getBytes(StandardCharsets.UTF_8);

        // add version and status code
        httpResponse.setVersion(httpRequest.getVersion());
        httpResponse.setStatusCode("200 OK");

        // process message body if exists
        if (responseBody.length > 0) {
            httpResponse.setBody(responseBody);

            // add content length
            httpResponse.getHeaders().put("Content-Length", new ArrayList<String>() {
                {
                    add(Integer.toString(responseBody.length));
                }
            });
        }

        try {
            // resolve file content type
            var contentType = Files.probeContentType(Paths.get(httpRequest.getPath()));
            
            // add to headers
            httpResponse.getHeaders().put("Content-Type", new ArrayList<String>() {
                {
                    add(contentType);
                }
            });
        } catch (IOException ex) {
            // ignore
        }

        return httpResponse;
    }
}
