package com.hcs.msauth.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name="tb_ods")
public class ODS extends RepresentationModel<ODS> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "INTEGER")
    private int points;
    @Column(columnDefinition = "TEXT")
    private String category;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private Users user;

    /*
    category (enum) – ["Reciclagem", "Energia", "Água", "Mobilidade"]
    userId (referência ao usuário)

     */
}
