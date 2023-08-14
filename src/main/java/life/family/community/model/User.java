package life.family.community.model;

import lombok.Data;

/**
 * @Author:QiTao
 */
@Data
public class User {
    private Long id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
