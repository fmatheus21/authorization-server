package com.fmatheus.app.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "id_person"}),
})
public class Address extends Base {

    @NotNull
    @Size(max = 70)
    @Column(name = "place", nullable = false, length = 70)
    private String place;

    @NotNull
    @Size(max = 10)
    @Column(name = "number", nullable = false, length = 10)
    private String number;

    @Size(max = 50)
    @Column(name = "complement", length = 50)
    private String complement;

    @NotNull
    @Size(max = 70)
    @Column(name = "district", nullable = false, length = 70)
    private String district;

    @NotNull
    @Size(max = 70)
    @Column(name = "city", nullable = false, length = 70)
    private String city;

    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "state", nullable = false, length = 2)
    private String state;

    @NotNull
    @Size(max = 15)
    @Column(name = "zip_code", nullable = false, length = 15)
    private String zipCode;

    @JoinColumn(name = "id_person", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Person person;

}
