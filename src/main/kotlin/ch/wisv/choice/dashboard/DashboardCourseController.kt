package ch.wisv.choice.dashboard

import ch.wisv.choice.course.model.Course
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
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/dashboard/courses")
class DashboardCourseController
@Autowired
constructor(@Autowired val courseService: CourseService) {

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("courses", courseService.readAllCourses())

        return "dashboard/course/index"
    }

    @GetMapping("/create/")
    fun create(model: Model): String {
        if (!model.containsAttribute("course")) {
            model.addAttribute("course", Course())
        }

        model.addAttribute("courses", courseService.readAllCourses());

        return "dashboard/course/create"
    }

    @PostMapping("/create/")
    fun create(redirect: RedirectAttributes, @ModelAttribute @Validated model: Course): String {
        return try {
            courseService.createCourse(model)

            "redirect:/dashboard/courses/"
        } catch (e:  Exception) {
            when (e) {
                is CHoiceException -> {
                    redirect.addFlashAttribute("error", e.message)
                    redirect.addFlashAttribute("course", model)

                    "redirect:/dashboard/courses/create/"
                }
                else -> throw e
            }
        }
    }
}