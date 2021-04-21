package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.CartService;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.phoneshop.web.controller.dto.QuickOrderForm;
import com.es.phoneshop.web.controller.dto.QuickOrderFormItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quickOrder")
public class QuickOrderPageController {

    @Resource
    private CartService cartService;

    @Resource
    private PhoneDao phoneDao;

    @ModelAttribute("orderForm")
    public QuickOrderForm populateOrderForm() {
        return new QuickOrderForm();
    }

    @GetMapping
    public String getQuickOrder(Model model) {
        return "quickOrder";
    }

    @PostMapping
    public String applyQuickOrder(@Valid @ModelAttribute("orderForm") QuickOrderForm form, BindingResult bindingResult, Model model) {
        Map<Phone, Integer> validItems = findValidItems(form.getOrderItems(), bindingResult);
        validItems.forEach((k, v) -> cartService.addPhone(k.getId(), v));
        removeValidItems(form.getOrderItems(), validItems);
        model.addAttribute("hasBindingErrors", validItems.size() != countNotEmptyItems(form.getOrderItems()));
        model.addAttribute("added", validItems);
        return "quickOrder";
    }

    private Map<Phone, Integer> findValidItems(List<QuickOrderFormItem> items, BindingResult bindingResult) {
        Map<Phone, Integer> validItems = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            if (!bindingResult.hasFieldErrors("orderItems[" + i + "*")) {
                validItems.put(phoneDao.get(items.get(i).getProductKey()).orElseThrow(IllegalArgumentException::new),
                        Integer.valueOf(items.get(i).getQuantity())
                );
            }
        }
        return validItems;
    }

    private Long countNotEmptyItems(List<QuickOrderFormItem> items) {
        return items.stream()
                .filter(item -> !(item.getProductKey().isEmpty() && item.getQuantity().isEmpty()))
                .count();
    }

    private void removeValidItems(List<QuickOrderFormItem> items, Map<Phone, Integer> validItems) {
        validItems.keySet().stream()
                .map(Phone::getModel)
                .forEach(model -> items.removeIf(item -> item.getProductKey().equals(model)));
    }
}
