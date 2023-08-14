package life.family.community.mapper;

import life.family.community.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author:QiTao
 */
@Mapper
public interface CommentMapper {
    @Insert("insert into comment (parent_id, type, commentator, gmt_create, gmt_modified, like_count, content, comment_count) values (#{parentId}, #{type}, #{commentator}, #{gmtCreate}, #{gmtModified}, #{likeCount}, #{content}, #{commentCount})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{parentId}")
    Comment getById(@Param(value = "parentId") Long parentId);

    @Select("select * from comment where parent_id = #{id} and type = #{type} order by gmt_create DESC")
    List<Comment> getByQuestionId(@Param(value = "id")Long id, @Param(value = "type")int type);

    @Update("update comment set comment_count = #{commentCount} where id = #{id}")
    void updateCommentCount(@Param(value = "commentCount")Integer commentCount, @Param(value = "id")Long id);
}
