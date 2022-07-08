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
@Table(name = "session")
public class MySession {

    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    @ManyToOne
    @JoinTable(
            name = "class_sessions",
            joinColumns = { @JoinColumn(name = "session_id") },
            inverseJoinColumns = { @JoinColumn(name = "class_id"),}
    )
    @NotNull(message = "class_id is required")
    private MyClass myClass;

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
    @JoinTable(
            name = "notes_sessions",
            joinColumns = { @JoinColumn(name = "session_id") },
            inverseJoinColumns = { @JoinColumn(name = "note_id"),}
    )
    private List<Note> notes;

    @Override
    public String toString() {
        return "MySession{" +
                "id=" + id +
                '}';
    }
}
