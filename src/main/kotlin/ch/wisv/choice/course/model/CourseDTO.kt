/*
 * Copyright (c) 2016  W.I.S.V. 'Christiaan Huygens'
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.wisv.choice.course.model

import ch.wisv.choice.exam.model.Exam
import javax.persistence.Id
import javax.validation.constraints.NotNull

data class CourseDTO(

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
        var studyProgram: StudyProgram? = null,

        /**
         * List of exam of this course
         */
        var exam: Set<Exam>? = null
) {
        constructor(course: Course, exams: Set<Exam>): this(course.code, course.name, course.predecessor, course.study, course.studyProgram, exams)
}
