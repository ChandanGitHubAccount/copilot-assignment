package com.copilot.assignment.entity;

import com.copilot.assignment.dataaccess.enmus.ArticleType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.copilot.assignment.constants.SchemaNameConstants.TABLE_ARTICLE;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
@Table(name = TABLE_ARTICLE)
@SuperBuilder
@lombok.Data
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private Category category;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleType type;

    @Column(name = "image")
    private String image;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "description")
    private String description;

    @CreatedDate
    @Column(name = "created_at", updatable = false, columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "datetime default current_timestamp")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}