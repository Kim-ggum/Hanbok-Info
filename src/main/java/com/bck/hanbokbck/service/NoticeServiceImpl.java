package com.bck.hanbokbck.service;

import com.bck.hanbokbck.domain.Notice;
import com.bck.hanbokbck.entity.NoticeEntity;
import com.bck.hanbokbck.exception.customException.NoticeCrudFailureException;
import com.bck.hanbokbck.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(NoticeServiceImpl.class);

    @Transactional
    @Override
    public Notice createNotice(Notice notice) {
        try {
            NoticeEntity noticeEntity = noticeRepository.save(dtoToEntity(notice));
            return entityToDto(noticeEntity);
        } catch (Exception e) {
            LOGGER.error("error : ", e);
            throw new NoticeCrudFailureException("게시글을 생성하는데 실패했습니다.");
        }
    }

    @Transactional
    @Override
    public Page<Notice> getNoticeListWithPagination(Pageable pageable) {
        try {
            Page<Notice> noticePage = noticeRepository.findAll(pageable).map(this::entityToDto); // entity -> dto로 변환(람다식 사용) .getContent()

            return noticePage;
        } catch (Exception e) {
            LOGGER.error("error : ", e);
            throw new NoticeCrudFailureException("공지사항 리스트를 가져오는데 실패했습니다.");
        }

    }

    @Transactional
    @Override
    public Notice getNotice(Long id) {
        try {
            Optional<NoticeEntity> noticeEntity = noticeRepository.findById(id);

            if (noticeEntity.isEmpty()) {
                throw new NoticeCrudFailureException("게시글을 찾을 수 없습니다.");
            }

            return entityToDto(noticeEntity.get());
        }catch (Exception e) {
            LOGGER.error("error : ", e);
            throw new NoticeCrudFailureException("게시글을 찾을 수 없습니다.");
        }
    }

    @Transactional
    @Override
    public Notice updateNotice(Long id, Notice notice) {
        try {
            Optional<NoticeEntity> selectedNoticeEntity = noticeRepository.findById(id);

            NoticeEntity updatedNoticeEntity;
            if (selectedNoticeEntity.isPresent()) {
                NoticeEntity noticeEntity = selectedNoticeEntity.get();

                noticeEntity.setTitle(notice.getTitle());
                noticeEntity.setContent(notice.getContent());
                noticeEntity.setWriter(notice.getWriter());

                updatedNoticeEntity = noticeRepository.save(noticeEntity);
            }
            else {
                throw new NoticeCrudFailureException("게시글 수정에 실패했습니다.");
            }
            return entityToDto(updatedNoticeEntity);
        } catch(Exception e) {
            LOGGER.error("error : ", e);
            throw new NoticeCrudFailureException("게시글 수정에 실패했습니다.");
        }
    }

    @Transactional
    @Override
    public void deleteNotice(Long id) {
        try {
            Optional<NoticeEntity> selectedNoticeEntity = noticeRepository.findById(id);
            if (selectedNoticeEntity.isPresent()) {
                NoticeEntity noticeEntity = selectedNoticeEntity.get();
                noticeRepository.delete(noticeEntity);
            }
            else {
                throw new NoticeCrudFailureException("게시글 삭제에 실패했습니다.");
            }
        }catch (Exception e) {
            LOGGER.error("error : ", e);
            throw new NoticeCrudFailureException("게시글 삭제에 실패했습니다.");
        }
    }

    public NoticeEntity dtoToEntity(Notice notice) {
        NoticeEntity entity = NoticeEntity.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writer(notice.getWriter())
                .build();
        return entity;
    }

    public Notice entityToDto(NoticeEntity noticeEntity) {
        Notice notice = Notice.builder()
                .id(noticeEntity.getId())
                .title(noticeEntity.getTitle())
                .content(noticeEntity.getContent())
                .writer(noticeEntity.getWriter())
                .createAt(noticeEntity.getCreateAt())
                .updateAt(noticeEntity.getUpdateAt())
                .build();
        return notice;
    }
}
