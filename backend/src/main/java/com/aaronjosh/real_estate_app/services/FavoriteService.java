package com.aaronjosh.real_estate_app.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaronjosh.real_estate_app.models.FavoriteEntity;
import com.aaronjosh.real_estate_app.models.PropertyEntity;
import com.aaronjosh.real_estate_app.models.UserEntity;
import com.aaronjosh.real_estate_app.repositories.FavoriteRepository;
import com.aaronjosh.real_estate_app.repositories.PropertyRepository;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepo;

    @Autowired
    private PropertyRepository propertyRepo;
    
    @Autowired
    private UserService userService;

    // Checks if a favorite with the given UUID exists
    public boolean getFavorite(UUID id) {
        return favoriteRepo.findById(id).isPresent();
    }

    // Adds a favorite for the current user and the specified property.
    public void setFavorite(UUID propertyId) {
        UserEntity user = userService.getUserEntity();
        PropertyEntity property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        FavoriteEntity favorite = new FavoriteEntity();

        favorite.setUser(user);
        favorite.setProperty(property);

        favoriteRepo.save(favorite);
    }
    
    // Removes a favorite entry by its UUID
    public void removeFavorite(UUID favoriteId) {
        FavoriteEntity favorite = favoriteRepo.findById(favoriteId)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
            
        favoriteRepo.delete(favorite);
    }
}
