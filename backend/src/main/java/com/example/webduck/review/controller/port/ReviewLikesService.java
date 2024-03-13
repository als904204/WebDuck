package com.example.webduck.review.controller.port;

import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.review.domain.Review;

public interface ReviewLikesService {

    Review updateLikes(Long reviewId, SessionMember sessionMember);

}
