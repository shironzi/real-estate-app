package com.aaronjosh.real_estate_app.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "property")
public class PropertyEntity {
    public enum PropertyType {
        APARTMENT,
        HOUSE,
        VILLA,
        CABIN
    }

    public enum PropertyStatus {
        ACTIVE,
        INACTIVE,
        PENDING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotNull(message = "Host ID is required")
    private Long hostId;

    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Description is required")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Price is required")
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Property Type is required")
    private PropertyType propertyType;

    @Column(nullable = false)
    @NotNull(message = "Max guest is required")
    private Integer maxGuest;

    @Column(nullable = false)
    @NotNull(message = "Total bedroom is required")
    private Integer totalBedroom;

    @Column(nullable = false)
    @NotNull(message = "Total Bed is required")
    private Integer totalBeds;

    @Column(nullable = false)
    @NotNull(message = "Total bath is required")
    private Integer totalBaths;

    @Column(nullable = false)
    @NotBlank(message = "Address is required")
    private String address;

    @Column(nullable = false)
    @NotBlank(message = "City is required")
    private String city;

    @Enumerated(EnumType.STRING)
    private PropertyStatus status = PropertyStatus.ACTIVE;

    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "PropertyImage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileEntity> images = new ArrayList<>();

    public PropertyEntity() {
    }

    public PropertyEntity(Long hostId, String title, String description, BigDecimal price,
            PropertyType propertyType, Integer maxGuest, Integer totalBedroom,
            String address, String city) {
        this.hostId = hostId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.propertyType = propertyType;
        this.maxGuest = maxGuest;
        this.totalBedroom = totalBedroom;
        this.address = address;
        this.city = city;
        this.status = PropertyStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
