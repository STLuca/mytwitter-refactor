package com.example.util;


import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PageLinkService {

    public static List<PageLink> getPageLinks(
            int currentPage,
            int totalPages
    ) {
        ServletUriComponentsBuilder currentReq = ServletUriComponentsBuilder.fromCurrentRequest();

        return PageLinkService.getPageNumbers(currentPage, totalPages)
                .mapToObj(pageNum -> new PageLink(
                        pageNum,
                        currentReq.replaceQueryParam("page", pageNum).toUriString() ,
                        pageNum == currentPage))
                .collect(Collectors.toList());

    }

    public static IntStream getPageNumbers (
            int currentPage,
            int pageTotal) {
        return IntStream.rangeClosed(currentPage - 3, currentPage + 3)
                    .filter(x -> x >= 0 && x <= pageTotal - 1);
    }

}

