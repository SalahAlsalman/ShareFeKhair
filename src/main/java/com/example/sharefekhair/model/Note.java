package com.example.sharefekhair.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Note {

    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    @ManyToOne
    @JoinTable(
            name = "notes_sessions",
            joinColumns = {@JoinColumn(name = "note_id")},
            inverseJoinColumns = {@JoinColumn(name = "session_id"),}
    )
    @JsonIgnore
    private MySession mySession;

    @OneToMany(mappedBy = "note",cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
//    @OneToMany(mappedBy = "note",cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Comment> comments;

    public Note(UUID id,String title, String body, Date messageDate, MyUser user, MySession mySession) {
        this.id = id;
        this.title =title;
        this.body = body;
        this.messageDate = new Date();
        this.user = user;
        this.mySession = mySession;
    }


}
