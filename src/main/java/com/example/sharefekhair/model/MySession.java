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
@Table(name = "session")
public class MySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinTable(
            name = "teacher_sessions",
            joinColumns = { @JoinColumn(name = "teacher_id") },
            inverseJoinColumns = { @JoinColumn(name = "session_id"),}
    )
    @NotNull(message = "teacher_id is required")
    private Teacher teacher;

    @ManyToOne
    @JoinTable(
            name = "class_sessions",
            joinColumns = { @JoinColumn(name = "class_id") },
            inverseJoinColumns = { @JoinColumn(name = "session_id"),}
    )
    @NotNull(message = "class_id is required")
    private MyClass myClass;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "student_sessions",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "session_id") }
    )
    private Set<Student> studentSet;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "notes_sessions",
            joinColumns = { @JoinColumn(name = "note_id") },
            inverseJoinColumns = { @JoinColumn(name = "session_id"),}
    )
    private Set<Note> notes;
}
