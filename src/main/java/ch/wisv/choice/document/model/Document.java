package ch.wisv.choice.document.model;

import ch.wisv.choice.exam.model.Exam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
public class Document {

    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    private String path;

    @Getter
    @Setter
    @ManyToOne(targetEntity = Exam.class)
    public Exam exam;

    @Getter
    @Setter
    @NotEmpty
    @ManyToMany(targetEntity = DocumentType.class)
    public List<DocumentType> types;
}
