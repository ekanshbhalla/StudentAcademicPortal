package com.klu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.klu.models.Course;
import com.klu.models.Enrollment;
import com.klu.models.Student;

import com.klu.repository.CourseRepository;
import com.klu.repository.EnrollmentRepository;
import com.klu.repository.StudentRepository;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MongoLogService mongoLogService;

    // ADD ENROLLMENT
    public Enrollment addEnrollment(
            Enrollment enrollment
    ) {

        Student student =
                studentRepository.findById(
                        enrollment.getStudent()
                                  .getStudentId()
                ).orElse(null);

        Course course =
                courseRepository.findById(
                        enrollment.getCourse()
                                  .getCourseId()
                ).orElse(null);

        enrollment.setStudent(student);

        enrollment.setCourse(course);

        Enrollment savedEnrollment = enrollmentRepository.save(
                enrollment
        );

        mongoLogService.logEnrollment(
                savedEnrollment.getStudent() != null ? savedEnrollment.getStudent().getStudentId() : null,
                savedEnrollment.getCourse() != null ? savedEnrollment.getCourse().getCourseId() : null,
                "ENROLLMENT_CREATED"
        );

        return savedEnrollment;
    }

    // GET ALL ENROLLMENTS
    public List<Enrollment> getAllEnrollments() {

        return enrollmentRepository.findAll();
    }
}
