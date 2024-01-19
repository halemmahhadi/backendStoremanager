package com.app.storemanager.user.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    public Image uploadImage(Image image){
        return imageRepository.save(image);
    }

    public Image getById(Long id)
    {
       return imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public Image deleteImage(Long id){
        Image image=getById(id);
        imageRepository.delete(image);
        return image;
    }
    @Transactional
    public Image editImage(Long id, MultipartFile multipartImage){
        Image imageToEdit=getById(id);
        try {
            imageToEdit.setContent(multipartImage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageToEdit;
    }

}
