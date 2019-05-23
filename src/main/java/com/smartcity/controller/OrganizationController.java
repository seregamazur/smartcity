package com.smartcity.controller;

import com.smartcity.dto.OrganizationDto;
import com.smartcity.dto.transfer.ExistingRecord;
import com.smartcity.dto.transfer.NewRecord;
import com.smartcity.service.OrganizationService;
import com.smartcity.service.OrganizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {
    private OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationServiceImpl organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("")
    public OrganizationDto create(@Validated(NewRecord.class) @RequestBody OrganizationDto organizationDto) {
        return organizationService.create(organizationDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationDto get(@PathVariable("id") Long id) {
        return organizationService.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationDto update(
            @Validated(ExistingRecord.class)
            @PathVariable("id") Long id,
            @RequestBody OrganizationDto organizationDto) {
        organizationDto.setId(id);
        return organizationService.update(organizationDto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return organizationService.delete(id);
    }

    @GetMapping
    public List<OrganizationDto> getAll() {
        return organizationService.getAll();
    }

}
