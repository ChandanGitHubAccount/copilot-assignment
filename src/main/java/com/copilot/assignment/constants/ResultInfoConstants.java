package com.copilot.assignment.constants;

import com.copilot.assignment.controller.response.ResultInfo;

public class ResultInfoConstants {
    private ResultInfoConstants() {
        throw new IllegalStateException(BAD_REQUEST.getMessage());
    }

    //Error Code Format - XXYYYZZZ where,
    //XX -> Service Code. For application-settings, service code is 11
    //YYY -> Feature code (service specific). For common service, feature code is 000
    //ZZZ -> Sequence number should start from 001
    public static final ResultInfo SUCCESS = new ResultInfo("11000001", "SUCCESS", "Successful Request", "S");
    public static final ResultInfo SYSTEM_ERROR = new ResultInfo("11000002", "SYSTEM_ERROR", "Internal Server Error", "F");
    public static final ResultInfo BAD_REQUEST = new ResultInfo("11000003", "BAD_REQUEST", "Bad Request", "F");
    public static final ResultInfo MSG_PARSE_ERROR = new ResultInfo("11000004", "MSG_PARSE_ERROR", "Message Parsing Error", "F");
    public static final ResultInfo NOT_FOUND = new ResultInfo("11000005", "NOT_FOUND", "Requested resource not found", "F");
    public static final ResultInfo REQ_FIELD_VALIDATION_ERROR = new ResultInfo("11000006", "REQ_FIELD_VALIDATION_ERROR", "", "F");
    public static final ResultInfo AUTHOR_ALREADY_EXIST = new ResultInfo("11001001", "AUTHOR_ALREADY_EXIST", "Author name already exist.", "F");
    public static final ResultInfo AUTHOR_NOT_FOUND = new ResultInfo("11001002", "AUTHOR_NOT_FOUND", "Author details not found.", "F");
    public static final ResultInfo CATEGORY_ALREADY_EXIST = new ResultInfo("11002001", "CATEGORY_ALREADY_EXIST", "Category name already exist.", "F");
    public static final ResultInfo CATEGORY_NOT_FOUND = new ResultInfo("11002002", "CATEGORY_NOT_FOUND", "Category not found.", "F");
    public static final ResultInfo ARTICLE_ALREADY_EXIST = new ResultInfo("11003001", "ARTICLE_ALREADY_EXIST", "Article title already exist.", "F");
    public static final ResultInfo ARTICLE_NOT_FOUND = new ResultInfo("11003002", "ARTICLE_NOT_FOUND", "Article not found.", "F");

}