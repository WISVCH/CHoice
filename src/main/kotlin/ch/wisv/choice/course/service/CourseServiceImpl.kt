package ch.wisv.choice.course.service

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.util.CHoiceException
import org.springframework.stereotype.Service

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
@Service
class CourseServiceImpl(val courseRepository: CourseRepository) : CourseService {

    /**
     * Get all list of all Course
     *
     * @return Collection<Course>
     */
    override fun getAllCourses(): Collection<Course>
            = courseRepository.findAll()

    /**
     * Create a new Course.
     *
     * @param course: Course
     *
     * @return Course
     */
    override fun createCourse(course: Course): Course {
        assertIsValid(course)

        return courseRepository.saveAndFlush(course)
    }

    /**
     * Get Course by CourseCode.
     *
     * @param courseCode: String
     *
     * @return Course?
     */
    override fun getCourseByCourseCode(courseCode: String): Course?
            = courseRepository.findOne(courseCode)

    /**
     * Get the Course predecessor by Course Code.
     *
     * @param courseCode: String
     *
     * @return Course?
     */
    override fun getCoursePredecessorByCourseCode(courseCode: String): Course? {
        val (_, _, predecessor) = courseRepository.findOne(courseCode)
        return courseRepository.findOne(predecessor)
    }

    /**
     * Delete a given course code.
     *
     * @param courseCode: String
     */
    override fun deleteCourseCode(courseCode: String)
            = courseRepository.delete(courseCode)

    /**
     * Assert if the course given a valid Course
     */
    fun assertIsValid(course: Course) {
        val predecessor = course.predecessor

        if (predecessor != "") {
            if (predecessor != null && getCourseByCourseCode(predecessor) == null) {
                throw CHoiceException("Predecessor course doesn't exist!")
            }
        } else {
            course.predecessor = null
        }

        if (course.code == "") {
            throw CHoiceException("Course code cannot be empty!")
        }

        if (course.name == "") {
            throw CHoiceException("Name of the course cannot be empty!")
        }
    }
}
