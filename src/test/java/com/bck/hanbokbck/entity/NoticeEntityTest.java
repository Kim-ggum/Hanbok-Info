package com.bck.hanbokbck.entity;

import com.bck.hanbokbck.repository.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class NoticeEntityTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    @DisplayName("jpa auditing 성공 테스트")
    public void entityAuditingTest() throws Exception {
        // given
        NoticeEntity noticeEntity = NoticeEntity.builder()
                .title("테스트")
                .content("테스트 내용 내용")
                .build();

        // when
        NoticeEntity savedNotice = noticeRepository.save(noticeEntity);

        // then
        System.out.println("수정된 시간 : " + savedNotice.getUpdateAt());
        System.out.println("생성된 시간 : " + savedNotice.getCreateAt());
        noticeRepository.delete(savedNotice); // 삭제
    }
}