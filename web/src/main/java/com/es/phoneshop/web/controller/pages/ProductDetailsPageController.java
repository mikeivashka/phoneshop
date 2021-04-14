package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    @Resource
    private PhoneDao phoneDao;

    @GetMapping("/{id}")
    public String getPhoneDetails(@PathVariable Long id, Model model){
        model.addAttribute("phone", phoneDao.get(id).orElseThrow(IllegalArgumentException::new));
        return "productDetails";
    }
}

