package org.example.controllers;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Book;
import org.example.utils.SortColum;
import org.example.utils.SortMethod;
import org.example.services.interfaces.ServiceParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Validated
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class GetBestTopBookController {
    private final ServiceParser serviceParser;

    @Autowired
    public GetBestTopBookController(ServiceParser serviceParser) {
        this.serviceParser = serviceParser;
    }

    @GetMapping(value = "all")
    public List<Book> parserTest() {
            return  serviceParser.getAllBooks();
    }

    @GetMapping(value = "top10")
    public List<Book> getTop10(@RequestParam(required = false, defaultValue = "-1") int year,@Valid @RequestParam(required = true) String column,@Valid @RequestParam(required = true) String sort) {
        return serviceParser.getBooksTop(10, year, SortColum.valueOf(column.toUpperCase()), SortMethod.valueOf(sort.toUpperCase()));
    }
}
