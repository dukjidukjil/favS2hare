package com.favshare.pop.dto.pop;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PopInfoRequest {
    private int userId;
    private int popId;
}
