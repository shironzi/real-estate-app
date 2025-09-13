/**
 * User Entity
 * ---------------------------------
 * Represents user in the system.
 * 
 * - Contains personal details (fname, lname, email)
 * - Stores authentication data (Hashed password, role)
 * - Automatically update the creation and date timestampt
 */

package com.aaronjosh.real_estate_app.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class UserEntity {

    /**
     * User Role
     * -----------------
     * Define user role in the system.
     * - ADMIN: Full access to the system
     * - OWNER: Can list and manage own properties
     * - RENTER: Can search and rent property
     */
    public enum Role {
        ADMIN,
        OWNER,
        RENTER,
    }

    // entity or table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Firstname is required")
    @Column(nullable = false)
    private String firstname;

    @NotBlank(message = "Lastname is required")
    @Column(nullable = false)
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.RENTER;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime updated_at;

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String email, Role role) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.email = email;
        this.role = role;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    // fields, constractors and getters/setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return firstname + " " + lastname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
     * Sets created_at and updated_at before persisting.
     */

    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
        updated_at = LocalDateTime.now();
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
    /*
     * Sets update_at before updating.
     */

    @PreUpdate
    protected void onUpdate() {
        updated_at = LocalDateTime.now();
    }
}
