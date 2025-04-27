package src.java.entity.data;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import src.java.entity.Student;
import src.java.entity.Teacher;


import java.util.List;

@JsonAutoDetect
public class Response {
    private String nameOfUniversity;
    private List<Student> students;
    private List<Teacher> teachers;

    public String getNameOfUniversity() {
        return nameOfUniversity;
    }

    public void setNameOfUniversity(String nameOfUniversity) {
        this.nameOfUniversity = nameOfUniversity;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}

