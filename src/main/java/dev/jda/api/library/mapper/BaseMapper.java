package dev.jda.api.library.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseMapper {

    protected final ModelMapper modelMapper = new ModelMapper();

    // Custom method to convert LocalDateTime to a formatted string
    protected final String formatLocalDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    protected final Converter<LocalDateTime, String> convertLocalToString = ctx -> formatLocalDateTimeToString(ctx.getSource());
}
