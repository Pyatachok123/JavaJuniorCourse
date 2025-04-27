package src.java.entity.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import src.java.entity.Student;


import java.util.List;

@JsonAutoDetect
public class Request {
    private String nameOfUniversity;
    private List<Student> studentsToAdd;

    public String getNameOfUniversity() {
        return nameOfUniversity;
    }

    public void setNameOfUniversity(String nameOfUniversity) {
        this.nameOfUniversity = nameOfUniversity;
    }

    public List<Student> getStudentsToAdd() {
        return studentsToAdd;
    }

    public void setStudentsToAdd(List<Student> studentsToAdd) {
        this.studentsToAdd = studentsToAdd;
    }
}