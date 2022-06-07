package com.example.sharefekhair.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "teacher_id",nullable = false)
    @NotNull(message = "teacher_id is required")
    private MyUser user;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<MyClass> classes;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private Set<MySession> sessions;


    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
