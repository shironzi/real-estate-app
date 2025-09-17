package com.aaronjosh.real_estate_app.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @NotBlank(message = "Address is required")
    private String address;

    @Column(nullable = false)
    @NotBlank(message = "City is required")
    private String city;

    @Column(columnDefinition = "TEXT")
    private String amenities;

    @Enumerated(EnumType.STRING)
    private PropertyStatus status = PropertyStatus.ACTIVE;

    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime createdAt = LocalDateTime.now();

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getMaxGuest() {
        return maxGuest;
    }

    public void setMaxGuest(Integer maxGuest) {
        this.maxGuest = maxGuest;
    }

    public Integer getTotalBedroom() {
        return totalBedroom;
    }

    public void setTotalBedroom(Integer totalBedroom) {
        this.totalBedroom = totalBedroom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
