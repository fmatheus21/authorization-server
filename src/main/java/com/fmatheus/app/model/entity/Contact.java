package com.fmatheus.app.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contact", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "email", "id_person"})
})
public class Contact extends Base{

    @NotNull
    @Size(max = 80)
    @Email
    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @NotNull
    @Size(max = 15)
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @JoinColumn(name = "id_person", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    private Person person;

}
