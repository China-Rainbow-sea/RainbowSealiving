package com.rainbowsea.rainbowsealiving.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.rainbowsea.common.valid.EnumValidate;
import com.rainbowsea.common.valid.SaveGroup;
import com.rainbowsea.common.valid.UpdateGroup;
import com.rainbowsea.common.valid.UpdateIsShowGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

/**
 * 家居品牌
 *
 * @author rainbowsea
 * @email rainbowsea@gmail.com
 * @date 2025-03-08 20:11:04
 */
@Data
@TableName("commodity_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 2L;

    /**
     * id
     *
     * @NotNull(message = "修改要求指定 id ",groups = {UpdateGroup.class})
     * 表示 @NotNull 在 UpdateGroup.class 组中才会生效
     * @Null(message = "添加不能指定 id",groups = {SaveGroup.class})
     * 表示 @Null 在SaveGroup 校验组生效
     */
    @TableId
    @NotNull(message = "修改要求指定 id ", groups = {UpdateGroup.class, UpdateIsShowGroup.class})
    @Null(message = "添加不能指定 id", groups = {SaveGroup.class})
    private Long id;
    /**
     * 品牌名
     * 1. @NotBlank 表示 name 必须包括一个非空字符
     * 2. message = "品牌名不能为空" 是老师指定一个校验消息
     * 3. 如果没有指定 message = "品牌名不能为空"，就会返回默认的校验信息 key = javax.validation.constion
     * 4. 这个默认的消息是在 ValidationMessages_zh_CN.properties 文件配置javax.validation.constran
     * 5. @NotBlank 可以用于 CharSequence
     * groups = {SaveGroup.class,UpdateGroup.class 就是@NotBlank 在 SaveGroup 和 UpdateGroup 都生成
     */

    @NotBlank(message = "品牌名不能为空", groups = {SaveGroup.class, UpdateGroup.class})
    private String name;
    /**
     * logo 因为这个 Logo 对应的是图片的 url
     * 1.@NotBlank 和 @URL 组合去校验 logo
     */
    @NotBlank(message = "logo 不能为空", groups = {SaveGroup.class})
    @URL(message = "logo 不是一个合法的 URL ", groups = {SaveGroup.class, UpdateGroup.class})
    private String logo;
    /**
     * 说明
     */
    private String description;
    /**
     * 显示，isshow 后面的 s 是小写
     * 1. 这里我们使用的注解是(@NotNull，他可以接收任意类型)
     * 2. 如果这里使用 @NotBlank，会报错，因为 @NotBlank 不支持 Integer
     * 3. 大家在开发时，需要知道注解可以用在哪些类型上，可以查看注解源码
     * 4. 说明: 假如 isshow 规定时 0 / 1,这时我们后面通过自定义约束来解决
     * 5. 如果是 String 类型，可以直接使用@Pattern 来进一步校验。
     */
    @NotNull(message = "显示状态不能为空", groups = {SaveGroup.class, UpdateGroup.class,UpdateIsShowGroup.class})
    @EnumValidate(values = {0,1},message = "显示状态的值需要0或者1~",groups = {SaveGroup.class,UpdateGroup.class})
    //@EnumValidate(regexp = "^[0-1]$",message = "显示状态的值需要0或者1~",groups = {SaveGroup.class,UpdateGroup.class})
    private Integer isshow;
    /**
     * 检索首字母 a-z, A-Z
     * 因为 firstLetter 是 String 可以直接使用 @Pattern
     */

    @NotBlank(message = "检索首字母不能为空", groups = {SaveGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是 a-z 或者 A-Z", groups = {SaveGroup.class, UpdateGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(message = "排序值不能为空", groups = {SaveGroup.class})
    @Min(value = 0, message = "排序值要求大于等于 0 ", groups = {SaveGroup.class, UpdateGroup.class})
    private Integer sort;

}
