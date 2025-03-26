package com.rainbowsea.rainbowsealiving.commodity.exception;


import com.rainbowsea.common.exception.RainbowSealivingCodeEnume;
import com.rainbowsea.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * 说明:
 * @ResponseBody : 表示以 json 格式返回数据
 * @Slf4j 可以输出日志，便于数据
 * @ControllerAdvice(basePackages = "com.rainbowsea.rainbowsealiving.commodity.controller")
 *   表示统一接管 com.rainbowsea.rainbowsealiving.commodity.controller 这个路径包下的
 *   抛出了的数据校验的异常，就由  @ExceptionHandler(value = MethodArgumentNotValidException.class)
 *   处理
 */
@Slf4j
@ResponseBody
@ControllerAdvice(basePackages = "com.rainbowsea.rainbowsealiving.commodity.controller")
public class RainbowSealivingExceptionControllerAdvice {



    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {

        log.error(" 数据校验出现问题{}，异常类型：{}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();

        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.error(RainbowSealivingCodeEnume.INVALID_EXCEPTION.getCode(),
                RainbowSealivingCodeEnume.INVALID_EXCEPTION.getMsg()).put("data", errorMap);
    }

    /**
     *
     这里再写一个处理 Throwable类型的异常的方法,没有精确匹配到的异常,走这里
     */
    /**
     * 编写方法，处理没有精确匹配到的异常/错误
     * 返回一个统一的信息，方便前端处理
     */
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable){
        return R.error(RainbowSealivingCodeEnume.UNKNOWN_EXCEPTION.getCode(),
                RainbowSealivingCodeEnume.UNKNOWN_EXCEPTION.getMsg());
    }
}
