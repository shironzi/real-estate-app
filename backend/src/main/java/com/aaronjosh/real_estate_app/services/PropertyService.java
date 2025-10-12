package com.aaronjosh.real_estate_app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.aaronjosh.real_estate_app.dto.property.PropertyDto;
import com.aaronjosh.real_estate_app.dto.property.PropertyDtoRes;
import com.aaronjosh.real_estate_app.models.PropertyImageEntity;
import com.aaronjosh.real_estate_app.models.PropertyEntity;
import com.aaronjosh.real_estate_app.models.UserEntity;
import com.aaronjosh.real_estate_app.repositories.PropertyRepository;
import com.aaronjosh.real_estate_app.repositories.UserRepository;

@Service
@Transactional
public class PropertyService {

    @Autowired
    private UserService userService;

    @Autowired
    private PropertyRepository propertyRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional(readOnly = true)
    public List<PropertyEntity> getProperties() {
        return propertyRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<PropertyDtoRes> getMyPropeties() {
        UserEntity user = userService.getUserEntity();

        List<PropertyDtoRes> properties = propertyRepo.findByHostId(user.getId()).stream().map(property -> {
            List<String> images = new ArrayList<String>();

            property.getImages().forEach(image -> {
                images.add("http://localhost:8080/api/image/" + image.getId());
            });

            PropertyDtoRes newProperty = new PropertyDtoRes();

            newProperty.setId(property.getId());
            newProperty.setTitle(property.getTitle());
            newProperty.setAddress(property.getAddress());
            newProperty.setCity(property.getCity());
            newProperty.setDescription(property.getDescription());
            newProperty.setMaxGuest(property.getMaxGuest());
            newProperty.setPrice(property.getPrice());
            newProperty.setPropertyType(property.getPropertyType());
            newProperty.setTotalBath(property.getTotalBath());
            newProperty.setTotalBed(property.getTotalBed());
            newProperty.setTotalBedroom(property.getTotalBedroom());
            newProperty.setStatus(property.getStatus());
            newProperty.setImage(images);

            return newProperty;
        }).collect(Collectors.toList());

        return properties;
    }

    @Transactional(readOnly = true)
    public PropertyEntity getPropertyById(UUID propertyId) {
        PropertyEntity property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        return property;

    }

    public PropertyEntity addProperty(PropertyDto propertyDto) {
        UserEntity jwtUser = userService.getUserEntity();

        // creating new property object
        PropertyEntity property = new PropertyEntity();

        // setting property info
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

        // adding the relation of images to property
        for (MultipartFile image : propertyDto.getImages()) {
            try {
                PropertyImageEntity file = new PropertyImageEntity();

                file.setName(image.getOriginalFilename());
                file.setType(image.getContentType());
                file.setData(image.getBytes());

                file.setPropertyEntity(property);
                property.getImages().add(file);
            } catch (java.io.IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to process image file", e);
            }
        }

        UserEntity userRef = userRepo.findById(jwtUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        property.setHost(userRef);

        // saving the property
        return propertyRepo.save(property);
    }

    // public PropertyEntity editProperty(PropertyDto propertyDto, UUID propertyId)
    // {
    // UserEntity user = userService.getUserEntity();

    // if (user.getRole() != Role.OWNER) {
    // throw new ResponseStatusException(HttpStatus.FORBIDDEN,
    // "Only property owners are allowed to create a property.");
    // }

    // PropertyEntity property = propertyRepo.findById(propertyId)
    // .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
    // "Property not found"));

    // property.setTitle(propertyDto.getTitle());
    // property.setAddress(propertyDto.getAddress());
    // property.setCity(propertyDto.getCity());
    // property.setDescription(propertyDto.getDescription());
    // property.setMaxGuest(propertyDto.getMaxGuest());
    // property.setPrice(propertyDto.getPrice());
    // property.setTotalBedroom(propertyDto.getTotalBedroom());
    // property.setPropertyType(propertyDto.getPropertyType());

    // return propertyRepo.save(property);
    // }

    // @Transactional(rollbackFor = Exception.class)
    // public void deleteProperty(UUID propertyId) {
    // PropertyEntity property = propertyRepo.findById(propertyId)
    // .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
    // "Property not found"));

    // UUID hostId = property.getHostId();
    // UserEntity user = userService.getUserEntity();

    // if (!hostId.equals(user.getId()) && user.getRole() != Role.ADMIN) {
    // throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have
    // access to delete this property");
    // }

    // propertyRepo.delete(property);
    // }

}
