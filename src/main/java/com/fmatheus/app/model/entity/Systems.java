package com.fmatheus.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

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

    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "system", fetch = FetchType.EAGER)
    private Collection<Permission> permissions;*/

}
