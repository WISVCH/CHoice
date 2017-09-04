package ch.wisv.choice.exam.controller

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.exam.service.ExamService
import ch.wisv.choice.util.ResponseEnityBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/exam", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
@CrossOrigin
class ExamController
@Autowired
constructor(@Autowired val examService: ExamService, @Autowired val courseService: CourseService) {

    @GetMapping()
    fun getExams(): ResponseEntity<*> {
        val exams = examService.getExams()

        // Remove ByteArray
        exams.forEach { exam -> exam.document!!.bytes = kotlin.ByteArray(0) }

        return ResponseEnityBuilder.createResponseEntity(HttpStatus.OK, "Successful", exams)
    }

    @GetMapping("/course/{code}")
    fun getExamsByCourse(@PathVariable code: String): Collection<Exam>
            = examService.getExamsByCourse(code)

    @GetMapping("/course/{code}/including")
    fun getExamByCourseAndPredecessors(@PathVariable code: String): ResponseEntity<*> {
        val course = courseService.getCourseByCourseCode(code)
        var exams: HashSet<Exam> = HashSet()

        if (course != null) {
            exams = getPredecessorsExams(course, HashSet())

            // Remove ByteArray
            exams.forEach { exam -> exam.document!!.bytes = kotlin.ByteArray(0) }
        }

        return ResponseEnityBuilder.createResponseEntity(HttpStatus.OK, "Successful", exams)
    }

    @GetMapping("/{examId}")
    fun getExamById(@PathVariable examId: Long): Exam
            = examService.getExamById(examId)

    private fun getPredecessorsExams(course: Course?, exams: HashSet<Exam>): HashSet<Exam> {
        if (course != null) {
            val predecessorExams = examService.getExamsByCourse(course.code)
            exams.addAll(predecessorExams)

            if (course.predecessor != null) {
                val predecessor = courseService.getCourseByCourseCode(course.predecessor!!)
                getPredecessorsExams(predecessor, exams)
            }
        }

        return exams
    }
}
