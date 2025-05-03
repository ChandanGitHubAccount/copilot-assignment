package com.copilot.assignment.examples;

public class CategoryRequests {

    /**
     * JSON payload for saving a new category.
     * Example:
     * {
     * "name": "Drama"
     * }
     */
    public static final String CATEGORY_SAVE_REQ = """
            {
              "name": "Drama"
            }""";

    /**
     * JSON payload for updating an existing category.
     * Example:
     * {
     * "id": 1,
     * "name": "Comedy"
     * }
     */
    public static final String CATEGORY_UPDATE_REQ = """
            {
              "id": 1,
              "name": "Comedy"
            }""";
}
