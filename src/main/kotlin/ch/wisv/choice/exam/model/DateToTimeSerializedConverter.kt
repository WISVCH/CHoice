package ch.wisv.choice.exam.model

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.time.LocalDate
import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Converter
class DateToTimeSerializedConverter : AttributeConverter<LocalDate, String> {
    override fun convertToDatabaseColumn(date : LocalDate): String {
        val bos = ByteArrayOutputStream()
        val os = ObjectOutputStream(bos)
        os.writeObject(date)
        val ans: String = bos.toString()
        os.close()
        return ans;
    }

    override fun convertToEntityAttribute(serDate: String):LocalDate {
        val bis = ByteArrayInputStream(serDate.toByteArray())
        val oInputStream = ObjectInputStream(bis)
        val ans: LocalDate = oInputStream.readObject() as LocalDate
        oInputStream.close()
        return ans
    }
}