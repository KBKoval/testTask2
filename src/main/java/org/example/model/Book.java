package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.converters.DateConverter;
import org.example.converters.DateConverterYearOnly;
import org.example.converters.ListConverter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Book {
    private static final String formatOut = "Book = {id: %-10d isbn: %-20s title: %-75s pages: %4d author: %-45s\n  %-100s\n  genres:%s}\n";
    @CsvBindByPosition(position = 0)
    @CsvNumber("#####")
    @JsonProperty("id")
    private int id;

    @CsvBindByName(column = "isbn")
    @CsvBindByPosition(position = 1)
    @JsonProperty("isbn")
    private String isbn;

    @CsvBindByName(column = "title")
    @CsvBindByPosition(position = 2)
    @JsonProperty("book")
    private String book;

    @CsvBindByName(column = "series_title")
    @CsvBindByPosition(position = 3)
    @JsonProperty("series")
    private String series;

    @CsvBindByName(column = "series_release_number")
    @CsvBindByPosition(position = 4)
    @JsonProperty("releaseNumber")
    private String releaseNumber;

    @CsvBindByName(column = "authors")
    @CsvBindByPosition(position = 5)
    @JsonProperty("author")
    private String author;

    @CsvBindByName(column = "description")
    @CsvBindByPosition(position = 8)
    @JsonProperty("description")
    private String description;


    @CsvBindByName(column = "num_pages")
    @CsvBindByPosition(position = 9)
    @CsvNumber("#####")
    @JsonProperty("numPages")
    private int numPages;

    @CsvBindByName(column = "format")
    @CsvBindByPosition(position = 10)
    @JsonProperty("format")
    private String format;

    @CsvCustomBindByPosition(position = 11, converter = ListConverter.class)
    @JsonProperty("genres")
    private List<String> genres;

    @CsvCustomBindByPosition(position = 12, converter = DateConverter.class)
    @JsonProperty("publicationDate")
    private Date publicationDate;

    @CsvBindByName(column = "rating_score")
    @CsvBindByPosition(position = 13)
    @CsvNumber("#####.##")
    @JsonProperty("rating")
    private double rating;

    @CsvBindByName(column = "num_ratings")
    @CsvBindByPosition(position = 14)
    @CsvNumber("#########")
    @JsonProperty("numberOfVoters")
    private int numberOfVoters;


    public String toStringTest() {
        StringBuilder genresBuild = new StringBuilder();
        genresBuild.append("[");
        genres.forEach(tag -> {
            genresBuild.append(tag);
            genresBuild.append(", ");
        });
        genresBuild.append("]");
        return String.format(formatOut, id, isbn, book, numPages, author, "description", genresBuild.toString());
    }

}
