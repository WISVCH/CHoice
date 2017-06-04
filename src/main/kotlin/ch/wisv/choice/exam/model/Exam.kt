package ch.wisv.choice.exam.model


import ch.wisv.choice.course.model.Course
import java.time.LocalDate
import javax.persistence.*

/**
 * An Exam entity represents an exam that was taken for a course,
 * not the physical document with the questions on it.
 */
@Table(uniqueConstraints = arrayOf(UniqueConstraint(columnNames = arrayOf("course", "date"))))
@Entity
data class Exam(

    @Id
    @GeneratedValue
    val id: Long? = null,

    @ManyToOne(targetEntity = Course::class)
    @JoinColumn(name = "course")
    var course: Course,

    @JoinColumn(name = "date")
    var date: LocalDate,

    // Used to extinguish between different types of exams: Tentamen, Hertentamen, Deeltentamen, etc..
    // Should not include date or course code.
    var name: String
)
