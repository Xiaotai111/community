package life.family.community.model;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;
}
