package life.family.community.controller;

import life.family.community.dto.NotificationDTO;
import life.family.community.dto.PaginationDTO;
import life.family.community.enums.NotificationTypeEnum;
import life.family.community.model.User;
import life.family.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:QiTao
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request, @PathVariable(name = "id") Long id) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);
        if(NotificationTypeEnum.REPLY_COMMIT.getType() == notificationDTO.getType()
            ||NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }
        else{
            return "redirect:/";
        }
    }
}
