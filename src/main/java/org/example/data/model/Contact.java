package org.example.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Contact {
    @Id
    private Long id;
    private Long userId;
    private String phoneNumber;
    private String name;
}
