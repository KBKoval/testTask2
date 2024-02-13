package org.example.repositories.implement;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
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
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.zip.ZipInputStream;

@Slf4j
@Repository
public class RepositoryBooksImpl implements RepositoryBooks {
    private List<Book> books = null;
    @Value("${download.file.url}")
    private String url;

    //@Cacheable(value = "books", cacheManager = "booksCM")
    public List<Book> getBooks() {
        try {
            if (books == null) parserCSV();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return books;
    }

    private void parserCSV() throws IOException, CsvException, ParseException, InterruptedException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        List<Book> beans = null;
        try {
            HttpGet httpget = new HttpGet(url);

            System.out.println("Executing request " + httpget.getRequestLine());

            CloseableHttpResponse responses = httpclient.execute(httpget);
            try {
                HttpEntity entity = responses.getEntity();

                if (entity != null) {
                    InputStream inStream = entity.getContent();
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    byte[] targetArray = toUnzippedByteArray(IOUtils.toByteArray(inStream));
                    System.out.println("File downloaded successfully  : " + targetArray.length);
                    String s = new String(targetArray, StandardCharsets.UTF_8);
                    //System.out.println(s);
                    Reader targetReader = new StringReader(new String(targetArray));

                    books = new CsvToBeanBuilder(targetReader)
                            .withSeparator(',')
                            .withType(Book.class)
                            .withSkipLines(1)
                            .build()
                            .parse();


                    targetReader.close();
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
