package life.family.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author:QiTao
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
