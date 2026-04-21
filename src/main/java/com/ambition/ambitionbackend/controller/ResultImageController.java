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
public Map upload(@RequestParam("file") MultipartFile file) throws Exception {

    System.out.println("🔥 Upload started");

    Map uploadResult = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.emptyMap()
    );

    System.out.println("🔥 Cloudinary upload done");

    String imageUrl = uploadResult.get("secure_url").toString();

    ResultImage r = new ResultImage();
    r.setImage(imageUrl);

    repo.save(r);

    System.out.println("🔥 Saved to DB");

    return Map.of("url", imageUrl);
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
