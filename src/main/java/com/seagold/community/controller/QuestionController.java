/**
 * FileName: QuestionController
 * Author:   xjh
 * Date:     2019-08-15 17:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagold.community.cache.HotTagCache;
import com.seagold.community.cache.TagCache;
import com.seagold.community.dto.CommentUserDTO;
import com.seagold.community.dto.QuestionDTO;
import com.seagold.community.entity.Question;
import com.seagold.community.entity.User;
import com.seagold.community.service.CommentService;
import com.seagold.community.service.QuestionService;
import com.seagold.community.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private HotTagCache hotTagCache;

    @Autowired
    private RedisService redisService;


    /**
     * post方式提交用户发起的问题
     * @param title
     * @param description
     * @param tag
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/publishQuestion")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotEmpty(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setHeadImg(user.getHeadImg());
        question.setId(id);
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionService.insertQuestion(question);
        }else {
            question.setGmtModified(question.getGmtCreate());
            questionService.updata(question);
        }
        return "redirect:/";
    }

    /**
     * 用户对自己提出过的问题进行编辑修改
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model, HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null){
            model.addAttribute("message","您还未登陆");
            return "error";
        }
        QuestionDTO questionDTO = questionService.findById(id);

        if (!questionDTO.getUser().getId().equals(user.getId())){
            model.addAttribute("message","这好像不是你的问题吧!");
            return "error";
        }

        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("id", questionDTO.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    /**
     * 发起过的所有问题数据分页返回数据
     * @param page
     * @param size
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String hello(@RequestParam(value = "page",defaultValue = "1") int page,
                        @RequestParam(value = "size",defaultValue = "10") int size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "tag", required = false) String tag,
                        @RequestParam(name = "sort", defaultValue = "") String sort,
                        Model model){
        PageHelper.startPage(page, size);
        //List<Question> all = questionService.findAllQuestion();
        List<Question> all = questionService.findAll(search,tag,sort);
        PageInfo<Question> pageInfo = new PageInfo<>(all);
        model.addAttribute("questions", pageInfo);
        model.addAttribute("tag", tag);
        model.addAttribute("tags",hotTagCache.getHots());
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        return "index";
    }


    /**
     * 用户搜索问题
     */
    @RequestMapping("/searchQuestions")
    public String searchQuestions(@RequestParam(value = "page",defaultValue = "1") int page,
                                  @RequestParam(value = "size",defaultValue = "10") int size,
                                  @RequestParam(value = "search") String search,
                                  Model model){
        PageHelper.startPage(page, size);
        List<Question> questions = questionService.searchQuestions(search);
        PageInfo<Question> pageInfo = new PageInfo<>(questions);
        model.addAttribute("questions", pageInfo);
        model.addAttribute("tags",hotTagCache.getHots());
        return "index";
    }

    @RequestMapping("/searchQuestionsByTag")
    public String searchQuestionsByTag(@RequestParam(value = "page",defaultValue = "1") int page,
                                       @RequestParam(value = "size",defaultValue = "10") int size,
                                       @RequestParam(value = "tag") String tag,
                                       Model model){
        PageHelper.startPage(page, size);
        List<Question> questions = questionService.searchQuestionsByTag(tag);
        PageInfo<Question> pageInfo = new PageInfo<>(questions);
        model.addAttribute("questions", pageInfo);
        model.addAttribute("tags",hotTagCache.getHots());
        return "index";
    }


    /**
     *
     * @param page
     * @param size
     * @param sort 七天最热 or 30天最热 or 最热
     * @param model
     * @return
     */
    @RequestMapping("/sort")
    public String searchQuestionsBySort(@RequestParam(value = "page",defaultValue = "1") int page,
                                       @RequestParam(value = "size",defaultValue = "10") int size,
                                       @RequestParam(value = "sort") String sort,
                                       Model model){
        System.out.println(sort);
        PageHelper.startPage(page, size);
        List<Question> questions = questionService.searchQuestionsBySort(sort);
        PageInfo<Question> pageInfo = new PageInfo<>(questions);
        model.addAttribute("questions", pageInfo);
        model.addAttribute("sort",sort);
        return "index";
    }

    /**
     * 根据id查询问题返回问题及发起问题的用户数据
     * 查询question中id=？的type=1的所有评论，表示评论为评论问题，而不是二级评论
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,Model model,HttpSession session){
        QuestionDTO question = questionService.findById(id);
        if (question == null){
            model.addAttribute("message","该问题不存在或已被删除");
            return "error";
        }
        model.addAttribute("question", question);


        List<CommentUserDTO> comments = commentService.findAllById(id, 1);

        /**
         * 用户如登陆，便查询该问题下所有评论的点赞关系
         */
        User user = (User)session.getAttribute("user");
        if (user != null && user.getId()!= null){
            for (CommentUserDTO commentUserDTO : comments){
                Integer likedStatus = redisService.getLikedStatus(String.valueOf(commentUserDTO.getId()), String.valueOf(user.getId()));
                commentUserDTO.setStatus(likedStatus);
            }
        }



        for (CommentUserDTO commentUserDTO : comments) {
            Integer likedCount = redisService.getLikedCount(String.valueOf(commentUserDTO.getId()));
            if (likedCount != null) {
                commentUserDTO.setLikedCount(likedCount);
            }
        }
        model.addAttribute("comments",comments);

        List<Question> relatedQuestions = questionService.selectRelated(question.getTag());
        model.addAttribute("relatedQuestions",relatedQuestions);

        return "question";
    }


}