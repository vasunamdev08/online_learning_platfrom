package com.vena.learning.dto.responseDto;

import com.vena.learning.model.Enrollment;
import lombok.Data;

@Data
public class EnrollmentSummary {
    private String courseTitle;
    private String instructorName;
    private Integer progress;
    private String grade;

    public EnrollmentSummary(Enrollment enrollment) {
        this.courseTitle = enrollment.getCourse().getTitle();
        this.instructorName = enrollment.getCourse().getInstructor().getName();
        this.progress = enrollment.getProgressMask();
        this.grade = enrollment.getGrade() != null ? enrollment.getGrade().name() : "Pending";
    }
}
