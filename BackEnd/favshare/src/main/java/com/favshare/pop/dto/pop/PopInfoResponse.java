package com.favshare.pop.dto.pop;

import com.favshare._temp.dto.PopInfoDto;
import com.favshare._temp.dto.input.UserProfileDto;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PopInfoResponse {
    private PopInfoDto popInfoDto;
    private UserProfileDto userProfileDto;

}
