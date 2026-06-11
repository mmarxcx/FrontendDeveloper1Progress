package com.iskollect.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Student {
    private int user_id;
    private String username;
    private String email;
    private String password_hash;
    private int age;
    private String profile_photo;
    private double total_points;
    private int raw_bottle_count;
    private String account_status;
    private int failed_login_attempts = 0;
    private String session_token;
    private java.time.LocalDateTime last_activity;
    private int weekly_bottles;
    private int streak;
    private LocalDate last_submit_date;
    private int failed_attempts = 0;
    private boolean is_locked;
    private java.time.LocalDateTime created_at;

    //for login
    public Student(int id, String email, String password_hash) {
        this.user_id = id;
        this.email = email;
        this.password_hash = password_hash;
    }

    //for signup
    public Student(String username, String email, String password_hash) {
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
    }

    //complete Student info
    public Student(int user_id, String username, String email, String password_hash,
                   int age, String profile_photo, double total_points,
                   int raw_bottle_count, String account_status, int failed_login_attempts,
                   String session_token, LocalDateTime last_activity) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
        this.age = age;
        this.profile_photo = profile_photo;
        this.total_points = total_points;
        this.raw_bottle_count = raw_bottle_count;
        this.account_status = account_status;
        this.failed_login_attempts = failed_login_attempts;
        this.session_token = session_token;
        this.last_activity = last_activity;
    }

    public Student() {}

    //getters and setters
    //user_id
    public int getStudentId() {
        return user_id;
    }
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    //username
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    //email
    public String getemail() {
        return email;
    }
    public void setemail(String email) {
        this.email = email;
    }

    //password
    public String getPassword_hash() {
        return password_hash;
    }
    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    //age
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    //photo
    public String getProfilePhoto() {
        return profile_photo;
    }
    public void setProfilePhoto(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    //total pts
    public double getTotalPoints() {
        return total_points;
    }
    public void setTotalPoints(double total_points) {
        this.total_points = total_points;
    }

    //raw bottle count
    public int getRawBottleCount() {
        return raw_bottle_count;
    }
    public void setRawBottleCount(int raw_bottle_count) {
        this.raw_bottle_count = raw_bottle_count;
    }

    //account status
    public String getAccountStatus() {
        return account_status;
    }
    public void setAccountStatus(String account_status) {
        this.account_status = account_status;
    }

    //failed login attempts
    public int getFailedLoginAttempts() {
        return failed_login_attempts;
    }
    public void setFailedLoginAttempts(int failed_login_attempts) {
        this.failed_login_attempts = failed_login_attempts;
    }

    //session token
    public String getSessionToken() { return session_token; }
    public void setSessionToken(String session_token) {
        this.session_token = session_token;
    }

    //last activity
    public LocalDateTime getLastActivity() {
        return last_activity;
    }
    public void setLastActivity(LocalDateTime last_activity) {
        this.last_activity = last_activity;
    }

    //weekly_bottles
    public int getWeeklyBottles() {
        return weekly_bottles;
    }
    public void setWeeklyBottles(int weekly_bottles) {
        this.weekly_bottles = weekly_bottles;
    }

    //streak
    public int getStreak() {
        return streak;
    }
    public void setStreak(int streak) {
        this.streak = streak;
    }

    //last_submit_date
    public LocalDate getLastSubmitDate() {
        return last_submit_date;
    }
    public void setLastSubmitDate(LocalDate last_submit_date) {
        this.last_submit_date = last_submit_date;
    }

    //failed_attempts
    public int getFailedAttempts() {
        return failed_attempts;
    }
    public void setFailedAttempts(int failed_attempts) {
        this.failed_attempts = failed_attempts;
    }

    //is_locked
    public boolean isLocked() {
        return is_locked;
    }
    public void setLocked(boolean is_locked) {
        this.is_locked = is_locked;
    }

    //created_at
    public LocalDateTime getCreatedAt() {
        return created_at;
    }
    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

}