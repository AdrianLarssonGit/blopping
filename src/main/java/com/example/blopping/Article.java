package com.example.blopping;

import lombok.Data;
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

    @Column(nullable = true, unique = false, length = NVARCHAR)
    private String articleText;


}
