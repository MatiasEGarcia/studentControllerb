package com.practice.studentControllerB.serial;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CalendarDeserializer extends JsonDeserializer<Calendar> {

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public Calendar deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
		String dateAsString = jsonParser.getText();

        try {
            Date date = formatter.parse(dateAsString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            throw new IOException(e);
        }
	}

}
