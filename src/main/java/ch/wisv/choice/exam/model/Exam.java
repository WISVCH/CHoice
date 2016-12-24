package ch.wisv.choice.exam.model;


import ch.wisv.choice.course.model.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * An Exam entity represents an exam that was taken for a course,
 * not the physical document with the questions on it.
 */
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"course", "date"}))
@Entity
@AllArgsConstructor
public class Exam {

    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "course")
    public Course course;

    @Getter
    @Setter
    @JoinColumn(name = "date")
    public LocalDate date;

    @Getter
    @Setter
    // Used to extinguish between different types of exams: Tentamen, Hertentamen, Deeltentamen, etc..
    // Should not include date or course code.
    public String name;
}
