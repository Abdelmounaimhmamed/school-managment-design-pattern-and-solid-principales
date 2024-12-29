package com.schoolmanagement.factories;

import com.schoolmanagement.builders.ProfessorBuilder;
import com.schoolmanagement.builders.StudentBuilder;
import com.schoolmanagement.models.Professor;
import com.schoolmanagement.models.Student;
import com.schoolmanagement.models.User;

public class UserFactory {
    public static User createUser(String userType, String username, String password, String... additionalInfo) {
        if ("Professor".equalsIgnoreCase(userType)) {
            return new ProfessorBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setFirstName(additionalInfo[0])
                    .setLastName(additionalInfo[1])
                    .setSpecialty(additionalInfo[2])
                    .build();
        } else if ("Student".equalsIgnoreCase(userType)) {
            return new StudentBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setName(additionalInfo[0])
                    .setEmail(additionalInfo[1])
                    .build();
        }
        throw new IllegalArgumentException("Invalid user type");
    }
}
