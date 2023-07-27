package life.family.community.model;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import lombok.Data;

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
