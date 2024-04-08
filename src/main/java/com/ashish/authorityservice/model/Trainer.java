package com.ashish.authorityservice.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Trainer {

    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private int slots;
    private int clients;
    private String qualifications;
    private String imageName;
    private boolean isActive;
    @JsonManagedReference
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Certificates> certificates;
}
