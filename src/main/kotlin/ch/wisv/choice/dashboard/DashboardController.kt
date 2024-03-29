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

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping
class DashboardController {

    /**
     * Dashboard index
     */
    @GetMapping("/dashboard/")
    fun index(model: Model): String {
        return "dashboard/index"
    }

    /**
     * Dashboard index redirect
     */
    @GetMapping("/dashboard")
    fun redirectToIndex(model: Model): String{
        return "redirect:dashboard/"
    }

    /**
     * Root redirect
     */
    @GetMapping("/")
    fun redirectToDashboard(model: Model): String{
        return "redirect:dashboard/"
    }
}
