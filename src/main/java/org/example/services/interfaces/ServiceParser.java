package org.example.services.interfaces;

import org.example.model.Book;
import org.example.utils.SortColum;
import org.example.utils.SortMethod;

import java.util.List;

public interface ServiceParser {
    public List<Book> getAllBooks();

    public List<Book> getBooksTop(int limit, int year, SortColum colum, SortMethod sort) ;
}
