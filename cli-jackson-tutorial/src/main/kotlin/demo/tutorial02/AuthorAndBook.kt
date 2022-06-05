package demo.tutorial02

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference

//
// AuthorはBookを参照
// BookもAuthorを参照
//
// @JsonManagedReferenceはシリアアライズから省略される
// @JsonBackReferenceはシリアアライズから省略される
//
//
data class Author(
    val name: String,
    @JsonManagedReference
    val books: MutableList<Book>,
)

data class Book (
    val title: String,
    @JsonBackReference
    val author: Author,
)