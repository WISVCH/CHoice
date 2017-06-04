package ch.wisv.choice.document.model

import ch.wisv.choice.exam.model.Exam
import org.springframework.web.multipart.MultipartFile
import javax.persistence.*

@Entity
data class Document(
        @GeneratedValue @Id val id: Long? = null,
        @Lob var file: MultipartFile? = null,
        var name: String = "",
        @ManyToOne(targetEntity = Exam::class) var exam: Exam)
