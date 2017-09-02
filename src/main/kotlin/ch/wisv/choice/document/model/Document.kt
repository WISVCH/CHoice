package ch.wisv.choice.document.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob

@Entity
data class Document(
        @GeneratedValue @Id val id: Long? = null,

        @Lob var bytes: ByteArray,

        var name: String = ""
)
