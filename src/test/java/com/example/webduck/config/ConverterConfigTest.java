package com.example.webduck.config;

import com.example.webduck.config.security.encryption.AttributeEncryptor;
import com.example.webduck.global.converter.CryptoConverter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ConverterConfigTest {


    @Bean
    public AttributeEncryptor attributeEncryptor() throws Exception {
        return new AttributeEncryptor("asdfasdfasdfasdfasdfasdfdasdfasdfadfafdsa");
    }

    @Bean
    public CryptoConverter cryptoConverter(AttributeEncryptor encryptor) {
        return new CryptoConverter(encryptor);
    }

}
