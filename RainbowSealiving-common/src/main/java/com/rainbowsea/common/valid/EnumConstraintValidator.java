package com.rainbowsea.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;


/**
 * 1. EnumConstraintValidator 是作为真正的校验器，即该自定义校验的逻辑业务都是在这里写的。
 * EnumConstraintValidator 需要实现接口  ConstraintValidator
 * ConstraintValidator<EnumValidate,Integer> 表示该自定义校验器是针对 @EnumValidate 传入的 Integer 类型数据
 * 进行校验的。
 */
public class EnumConstraintValidator implements ConstraintValidator<EnumValidate, Integer> {


    // 定义一个 set 集合，用于收集 EnumValidate 传入的 values
    private Set<Integer> set = new HashSet<>();

    // 定义一个获取到 用于收集 EnumValidate 传入的 regexp 正则表达式的字符串值
    //private String regexp;

    @Override
    public void initialize(EnumValidate constraintAnnotation) {
        // 这里我们测试一下，看看能否得到注解出传入的 values
        // 通过注解获得 values,我们
        int[] values = constraintAnnotation.values();
        for (int value : values) {
            //System.out.println("EnumValidate注解指定的 value = " + value);
            set.add(value);
        }

        // 获取到注解 regexp() 当中的正则表达式字符串的值
        //regexp = constraintAnnotation.regexp();


    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        //这个 value就是从表单传递的数值
        // 判断是否在注解填写的枚举值中
        System.out.println("表单传入的 value ~" + value);
        return set.contains(value);  // 判断该表单当中是否含有该 set()校验集合当中的内容。有返回 true,校验成功
        // 否 ，校验失败，返回失败信息。

        // 正则表达式判断
        //return value.toString().matches(regexp);
    }
}
