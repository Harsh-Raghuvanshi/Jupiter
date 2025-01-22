package com.exengg.jupiter.Utils;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Extra{
    private Integer weight;
    private String eyeSight;
    private Double height;
}

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Student {
    private String name;
//    private Integer age;
//    private String rollNo;
    private Character gender;
    private Extra extra;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Person {
    private String name;
    private Integer age;
    private Character gender;
    private Extra extra;
}

public class Test {
    public static void main(String[] args) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person("Harsh", 21, 'M',new Extra(74,"6/6",182.23));
        System.out.println(person);
        String personString = objectMapper.writeValueAsString(person);
        System.out.println(personString);

        Student student=objectMapper.readValue(personString, Student.class);
        System.out.println(student.getExtra().getWeight());
        System.out.println(student);

    }
}
