/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

/**
 * Represents book model
 * 
 * @author Sona.Hakobyan
 */
public class Book {
    /**
     * The book id
     */

    private int id;

    /**
     * The book title
     */
    private String title;

   /**
    * The book author
    */
    private String author;

   /**
    * Creates an instance of Book
    */
    public Book() {
    }
    
   /**
    * Creates an instance of Book
    * @param id The book id
    * @param title The book title
    * @param author The book author
    */
    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
    
    /**
     * Returns book id
     * @return 
     */
    public int getId(){
        return this.id;
    }
    
    /**
     *  Sets book id
     * @param id The book id
     */
    public void setId(int id){
        this.id = id;
    }
    
    /**
     * Returns book title
     * @return 
     */
    public String getTitle(){
        return this.title;
    }
    
    /**
     *  Sets book title
     * @param title The book title
     */
    public void setTitle(String title){
        this.title = title;
    }
    
     /**
     * Returns book author
     * @return 
     */
    public String getAuthor(){
        return this.author;
    }
    
    /**
     *  Sets book author
     * @param author The book author
     */
    public void setAuthor(String author){
        this.author = author;
    }
}
