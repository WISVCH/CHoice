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

package ch.wisv.choice.util

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.course.model.Study
import ch.wisv.choice.course.model.StudyProgram
import ch.wisv.choice.course.service.CourseRepository
import ch.wisv.choice.document.model.Document
import ch.wisv.choice.document.service.DocumentRepository
import ch.wisv.choice.document.service.FileRepository
import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.exam.service.ExamRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@Profile("dev")
class TestDataRunner(val courseRepository: CourseRepository,
                     val fileRepository: FileRepository,
                     val documentRepository: DocumentRepository,
                     val examRepository: ExamRepository) : CommandLineRunner {

    /**
     * Callback used to run the bean.
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    override fun run(vararg args: String?) {
        val predecessor2 = Course("TI1605", "Web- en Databasetechnologie", null, Study.CS, StudyProgram.FIRST_YEAR)
        courseRepository.saveAndFlush(predecessor2)

        val predecessor = Course("TI1600", "Web- en Databasetechnologie", predecessor2.code, Study.CS, StudyProgram.FIRST_YEAR)
        courseRepository.saveAndFlush(predecessor)

        val course = Course("TI1505", "Web & Databasetechnologie", predecessor.code, Study.CS, StudyProgram.FIRST_YEAR)
        courseRepository.saveAndFlush(course)

        val course2 = Course("TI2216M", " Kansrekening en statistiek", null, Study.CS, StudyProgram.SECOND_YEAR)
        courseRepository.saveAndFlush(course2)

        val course3 = Course("TI2206M", "Reeele analyse", null, Study.AM, StudyProgram.SECOND_YEAR)
        courseRepository.saveAndFlush(course3)

        val exam = Exam(1, course, LocalDate.now(), false, "Exam")
        examRepository.saveAndFlush(exam)

        val document = Document(1, null, "TI1505 Exam July 8, 2017", exam)
        documentRepository.saveAndFlush(document)

        val exam2 = Exam(2, predecessor, LocalDate.now(), true, "Exam")
        examRepository.saveAndFlush(exam2)

//        val document2 = Document(2, null, "TI600 Exam July 6, 2015", exam2)
//        documentRepository.saveAndFlush(document2)

        val exam3 = Exam(3, predecessor2, LocalDate.now(), true, "Exam")
        examRepository.saveAndFlush(exam3)

        val document3 = Document(3, null, "TI1605 Exam July 9, 2014", exam3)
        documentRepository.saveAndFlush(document3)
    }
}