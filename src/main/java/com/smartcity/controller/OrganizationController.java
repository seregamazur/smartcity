package com.smartcity.controller;

import com.smartcity.dto.OrganizationDto;
import com.smartcity.service.OrganizationService;
import com.smartcity.service.OrganizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public OrganizationDto create(@RequestBody OrganizationDto organizationDto) {
        return organizationService.create(organizationDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationDto get(@PathVariable("id") Long id) {
        return organizationService.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationDto update(@PathVariable("id") Long id, @RequestBody OrganizationDto organizationDto) {
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
