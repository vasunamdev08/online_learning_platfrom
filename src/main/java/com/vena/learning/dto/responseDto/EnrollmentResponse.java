package com.vena.learning.dto.responseDto;

import com.vena.learning.enums.Grade;
import com.vena.learning.model.Enrollment;
import lombok.Data;

import java.util.Date;
@Data
public class EnrollmentResponse {
    private String id;
    private Date enrollmentDate;
    private Date completionDate;
    private Boolean isCompleted;
    private Integer progressMask;
    private Integer attempts;
    private Grade grade;
    private String studentName;
    private String courseTitle;

    public EnrollmentResponse (Enrollment e){
        this.id = e.getId();
        this.enrollmentDate = e.getEnrollmentDate();
        this.completionDate = e.getCompletionDate();
        this.isCompleted = e.getIsCompleted();
        this.progressMask = e.getProgressMask();
        this.attempts = e.getAttempts();
        this.grade = e.getGrade();
        this.studentName = e.getStudent().getName();
        this.courseTitle = e.getCourse().getTitle();
    }
}
