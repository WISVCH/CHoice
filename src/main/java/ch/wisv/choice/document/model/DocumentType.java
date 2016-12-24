package ch.wisv.choice.document.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class DocumentType {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    @Column(name = "name", unique = true)
    public String name;
}
