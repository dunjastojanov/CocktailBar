package com.ftn.sbnz.model.cocktail;

import com.ftn.sbnz.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "cocktail_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;
    private LocalDateTime createTime;
}
