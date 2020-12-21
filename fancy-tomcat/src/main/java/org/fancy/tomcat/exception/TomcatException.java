package org.fancy.tomcat.exception;

public class TomcatException extends Exception {

    public TomcatException(String message) {
        super(message);
    }

    public TomcatException(Throwable cause) {
        super(cause);
    }
}
