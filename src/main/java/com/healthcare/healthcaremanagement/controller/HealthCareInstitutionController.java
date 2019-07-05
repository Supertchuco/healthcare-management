package com.healthcare.healthcaremanagement.controller;

import com.healthcare.healthcaremanagement.dto.HealthCareInstitutionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "HealthCareInstitution controller")
@Slf4j
@Controller
public class HealthCareInstitutionController {


    /**
     * Create new health care institution on database.
     *
     * @param healthCareInstitutionDto request data input
     * @return 200 success
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create new health care institution on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = HealthCareInstitutionDto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = HealthCareInstitutionDto.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = HealthCareInstitutionDto.class)
    })
    public HealthCareInstitutionDto createHealthCareInstitution(@Valid @RequestBody HealthCareInstitutionDto healthCareInstitutionDto) {
        log.info("Create new health care institution on database with CNPJ:{} and name:{}", healthCareInstitutionDto.getCnpj(), healthCareInstitutionDto.getName());
        //return binaryDataService.updateBinaryDataSide(binaryDataId, binaryDataDto, DataSide.LEFT.name());
        return null;
    }
}
