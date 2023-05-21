package com.ftn.sbnz.model.event;

import com.ftn.sbnz.model.cocktail.Cocktail;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Cocktail> menu = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private EventType type;

    private LocalDateTime updateTime;
}
