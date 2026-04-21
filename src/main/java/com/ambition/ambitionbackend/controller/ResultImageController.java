package com.ambition.ambitionbackend.controller;

import com.ambition.ambitionbackend.model.ResultImage;
import com.ambition.ambitionbackend.repository.ResultImageRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/result-images")
@CrossOrigin(origins = "*")
public class ResultImageController {

    @Autowired
    private ResultImageRepository repo; 

    @Autowired
private Cloudinary cloudinary;

@PostMapping("/upload")
public Map upload(@RequestParam("file") MultipartFile file) {

    try {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),   // ✅ CHANGE THIS BACK
                ObjectUtils.asMap("resource_type", "auto")  // ✅ IMPORTANT
        );

        String imageUrl = uploadResult.get("secure_url").toString();

        ResultImage r = new ResultImage();
        r.setImage(imageUrl);
        repo.save(r);

        return Map.of("url", imageUrl);

    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Upload failed");
    }
}
    
@GetMapping
public List<ResultImage> getAllImages(){
    return repo.findAll(); 
}


@DeleteMapping("/{id}")
public void delete(@PathVariable String id) {
    repo.deleteById(id); 
}
    @PostMapping
public ResultImage saveImage(@RequestBody ResultImage r){
    return repo.save(r);
}
}
