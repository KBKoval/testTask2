package org.example.utils;

import org.example.model.Book;

import java.util.function.Predicate;

public enum SortColum {
    BOOK, AUTHOR, NUMPAGES, PUBLICATIONDATE, RATING, NUMBEROFVOTERS;
    private static final Predicate<Book> streamsPredicateBook = item -> item.getBook()  != null;
    private static final Predicate<Book> streamsPredicateNumPages = item -> item.getNumPages()  > 0;
    private static final Predicate<Book> streamsPredicateAuthor = item -> item.getAuthor()  != null;
    private static final Predicate<Book> streamsPredicatePublicationDate = item -> item.getPublicationDate()  != null;
    private static final Predicate<Book> streamsPredicateRating = item -> item.getRating()  > 0;
    private static final Predicate<Book> streamsPredicateNumberOfVoters = item -> item.getNumberOfVoters() > 0;
    private Predicate<Book> bookPredicate;
    public Predicate<Book> is() {
        switch (this) {
            case BOOK: return streamsPredicateBook;
            case AUTHOR: return streamsPredicateAuthor;
            case NUMPAGES: return streamsPredicateNumPages;
            case PUBLICATIONDATE: return streamsPredicatePublicationDate;
            case RATING: return streamsPredicateRating;
            case NUMBEROFVOTERS: return streamsPredicateNumberOfVoters;
        }
        return null;
    }
}
