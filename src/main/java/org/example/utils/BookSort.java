package org.example.utils;

import org.example.model.Book;
import java.util.Comparator;

public enum BookSort {
    PUBLICATIONDATE_DESC(Comparator.comparing(Book::getPublicationDate).reversed()), PUBLICATIONDATE_ASC(Comparator.comparing(Book::getPublicationDate)),
    BOOK_ASC(Comparator.comparing(Book::getBook)),
    BOOK_DESC(Comparator.comparing(Book::getBook).reversed()),
    AUTHOR_ASC(Comparator.comparing(Book::getAuthor)),
    AUTHOR_DESC(Comparator.comparing(Book::getAuthor).reversed()),
    NUMPAGES_ASC(Comparator.comparingInt(Book::getNumPages)),
    NUMPAGES_DESC(Comparator.comparingInt(Book::getNumPages).reversed()),
    RATING_ASC(Comparator.comparingDouble(Book::getRating)),
    RATING_DESC(Comparator.comparingDouble(Book::getRating).reversed()),
    NUMBEROFVOTERS_ASC(Comparator.comparingInt(Book::getNumberOfVoters)),
    NUMBEROFVOTERS_DESC(Comparator.comparingInt(Book::getNumberOfVoters).reversed());

    private Comparator<Book> bookComparator;

    BookSort(Comparator<Book> bookComparator) {
        this.bookComparator = bookComparator;
    }

    public Comparator<Book> bookComparator() {
        return this.bookComparator;
    }

}