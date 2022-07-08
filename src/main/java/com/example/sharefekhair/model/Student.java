package com.example.sharefekhair.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
public class Student {
    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id",nullable = false)
    @NotNull(message = "student_id is required")
    private MyUser user;

    @ManyToMany(mappedBy = "studentSet")
    private List<MyClass> classes;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                '}';
    }
}
