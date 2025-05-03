package com.copilot.assignment.examples;

public class AuthorRequests {
    /**
     * JSON representation of a request to save a new author.
     * Contains the author's name, image URL, and description.
     */
    public static final String AUTHOR_SAVE_REQ = """
            {
              "name": "Author 1",
              "image": "https://ui-avatars.com/api/?name=Jhon+deo&background=0D8ABC&color=fff&size=256",
              "description": "John Doe is a passionate Java and Spring Boot developer"
            }""";


    /**
     * JSON representation of a request to update an existing author.
     * Contains the author's ID, updated name, image URL, and description.
     */
    public static final String AUTHOR_UPDATE_REQ = """
            {
              "id": 1,
              "name": "Jane Doe",
              "image": "https://ui-avatars.com/api/?name=Jhon+deo&background=0D8ABC&color=fff&size=256",
              "description": "John Doe is a passionate Java and Spring Boot developer with a strong focus on building scalable, secure, and maintainable backend services"
            }""";

}