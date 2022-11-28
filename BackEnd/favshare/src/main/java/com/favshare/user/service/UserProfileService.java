package com.favshare.user.service;

import com.favshare.user.dto.request.EditUserProfileRequest;
import com.favshare.user.dto.UserProfile;

public interface UserProfileService {
//    - 사용자 프로필 조회(프로필 페이지) - 여러군데에서 사용
        UserProfile getUserProfile(int userId);
//    - 프로필 수정 시 조회 -> 위에 내용과 동일하게 사용 가능할 듯
//    - 사용자 프로필 수정
        void modifyUserProfile(EditUserProfileRequest editUserProfileRequest);

//    - 사용자의 다중 피드리스트 반환하는 api
//    - 다중 피드 안에 속한 poplist를 반환하는 api ( 전체, 특정 피드 )
}
