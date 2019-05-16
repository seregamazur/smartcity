package com.smartcity.controller;

import com.smartcity.dao.BudgetDao;
import com.smartcity.dao.BudgetDaoImpl;
import com.smartcity.domain.Budget;
import com.smartcity.dto.BudgetDto;
import com.smartcity.mapperDto.BudgetDtoMapper;
import com.smartcity.service.BudgetService;
import com.smartcity.service.BudgetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    private BudgetService service;

    @Autowired
    public BudgetController(BudgetServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public BudgetDto get() {
        return service.set(new BudgetDto(1L));
    }

    @PostMapping
    public BudgetDto put(@RequestBody BudgetDto budgetDto) {
        System.out.println("in: " + budgetDto);
        BudgetDto out = service.set(budgetDto);
        System.out.println("out: "+out);

        return out;
    }
}