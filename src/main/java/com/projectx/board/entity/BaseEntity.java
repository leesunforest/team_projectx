package com.projectx.board.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    @CreationTimestamp // 작성시간
    @Column(updatable = false, name = "created_at") // 수정할 때는 관여 안함.
    private LocalDateTime createdTime;

    @UpdateTimestamp // 수정시간
    @Column(insertable = false, name = "updated_at") // 작성할 때는 관여 안함.
    private LocalDateTime updatedTime;
}