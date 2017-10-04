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

import ch.wisv.choice.course.model.Course
import ch.wisv.choice.course.service.CourseService
import ch.wisv.choice.util.CHoiceException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/dashboard/courses")
class DashboardCourseController(val courseService: CourseService) {

    /**
     * Dashboard course index.
     */
    @GetMapping
    fun index(model: Model): String {
        model.addAttribute("courses", courseService.getAllCourses())

        return "dashboard/course/index"
    }

    /**
     * Dashboard course create.
     */
    @GetMapping("/create/")
    fun create(model: Model): String {
        if (!model.containsAttribute("course")) {
            model.addAttribute("course", Course())
        }

        model.addAttribute("courses", courseService.getAllCourses());

        return "dashboard/course/create"
    }

    /**
     * POST course create.
     */
    @PostMapping("/create/")
    fun create(redirect: RedirectAttributes, @ModelAttribute model: Course): String {
        return try {
            courseService.createCourse(model)

            "redirect:/dashboard/courses/"
        } catch (e: CHoiceException) {
            redirect.addFlashAttribute("error", e.message)
            redirect.addFlashAttribute("course", model)

            "redirect:/dashboard/courses/create/"
        }
    }
}