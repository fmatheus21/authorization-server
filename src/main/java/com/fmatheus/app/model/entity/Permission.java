package com.fmatheus.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;


@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "permission", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"})})
public class Permission extends Base {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ToString.Exclude
    @JoinTable(name = "user_permission_join", joinColumns = {@JoinColumn(name = "id_permission", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<User> users;

    @JoinColumn(name = "id_system", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Systems system;


}
