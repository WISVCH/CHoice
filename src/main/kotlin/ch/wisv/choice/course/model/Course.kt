package ch.wisv.choice.course.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotNull

/**
 * Copyright (c) 2016  W.I.S.V. 'Christiaan Huygens'
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
