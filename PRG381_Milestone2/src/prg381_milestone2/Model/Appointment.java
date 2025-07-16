/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg381_milestone2.Model;

import java.sql.Date;
/**
 *
 * @author Client
 */
public class Appointment {
    private int id;
    private String studentId;
    private String studentName;
    private String counselorName;
    private Date appointmentDate;
    private String appointmentTime;
    private String status;
    
    public Appointment(String studentId, String studentName, String counselorName, Date appointmentDate, String appointmentTime, String status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.counselorName = counselorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }
    
    public Appointment(int id, String studentId, String studentName, String counselorName, Date appointmentDate, String appointmentTime, String status) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.counselorName = counselorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }
    
      public int getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getCounselorName() { return counselorName; }
    public Date getAppointmentDate() { return appointmentDate; }
    public String getAppointmentTime() { return appointmentTime; }
    public String getStatus() { return status; }
    
    public void setId(int id) { this.id = id; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setCounselorName(String counselorName) { this.counselorName = counselorName; }
    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setStatus(String status) { this.status = status; }
}
