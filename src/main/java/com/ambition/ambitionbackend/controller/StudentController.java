
package com.ambition.ambitionbackend.controller;

import com.ambition.ambitionbackend.model.Student;
import com.ambition.ambitionbackend.model.StudentRequest;
import com.ambition.ambitionbackend.model.User;
import com.ambition.ambitionbackend.repository.AttendanceRepository;
import com.ambition.ambitionbackend.repository.StudentRepository;
import com.ambition.ambitionbackend.model.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ambition.ambitionbackend.repository.UserRepository;

@CrossOrigin(origins = "https://ambitionclassesozar.in")
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private UserRepository userRepository;

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ✅ ADD
    @PostMapping
public ResponseEntity<?> addStudent(@RequestBody Student student) {

    // 🔥 CHECK IF ALREADY EXISTS
    Student existing = studentRepository.findByStudentMobile(student.getStudentMobile());

    if(existing != null){
        return ResponseEntity
                .status(400)
                .body("Student already exists with this mobile number");
    }

    Student saved = studentRepository.save(student);

    return ResponseEntity.ok(saved);
}

    // ✅ GET
    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // ✅ DELETE
    @DeleteMapping("/mobile/{mobile}")
public ResponseEntity<?> deleteStudent(@PathVariable String mobile) {
    try {

        System.out.println("DELETE HIT: " + mobile);

        Student student = studentRepository.findByStudentMobile(mobile);

        if (student == null) {
            return ResponseEntity.status(404).body("Student not found");
        }

        String id = student.getId();

        // 🔥 SAFE DELETE
        studentRepository.deleteById(id);

        return ResponseEntity.ok("Deleted OK");

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(e.toString());
    }
}
    // 🔥 ADD THIS HERE (UPDATE)
@PutMapping("/fees/{id}")
public Student updateFees(@PathVariable String id,
                          @RequestBody StudentRequest request) {

    Student existing = studentRepository.findById(id).orElse(null);

    if (existing == null) {
        throw new RuntimeException("Student not found");
    }

    existing.setTotalFees(request.getTotalFees());
    existing.setPaidAmount(request.getPaidAmount());

    // ❌ REMOVE feesStatus logic completely

    return studentRepository.save(existing);
}
   
    @GetMapping("/{id}")
    public Student getById(@PathVariable String id){
        return studentRepository.findById(id).orElse(null);
    }

    @GetMapping("/mobile/{mobile}")
    public Student getByMobile(@PathVariable String mobile){
        return studentRepository.findByStudentMobile(mobile);
    }

    @RequestMapping(method = RequestMethod.OPTIONS, path = "/**")
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }


}
