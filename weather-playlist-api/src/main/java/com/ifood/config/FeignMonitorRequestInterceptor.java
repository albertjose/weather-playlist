package com.ifood.config;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Util;

@Component
public class FeignMonitorRequestInterceptor implements RequestInterceptor {

	// Fixing enconde spaces %2b
	private static String urlDecode(String arg) {
		try {
			return URLDecoder.decode(arg, Util.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void apply(RequestTemplate template) {

		for (String key : template.queries().keySet()) {
			Collection<String> values = template.queries().get(key);
			if (allValuesAreNull(values)) {
				continue;
			} else {
				Collection<String> encodedValues = new ArrayList<String>();
				for (String value : values) {
					encodedValues.add(urlDecode(value));
				}
				template.query(true, key, encodedValues);
			}
		}
	}

	private boolean allValuesAreNull(Collection<String> values) {
		if (values == null || values.isEmpty()) {
			return true;
		}
		for (String val : values) {
			if (val != null) {
				return false;
			}
		}
		return true;
	}
}