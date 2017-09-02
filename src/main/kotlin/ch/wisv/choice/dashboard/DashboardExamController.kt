package ch.wisv.choice.dashboard

import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.document.model.DocumentDTO
import ch.wisv.choice.document.service.DocumentService
import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.exam.service.ExamService
import ch.wisv.choice.util.CHoiceException
import org.apache.tomcat.util.http.fileupload.FileUploadBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartException
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/dashboard/exams")
class DashboardExamController
@Autowired
constructor(@Autowired val examService: ExamService, @Autowired val courseService: CourseService, @Autowired val documentService: DocumentService) {

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("exams", examService.getExams())

        return "dashboard/exam/index"
    }

    @GetMapping("/create/")
    fun create(model: Model): String {
        if (!model.containsAttribute("exam")) {
            model.addAttribute("exam", Exam())
        }

        model.addAttribute("courses", courseService.readAllCourses())

        return "dashboard/exam/create"
    }

    @PostMapping("/create/")
    fun create(redirect: RedirectAttributes, @ModelAttribute @Validated model: Exam, @RequestParam("file") file: MultipartFile): String {
        return try {
            model.document = documentService.storeDocument(file, DocumentDTO(model.name))
            examService.createExam(model)

            "redirect:/dashboard/exams/"
        } catch (e:  Exception) {
            when (e) {
                is CHoiceException -> {
                    redirect.addFlashAttribute("error", e.message)
                    redirect.addFlashAttribute("exam", model)

                    "redirect:/dashboard/exams/create/"
                }
                else -> throw e
            }
        }
    }
}
