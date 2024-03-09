package com.example.webduck.global.security.encryption;

import static com.example.webduck.global.exception.exceptionCode.LogicExceptionCode.BAD_REQUEST;

import com.example.webduck.global.exception.CustomException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.ConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

/**
 * AES 알고리즘을 사용하여 암호화하고 복호화
 */
@Slf4j
@Component
public class AttributeEncryptor {

    // "AES" 알고리즘의 "CBC" 모드와 "PKCS5Padding" 패딩 방식을 사용
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    // 암호화 및 복호화에 사용될 AES 키
    private final SecretKey aesSecretKey;

    // 암호화 및 복호화에 사용될 초기화 벡터(IV)입니다. CBC 모드에서는 각 암호화 블록이 이전 블록에 의존하기 때문에 필요
    private final IvParameterSpec iv;

    // 시크릿키를 가져오지 못할경우 500번 에러
    public AttributeEncryptor(@Value("${secret.key}") String secretKey) throws Exception {
        if (secretKey.isEmpty() || secretKey.isBlank()) {
            log.error("#failed to load config. secretKey is can't not be null or empty");
            throw new ConfigurationException();
        }

        // SHA-256 해시 알고리즘을 사용하여 시크릿키를 해시. 이렇게 하면 어떤 길이의 키든 일정한 길이(256비트)의 해시로 변환
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(secretKey.getBytes(StandardCharsets.UTF_8));

        // 해시된 키를 사용하여 SecretKey 객체를 생성
        this.aesSecretKey = new SecretKeySpec(keyBytes, "AES");

        // 초기화 벡터를 생성 여기서는 모든 바이트가 0인 16바이트 배열을 사용(랜덤으로 하는게 더 보안상 좋지만 복잡해서 포기..)
        this.iv = new IvParameterSpec(new byte[16]);
    }

    // 주어진 문자열을 암호화
    public String encrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, aesSecretKey, iv);
            return Base64.getEncoder().encodeToString(cipher.doFinal(input.getBytes()));
        } catch (Exception e) {
            log.error("#encrypt error={}",e.getMessage());
            throw new CustomException(BAD_REQUEST);
        }
    }

    // 암호화된 문자열을 복호화
    public String decrypt(String encrypted) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, aesSecretKey, iv);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)));
        } catch (Exception e) {
            log.error("#decrypt error={}",e.getMessage());
            throw new CustomException(BAD_REQUEST);
        }
    }

}
