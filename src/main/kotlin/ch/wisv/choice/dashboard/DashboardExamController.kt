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

package ch.wisv.choice.dashboard

import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.document.model.DocumentDTO
import ch.wisv.choice.document.service.DocumentService
import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.exam.service.ExamService
import ch.wisv.choice.util.CHoiceException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/dashboard/exams")
class DashboardExamController(val examService: ExamService,
                              val courseService: CourseService,
                              val documentService: DocumentService) {

    /**
     * Dashboard exam index.
     */
    @GetMapping
    fun index(model: Model): String {
        model.addAttribute("exams", examService.getExams())

        return "dashboard/exam/index"
    }

    /**
     * Dashboard exam create.
     */
    @GetMapping("/create/")
    fun create(model: Model): String {
        if (!model.containsAttribute("exam")) {
            model.addAttribute("exam", Exam())
        }

        model.addAttribute("courses", courseService.getAllCourses())

        return "dashboard/exam/create"
    }

    /**
     * POST exam create.
     */
    @PostMapping("/create/")
    fun create(redirect: RedirectAttributes,
               @ModelAttribute model: Exam,
               modelErrors: Errors,
               @RequestParam("file") file: MultipartFile): String {
        return try {
            if (modelErrors.hasErrors())
                throw CHoiceException("Invalid input" +
                                      if (modelErrors.hasFieldErrors()) " for " + modelErrors.fieldError.field
                                      else "")
            
            examService.createExam(model)
            documentService.storeDocument(file, DocumentDTO(model.course.code + " " + model.name + " " + model.date, model))

            "redirect:/dashboard/exams/"
        } catch (e: CHoiceException) {
            redirect.addFlashAttribute("error", e.message)
            redirect.addFlashAttribute("exam", model)

            "redirect:/dashboard/exams/create/"
        }
    }

    /**
     * DELETE exam delete.
     */
    @GetMapping("/delete/{examId}/")
    fun delete(redirect: RedirectAttributes, @PathVariable("examId") examId: String): String {
        return try {
            val exam = examService.getExamById(examId.toLong())
            documentService.deleteDocumentByExamId(examId.toLong())
            examService.deleteExam(exam)

            redirect.addFlashAttribute("message", "Exam $examId successfully deleted!")

            "redirect:/dashboard/exams/"
        } catch (e: CHoiceException) {
            redirect.addFlashAttribute("error", e.message)

            "redirect:/dashboard/exams/"
        }
    }
}
