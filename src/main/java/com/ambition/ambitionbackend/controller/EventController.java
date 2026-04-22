package com.ambition.ambitionbackend.controller;

import com.ambition.ambitionbackend.model.Event;
import com.ambition.ambitionbackend.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://ambitionclassesozar.in")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // ✅ 1. Create Event
    @PostMapping("/api/events")
    public Event createEvent(@RequestBody Event event){
        return eventRepository.save(event);
    }

    // ✅ 2. Add Photo (Cloudinary URL)
    @PostMapping("/api/events/add-photo")
    public String addPhoto(@RequestBody Map<String, String> data){

        String photo = data.get("photo");
        String eventId = data.get("eventId");

        if(photo == null || eventId == null){
            return "Missing data";
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if(event.getPhotos() == null){
            event.setPhotos(new ArrayList<>());
        }

        event.getPhotos().add(photo);

        eventRepository.save(event);

        return "Photo added successfully";
    }

    // ✅ 3. Get Events
    @GetMapping("/api/events")
    public List<Event> getEvents(){
        return eventRepository.findAll();
    }

    // ✅ 4. Delete Photo (only from DB)
    @DeleteMapping("/api/photo")
    public String deletePhoto(@RequestParam String eventId,
                              @RequestParam String photoPath){

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if(event.getPhotos() != null){
            event.getPhotos().remove(photoPath);
        }

        eventRepository.save(event);

        return "Photo deleted";
    }

    // ✅ 5. Delete Event
    @DeleteMapping("/api/event/{id}")
    public String deleteEvent(@PathVariable String id){

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        eventRepository.deleteById(id);

        return "Event deleted";
    }
}
