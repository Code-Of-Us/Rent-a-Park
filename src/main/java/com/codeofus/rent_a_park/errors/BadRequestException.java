package com.codeofus.rent_a_park.errors;

public class BadRequestException extends Throwable {

    String message;
    String entityName;
    String errorKey;

    public BadRequestException(String message, String entityName, String errorKey) {
        this.message = message;
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }
}
