package com.example.webduck.global.converter;

import com.example.webduck.global.security.encryption.AttributeEncryptor;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * DB insert 전 암호화 & DB select 후 암호화 된 값 복호화 후 객체에 바인딩하는 컨버터
 * String email
 * String socialPK
 * String socialID
 * String username
 */
@Converter
public class EncryptorConverter implements AttributeConverter<String, String> {

    private final AttributeEncryptor encryptor;

    public EncryptorConverter(AttributeEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    // INSERT 직전 암호화
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return encryptor.encrypt(attribute);
    }

    // SELECT 후 암호화된 값을 복호화 후 객체에 바인딩
    @Override
    public String convertToEntityAttribute(String dbData) {
        return encryptor.decrypt(dbData);
    }
}
