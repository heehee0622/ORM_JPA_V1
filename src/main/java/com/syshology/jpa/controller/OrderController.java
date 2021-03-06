package com.syshology.jpa.controller;

import com.syshology.jpa.entity.Item;
import com.syshology.jpa.entity.Member;
import com.syshology.jpa.entity.Order;
import com.syshology.jpa.repository.OrderSearch;
import com.syshology.jpa.service.ItemService;
import com.syshology.jpa.service.MemberService;
import com.syshology.jpa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by s.h.kim.
 * User: 김상희
 * Date: 2021-01-27
 * Time: 오후 4:04
 * Project : IntelliJ IDEA
 */
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @GetMapping(value = "/order")
    public ModelAndView createForm(ModelAndView modelAndView){
        List<Member> members = memberService.findMembers();
        List<Item> idItems = itemService.findIdItems();
        modelAndView.addObject("members", members);
        modelAndView.addObject("items",idItems);
        modelAndView.setViewName("order/orderForm");
        return modelAndView;
    }

    @PostMapping(value = "/order")
    public void order(HttpServletResponse response, @RequestParam("memberId") Long memberId, @RequestParam("itemId") Long itemId, @RequestParam("count") int count) throws Exception {
        orderService.order(memberId,itemId,count);
        response.sendRedirect("/orders");
    }
    @GetMapping(value = "/orders")
    public ModelAndView orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, ModelAndView model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addObject("orders", orders);
        model.setViewName("order/orderList");
        return model;
    }
    @PostMapping(value = "/orders/{orderId}/cancel")
    public void cancelOrder(HttpServletResponse response, @PathVariable("orderId") Long orderId) throws IOException {
        orderService.cancelOrder(orderId);
        response.sendRedirect("/orders");
    }
}
