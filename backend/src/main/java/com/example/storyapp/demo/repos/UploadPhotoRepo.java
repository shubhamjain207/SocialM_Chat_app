package com.example.storyapp.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.storyapp.demo.entities.Photos;
import com.example.storyapp.demo.entities.PublicPhoto;

public interface UploadPhotoRepo extends JpaRepository<Photos,Integer> {
    
    @Query("SELECT new com.example.storyapp.demo.entities.PublicPhoto(p.photoUrl) FROM Photos p")
    public List<PublicPhoto> findPublicPhotos();

}