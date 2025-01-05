package com.schoolmanagement.repositories;

import com.schoolmanagement.models.Grade;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GradeRepositoryTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private GradeRepository gradeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        gradeRepository = new GradeRepository(connection);

        // Default behavior for connection
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void findGradesByModuleElementId_ShouldReturnListOfGrades() throws Exception {
        // Arrange
        int moduleElementId = 1;
        when(resultSet.next()).thenReturn(true, true, false); // Will return 2 grades

        // Mock first grade
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getInt("student_id")).thenReturn(101, 102);
        when(resultSet.getInt("module_element_id")).thenReturn(moduleElementId, moduleElementId);
        when(resultSet.getDouble("grade_value")).thenReturn(85.5, 90.0);
        when(resultSet.getBoolean("is_absent")).thenReturn(false, false);

        // Act
        List<Grade> grades = gradeRepository.findGradesByModuleElementId(moduleElementId);

        // Assert
        assertEquals(2, grades.size());

        Grade firstGrade = grades.get(0);
        assertEquals(1, firstGrade.getId());
        assertEquals(101, firstGrade.getStudentId());
        assertEquals(moduleElementId, firstGrade.getModuleElementId());
        assertEquals(85.5, firstGrade.getGradeValue(), 0.001);
        assertFalse(firstGrade.isAbsent());

        Grade secondGrade = grades.get(1);
        assertEquals(2, secondGrade.getId());
        assertEquals(102, secondGrade.getStudentId());
        assertEquals(moduleElementId, secondGrade.getModuleElementId());
        assertEquals(90.0, secondGrade.getGradeValue(), 0.001);
        assertFalse(secondGrade.isAbsent());

        // Verify correct SQL query execution
        verify(preparedStatement).setInt(1, moduleElementId);
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void findGradesByModuleElementId_ShouldReturnEmptyList_WhenNoGradesFound() throws Exception {
        // Arrange
        int moduleElementId = 1;
        when(resultSet.next()).thenReturn(false);

        // Act
        List<Grade> grades = gradeRepository.findGradesByModuleElementId(moduleElementId);

        // Assert
        assertTrue(grades.isEmpty());
        verify(preparedStatement).setInt(1, moduleElementId);
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void saveGrade_ShouldExecuteCorrectSQLStatement() throws Exception {
        // Arrange
        Grade grade = new Grade(1, 101, 201, 95.5, false);

        // Act
        gradeRepository.saveGrade(grade);

        // Assert
        verify(preparedStatement).setInt(1, grade.getStudentId());
        verify(preparedStatement).setInt(2, grade.getModuleElementId());
        verify(preparedStatement).setDouble(3, grade.getGradeValue());
        verify(preparedStatement).setBoolean(4, grade.isAbsent());
        verify(preparedStatement).setDouble(5, grade.getGradeValue());
        verify(preparedStatement).setBoolean(6, grade.isAbsent());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void saveGrade_ShouldHandleExceptionGracefully() throws Exception {
        // Arrange
        Grade grade = new Grade(1, 101, 201, 95.5, false);
        when(preparedStatement.executeUpdate()).thenThrow(new RuntimeException("Database error"));

        // Act
        gradeRepository.saveGrade(grade); // Should not throw exception

        // Assert
        verify(preparedStatement).executeUpdate();
    }
}