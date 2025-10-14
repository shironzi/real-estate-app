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
import com.aaronjosh.real_estate_app.dto.property.PropertyResDto;
import com.aaronjosh.real_estate_app.dto.property.UpdatePropertyDto;
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

    protected PropertyResDto toDto(PropertyEntity property) {
        List<String> images = new ArrayList<>();

        property.getImages().forEach(image -> {
            images.add("http://localhost:8080/api/image/" + image.getId());
        });

        PropertyResDto dto = new PropertyResDto();

        dto.setId(property.getId());
        dto.setTitle(property.getTitle());
        dto.setAddress(property.getAddress());
        dto.setCity(property.getCity());
        dto.setDescription(property.getDescription());
        dto.setMaxGuest(property.getMaxGuest());
        dto.setPrice(property.getPrice());
        dto.setPropertyType(property.getPropertyType());
        dto.setTotalBath(property.getTotalBath());
        dto.setTotalBed(property.getTotalBed());
        dto.setTotalBedroom(property.getTotalBedroom());
        dto.setStatus(property.getStatus());
        dto.setImage(images);

        return dto;
    }

    @Transactional(readOnly = true)
    public List<PropertyResDto> getProperties() {
        List<PropertyEntity> properties = propertyRepo.findAll();

        return properties.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PropertyResDto> getMyPropeties() {
        UserEntity user = userService.getUserEntity();

        return propertyRepo.findByHostId(user.getId()).stream().map(this::toDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public PropertyResDto getPropertyById(UUID propertyId) {
        PropertyEntity property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found"));

        return toDto(property);
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

    public PropertyEntity editProperty(UpdatePropertyDto propertyDto, UUID propertyId) {
        UserEntity user = userService.getUserEntity();

        PropertyEntity property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Property not found"));

        UUID hostId = property.getHost().getId();
        UUID userId = user.getId();

        if (!userId.equals(hostId)) {
            throw new RuntimeException("You dont have access");
        }

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

        return propertyRepo.save(property);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProperty(UUID propertyId) {
        PropertyEntity property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Property not found"));

        UUID hostId = property.getHost().getId();
        UUID userId = userService.getUserEntity().getId();

        if (!hostId.equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to delete this property");
        }

        propertyRepo.delete(property);
    }

}
