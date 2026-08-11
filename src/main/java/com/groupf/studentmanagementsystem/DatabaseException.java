package com.groupf.studentmanagementsystem;

/**
 * Wraps a database error so the user interface can display a useful message.
 */
public class DatabaseException extends RuntimeException {

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
