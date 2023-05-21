package com.main.LocalServer.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Error {
    private String message;

    private String errorType;

    public enum ErrorType {
        NOT_FOUND_USER("NOT_FOUND_USER"),
        VALIDATION_ERROR("VALIDATION_ERROR"),
        UNEXPECTED("UNEXPECTED"),
        WRONG_INPUT("WRONG_INPUT"),
        ALREADY_EXISTS("ALREADY_EXISTS");

        private final String name;

        ErrorType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}


