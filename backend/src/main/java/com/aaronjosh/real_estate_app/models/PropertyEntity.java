package com.aaronjosh.real_estate_app.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
    @NotBlank(message = "Title is required")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Description is required")
    private String description;

    @Column(nullable = false)
    @Min(0)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Property Type is required")
    private PropertyType propertyType;

    @Column(nullable = false)
    @Min(1)
    private Integer maxGuest;

    @Column(nullable = false)
    @Min(0)
    private Integer totalBedroom;

    @Column(nullable = false)
    @Min(0)
    private Integer totalBed;

    @Column(nullable = false)
    @Min(0)
    private Integer totalBath;

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

    @OneToMany(mappedBy = "propertyEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyImageEntity> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "host_id")
    private UserEntity host;

    public PropertyEntity() {
    }

    public PropertyEntity(UUID id, String title, String description, BigDecimal price,
            PropertyType propertyType, Integer maxGuest, Integer totalBedroom,
            Integer totalBed, Integer totalBath, String address, String city, List<PropertyImageEntity> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.propertyType = propertyType;
        this.maxGuest = maxGuest;
        this.totalBedroom = totalBedroom;
        this.totalBed = totalBed;
        this.totalBath = totalBath;
        this.address = address;
        this.city = city;
        this.images = images;
        this.status = PropertyStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
