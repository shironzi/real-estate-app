package com.aaronjosh.real_estate_app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.aaronjosh.real_estate_app.dto.PropertyDto;
import com.aaronjosh.real_estate_app.models.PropertyEntity;
import com.aaronjosh.real_estate_app.models.UserEntity.Role;
import com.aaronjosh.real_estate_app.repositories.PropertyRepository;

@Service
public class PropertyService {

    @Autowired
    private UserService userService;

    @Autowired
    private PropertyRepository propertyRepo;

    public List<PropertyEntity> getProperties() {
        return propertyRepo.findAll();
    }

    public PropertyEntity getPropertyById(Long propertyId) {
        PropertyEntity property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        return property;

    }

    public PropertyEntity addProperty(PropertyDto propertyDto) {
        Long userId = userService.getUserId();
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

    public PropertyEntity editProperty(PropertyDto propertyDto, Long propertyId) {
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

    public void deleteProperty(Long propertyId) {
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
