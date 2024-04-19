package com.example.springbootmailoauth.common;

public interface ResponseMessage {

    String SUCCESS = "Success";

    String VALIDATION_FAIL = "Validation failed";

    String DUPLICATE_ID = "Duplicate ID";

    String MAIL_FAIL = "Mail Send Failed";

    String SIGN_IN_FAIL = "Login information mismatch";

    String CERTIFICATION_FAIL = "Certification failed";
    String DATABASE_ERROR = "Database Error";

}
