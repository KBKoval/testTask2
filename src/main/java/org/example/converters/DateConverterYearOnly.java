package org.example.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.example.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateConverterYearOnly extends AbstractBeanField {
    private static final String PATTERN = "MMMM d, yyyy";
    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if ( Utils.checkIsEmptyString(value) ) return null;

        value = value.replace("Published", "").trim();

        try {
            Date date = new SimpleDateFormat(PATTERN, Locale.ENGLISH).parse(value);
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
