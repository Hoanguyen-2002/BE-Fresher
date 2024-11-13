package com.lg.fresher.lgcommerce.entity.author;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lg.fresher.lgcommerce.entity.book.BookAuthor;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "author", schema = "lgecommerce")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "author_id", nullable = false, length = 50)
    private String authorId;

    @Size(max = 255, message = "The length of the name has max 255 characters")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    private List<BookAuthor> authorBooks;

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", authorId='" + authorId + '\'' +
                '}';
    }
}