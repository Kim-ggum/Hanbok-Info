package com.bck.hanbokbck.controller;

import com.bck.hanbokbck.domain.ErrorResponse;
import com.bck.hanbokbck.domain.Notice;
import com.bck.hanbokbck.domain.Pagination;
import com.bck.hanbokbck.service.NoticeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/hanbok/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeServiceImpl noticeService;

    private final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);

    @GetMapping("")
    public String list(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)
                           Pageable pageable, Model model) {

        Page<Notice> noticePage = noticeService.getNoticeListWithPagination(pageable);
        Pagination page = new Pagination(pageable.getPageNumber(), noticePage.getTotalPages(), 5);

        List<Notice> noticeList = noticePage.getContent();
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("page", page);
        return "/notice/notice";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Notice notice = noticeService.getNotice(id);
        model.addAttribute("notice", notice);
        return "/notice/notice-detail";
    }

    @GetMapping("/create")
    public String createForm() {
        return "/notice/notice-create";
    }

    @PostMapping("")
    public String create(Notice notice, Model model) {
        Notice savedNotice = noticeService.createNotice(notice);
        model.addAttribute("notice", savedNotice);
        return "/notice/notice-detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Notice notice = noticeService.getNotice(id);
        model.addAttribute("notice", notice);
        return "/notice/notice-edit";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Long id, Notice notice, Model model) {
        Notice savedNotice = noticeService.updateNotice(id, notice);
        model.addAttribute("notice", savedNotice);
        return "/notice/notice-detail";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) { // delete
        noticeService.deleteNotice(id);
        return "redirect:/hanbok/notice";
    }


    @Transactional
    @PostMapping("/upload/image")
    public @ResponseBody Map<String, Object> imageUpload(@RequestParam Map<String, Object> paramMap, @RequestParam("upload") List<MultipartFile> files) {
        String path = System.getProperty("user.dir");
        String uploadLocation = path + "\\images\\notice\\"; // ????????? ??????
        //        String uploadLocation = path + "\\src\\main\\resources\\images\\notice\\"; // ????????? ??????
        //String uploadLocation = servletContext.getRealPath("/").replace("\\", "/") + "/static/upload/images/"; // ????????? ??????

        // ?????? ?????????(?????????) ?????? ??????
        for(MultipartFile file : files) {

            String fileRealName = file.getOriginalFilename(); // ????????? ??????

            String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length()); // .jsp ?????? ????????? ??????

            // ??? ???????????? ?????????
            UUID uuid = UUID.randomUUID();
            String[] uuids = uuid.toString().split("-");
            String uniqueFileName = uuids[0] + uuids[1] + uuids[2] + uuids[3] + uuids[4];

            // ?????? ??????
            File saveFile = new File(uploadLocation + uniqueFileName + fileExtension);  // ??????
            try {
                file.transferTo(saveFile); // ?????? ??????
                LOGGER.info("????????? ?????? ??????");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("????????? ?????? ??????");
                paramMap.put("error", "????????? ???????????? ??????????????????.");
                return paramMap;
            }

            paramMap.put("url", "/images/notice/" + uniqueFileName + fileExtension);
        }

        return paramMap;
    }

    @GetMapping("/access-denied")
    public String acessDenied(Model model) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "?????? ????????? ????????????.");
        model.addAttribute("error", errorResponse);
        return "/error/error_page";
    }
}
