package com.fmatheus.app.model.entity;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "systems", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"}),
        @UniqueConstraint(columnNames = {"id"})})
public class Systems extends Base {

    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "system")
    private Collection<Permission> permissions;

}
