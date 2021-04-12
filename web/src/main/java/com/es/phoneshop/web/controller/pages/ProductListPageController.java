package com.es.phoneshop.web.controller.pages;

import com.es.core.enumeration.SortField;
import com.es.core.enumeration.SortOrder;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Validated
@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    private static final int LIMIT = 10;

    @Resource
    private PhoneDao phoneDao;

    @GetMapping
    public String showProductList(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "") String query,
                                  @RequestParam(defaultValue = "ASC") SortOrder order,
                                  @RequestParam(defaultValue = "PRICE") SortField sort,
                                  Model model) {
        long totalResults = phoneDao.countQueryResults(query);
        model.addAttribute("phones", phoneDao.query(query, page * LIMIT, LIMIT, sort, order));
        model.addAttribute("page", page);
        model.addAttribute("pagesCount", totalResults / LIMIT);
        model.addAttribute("totalResults", totalResults);
        return "productList";
    }
}
