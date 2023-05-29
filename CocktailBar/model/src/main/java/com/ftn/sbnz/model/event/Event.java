package com.ftn.sbnz.model.event;

import com.ftn.sbnz.model.cocktail.Cocktail;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
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

    public Event() {
        menu = new HashSet<>();
        updateTime = LocalDateTime.now();
    }

    public Event(Event other) {
        this.id = other.id;
        this.menu = new HashSet<>(other.menu);
        this.type = other.type;
        this.updateTime = other.updateTime;
    }

    public void addCocktailToMenu(Cocktail cocktail) {
        menu.add(cocktail);
    }

    public boolean doesNotContain(Cocktail cocktail) {
        return !menu.contains(cocktail);
    }

    public boolean hasFullMenu() {
        return menu.size() == 10;
    }
}
