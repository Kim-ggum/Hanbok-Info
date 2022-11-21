package com.bck.hanbokbck.service;

import com.bck.hanbokbck.domain.Member;
import com.bck.hanbokbck.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {
    Notice createNotice(Notice notice);
    Page<Notice> getNoticeListWithPagination(Pageable pageable);
    Notice getNotice(Long id);
    Notice updateNotice(Long id, Notice notice);
    void deleteNotice(Long id);
}
