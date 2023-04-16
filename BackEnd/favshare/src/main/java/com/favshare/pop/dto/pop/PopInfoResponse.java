package com.favshare.pop.dto.pop;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PopInfoResponse {
    private PopInfoDto popInfoDto;
    private UserProfileDto userProfileDto;

}
