package com.aaronjosh.real_estate_app.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aaronjosh.real_estate_app.dto.PropertyDto;
import com.aaronjosh.real_estate_app.models.PropertyEntity;
import com.aaronjosh.real_estate_app.services.PropertyService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping({ "", "/" })
    public ResponseEntity<?> getProperties() {
        List<PropertyEntity> properties = propertyService.getProperties();

        return ResponseEntity.ok(Map.of("success", true, "properties", properties));
    }

    @GetMapping("/my-properties")
    public ResponseEntity<?> getMyProperties() {
        List<PropertyEntity> properties = propertyService.getMyPropeties();

        return ResponseEntity.ok(Map.of("success", true, "properties", properties));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProperty(@Valid @PathVariable UUID propertyId) {
        PropertyEntity property = propertyService.getPropertyById(propertyId);

        return ResponseEntity.ok(Map.of("success", true, "property", property));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProperty(@ModelAttribute PropertyDto property) {
        propertyService.addProperty(property);

        return ResponseEntity.ok(Map.of("success", true, "message", "Property created Successfully"));
    }

    // @PatchMapping("/{id}")
    // public ResponseEntity<?> editProperty(@PathVariable UUID propertyId,
    // @RequestBody PropertyDto propertyDto) {
    // propertyService.editProperty(propertyDto, propertyId);

    // return ResponseEntity.ok(Map.of("success", true, "message", "Property Updated
    // Successfully"));
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<?> deleteProperty(@PathVariable UUID propertyId) {
    // propertyService.deleteProperty(propertyId);

    // return ResponseEntity.ok(Map.of("success", true, "message", "Property Deleted
    // Successfully"));
    // }

}
