package com.example.blopping;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;

import javax.persistence.*;

import static java.sql.Types.NVARCHAR;

@Entity
@Table(name = "artiklar")
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, unique = false, columnDefinition = "LONGTEXT")
    private String articleText;

    @Column(nullable = false, unique = true, columnDefinition = "LONGTEXT")
    private String emailOfAuthor = "admin@google.se";


}
