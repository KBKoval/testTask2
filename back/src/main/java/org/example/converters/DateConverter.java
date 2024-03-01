package org.example.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.example.utils.Utils;

public class DateConverter extends AbstractBeanField {
private static final String PATTERN = "MMMM d, yyyy";
    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if ( Utils.checkIsEmptyString(value) ) return null;
        value = value.replace("Published", "").trim();
        try {
            Date date = new SimpleDateFormat(PATTERN, Locale.ENGLISH).parse(value);
            return  date;
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
