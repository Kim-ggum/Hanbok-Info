package com.bck.hanbokbck.controller;

import com.bck.hanbokbck.domain.Notice;
import com.bck.hanbokbck.domain.Pagination;
import com.bck.hanbokbck.service.NoticeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pages")
@RequiredArgsConstructor
public class PageController {

    private final NoticeServiceImpl noticeService;

    @GetMapping("/notice")
    public String noticePage(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)
                                         Pageable pageable, Model model) {

        Page<Notice> noticePage = noticeService.getNoticeListWithPagination(pageable);
        Pagination page = new Pagination(pageable.getPageNumber(), noticePage.getTotalPages(), 5);

        List<Notice> noticeList = noticePage.getContent();
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("page", page);
        return "/main_content/board1";
    }
    @GetMapping("/post")
    public String postPage() {
        return "/main_content/board_view";
    }
}
