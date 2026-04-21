package com.ambition.ambitionbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "result_images")
public class ResultImage {

    @Id
    private String id;

    private String image; // Cloudinary URL

    public String getId() { return id; }
    public String getImage() { return image; }

    public void setId(String id) { this.id = id; }
    public void setImage(String image) { this.image = image; }
}
