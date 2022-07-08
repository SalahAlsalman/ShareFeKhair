package com.example.sharefekhair.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
@Table(name = "user")
public class MyUser implements UserDetails {
    @Id
    @GeneratedValue
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    @NotEmpty(message = "username is required")
    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
    @NotEmpty(message = "password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(nullable = false)
    @NotEmpty(message = "email is required")
    @Email(message = "email must be valid")
    private String email;
    @Column(nullable = false)
    @NotEmpty(message = "role is required")
    @Pattern(regexp = "(student|teacher)",message = "role must be (student|teacher)")
    private String role;

    @OneToOne(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private Teacher teacher;

    @OneToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private Student student;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
    @JsonIgnore
    private List<Note> notes;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.getRole()));
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
