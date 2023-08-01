package life.family.community.service;

import life.family.community.dto.CommentDTO;
import life.family.community.enums.CommentTypeEnum;
import life.family.community.exception.CustomizeErrorCode;
import life.family.community.exception.CustomizeException;
import life.family.community.mapper.CommentMapper;
import life.family.community.mapper.QuestionMapper;
import life.family.community.mapper.UserMapper;
import life.family.community.model.Comment;
import life.family.community.model.Question;
import life.family.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserMapper userMapper;
    //事务管理
    @Transactional
    public void insert(Comment comment) {

        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            Comment dbComment = commentMapper.getById(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            commentMapper.updateCommentCount(dbComment.getCommentCount()+1, dbComment.getId());
        }else{
            Question question = questionMapper.getById(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            comment.setCommentCount(0);
            commentMapper.insert(comment);
            questionService.incCommentCount(question.getId());
        }
    }

    public List<CommentDTO> listByTargetId(Long id, Integer type) {
        List<Comment> comments = commentMapper.getByQuestionId(id, type);
        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentators);
        List<User> users = new ArrayList<>();
        for (Long userId : userIds) {
            User user = userMapper.findById(userId);
            if (user != null) {
                users.add(user);
            }
        }
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
