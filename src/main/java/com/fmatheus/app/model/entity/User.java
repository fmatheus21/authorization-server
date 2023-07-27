package com.fmatheus.app.model.entity;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_person"}),
        @UniqueConstraint(columnNames = {"id"}),
        @UniqueConstraint(columnNames = {"username"})})
public class User extends Base {

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 70)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @ToString.Exclude
    @JoinColumn(name = "id_person", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Person person;

    @ToString.Exclude
    @JoinTable(name = "user_permission_join", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_permission"))
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Permission> permissions;

}
