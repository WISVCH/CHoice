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

package ch.wisv.choice.course.service

import ch.wisv.choice.course.model.Course

interface CourseService {

    /**
     * Get all list of all Course
     *
     * @return Collection<Course>
     */
    fun getAllCourses(): Collection<Course>

    /**
     * Create a new Course.
     *
     * @param course: Course
     *
     * @return Course
     */
    fun createCourse(course: Course): Course

    /**
     * Get Course by CourseCode.
     *
     * @param courseCode: String
     *
     * @return Course?
     */
    fun getCourseByCourseCode(courseCode: String): Course

    /**
     * Get the Course predecessor by Course Code.
     *
     * @param courseCode: String
     *
     * @return Course?
     */
    fun getCoursePredecessorByCourseCode(courseCode: String): Course?

    /**
     * Delete a given course code.
     *
     * @param courseCode: String
     */
    fun deleteCourseCode(courseCode: String)

    /**
     * Delete a given course.
     *
     * @param course: Course
     */
    fun deleteCourse(course: Course)

}
