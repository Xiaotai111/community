package life.family.community.mapper;

import life.family.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (notifier, receiver, outerId, type, gmt_create, status, notifier_name, outer_title) values (#{notifier}, #{receiver}, #{outerId}, #{type}, #{gmtCreate}, #{status}, #{notifierName}, #{outerTitle})")
    void insert(Notification notification);

    @Select("select count(1) from notification where receiver = #{id}")
    Integer countByReceiver(@Param(value = "id") Long id);

    @Select("select * from notification where receiver = #{id} order by gmt_create DESC limit #{offset}, #{size}")
    List<Notification> listByReceiver(@Param(value = "id")Long id, @Param(value = "offset")Integer offset, @Param(value = "size")Integer size);

    @Select("select * from notification where id = #{id}")
    Notification selectById(@Param(value = "id") Long id);

    @Update("UPDATE notification SET notifier = #{notifier}, receiver = #{receiver}, outerId = #{outerId}, " +
            "type = #{type}, gmt_create = #{gmtCreate}, status = #{status}, " +
            "notifier_name = #{notifierName}, outer_title = #{outerTitle} WHERE id = #{id}")
    void update(Notification notification);

    @Select("select count(1) from notification where receiver = #{id} and status = 0")
    Integer countByStatus(@Param(value = "id") Long id);
}
