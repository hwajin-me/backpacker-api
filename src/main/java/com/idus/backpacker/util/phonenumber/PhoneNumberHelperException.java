package com.idus.backpacker.util.phonenumber;

public class PhoneNumberHelperException extends RuntimeException {
    PhoneNumberHelperException(String message) {
        super(message);
    }

    PhoneNumberHelperException(String message, Throwable cause) {
        super(message, cause);
    }
}
