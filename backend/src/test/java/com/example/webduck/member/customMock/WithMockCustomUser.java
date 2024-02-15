package com.example.webduck.member.customMock;

import com.example.webduck.member.entity.Role;
import com.example.webduck.member.entity.SocialType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * Test 용 Custom Security Mock Member
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

     String username() default "HelloUser";

     String socialPk() default "SocialPk";

     String socialId() default "SocialId";

     String email() default "hello@email.com";

     SocialType SOCIAL_TYPE() default SocialType.GOOGLE;

     Role role() default Role.USER;

}
