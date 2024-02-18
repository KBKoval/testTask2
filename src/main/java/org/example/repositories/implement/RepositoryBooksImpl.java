package org.example.repositories.implement;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.model.Book;
import org.example.repositories.interfaces.RepositoryBooks;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.List;
import java.util.zip.ZipInputStream;

@Slf4j
@Repository
public class RepositoryBooksImpl implements RepositoryBooks {
    private List<Book> books = null;
    @Value("${download.file.url}")
    private String url;
    @Value("${file.dataset}")
    private String fileName;

    //@Cacheable(value = "books", cacheManager = "booksCM")
    public List<Book> getBooks() {
        try {
            if (books == null) parserCSVFromFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return books;
    }

    private void parserCSVFromFile() throws IOException {
        Reader targetReader = new FileReader(fileName);
        convert(targetReader);
    }

    private void convert(Reader targetReader) throws IOException {
        books = new CsvToBeanBuilder(targetReader)
                .withSeparator(',')
                .withType(Book.class)
                .withSkipLines(1)
                .build()
                .parse();
        targetReader.close();
    }

    private void parserCSV() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse responses = httpclient.execute(httpget);
            try {
                HttpEntity entity = responses.getEntity();

                if (entity != null) {
                    InputStream inStream = entity.getContent();
                    byte[] targetArray = toUnzippedByteArray(IOUtils.toByteArray(inStream));
                    Reader targetReader = new StringReader(new String(targetArray));
                    convert(targetReader);
                }


                EntityUtils.consume(entity);
            } finally {
                responses.close();
            }
        } finally {
            httpclient.close();
        }
    }

    private byte[] toUnzippedByteArray(byte[] zippedBytes) throws IOException {
        var zipInputStream = new ZipInputStream(new ByteArrayInputStream(zippedBytes));
        var buff = new byte[1024];
        if (zipInputStream.getNextEntry() != null) {
            var outputStream = new ByteArrayOutputStream();
            int l;
            while ((l = zipInputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, l);
            }
            return outputStream.toByteArray();
        }
        return new byte[0];
    }


}
