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

package ch.wisv.choice

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.document.model.Document
import ch.wisv.choice.document.service.DocumentService
import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.exam.service.ExamService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component
import org.springframework.test.context.ActiveProfiles
import java.io.File
import java.time.LocalDate

@SpringBootApplication
@ActiveProfiles("test")
class ApplicationTest {

    fun main(args: Array<String>) {
        SpringApplication.run(ApplicationTest::class.java, *args)
    }

    @Component
    class TestRunner(var courseService: CourseService,
                     var examService: ExamService,
                     var documentService: DocumentService) : CommandLineRunner {

        override fun run(vararg evt: String) {
            val course11 = Course("TI0011", "Test Course 1")
            courseService.createCourse(course11)
            val course12 = Course("TI0012", "Test Course 1.2", "TI0011")
            courseService.createCourse(course12)
            val course21 = Course("TI0021", "Test Course 2")
            courseService.createCourse(course21)

            val exam1 = Exam(1, course11, LocalDate.now().plusWeeks(1), false, "Tentamen")
            examService.createExam(exam1)
            val exam2 = Exam(2, course21, LocalDate.now().plusWeeks(2), false, "Hertentamen")
            examService.createExam(exam2)

            val file = File("src/test/resources/TI1300 - Tentamen.pdf")
            val document = Document(null, file.readBytes(), "Tentamen", exam1)
            documentService.storeDocument(document)

            val file2 = File("src/test/resources/TI1300 - Antwoorden.pdf")
            val document2 = Document(null, file2.readBytes(), "Antwoorden", exam2)
            documentService.storeDocument(document2)
        }
    }
}
