package hello.typeconverter.converter;

import hello.typeconverter.formatter.MyNumberFormatter;
import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class FormattingConversionServiceTest {
    @Test
    void formattingConversionServiceTest() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        //Converter
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        //Formatter
        conversionService.addFormatter(new MyNumberFormatter());

        assertThat(conversionService.convert("127.0.0.1:8080", IpPort.class)).isEqualTo(new IpPort("127.0.0.1", 8080));
        assertThat(conversionService.convert(new IpPort("127.0.0.1", 8080), String.class)).isEqualTo("127.0.0.1:8080");
        assertThat(conversionService.convert(new IpPort("127.0.0.1", 8080), String.class)).isEqualTo("127.0.0.1:8080");

        assertThat(conversionService.convert("1,000", Long.class)).isEqualTo(1000L);
        assertThat(conversionService.convert(1000,String.class)).isEqualTo("1,000");
    }

}