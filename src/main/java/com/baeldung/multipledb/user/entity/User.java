package com.baeldung.multipledb.user.entity;

//package com.baeldung.multipledb.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "userss")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private int id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private int age;
}
