package com.example.bookweb_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // cho phép kế thừa không cần tạo table riêng
@EntityListeners(AuditingEntityListener.class) // kích hoạt audit tự động
@Getter
@Setter
public abstract class BaseEntity {

    @CreatedDate // tự động cập nhật ngày giờ
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @CreatedBy // set tự động thông qua AuditorAware
    @Column(name = "created_by", updatable = false)
    private String createBy;

    @LastModifiedDate // tự động cập nhật ngày giờ
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @LastModifiedBy // set tự động thông qua AuditorAware
    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @PrePersist
    public void create()
    {
        this.updateDate = null;
        this.updateBy = null;
    }

    @PreUpdate
    public void update()
    {
        this.updateDate = LocalDateTime.now();
    }
}
