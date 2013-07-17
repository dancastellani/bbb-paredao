/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.exception;

/**
 *
 * @author DanCastellani
 */
public class ApplicationException extends Exception {

    /**
     * Creates a new instance of
     * <code>ApplicationException</code> without detail message.
     */
    public ApplicationException() {
    }

    /**
     * Constructs an instance of
     * <code>ApplicationException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ApplicationException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of
     * <code>ApplicationException</code> with the specified detail message.
     *
     * @param msg the detail message.
     * @param ex Exception.getCause() method). (A null value is permitted, and
     * indicates that the cause is nonexistent or unknown.)
     */
    public ApplicationException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
