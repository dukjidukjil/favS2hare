package com.favshare.pop.dto.pop;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DeletePopRequest {
    List<Integer> popIdList = new ArrayList<>();
}
