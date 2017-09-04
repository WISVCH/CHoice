package ch.wisv.choice.course.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotNull

/**
 * A Course represents a course that exists or existed at TU Delft.
 */
@Entity
data class Course(

        /**
         * Course code
         */
        @Id var code: String = "",

        /**
         * Name of the course
         */
        @NotNull var name: String = "",

        /**
         * Course code of the predecessor of this course
         */
        var predecessor: String? = null,

        /**
         * Study (AM, CS)
         */
        var study: Study? = null,

        /**
         * Study program of the course
         */
        var studyProgram: StudyProgram? = null
)
