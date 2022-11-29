package com.favshare.pop.dto.pop;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class GetPopListRequest {
    int userId;
    int idolId;
}
