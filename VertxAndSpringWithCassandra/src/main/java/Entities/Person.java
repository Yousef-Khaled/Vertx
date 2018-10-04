package Entities;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;


@Table(name = "person", keyspace = "test")
public class Person {

    @PartitionKey
    @Column(name = "person_id")
    private String person_id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    Integer age;

    @Column(name = "status")
    private String status;

    public Person() {
    }

    public Person(String person_id, String name, Integer age, String status) {
        this.person_id = person_id;
        this.name = name;
        this.age = age;
        this.status = status;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }
}
