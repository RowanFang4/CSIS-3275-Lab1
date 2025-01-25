package com.example.crs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.crs.model.Course;
import com.example.crs.model.CourseRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        try {
            List<Course> courses = new ArrayList<Course>();
            courseRepository.findAll().forEach(courses::add);
            if (courses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") long id) {
        List<Course> courses = new ArrayList<Course>();
        courseRepository.findAll().forEach(courses::add);
        return new ResponseEntity<>(courses.get((int) id - 1), HttpStatus.OK);
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course coursesave = courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(coursesave);

    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") long id, @RequestBody Course course) {
        Course existingCourse = courseRepository.findById(id).orElse(null);
        existingCourse.setCode(course.getCode());
        existingCourse.setTitle(course.getTitle());
        courseRepository.save(existingCourse);
        return new ResponseEntity<>(existingCourse, HttpStatus.OK);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Long> deleteCourse(@PathVariable("id") long id) {
        courseRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}