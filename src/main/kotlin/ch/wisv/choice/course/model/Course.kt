package ch.wisv.choice.course.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotNull

/**
 * A Course represents a course that exists or existed at TU Delft.
 */
@Entity
data class Course(
    @Id var code: String = "",
    @NotNull var name: String = "",
    var predecessor: String? = null)
