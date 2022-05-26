package com.victorlevin.moexstockservice.exception;

public class ThreadInterruptedException extends RuntimeException {
    public ThreadInterruptedException(Exception ex) {
        super(ex);
    }
}
