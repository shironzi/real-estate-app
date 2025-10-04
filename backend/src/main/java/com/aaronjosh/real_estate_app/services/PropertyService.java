package com.aaronjosh.real_estate_app.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.aaronjosh.real_estate_app.dto.PropertyDto;
import com.aaronjosh.real_estate_app.models.FileEntity;
import com.aaronjosh.real_estate_app.models.PropertyEntity;
import com.aaronjosh.real_estate_app.models.UserEntity;
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
        UserEntity user = userService.getUserEntity();

        if (user.getRole() != Role.OWNER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Only property owners are allowed to create a property.");
        }

        PropertyEntity property = new PropertyEntity();

        property.setHostId(user.getId());
        property.setTitle(propertyDto.getTitle());
        property.setDescription(propertyDto.getDescription());
        property.setPrice(propertyDto.getPrice());
        property.setPropertyType(propertyDto.getPropertyType());
        property.setMaxGuest(propertyDto.getMaxGuest());
        property.setTotalBedroom(propertyDto.getTotalBedroom());
        property.setTotalBed(propertyDto.getTotalBed());
        property.setTotalBath(propertyDto.getTotalBath());
        property.setAddress(propertyDto.getAddress());
        property.setCity(propertyDto.getCity());

        for (MultipartFile image : propertyDto.getImages()) {
            try {
                FileEntity file = new FileEntity();

                file.setName(image.getOriginalFilename());
                file.setType(image.getContentType());
                file.setData(image.getBytes());

                file.setPropertyEntity(property);
                property.getImages().add(file);
            } catch (java.io.IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to process image file", e);
            }
        }

        return propertyRepo.save(property);
    }

    public PropertyEntity editProperty(PropertyDto propertyDto, UUID propertyId) {
        UserEntity user = userService.getUserEntity();

        if (user.getRole() != Role.OWNER) {
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

        return propertyRepo.save(property);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProperty(UUID propertyId) {
        PropertyEntity property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        UUID hostId = property.getHostId();
        UserEntity user = userService.getUserEntity();

        if (!hostId.equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to delete this property");
        }

        propertyRepo.delete(property);
    }

}
