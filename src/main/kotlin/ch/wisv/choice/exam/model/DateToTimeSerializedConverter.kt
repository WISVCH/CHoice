package ch.wisv.choice.exam.model

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.time.LocalDate
import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Converter
class DateToTimeSerializedConverter : AttributeConverter<LocalDate, ByteArray> {
    override fun convertToDatabaseColumn(date : LocalDate): ByteArray {
        val bos = ByteArrayOutputStream()
        val os = ObjectOutputStream(bos)
        os.writeObject(date)
        val ans: ByteArray = bos.toByteArray()
        os.close()
        bos.close()
        return ans;
    }

    override fun convertToEntityAttribute(serDate: ByteArray):LocalDate {
        val bis = ByteArrayInputStream(serDate)
        val oInputStream = ObjectInputStream(bis)
        val ans: LocalDate = oInputStream.readObject() as LocalDate
        oInputStream.close()
        bis.close()
        return ans
    }
}