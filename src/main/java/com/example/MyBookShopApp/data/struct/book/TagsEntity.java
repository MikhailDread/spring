package com.example.MyBookShopApp.data.struct.book;

import com.example.MyBookShopApp.data.struct.book.BookEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@Api(description = "table tags genre")
public class TagsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "tags id generated by db", position = 1)
    private Integer id;

    @Column(name = "tag_name")
    @JsonProperty("tag_name")
    @ApiModelProperty("tag name")
    private String tagsName;

    @OneToMany(mappedBy = "tag")
    @JsonIgnore
    private List<BookEntity> bookList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagsName() {
        return tagsName;
    }

    public void setTagsName(String tagsName) {
        this.tagsName = tagsName;
    }

    public List<BookEntity> getBookList() {
        return bookList;
    }

    public void setBookList(List<BookEntity> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "TagsEntity{" +
                "id=" + id +
                ", tagsName='" + tagsName + '\'' +
                '}';
    }
}
