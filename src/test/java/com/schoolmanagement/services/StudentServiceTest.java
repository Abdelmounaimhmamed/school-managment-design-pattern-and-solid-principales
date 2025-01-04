package com.schoolmanagement.services;

import com.schoolmanagement.interfaces.StudentRepositoryInterface;
import com.schoolmanagement.models.Student;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StudentServiceTest {
    private StudentRepositoryInterface studentRepository;
    private StudentService studentService;

    @Before
    public void setUp() {
        studentRepository = mock(StudentRepositoryInterface.class);
        studentService = new StudentService(studentRepository);
    }

    @Test
    public void testGetAllStudents_ShouldReturnListOfStudents() {
        // Arrange
        List<Student> mockStudents = List.of(
                new Student(1, "olahbabi", "root", "lahbabi omar", "john.doe@example.com"),
                new Student(1, "ahmamed", "root", "hmamed mounaim", "john.mashi.doe@example.com")
        );
        when(studentRepository.findAll()).thenReturn(mockStudents);

        // Act
        List<Student> students = studentService.getAllStudents();

        // Assert
        assertEquals(2, students.size());
        assertEquals("lahbabi omar", students.get(0).getName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteStudentById_ShouldCallRepositoryDeleteById() {
        // Act
        studentService.deleteStudentById(1);

        // Assert
        verify(studentRepository, times(1)).deleteById(1);
    }
}
