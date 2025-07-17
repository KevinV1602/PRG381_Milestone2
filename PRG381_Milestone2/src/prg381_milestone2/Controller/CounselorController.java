package prg381_milestone2.Controller;

import prg381_milestone2.Model.Counselor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CounselorController {

    private static final Logger logger = Logger.getLogger(CounselorController.class.getName());

    // Use the same Derby URL and Driver as AppointmentController
    private static final String JDBC_URL = "jdbc:derby:wellnessDB;create=true";
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

    public CounselorController() throws SQLException {
        try {
            // Load the Derby JDBC driver
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Derby JDBC driver not found. Make sure derby.jar is in your classpath.", e);
            throw new SQLException("Derby JDBC driver not found.", e);
        }
        createCounselorsTable(); // Ensure the table exists
        logger.info("CounselorController initialized and database connection established (Derby).");
    }

    // Method to establish a new connection
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }

    // Method to create the counselors table if it doesn't exist
    private void createCounselorsTable() throws SQLException {
        // Derby's SQL for auto-increment and table existence check
        String sql = "CREATE TABLE counselors (\n" +
                     "    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                     "    name VARCHAR(100) NOT NULL,\n" +
                     "    specialization VARCHAR(100) NOT NULL,\n" +
                     "    availability VARCHAR(100) NOT NULL,\n" +
                     "    PRIMARY KEY (id)\n" +
                     ")";
        try (Connection conn = connect(); // Get a fresh connection for table creation
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.info("Counselors table created successfully.");
        } catch (SQLException e) {
            // X0Y32 is the SQLState for "table already exists" in Derby
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println("Counselors table already exists."); // Using System.out for quick info
                logger.info("Counselors table already exists. No need to create.");
            } else {
                logger.log(Level.SEVERE, "Error creating counselors table.", e);
                throw e; // Re-throw the exception for the Dashboard to catch
            }
        }
    }

    // --- CRUD Operations ---

    // Add a new counselor
    public void addCounselor(Counselor counselor) throws SQLException {
        String sql = "INSERT INTO counselors (name, specialization, availability) VALUES (?, ?, ?)";
        try (Connection conn = connect(); // Get a new connection for the operation
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, counselor.getName());
            pstmt.setString(2, counselor.getSpecialization()); // FIX: Changed from lockSpecialization()
            pstmt.setString(3, counselor.getAvailability());
            pstmt.executeUpdate();
            logger.info("Counselor added: " + counselor.getName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding counselor: " + counselor.getName(), e);
            throw e;
        }
    }

    // Get a counselor by ID
    public Counselor getCounselorById(int id) throws SQLException {
        String sql = "SELECT * FROM counselors WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Counselor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("availability")
                    );
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting counselor by ID: " + id, e);
            throw e;
        }
        return null; // Counselor not found
    }

    // Get all counselors
    public List<Counselor> getAllCounselors() throws SQLException {
        List<Counselor> counselors = new ArrayList<>();
        String sql = "SELECT * FROM counselors";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                counselors.add(new Counselor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("availability")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting all counselors.", e);
            throw e;
        }
        return counselors;
    }

    // Update an existing counselor
    public void updateCounselor(Counselor counselor) throws SQLException {
        String sql = "UPDATE counselors SET name = ?, specialization = ?, availability = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, counselor.getName());
            pstmt.setString(2, counselor.getSpecialization());
            pstmt.setString(3, counselor.getAvailability());
            pstmt.setInt(4, counselor.getId());
            pstmt.executeUpdate();
            logger.info("Counselor updated: " + counselor.getName() + " (ID: " + counselor.getId() + ")");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating counselor: " + counselor.getName() + " (ID: " + counselor.getId() + ")", e);
            throw e;
        }
    }

    // Delete a counselor by ID
    public void deleteCounselor(int id) throws SQLException {
        String sql = "DELETE FROM counselors WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            logger.info("Counselor deleted with ID: " + id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting counselor with ID: " + id, e);
            throw e;
        }
    }
}