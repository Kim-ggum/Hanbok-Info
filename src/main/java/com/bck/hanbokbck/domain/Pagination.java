package com.bck.hanbokbck.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Pagination {
    private int currentPage; // 현재 페이지
    private int totalPage; // 페이지 총 개수
    private int size; // 한 페이지에 보일 페이지 개수

    private int beginPage;
    private int endPage;

    public Pagination(int currentPage, int totalPage, int size) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.size = size;

        if(totalPage > 0) {
            this.beginPage = currentPage / size * size + 1;
            this.endPage = this.beginPage + size - 1;
            if(this.endPage > totalPage)
                this.endPage = totalPage;
        }
    }
}
