package ch.wisv.choice.dashboard

import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.document.model.DocumentDTO
import ch.wisv.choice.document.service.DocumentService
import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.exam.service.ExamService
import ch.wisv.choice.util.CHoiceException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes

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
@Controller
@RequestMapping("/dashboard/exams")
class DashboardExamController

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

        model.addAttribute("courses", courseService.getAllCourses())

        return "dashboard/exam/create"
    }

    @PostMapping("/create/")
    fun create(redirect: RedirectAttributes, @ModelAttribute model: Exam, @RequestParam("file") file: MultipartFile): String {
        return try {
            model.document = documentService.storeDocument(file, DocumentDTO(model.name))
            examService.createExam(model)

            "redirect:/dashboard/exams/"
        } catch (e: CHoiceException) {
            redirect.addFlashAttribute("error", e.message)
            redirect.addFlashAttribute("exam", model)

            "redirect:/dashboard/exams/create/"
        }
    }
}