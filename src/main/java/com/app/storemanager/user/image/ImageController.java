package com.app.storemanager.user.image;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/images/")
public class ImageController {

    @Autowired
    ImageService imageService;

    @Autowired
    ImageRepository imageRepository;

    @PostMapping(value = "upload")
    @ApiOperation(value = "add image",
            notes = "add new image to DB",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added image"),
            @ApiResponse(code = 400, message = "Errors during input"),
    })
    public ResponseEntity<Image> uploadImage(@RequestPart MultipartFile multipartImage) throws Exception {
        try {
            Image dbImage = new Image();
            dbImage.setContent(multipartImage.getBytes());
            return new ResponseEntity<>(imageService.uploadImage(dbImage), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }


    Resource downloadImage(@PathVariable Long imageId) {
        Image image = imageService.getById(imageId);
        return new ByteArrayResource(image.getContent());
    }

    @DeleteMapping(value = "delete_image/id/{id}")
    public Image deleteImage(@PathVariable final Long id){
        Image image=imageService.deleteImage(id);
        return image;
    }
    @PutMapping(value = "update_image/id/{id}")
    public Image editImage(@RequestPart MultipartFile multipartImage, @PathVariable final Long id) throws Exception {
        Image editImage = imageService.editImage(id,multipartImage);
        return editImage;
    }

}
