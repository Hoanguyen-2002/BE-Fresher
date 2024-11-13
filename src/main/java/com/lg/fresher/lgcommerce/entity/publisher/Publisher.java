package com.lg.fresher.lgcommerce.entity.publisher;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lg.fresher.lgcommerce.entity.book.Book;
import com.lg.fresher.lgcommerce.entity.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "publisher", schema = "lgecommerce")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Publisher extends BaseEntity {
    @Id
    @Size(max = 50)
    @Column(name = "publisher_id", nullable = false, length = 50)
    private String publisherId;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "publisher")
    @JsonManagedReference
    private List<Book> books;

    @Override
    public String toString() {
        return "Publisher{" +
                "publisherId='" + publisherId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}