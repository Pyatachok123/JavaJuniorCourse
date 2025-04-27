package src.java.json_example;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;


@JsonAutoDetect
@JsonPropertyOrder({"name", "age"})
class User {
    @JsonProperty("nameOfUser")
    private String name;
    private String age;

    private List<Hobbie> hobbies;


    public List<Hobbie> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobbie> hobbies) {
        this.hobbies = hobbies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(age, user.age) && Objects.equals(hobbies, user.hobbies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, hobbies);
    }
}


class Hobbie {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hobbie)) return false;
        Hobbie hobbie = (Hobbie) o;
        return Objects.equals(name, hobbie.name) && Objects.equals(description, hobbie.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}

public class ExampleDeserialize {
    public static void main(String[] args) throws JsonProcessingException {
        User userToSerialize = new User();
        userToSerialize.setName("Maria");
        userToSerialize.setAge("18");

        Hobbie hobbie = new Hobbie();
        hobbie.setName("Play guitar");
        hobbie.setDescription("Electro guitar");

        Hobbie hobbie2 = new Hobbie();
        hobbie2.setName("Play piano");
        hobbie2.setDescription("Electro guitar");

        userToSerialize.setHobbies(List.of(hobbie, hobbie2));

        ObjectMapper objectMapper = new ObjectMapper();

        String serialized = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userToSerialize);

        System.out.println(serialized);

        User userDeserialized = objectMapper.readValue(serialized, User.class);

        System.out.println(userToSerialize.equals(userDeserialized));
    }
}
