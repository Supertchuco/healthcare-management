package com.healthcare.healthcaremanagement.controller;

import com.healthcare.healthcaremanagement.dto.ExamDto;
import com.healthcare.healthcaremanagement.entity.Exam;
import com.healthcare.healthcaremanagement.service.ExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/exam", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Exam controller")
@Slf4j
@Controller
public class ExamController {

    @Autowired
    private ExamService examService;

    /**
     * Create new Exam on database.
     *
     * @param examDto request data input
     * @return 200 success
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create new exam on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Exam.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public Exam createExam(@Valid @RequestBody ExamDto examDto) {
        log.info("Create new exam on database with patient CPF:{} and patient name:{}", examDto.getPatientCPF(),
                examDto.getPatientName());
        return examService.createExam(examDto);
    }

    /**
     * Update Exam on database.
     *
     * @param examId  request data input
     * @param examDto request data input
     * @return 200 success
     */
    @RequestMapping(value = "/{examId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update exam on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Exam.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public Exam updateExam(@PathVariable int examId, @Valid @RequestBody ExamDto examDto) {
        log.info("Update exam on database with exam id:{}", examId);
        return examService.updateExam(examId, examDto);
    }

    /**
     * Delete Exam on database.
     *
     * @param examId request data input
     * @return 200 success
     */
    @RequestMapping(value = "/{examId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete exam on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public ResponseEntity deleteExam(@PathVariable int examId) {
        log.info("Delete exam on database with exam id:{}", examId);
        examService.deleteExam(examId);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieve Exam on database.
     *
     * @param examId request data input
     * @return 200 success
     */
    @RequestMapping(value = "/{examId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve exam on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Exam.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public Exam retrieveExam(@PathVariable int examId) {
        log.info("Retrieve exam on database with exam id:{}", examId);
        return examService.retrieveExam(examId);
    }
}
