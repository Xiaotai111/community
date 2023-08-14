package life.family.community.service;

import life.family.community.dto.NotificationDTO;
import life.family.community.dto.PaginationDTO;
import life.family.community.dto.QuestionDTO;
import life.family.community.enums.NotificationStatusEnum;
import life.family.community.enums.NotificationTypeEnum;
import life.family.community.exception.CustomizeErrorCode;
import life.family.community.exception.CustomizeException;
import life.family.community.mapper.NotificationMapper;
import life.family.community.mapper.UserMapper;
import life.family.community.model.Notification;
import life.family.community.model.Question;
import life.family.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author:QiTao
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
        Integer totalCount = notificationMapper.countByReceiver(userId);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage&&totalPage!=0) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);
        Integer offset = size * (page - 1);
        List<Notification> notifications = notificationMapper.listByReceiver(userId, offset, size);
        if(notifications.size() == 0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Integer unreadCount(Long userId) {
        return notificationMapper.countByStatus(userId);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectById(id);
        if(notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!notification.getReceiver().equals(user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.update(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
