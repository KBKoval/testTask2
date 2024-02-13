package org.example.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import lombok.NoArgsConstructor;
import org.example.utils.Utils;

import java.util.Arrays;

@NoArgsConstructor
public class ListConverter extends AbstractBeanField {

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if ( Utils.checkIsEmptyString(value) ) return null;
        value = value.replace("[", "");
        value = value.replace("]", "").trim();
        value = value.replace("'", "");
        return Arrays.asList(value.split(","));
    }
}
