package life.family.community.mapper;

import life.family.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment (parent_id, type, commentator, gmt_create, gmt_modified, like_count, content) values (#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModified}, #{likeCount}, #{content})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{parentId}")
    Comment getById(Long parentId);
}
