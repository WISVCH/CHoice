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

package ch.wisv.choice.exam.service

import ch.wisv.choice.exam.model.Exam
import ch.wisv.choice.util.CHoiceException
import org.springframework.stereotype.Service

@Service
class ExamServiceImpl(val examRepository: ExamRepository) : ExamService {

    override fun createExam(exam: Exam): Exam {
        val filtered = getExamsByCourse(exam.course.code).filter { item -> item.name == exam.name && item.date == exam.date }

        if (filtered.isNotEmpty()) {
            throw CHoiceException("Already have a(n) " + exam.name + " of this course on date " + exam.date)
        }

        return examRepository.saveAndFlush(exam)
    }

    override fun getExams(): Collection<Exam>
            = examRepository.findAll()

    override fun getExamsByCourse(code: String): Collection<Exam>
            = examRepository.findAllByCourse_Code(code)

    override fun getExamById(id: Long): Exam
            = examRepository.findOne(id)

    override fun deleteExam(exam: Exam)
            = examRepository.delete(exam)

    override fun deleteExamsByCourse(code: String)
            = examRepository.deleteAllByCourse_Code(code)
}
