package com.copilot.assignment.utils;

import com.copilot.assignment.controller.response.GenericResponse;
import com.copilot.assignment.controller.response.ResultInfo;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static com.copilot.assignment.constants.ResultInfoConstants.MSG_PARSE_ERROR;

@UtilityClass
public class ResultUtil {

    public ResponseEntity<Object> generateResponse(ResultInfo resultInfo, Object resObj) {
        var response = GenericResponse.builder().resultInfo(resultInfo).data(resObj).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static String buildResult(ResultInfo resultInfo, Object data) {
        try {
            return ObjectMapperUtil.mapObjectToJson(GenericResponse.builder().resultInfo(resultInfo).data(data).build());
        } catch (IOException e) {
            return ResultUtil.buildResult(MSG_PARSE_ERROR, null);
        }
    }
}