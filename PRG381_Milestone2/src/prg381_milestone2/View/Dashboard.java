/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package prg381_milestone2.View;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import prg381_milestone2.Controller.AppointmentController;
import prg381_milestone2.Model.Appointment;
import javax.swing.JOptionPane;
import java.sql.Date; 
import java.util.List; 
import javax.swing.table.DefaultTableModel; 
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener; 

/**
 *
 * @author squis
 */
public class Dashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Dashboard.class.getName());
    
     private final AppointmentController appointmentController;
    private void loadAppointmentsIntoTable() {
        DefaultTableModel model = (DefaultTableModel) tblAppointment.getModel();
        model.setRowCount(0);

        try {
            List<Appointment> appointments = appointmentController.getAllAppointments();

            for (Appointment appt : appointments) {
                model.addRow(new Object[]{
                    appt.getId(),
                    appt.getStudentId(),
                    appt.getStudentName(),
                    appt.getCounselorName(),
                    appt.getAppointmentDate().toString(),
                    appt.getAppointmentTime(),
                    appt.getStatus()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Creates new form Dashboard
     */
    public Dashboard() {
        initComponents();
        
         setTitle("Wellness Management Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cmbCounselor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Counselor", "John Doe", "Jane Smith", "Emily White", "Dr. Alex Lee" }));
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Confirmed", "Cancelled", "Completed" }));
        cmbTime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Time", "09:00 AM", "10:00 AM", "11:00 AM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM" }));

        
        tblAppointment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Student ID", "Student Name", "Counselor", "Date", "Time", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
         // Set up button texts and add action listeners
        btnBookAppointment.setText("Book Appointment"); 
        btnBookAppointment.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        
        JScrollPane.setViewportView(tblAppointment);
        
        appointmentController = new AppointmentController();
        
        if (cmbStatus.getItemCount() > 0) {
            cmbStatus.setSelectedItem("Pending");
        }
        
        loadAppointmentsIntoTable();
        
        tblAppointment.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblAppointment.getSelectedRow() != -1) {
                    int selectedRow = tblAppointment.getSelectedRow();
                    // Assuming column indices: 1=StudentID, 3=CounselorName, 4=Date, 5=Time, 6=Status
                    String selectedStudentId = (String) tblAppointment.getValueAt(selectedRow, 1);
                    String selectedCounselor = (String) tblAppointment.getValueAt(selectedRow, 3);
                    String selectedDateStr = (String) tblAppointment.getValueAt(selectedRow, 4);
                    String selectedTime = (String) tblAppointment.getValueAt(selectedRow, 5);
                    String selectedStatus = (String) tblAppointment.getValueAt(selectedRow, 6);

                    txtStudentId.setText(selectedStudentId);
                    // If you have a field for student name (jTextField2), uncomment this:
                    // jTextField2.setText((String) tblAppointments.getValueAt(selectedRow, 2));
                    cmbCounselor.setSelectedItem(selectedCounselor);

                    try {
                        java.util.Date date = java.sql.Date.valueOf(selectedDateStr);
                        dateChooserAppointment.setDate(date);
                    } catch (IllegalArgumentException ex) {
                        System.err.println("Error parsing date: " + selectedDateStr + " - " + ex.getMessage());
                        dateChooserAppointment.setDate(null);
                    }

                    cmbTime.setSelectedItem(selectedTime);
                    cmbStatus.setSelectedItem(selectedStatus);
                } else if (tblAppointment.getSelectedRow() == -1) {
                    // Clear fields if no row is selected
                    txtStudentId.setText("");
                    // jTextField2.setText(""); // Clear student name field if applicable
                    cmbCounselor.setSelectedIndex(0);
                    dateChooserAppointment.setDate(null);
                    cmbTime.setSelectedIndex(0);
                    cmbStatus.setSelectedIndex(0);
                }
            }
        });
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
       
        
       
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtStudentId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbCounselor = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbTime = new javax.swing.JComboBox<>();
        btnBookAppointment = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSearchID = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        JScrollPane = new javax.swing.JScrollPane();
        tblAppointment = new javax.swing.JTable();
        btnUpdate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 255));
        setForeground(new java.awt.Color(204, 204, 255));

        jTabbedPane2.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        jLabel1.setText("Book Appointment:");

        jLabel2.setText("Student ID:");

        jLabel3.setText("Student name:");

        jLabel4.setText("Counsellor:");

        cmbCounselor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Status:");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Date:");

        jLabel7.setText("Time:");

        cmbTime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnBookAppointment.setText("Book Appointment");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnBookAppointment)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtStudentId)
                            .addComponent(cmbCounselor, 0, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2)
                            .addComponent(cmbStatus, 0, 100, Short.MAX_VALUE)
                            .addComponent(cmbTime, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(172, 172, 172))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtStudentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbCounselor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cmbTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBookAppointment)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel8.setText("View / Update / Cancel Appointments:");

        jLabel9.setText("Search by StudentID:");

        btnSearch.setText("Search");

        tblAppointment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        JScrollPane.setViewportView(tblAppointment);

        btnUpdate.setText("Update");

        btnCancel.setText("Cancel");

        btnRefresh.setText("Refresh");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JScrollPane)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSearchID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(150, 150, 150)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearchID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnCancel)
                    .addComponent(btnRefresh)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Appointment Management", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1465, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Counselor Management", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1465, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Feedback Management", jPanel3);

        jPanel6.setBackground(new java.awt.Color(153, 153, 255));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Wellness Dashboard");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel10)
                .addContainerGap(830, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel10)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jTabbedPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
         String studentId = txtStudentId.getText().trim();
        String counselorName = (String) cmbCounselor.getSelectedItem();
        java.util.Date utilDate = dateChooserAppointment.getDate();
        String time = (String) cmbTime.getSelectedItem();
        String status = (String) cmbStatus.getSelectedItem();
        
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student ID cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (counselorName == null || counselorName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a Counselor.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (utilDate == null) {
            JOptionPane.showMessageDialog(this, "Please select an Appointment Date.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (time == null || time.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an Appointment Time.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (status == null || status.trim().isEmpty()) {
             JOptionPane.showMessageDialog(this, "Please select an Appointment Status.", "Input Error", JOptionPane.ERROR_MESSAGE);
             return;
        }
        
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
       try {
            Appointment newAppointment = new Appointment(studentId, null, counselorName, sqlDate, time, status);

            appointmentController.bookAppointment(newAppointment);

            JOptionPane.showMessageDialog(this, "Appointment booked successfully!", "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);

            txtStudentId.setText("");
            cmbCounselor.setSelectedIndex(0);
            dateChooserAppointment.setDate(null);
            cmbTime.setSelectedIndex(0);
            cmbStatus.setSelectedItem("Pending");

            loadAppointmentsIntoTable();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error booking appointment: " + ex.getMessage(), "Booking Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }  
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String searchStudentId = txtSearchID.getText().trim(); // Assuming jTextField3 is your search input field
        DefaultTableModel model = (DefaultTableModel) tblAppointment.getModel();
        model.setRowCount(0); // Clear existing rows

        try {
            List<Appointment> appointments;
            if (searchStudentId.isEmpty()) {
                appointments = appointmentController.getAllAppointments(); // If search field is empty, show all
            } else {
                appointments = appointmentController.searchAppointmentsByStudentId(searchStudentId);
            }

            if (appointments.isEmpty() && !searchStudentId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No appointments found for Student ID: " + searchStudentId, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }

            for (Appointment appt : appointments) {
                model.addRow(new Object[]{
                    appt.getId(),
                    appt.getStudentId(),
                    appt.getStudentName(),
                    appt.getCounselorName(),
                    appt.getAppointmentDate().toString(),
                    appt.getAppointmentTime(),
                    appt.getStatus()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error searching appointments: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
            logger.log(java.util.logging.Level.SEVERE, "Error searching appointments", ex);
        }
    }     
      private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        int selectedRow = tblAppointment.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Get appointment ID from the selected table row (assuming it's in column 0)
            int appointmentId = (int) tblAppointment.getValueAt(selectedRow, 0);
            String studentId = txtStudentId.getText().trim();
            // String studentName = jTextField2.getText().trim(); // If you're using a student name field for update
            String counselorName = (String) cmbCounselor.getSelectedItem();
            java.util.Date utilDate = dateChooserAppointment.getDate();
            String time = (String) cmbTime.getSelectedItem();
            String status = (String) cmbStatus.getSelectedItem();

            if (studentId.isEmpty() || counselorName == null || "Select Counselor".equals(counselorName.trim()) || utilDate == null || time == null || "Select Time".equals(time.trim()) || status == null || status.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled and valid selections made to update an appointment.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            // Pass null for studentName if not updated from input field
            Appointment updatedAppointment = new Appointment(appointmentId, studentId, null, counselorName, sqlDate, time, status);

            appointmentController.updateAppointment(updatedAppointment);
            JOptionPane.showMessageDialog(this, "Appointment updated successfully!", "Update Confirmed", JOptionPane.INFORMATION_MESSAGE);
            loadAppointmentsIntoTable(); // Refresh table
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating appointment: " + ex.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
            logger.log(java.util.logging.Level.SEVERE, "Error updating appointment", ex);
        }
    } 
      private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        int selectedRow = tblAppointment.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to cancel.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this appointment?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Get appointment ID from the selected table row (assuming it's in column 0)
                int appointmentId = (int) tblAppointment.getValueAt(selectedRow, 0);
                appointmentController.cancelAppointment(appointmentId);
                JOptionPane.showMessageDialog(this, "Appointment cancelled successfully!", "Cancellation Confirmed", JOptionPane.INFORMATION_MESSAGE);
                loadAppointmentsIntoTable(); // Refresh table
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error cancelling appointment: " + ex.getMessage(), "Cancellation Error", JOptionPane.ERROR_MESSAGE);
                logger.log(java.util.logging.Level.SEVERE, "Error cancelling appointment", ex);
            }
        }
    }      
     private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        loadAppointmentsIntoTable(); // Reload all appointments
        JOptionPane.showMessageDialog(this, "Appointment list refreshed.", "Refresh", JOptionPane.INFORMATION_MESSAGE);
    } 
     
   
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new Dashboard().setVisible(true));
    }

 



    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane JScrollPane;
    private javax.swing.JButton btnBookAppointment;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbCounselor;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbTime;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable tblAppointment;
    private javax.swing.JTextField txtSearchID;
    private javax.swing.JTextField txtStudentId;
    // End of variables declaration//GEN-END:variables

    private static class dateChooserAppointment {

        private static java.util.Date getDate() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private static void setDate(Object object) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public dateChooserAppointment() {
        }
    }
}
