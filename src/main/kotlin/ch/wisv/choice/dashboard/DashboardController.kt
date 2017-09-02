package ch.wisv.choice.dashboard

import ch.wisv.choice.exam.service.ExamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/dashboard")
class DashboardController

@Autowired
constructor(@Autowired val examService: ExamService) {

    @GetMapping("/")
    fun index(model: Model): String {
        return "dashboard/index"
    }
}