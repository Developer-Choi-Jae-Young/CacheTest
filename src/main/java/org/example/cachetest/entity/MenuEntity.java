package org.example.cachetest.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "menu")
public class MenuEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    public void changeName(String changeName) {
        this.name = changeName;
    }

    public void changeType(String changeType) {
        this.type = changeType;
    }
}
