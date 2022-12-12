package com.bck.hanbokbck.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "persistent_logins")
@Getter
@Setter
@Entity
public class PersistentLogins {

    @Id
    @Column(length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false, length = 64)
    private LocalDateTime lastUsed;
}
