package com.aaronjosh.real_estate_app.dto;

import java.math.BigDecimal;

import com.aaronjosh.real_estate_app.models.PropertyEntity.PropertyType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "Property Type is required")
    private PropertyType propertyType;

    @NotNull(message = "Max guest is required")
    private Integer maxGuest;

    @NotNull(message = "Total bedroom is required")
    private Integer totalBedroom;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;
}
