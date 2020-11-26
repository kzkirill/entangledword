package io.entangledword.web.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class LocalDateTimeSerializerConfig {

	private static String datePattern = "yyyy dd/MM HH:mm";
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer localDateCustomizer() {
		return builder -> {
			builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
			builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
		};
	}
	
	public static void main(String[] args) {
		System.out.println("Formatted date " + formatter.format(LocalDateTime.now()));
	}
}
