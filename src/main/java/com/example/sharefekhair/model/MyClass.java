package com.example.sharefekhair.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
@Table(name = "class")
public class MyClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "name is required")
    @Column(nullable = false,unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIgnore
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "Student_Class",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "class_id") }
    )
    @JsonIgnore
    private List<Student> studentSet;

    @OneToMany(mappedBy = "myClass",cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
//    @OneToMany(mappedBy = "myClass",cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
    @JsonIgnore
    private List<MySession> sessions;

    @Override
    public String toString() {
        return "MyClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
