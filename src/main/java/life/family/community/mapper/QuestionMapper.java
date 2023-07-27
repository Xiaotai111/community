package life.family.community.mapper;

import life.family.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title, description, gmt_create,gmt_modified,creator,tag) values (#{title},#{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset}, #{size}")
    List<Question> listByUserId(@Param(value = "userId")Long userId, @Param(value = "offset") Integer offset,@Param(value = "size")Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param(value = "userId") Long userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param(value = "id")Long id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag} where id = #{id}")
    void update(Question question);

    @Update("update question set view_count = #{viewCount} where id = #{id}")
    void updateView(@Param(value = "viewCount")Integer viewCount, @Param(value = "id")Long id);

    @Update("update question set comment_count = #{commentCount} where id = #{id}")
    void updateCommentCount(@Param(value = "commentCount")Integer commentCount, @Param(value = "id")Long id);
}
