package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConverterTest {

    @Test
    void stringToInteger(){
        StringToIntegerConverter stringToIntergerConverter = new StringToIntegerConverter();
        Integer convert = stringToIntergerConverter.convert("10");
        Assertions.assertThat(convert).isEqualTo(10);
    }
    @Test
    void integerToString(){
        IntegerToStringConverter integerToStringConverter = new IntegerToStringConverter();
        String convert = integerToStringConverter.convert(10);
        Assertions.assertThat(convert).isEqualTo("10");
    }
    @Test
    void stringToIpPort(){
        StringToIpPortConverter stringToIpPortConverter = new StringToIpPortConverter();
        IpPort convert = stringToIpPortConverter.convert("127.0.0.1:8080");
        Assertions.assertThat(convert).isEqualTo(new IpPort("127.0.0.1", 8080));
    }

    @Test
    void ipPortToString(){
        IpPortToStringConverter ipPortToStringConverter = new IpPortToStringConverter();
        IpPort source = new IpPort("127.0.0.1", 8080);
        String convert = ipPortToStringConverter.convert(source);
        Assertions.assertThat(convert).isEqualTo("127.0.0.1:8080");
    }


}