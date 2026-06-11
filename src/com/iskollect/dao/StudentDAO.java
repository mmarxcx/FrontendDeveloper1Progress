package com.iskollect.dao;

import com.iskollect.exception.DatabaseException;
import com.iskollect.model.Student;
import com.iskollect.util.DBConnection;
import com.iskollect.util.PasswordUtil;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    //register user
    public boolean registerStudent(Student student) throws DatabaseException {
        String register_query = "INSERT INTO students (username, email, password_hash) VALUES (?, ?, ?);";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(register_query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, student.getUsername());
            ps.setString(2, student.getemail());
            String hashedPassword = PasswordUtil.hashPassword(student.getPassword_hash());
            ps.setString(3,hashedPassword);

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        student.setUserId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Student registered successfully with ID: " + student.getStudentId());
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to execute registration insertion on Supabase server.", e);
        }
    }

    //search for user's email to match inputted email and password
    public Student searchStudent(String email) throws DatabaseException {
        String query = "SELECT * FROM students WHERE LOWER(email) = LOWER(?)";

        Student fullStudent = null;

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    fullStudent = new Student();

                    fullStudent.setUserId(rs.getInt("user_id"));
                    fullStudent.setUsername(rs.getString("username"));
                    fullStudent.setemail(rs.getString("email"));
                    fullStudent.setPassword_hash(rs.getString("password"));
                    fullStudent.setAge(rs.getInt("age"));
                    fullStudent.setProfilePhoto(rs.getString("profile_photo"));
                    fullStudent.setTotalPoints(rs.getInt("total_points"));
                    fullStudent.setRawBottleCount(rs.getInt("raw_bottle_count"));
                    fullStudent.setAccountStatus(rs.getString("account_status"));
                    fullStudent.setFailedLoginAttempts(rs.getInt("failed_login_attempts"));
                    fullStudent.setSessionToken(rs.getString("session_token"));

                    java.sql.Timestamp activityTimestamp = rs.getTimestamp("last_activity");
                    if (activityTimestamp != null) {
                        fullStudent.setLastActivity(activityTimestamp.toLocalDateTime());
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to search student credential.", e);
        }

        return fullStudent;
    }

    //get the user's token
    public String getSessionTokenDB(int studentId) {
        String query = "SELECT session_token FROM students WHERE user_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("session_token");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error verifying token against database.", e);
        }
        return null;
    }

    //update the user's token
    public void updateSessionToken(int studentId, String token) throws DatabaseException {
        String query = "UPDATE students SET session_token = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, token);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update token in database.", e);
        }
    }

    public Student findById(int studentId) throws DatabaseException {
        String query = "SELECT * FROM students WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setUserId(rs.getInt("user_id"));
                    student.setUsername(rs.getString("username"));
                    student.setemail(rs.getString("email"));
                    student.setPassword_hash(rs.getString("password"));
                    student.setAge(rs.getInt("age"));
                    student.setProfilePhoto(rs.getString("profile_photo"));
                    student.setTotalPoints(rs.getInt("total_points"));
                    student.setRawBottleCount(rs.getInt("raw_bottle_count"));
                    student.setAccountStatus(rs.getString("account_status"));
                    student.setFailedLoginAttempts(rs.getInt("failed_login_attempts"));
                    student.setSessionToken(rs.getString("session_token"));
                    return student;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to find student by ID.", e);
        }
        return null;
    }

    public void updatePoints(int studentId, double points) throws DatabaseException {
        String query = "UPDATE students SET total_points = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, points);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update student points.", e);
        }
    }

    public boolean deductPointsAtomic(int studentId, double amount) throws DatabaseException {
        String query = "UPDATE students SET total_points = total_points - ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, studentId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to deduct student points.", e);
        }
    }

    public void updateWeeklyStats(int studentId, int weeklyBottles, int streak, LocalDate date) throws DatabaseException {
        String query = "UPDATE students SET weekly_bottles = ?, streak = ?, last_submit_date = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, weeklyBottles);
            pstmt.setInt(2, streak);
            pstmt.setDate(3, java.sql.Date.valueOf(date));
            pstmt.setInt(4, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update student weekly stats.", e);
        }
    }

    public void updateWeeklyStats(int studentId, int weeklyBottles, int streak, LocalDateTime dateTime) throws DatabaseException {
        String query = "UPDATE students SET weekly_bottles = ?, streak = ?, last_submit_date = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, weeklyBottles);
            pstmt.setInt(2, streak);
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(dateTime));
            pstmt.setInt(4, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update student weekly stats.", e);
        }
    }

    public void resetWeeklyStats(int studentId) throws DatabaseException {
        String query = "UPDATE students SET weekly_bottles = 0, streak = 0 WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to reset student weekly stats.", e);
        }
    }

    public List<Integer> getAllStudentIds() throws DatabaseException {
        String query = "SELECT user_id FROM students";
        List<Integer> ids = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch all student IDs.", e);
        }
        return ids;
    }

    public void updateProfile(int studentId, String username) throws DatabaseException {
        String query = "UPDATE students SET username = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update student profile.", e);
        }
    }
}
