package com.example.bookweb_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @PrePersist
    public void createTime()
    {
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    public void updateTime()
    {
        this.updateAt = LocalDateTime.now();
    }

    //n Comment - 1 User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //n Comment - 1 Post
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
