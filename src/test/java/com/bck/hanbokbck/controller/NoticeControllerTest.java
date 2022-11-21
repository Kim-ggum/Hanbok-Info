package com.bck.hanbokbck.controller;

import com.bck.hanbokbck.domain.Notice;
import com.bck.hanbokbck.service.NoticeServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(NoticeController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoticeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    NoticeServiceImpl noticeService;

    @BeforeEach
    void beforeEach() {
        System.out.println("notice 테스트 시작");
    }

    @AfterEach
    void afterEach() {
        System.out.println("notice 테스트 끝");
    }

    @Test
    @DisplayName("notice 상세 페이지 성공 테스트") //
    public void listTest() throws Exception {
        // given
        List<Notice> givenNoticeList = new ArrayList<>();
        Notice notice = Notice.builder()
                .id(1L)
                .title("테스트")
                .content("테스트 내용")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
        givenNoticeList.add(notice);
        givenNoticeList.add(notice);
        givenNoticeList.add(notice);
        givenNoticeList.add(notice);
        givenNoticeList.add(notice);

        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        given(noticeService.getNoticeListWithPagination(pageable)).willReturn(givenNoticeList);

        // when, then
        mockMvc.perform(get("/hanbok/notice"))
                .andExpect(status().isOk())
                .andExpect(view().name("/notice/notice"))
                .andDo(print());

        verify(noticeService).getNoticeListWithPagination(pageable);
    }

    @Test
    @DisplayName("notice 상세 페이지 성공 테스트")
    public void detailTest() throws Exception {
        // given
        Notice notice = Notice.builder()
                .id(1L)
                .title("테스트")
                .content("테스트 내용")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
        Long boardId = 1L;
        given(noticeService.getNotice(boardId)).willReturn(notice);

        // when, then
        mockMvc.perform(get("/hanbok/notice/" + boardId))
                .andExpect(status().isOk())
                .andExpect(view().name("/notice/notice-detail"))
                .andDo(print());

        verify(noticeService).getNotice(boardId);
    }

    @Test
    @DisplayName("notice 생성 페이지 성공 테스트")
    public void createFormTest() throws Exception {
        // when, then
        mockMvc.perform(get("/hanbok/notice/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("/notice/notice-create"))
                .andDo(print());
    }

    @Test
    @DisplayName("notice 생성 성공 테스트")
    public void createTest() throws Exception {
        // given
        Notice givenNotice = Notice.builder()
                .title("테스트")
                .content("테스트 내용")
                .build();

        given(noticeService.createNotice(givenNotice)).willReturn(givenNotice);

        Gson gson = new Gson();
        String content = gson.toJson(givenNotice);

        // when, then
        mockMvc.perform(post("/hanbok/notice")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("/notice/notice-detail"))
                .andDo(print());

        verify(noticeService).createNotice(givenNotice);
    }

    @Test
    @DisplayName("notice 수정 페이지 성공 테스트")
    public void editFormTest() throws Exception { //
        // given
        Long id = 1L;
        Notice givenNotice = Notice.builder()
                .id(id)
                .title("테스트")
                .content("테스트 내용")
                .updateAt(LocalDateTime.now())
                .createAt(LocalDateTime.now())
                .build();
        given(noticeService.getNotice(id)).willReturn(givenNotice);

        // when, then
        mockMvc.perform(get("/hanbok/notice/" + id + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/notice/notice-edit"))
                .andDo(print());

        verify(noticeService).getNotice(id);
    }

    @Test
    @DisplayName("notice 수정 로직 성공 테스트")
    public void editTest() throws Exception {
        // given
        Long id = 1L;
        Notice givenNotice = Notice.builder()
                .id(id)
                .title("테스트")
                .content("테스트 내용")
                .build();
        given(noticeService.updateNotice(id, givenNotice)).willReturn(givenNotice);

        Gson gson = new Gson();
        String content = gson.toJson(givenNotice);

        // when, then
        mockMvc.perform(put("/hanbok/notice/" + id)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("/notice/notice-detail"))
                .andDo(print());

        verify(noticeService).updateNotice(id, givenNotice);
    }
}
