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

    @PostMapping
    public ResultImage saveImage(@RequestBody ResultImage r){
        return repo.save(r);
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
