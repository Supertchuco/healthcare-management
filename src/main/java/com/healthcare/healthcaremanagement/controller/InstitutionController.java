package com.healthcare.healthcaremanagement.controller;

import com.healthcare.healthcaremanagement.dto.InstitutionDto;
import com.healthcare.healthcaremanagement.entity.Institution;
import com.healthcare.healthcaremanagement.service.InstitutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/healthCareInstitution", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Institution controller")
@Slf4j
@Controller
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    /**
     * Create new health care institution on database.
     *
     * @param institutionDto request data input
     * @return 200 success
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create new health care institution on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = InstitutionDto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = InstitutionDto.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = InstitutionDto.class)
    })
    public Institution institution(@Valid @RequestBody InstitutionDto institutionDto) {
        log.info("Create new health care institution on database with CNPJ:{} and name:{}", institutionDto.getCnpj(), institutionDto.getName());
        return institutionService.createInstitution(institutionDto);
    }
}
