/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author joseluis.bachiller
 */

public class DateUtil {

    /** The date pattern that is used for conversion. Change as you wish. */
    private static final String DATE_PATTERN = "dd/MM/yyyy";

    /** The date formatter. */
    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Returns the given date as a well formatted String. The above defined 
     * {@link DateUtil#DATE_PATTERN} is used.
     * 
     * @param date the date to be returned as a string
     * @return formatted string
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Converts a String in the format of the defined {@link DateUtil#DATE_PATTERN} 
     * to a {@link LocalDate} object.
     * 
     * Returns null if the String could not be converted.
     * 
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Checks the String whether it is a valid date.
     * 
     * @param dateString
     * @return true if the String is a valid date
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }
    
    /**
     * Devuelve el timestamp actual (Fecha y hora actual)
     * @return 
     */
    public static LocalDate now() {
        return LocalDate.now();
    }
    
    /**
     * 
     * Convierte una fecha y hora del formato de Java al formato de la BBDD.
     * 
     * @param entityValue fecha recuperada de la aplicación
     * @return fecha y hora para la BBDD
     */
    public static Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
        return Timestamp.valueOf(entityValue);
    }

    /**
     * Convierte de una fecha y hora en formato de la BBDD a una de java
     * 
     * @param databaseValue fecha y hora de la bbdd
     * @return fecha y hora en formato java
     */
    public static LocalDateTime convertToEntityAttribute(Timestamp databaseValue) {
        if (databaseValue != null) {
            return databaseValue.toLocalDateTime();
        } else {
            return null;
        }
        
    }
    
    /**
     * 
     * Convierte una fecha del formato de Java al formato de la BBDD.
     * 
     * @param entityValue fecha recuperada de la aplicación
     * @return fecha para la BBDD
     */
    public static Date convertToDatabaseColumn(LocalDate entityValue) {
        if (entityValue != null) {
            return Date.valueOf(entityValue);   
        } else {
            return null;
        }
    }
    
    /**
     * Convierte de una fecha y hora en formato de la BBDD a una de java
     * 
     * @param databaseValue fecha y hora de la bbdd
     * @return fecha y hora en formato java
     */
    public static LocalDate convertToEntityAttribute(Date databaseValue) {
        if (databaseValue != null) {
            LocalDate ld = new java.sql.Date( databaseValue.getTime() ).toLocalDate();
            return ld;
        } else {
            return null;
        }
    }
    
    

}