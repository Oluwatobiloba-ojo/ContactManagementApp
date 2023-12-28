package org.example.data.repository;

import org.example.data.model.ContactApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactAppRepository extends JpaRepository<ContactApp, Long> {
    ContactApp findByEmail(String email);

}
