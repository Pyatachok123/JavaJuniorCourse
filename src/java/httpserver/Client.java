package src.java.httpserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import src.java.entity.Student;
import src.java.entity.data.Request;
import src.java.entity.data.Response;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Client {
    private static final String SERVER_URL = "http://localhost:8000/add-students";

    public static Response addStudentsToUniversity(Request request) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        String requestBody = objectMapper.writeValueAsString(request);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(SERVER_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Response responseObject = objectMapper.readValue(response.body(), Response.class);
            return responseObject;
        } else {
            System.out.println("Failed to add students. HTTP error code: " + response.statusCode());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        Student student1 = new Student();
        student1.setName("Anna");
        student1.setDepartment("Computer Science");
        student1.setAge(17);

        Request request = new Request();
        request.setNameOfUniversity("Science University");
        request.setStudentsToAdd(List.of(student1));

        Response response = addStudentsToUniversity(request);

        System.out.println("Updated University: " + response.getNameOfUniversity());
        System.out.println("Students: " + response.getStudents());
        System.out.println("Teachers: " + response.getTeachers());

    }
}
