package com.favshare.pop.dto.pop;

import com.favshare._temp.dto.PopDto;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PopInfoFromOriginYoutubeResponse {
    private int popCnt;
    private List<PopDto> popList;
}
