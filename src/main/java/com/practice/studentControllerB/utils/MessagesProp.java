package com.practice.studentControllerB.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessagesProp {

	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;
	
	public String getMessage(String messageId) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(messageId,null,locale);
	}
}
