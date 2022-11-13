package com.bck.hanbokbck.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Member {
    private Long member_id;
    private String member_email;
    private String member_pw;
    private String member_name;
}
