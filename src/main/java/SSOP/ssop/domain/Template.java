package SSOP.ssop.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "template")
    private Set<Card> cards;
}
