package com.syshology.jpa.entity;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
public class Book extends Item {
 private String author;
 private String isbn;
@Builder
 public Book(String name, int price, int stockQuantity) {
  super(name, price, stockQuantity);
 }

 public Book() {

 }
}