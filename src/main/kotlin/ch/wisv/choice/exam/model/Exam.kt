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

package ch.wisv.choice.exam.model

import ch.wisv.choice.course.model.Course
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.persistence.*

@Table(uniqueConstraints = arrayOf(UniqueConstraint(columnNames = arrayOf("course", "name", "date"))))
@Entity
data class Exam(

        @Id
        @GeneratedValue
        val id: Long? = null,

        @ManyToOne(targetEntity = Course::class)
        @JoinColumn(name = "course")
        var course: Course = Course(),

        @JoinColumn(name = "date")
        var date: LocalDate = LocalDate.now(),

        var includingAnswers: Boolean = false,

        // Used to extinguish between different types of exams: Exam, Resit, Mid term, Answers, etc..
        // Should not include date or course code.
        var name: String = ""
)
