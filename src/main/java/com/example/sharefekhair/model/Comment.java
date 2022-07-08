package com.example.sharefekhair.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment {

    @Id
    private String id= UUID.randomUUID().toString().toUpperCase();
    @Column(columnDefinition = "TEXT")
    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    @ManyToOne
    @JoinTable(
            name = "comments_note",
            joinColumns = {@JoinColumn(name = "comment_id")},
            inverseJoinColumns = {@JoinColumn(name = "note_id"),}
    )
    @JsonIgnore
    private Note note;

    public Comment(Integer id, String message, Date messageDate, MyUser user, Note note) {
        this.id = id;
        this.message = message;
        this.messageDate = new Date();
        this.user = user;
        this.note = note;
    }
}
