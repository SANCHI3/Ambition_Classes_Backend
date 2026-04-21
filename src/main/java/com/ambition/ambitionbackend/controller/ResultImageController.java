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
@CrossOrigin(origins = "https://ambitionclassesozar.in")
public class ResultImageController {

    @Autowired
    private ResultImageRepository imageRepository; // ✅ FIXED

    @Autowired
private Cloudinary cloudinary;

@PostMapping("/upload")
public Map upload(@RequestParam("file") MultipartFile file) throws Exception {

    Map uploadResult = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.emptyMap()
    );

    String imageUrl = uploadResult.get("secure_url").toString();

    ResultImage r = new ResultImage();
    r.setImage(imageUrl);

    repo.save(r);

    return Map.of("url", imageUrl);
}

    // ✅ GET ALL IMAGES
    @GetMapping
    public List<ResultImage> getAllImages(){
        return imageRepository.findAll(); // ✅ FIXED
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        imageRepository.deleteById(id);
    }
}
