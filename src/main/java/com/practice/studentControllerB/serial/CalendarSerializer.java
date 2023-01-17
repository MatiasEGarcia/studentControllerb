package com.practice.studentControllerB.serial;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

//from json to calendar
public class CalendarSerializer extends JsonSerializer<Calendar>{

	private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	
	@Override
	public void serialize(Calendar calendar, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		String dateAsString = formatter.format(calendar.getTime());
		gen.writeString(dateAsString);
	}

}
