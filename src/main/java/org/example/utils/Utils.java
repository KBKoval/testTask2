package org.example.utils;

public class Utils {
    public static boolean checkIsEmptyString( String value ){
        return (value == null || value.isEmpty() || value.trim().length() == 0 ) ;
    }
}
