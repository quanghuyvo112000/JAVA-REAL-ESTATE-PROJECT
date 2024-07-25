package com.tdtu.finalproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
     String name;
     String description;

     @ManyToMany
     Set<Permission> permission;
}
