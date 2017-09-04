package ch.wisv.choice.util

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.course.model.Study
import ch.wisv.choice.course.model.StudyProgram
import ch.wisv.choice.course.service.CourseRepository
import ch.wisv.choice.document.model.Document
import ch.wisv.choice.document.service.DocumentRepository
import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.exam.service.ExamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@Profile("dev")
class TestDataRunner(@Autowired val courseRepository: CourseRepository, @Autowired val documentRepository: DocumentRepository, @Autowired val examRepository: ExamRepository) : CommandLineRunner {

    /**
     * Callback used to run the bean.
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    override fun run(vararg args: String?) {
        val predecessor = Course("TI1500", "Web- en Databasetechnologie", null, Study.CS, StudyProgram.FIRST_YEAR)
        courseRepository.saveAndFlush(predecessor)

        val course = Course("TI1505", "Web & Databasetechnologie", predecessor.code, Study.CS, StudyProgram.FIRST_YEAR)
        courseRepository.saveAndFlush(course)

        val course2 = Course("TI2216M", " Kansrekening en statistiek", null, Study.CS, StudyProgram.SECOND_YEAR)
        courseRepository.saveAndFlush(course2)

        val course3 = Course("TI2206M", "Reeele analyse", null, Study.AM, StudyProgram.SECOND_YEAR)
        courseRepository.saveAndFlush(course3)

        val document = Document(1, kotlin.ByteArray(0), "TI1505 Exam July 8, 2017")
        documentRepository.saveAndFlush(document)

        val exam = Exam(1, course, LocalDate.now(), "Exam", document)
        examRepository.saveAndFlush(exam)

        val document2 = Document(2, kotlin.ByteArray(0), "TI1500 Exam July 8, 2016")
        documentRepository.saveAndFlush(document2)

        val exam2 = Exam(2, predecessor, LocalDate.now(), "Exam", document, true)
        examRepository.saveAndFlush(exam2)
    }
}