package com.springboot.practiceimitateshopeebackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity {

    @CreatedDate
    @Column(
            nullable = false,
            updatable=false
    )
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;

    @CreatedBy
    @Column(
            nullable = false,
            updatable = false
    )
    private String createdBy;

    @LastModifiedBy
    @Column(insertable = false)
    private String lastModifiedBy;
}
