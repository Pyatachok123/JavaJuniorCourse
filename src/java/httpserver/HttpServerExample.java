package src.java.httpserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import src.java.entity.Student;
import src.java.entity.Subject;
import src.java.entity.Teacher;
import src.java.entity.University;
import src.java.entity.data.Request;
import src.java.entity.data.Response;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServerExample {
    private static Map<String, University> universityMap = new HashMap<>();

    static {
        fillMapInitially(universityMap);
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/add-students", new AddStudentsHandler());
        server.setExecutor(null);
        server.start();
    }

    static class AddStudentsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                ObjectMapper objectMapper = new ObjectMapper();

                Request request = objectMapper.readValue(exchange.getRequestBody(), Request.class);

                University university = universityMap.get(request.getNameOfUniversity());
                if (university != null) {
                    university.getStudents().addAll(request.getStudentsToAdd());
                } else {
                    university = new University();
                    university.setStudents(request.getStudentsToAdd());
                    university.setTeachers(List.of());
                    university.setNameOfUniversity(request.getNameOfUniversity());

                    universityMap.put(request.getNameOfUniversity(), university);
                }

                Response response = new Response();

                response.setNameOfUniversity(university.getNameOfUniversity());
                response.setStudents(university.getStudents());
                response.setTeachers(university.getTeachers());

                String jsonResponse = objectMapper.writeValueAsString(response);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);

                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }


    private static void fillMapInitially(Map<String, University> universityMap) {
        Student student1 = new Student();
        student1.setName("Alice");
        student1.setDepartment("Computer Science");
        student1.setAge(20);

        Student student2 = new Student();
        student2.setName("Bob");
        student2.setDepartment("Physics");
        student2.setAge(22);

        Subject subject1 = new Subject();
        subject1.setNameOfSubject("Algorithms");
        subject1.setTrainingPeriod(6);

        Subject subject2 = new Subject();
        subject2.setNameOfSubject("Quantum Mechanics");
        subject2.setTrainingPeriod(8);

        Teacher teacher1 = new Teacher();
        teacher1.setName("Dr. Smith");
        teacher1.setSubjects(List.of(subject1, subject2));

        List<Student> students1 = new ArrayList<>(List.of(student1, student2));
        List<Teacher> teachers1 = new ArrayList<>(List.of(teacher1));

        University university1 = new University();
        university1.setNameOfUniversity("Tech University");
        university1.setStudents(students1);
        university1.setTeachers(teachers1);

        universityMap.put(university1.getNameOfUniversity(), university1);

        Student student3 = new Student();
        student3.setName("Charlie");
        student3.setDepartment("Mathematics");
        student3.setAge(21);

        Teacher teacher2 = new Teacher();
        teacher2.setName("Dr. Johnson");
        teacher2.setSubjects(List.of(subject1));

        List<Student> students2 = new ArrayList<>(List.of(student3));
        List<Teacher> teachers2 = new ArrayList<>(List.of(teacher2));

        University university2 = new University();
        university2.setNameOfUniversity("Science University");
        university2.setStudents(students2);
        university2.setTeachers(teachers2);

        universityMap.put(university2.getNameOfUniversity(), university2);
    }
}
