package com.example.sharefekhair.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    @ManyToOne
    @JoinTable(
            name = "notes_sessions",
            joinColumns = {@JoinColumn(name = "session_id")},
            inverseJoinColumns = {@JoinColumn(name = "note_id"),}
    )
    @JsonIgnore
    private MySession mySession;

    @OneToMany(mappedBy = "note")
    private Set<Comment> comments;

    public Note(Integer id, String message, Date messageDate, MyUser user, MySession mySession) {
        this.id = id;
        this.message = message;
        this.messageDate = new Date();
        this.user = user;
        this.mySession = mySession;
    }
}
