package com.example.sharefekhair.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "message is required")
    private String message;
    @NotEmpty(message = "messageDate is required")
    private Date messageDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotEmpty(message = "user_id is required")
    private MyUser user;

    @ManyToOne
    @JoinTable(
            name = "notes_sessions",
            joinColumns = { @JoinColumn(name = "session_id") },
            inverseJoinColumns = { @JoinColumn(name = "note_id"),}
    )
    private MySession mySession;

}
