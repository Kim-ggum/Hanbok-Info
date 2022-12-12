package com.bck.hanbokbck.service;

import com.bck.hanbokbck.domain.Notice;
import com.bck.hanbokbck.entity.NoticeEntity;
import com.bck.hanbokbck.repository.NoticeRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class NoticeServiceTest {
    private NoticeRepository noticeRepository = Mockito.mock(NoticeRepository.class);
    private NoticeServiceImpl noticeService;

    @BeforeEach
    public void setUpTest() {
        noticeService = new NoticeServiceImpl(noticeRepository);
        System.out.println("notice service 테스트 시작");
    }

    @AfterEach
    void afterEach() {
        System.out.println("notice service 테스트 끝");
    }

    @Test
    @DisplayName("공지사항 생성 성공 테스트")
    public void createNoticeTest() {
        //given
        Notice givenNotice = Notice.builder()
                .title("테스트")
                .content("테스트 내용")
                .build();

        NoticeEntity savedNoticeEntity = noticeService.dtoToEntity(givenNotice);
        savedNoticeEntity.setUpdateAt(LocalDateTime.now());
        savedNoticeEntity.setCreateAt(LocalDateTime.now());

        Mockito.when(noticeRepository.save(noticeService.dtoToEntity(givenNotice)))
                .thenReturn(savedNoticeEntity);

        // when
        Notice resultNotice = noticeService.createNotice(givenNotice);

        // then
        Assertions.assertEquals(resultNotice, noticeService.entityToDto(savedNoticeEntity));
        verify(noticeRepository).save(any(NoticeEntity.class));
    }

    @Test
    @DisplayName("공지사항 리스트 반환 테스트")
    public void getNoticeListWithPaginationTest() {
        // given
        List<Notice> givenNoticeList = new ArrayList<>();
        Notice notice = Notice.builder()
                .id(1L)
                .title("테스트")
                .content("테스트 내용")
                .updateAt(LocalDateTime.now())
                .createAt(LocalDateTime.now())
                .build();
        givenNoticeList.add(notice);
        givenNoticeList.add(notice);
        givenNoticeList.add(notice);
        givenNoticeList.add(notice);
        givenNoticeList.add(notice);

        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        Mockito.when(noticeRepository.findAll(pageable).map(notice1 -> noticeService.entityToDto(notice1)).getContent())
                .thenReturn(givenNoticeList);

        // when
        List<Notice> resultList = noticeService.getNoticeListWithPagination(pageable);

        // then
        Assertions.assertEquals(resultList, givenNoticeList);
    }


    @Test
    @DisplayName("공지사항 가져오기 테스트")
    public void getNoticeTest() {
        //given
        Notice givenNotice = Notice.builder()
                .id(1L)
                .title("테스트")
                .content("테스트 내용")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

        NoticeEntity givenResultNoticeEntity = NoticeEntity.builder()
                .id(1L)
                .title("테스트")
                .content("테스트 내용")
                .build();
        givenResultNoticeEntity.setUpdateAt(givenNotice.getUpdateAt());
        givenResultNoticeEntity.setCreateAt(givenNotice.getCreateAt());

        Mockito.when(noticeRepository.findById(1L))
                .thenReturn(Optional.of(givenResultNoticeEntity));

        // when
        Notice resultNotice = noticeService.getNotice(1L);

        // then
        Assertions.assertEquals(resultNotice.getId(), givenNotice.getId());
        Assertions.assertEquals(resultNotice.getTitle(), givenNotice.getTitle());
        Assertions.assertEquals(resultNotice.getContent(), givenNotice.getContent());
        Assertions.assertEquals(resultNotice.getCreateAt(), givenNotice.getCreateAt());
        Assertions.assertEquals(resultNotice.getUpdateAt(), givenNotice.getUpdateAt());

        verify(noticeRepository).findById(1L);
    }



    @Test
    @DisplayName("게시글 수정 성공 테스트")
    public void updateNoticeTest() {
        //given
        NoticeEntity givenNoticeEntity = NoticeEntity.builder()
                .id(1L)
                .title("테스트")
                .content("테스트 내용")
                .build();
        givenNoticeEntity.setCreateAt(LocalDateTime.now());
        givenNoticeEntity.setUpdateAt(LocalDateTime.now());

        Mockito.when(noticeRepository.findById(1L))
                .thenReturn(Optional.ofNullable(givenNoticeEntity));

        NoticeEntity noticeEntity = givenNoticeEntity;
        noticeEntity.setTitle("테스트 수정");
        noticeEntity.setContent("테스트 수정 내용");
        noticeEntity.setUpdateAt(LocalDateTime.of(2022, 12, 30, 10, 10)); // 테스트이기 때문에 수동으로 업데이트 시킴

        Mockito.when(noticeRepository.save(any(NoticeEntity.class)))
                .thenReturn(noticeEntity);

        Long noticeId = 1L;

        Notice notice = Notice.builder()
                .id(1L)
                .title("테스트 수정")
                .content("테스트 내용 수정")
                .createAt(noticeEntity.getCreateAt())
                .updateAt(noticeEntity.getUpdateAt())
                .build();
        // when
        Notice savedNotice = noticeService.updateNotice(noticeId, notice);

        // then
        Assertions.assertEquals(savedNotice.getId(), noticeEntity.getId());
        Assertions.assertEquals(savedNotice.getContent(), noticeEntity.getContent());
        Assertions.assertEquals(savedNotice.getTitle(), noticeEntity.getTitle());
        Assertions.assertEquals(savedNotice.getCreateAt(), noticeEntity.getCreateAt());
        Assertions.assertEquals(savedNotice.getUpdateAt(), noticeEntity.getUpdateAt());
        verify(noticeRepository).findById(1L);
        verify(noticeRepository).save(any(NoticeEntity.class));
    }


    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deleteBoardTest() {
        // given
        //given
        NoticeEntity givenNotice = NoticeEntity.builder()
                .id(1L)
                .title("테스트")
                .content("테스트 내용")
                .build();

        Mockito.when(noticeRepository.findById(1L))
                .thenReturn(Optional.of(givenNotice));

        // when
        noticeService.deleteNotice(1L);

        // then
        verify(noticeRepository).delete(any(NoticeEntity.class));
    }
}
