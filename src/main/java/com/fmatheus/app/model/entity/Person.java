package com.fmatheus.app.model.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "document"})
})
public class Person extends Base {

    @NotNull
    @Size(max = 70)
    @Column(name = "name", nullable = false, length = 70)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(name = "document", nullable = false, length = 20)
    private String document;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
    private Address address;

    @JoinColumn(name = "id_person_type", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private PersonType personType;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
    private Contact contact;

}
