package org.example.services.implement;

import lombok.extern.slf4j.Slf4j;

import org.example.repositories.interfaces.RepositoryBooks;
import org.example.services.interfaces.ServiceParser;
import org.example.utils.BookSort;
import org.example.utils.SortColum;
import org.example.utils.SortMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.model.Book;
import org.springframework.stereotype.Service;


import java.time.ZoneId;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ServiceParserImpl implements ServiceParser {
    private final RepositoryBooks repositoryBooks;

    @Autowired
    public ServiceParserImpl(RepositoryBooks repositoryBooks) {
        this.repositoryBooks = repositoryBooks;
    }

    public List<Book> getAllBooks() {
        return repositoryBooks.getBooks();
    }

    /*
    year - необязательный параметр, при наличии выдавать книги только указанного года публикации,
        column - обязательный параметр, наименование поля, по которому требуется отсортировать данные. Возможные значения: book, author, numPages, publicationDate, rating, numberOfVoters,
        sort - обязательный параметр, сортировка по возрастанию/убыванию. Возможные значения: ASC, DESC.
     */
    public List<Book> getBooksTop(int limit, int year, SortColum columnn, SortMethod sortMethod) {
        String sort = columnn + "_" + sortMethod;
        if( year >0 ){
            Predicate<Book> streamsPredicateYear = item ->  item.getPublicationDate() !=null && item.getPublicationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear() == year;
            return repositoryBooks.getBooks().stream().filter(columnn.is()).filter(streamsPredicateYear).sorted(BookSort.valueOf(sort).bookComparator()).limit(limit).collect(Collectors.toList());
        }
        return repositoryBooks.getBooks().stream().filter(columnn.is()).sorted(BookSort.valueOf(sort).bookComparator()).limit(limit).collect(Collectors.toList());
    }

}
