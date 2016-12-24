package ch.wisv.choice.course.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * A Course represents a course that exists or existed at TU Delft.
 */
@Entity
@AllArgsConstructor
public class Course {

    @Getter
    @Setter
    @Id
    public String courseCode;

    @Getter
    @Setter
    @NotNull
    public String name;

    @Getter
    @Setter
    public String predecessor;
}
