package life.family.community.dto;

import lombok.Data;

/**
 * @Author:QiTao
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer offset;
    private Integer size;
}
