/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg381_milestone2.Model;

/**
 *
 * @author Admin
 */

public class Feedback {
    private int id;
    private String studentId;
    private String counselor;
    private int rating;
    private String comments;

    // Constructors
    public Feedback() {}
    
    public Feedback(String studentId, String counselor, int rating, String comments){
    this.studentId = studentId;
    this.counselor = counselor;
    this.rating = rating;
    this.comments = comments;
    }
    
    public Feedback(int id, String studentId, String counselor, int rating, String comments) {
    this.id = id;
    this.studentId = studentId;
    this.counselor = counselor;
    this.rating = rating;
    this.comments = comments;
}

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getCounselor() { return counselor; }
    public void setCounselor(String counselor) { this.counselor = counselor; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}
