package com.liuao.game_card_sell.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuao.game_card_sell.annotation.RelativeOrAbsoluteURL;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateCartridgeRequest {
    @NotEmpty(message = "类别id列表不能为空")
    private List<Long> categoryIds;

    @NotEmpty(message = "平台ID列表不能为空")
    private List<Long> platformIds;

    @NotBlank(message = "封面图片URL不能为空")
    @RelativeOrAbsoluteURL(message = "必须是有效的URL或相对路径（如 /images/xxx.jpg）")
    private String coverImageUrl;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.0", inclusive = true, message = "价格不能为负数")
    private Double price;

    @PastOrPresent(message = "发行日期不能晚于当前日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate releaseDate;

    @NotNull(message = "评分不能为空")
    @Min(value = 0, message = "评分最低为0")
    @Max(value = 100, message = "评分最高为100")
    private Double score;

    @NotBlank(message = "标题不能为空")
    @Size(max = 100, message = "标题长度不能超过100个字符")
    private String title;
}
