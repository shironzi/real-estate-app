package com.aaronjosh.real_estate_app.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.aaronjosh.real_estate_app.dto.PropertyDto;
import com.aaronjosh.real_estate_app.models.PropertyEntity;
import com.aaronjosh.real_estate_app.models.UserEntity.Role;
import com.aaronjosh.real_estate_app.repositories.PropertyRepository;

@Service
@Transactional
public class PropertyService {

    @Autowired
    private UserService userService;

    @Autowired
    private PropertyRepository propertyRepo;

    @Transactional(readOnly = true)
    public List<PropertyEntity> getProperties() {
        return propertyRepo.findAll();
    }

    @Transactional(readOnly = true)
    public PropertyEntity getPropertyById(UUID propertyId) {
        PropertyEntity property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        return property;

    }

    public PropertyEntity addProperty(PropertyDto propertyDto) {
        Long userId = userService.getUserId();
        Role userRole = userService.getRole();

        if (userRole != Role.OWNER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Only property owners are allowed to create a property.");
        }

        PropertyEntity property = new PropertyEntity();

        property.setHostId(userId);
        property.setTitle(propertyDto.getTitle());
        property.setAddress(propertyDto.getAddress());
        property.setCity(propertyDto.getCity());
        property.setDescription(propertyDto.getDescription());
        property.setMaxGuest(propertyDto.getMaxGuest());
        property.setPrice(propertyDto.getPrice());
        property.setTotalBedroom(propertyDto.getTotalBedroom());
        property.setPropertyType(propertyDto.getPropertyType());
        property.setAmenities(propertyDto.getAmenities());

        return propertyRepo.save(property);
    }

    public PropertyEntity editProperty(PropertyDto propertyDto, UUID propertyId) {
        Role userRole = userService.getRole();

        if (userRole != Role.OWNER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Only property owners are allowed to create a property.");
        }

        PropertyEntity property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        property.setTitle(propertyDto.getTitle());
        property.setAddress(propertyDto.getAddress());
        property.setCity(propertyDto.getCity());
        property.setDescription(propertyDto.getDescription());
        property.setMaxGuest(propertyDto.getMaxGuest());
        property.setPrice(propertyDto.getPrice());
        property.setTotalBedroom(propertyDto.getTotalBedroom());
        property.setPropertyType(propertyDto.getPropertyType());
        property.setAmenities(propertyDto.getAmenities());

        return propertyRepo.save(property);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProperty(UUID propertyId) {
        PropertyEntity property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        Long hostId = property.getHostId();
        Long userId = userService.getUserId();
        Role userRole = userService.getRole();

        if (!hostId.equals(userId) && userRole != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to delete this property");
        }

        propertyRepo.delete(property);
    }

}
