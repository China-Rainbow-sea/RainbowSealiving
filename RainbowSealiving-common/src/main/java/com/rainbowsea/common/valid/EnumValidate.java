package com.rainbowsea.common.valid;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

/**
 *
 老韩解读
 * 1.参考@NotNull源码来开发
 * 2. EnumConstraintValidator.class是自定义的真正的校验器，需要自己开发
 * 3. @Constraint(validatedBy = {EnumConstraintValidator.class}) 可以指定该自定义注解和
 *     EnumConstraintValidator 逻辑业务处理，交给它进行处理
 * 4. String message() default "{com.rainbowsea.common.valid.EnumValidate.message}"; 可以指定校验时，返回的信息
 *      这里表示从这个位置得到校验得信息
 *  读取, resources\ValidationMessages.properties的
     key=com.rainbowsea.common.valid.EnumValidate.message
 */
@Documented
@Constraint(validatedBy = {EnumConstraintValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER,
        TYPE_USE})
@Retention(RUNTIME)
public @interface EnumValidate {

    String message() default "{com.rainbowsea.common.valid.EnumValidate.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // 增加 values 属性
    int[] values() default {};

    // 增加 regexp 用于正则表达式处理
    String regexp() default "";
}
