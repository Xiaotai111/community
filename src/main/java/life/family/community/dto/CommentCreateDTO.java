package life.family.community.dto;

import lombok.Data;

/**
 * @Author:QiTao
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
