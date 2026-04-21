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
@CrossOrigin(origins = {
    "https://ambitionclassesozar.in",
    "https://www.ambitionclassesozar.in"
})
public class ResultImageController {

    @Autowired
    private ResultImageRepository repo; 

    @Autowired
private Cloudinary cloudinary;

@PostMapping("/upload")
public Map upload(@RequestParam("file") MultipartFile file) {

    try {
        System.out.println("FILE NAME: " + file.getOriginalFilename());

        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.emptyMap()
        );

        String imageUrl = uploadResult.get("secure_url").toString();

        ResultImage r = new ResultImage();
        r.setImage(imageUrl);

        repo.save(r);

        return Map.of("url", imageUrl);

    } catch (Exception e) {
        e.printStackTrace(); // 🔥 THIS WILL SHOW REAL ERROR
        throw new RuntimeException(e);
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
}
