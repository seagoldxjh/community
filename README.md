# 社区搭建
community of study
### [社区地址](http://129.211.52.132)

## github授权登陆模块
1. 用户点击登陆按钮，请求github提供的authorize接口，需要提供的参数
   - client_id: github生成的
   - redirect_uri: 自己设置的回调接口地址
   - state=1
   - scope=user
2. github自动跳转到我们设置的callback接口中，并且携带参数code
3. 我们继续调用github提供的access_token接口，需要提供参数，将数据穿的的参数封装为AccessTokenDTO对象
   - Client_id: github生成的
   - Client_secret: github生成的
   - Code: github: 回调提供的参数
   - Redirect_uri: 自己设置的回调接口地址
   - State: 1
   注意请求url: https://github.com/login/oauth/access_token   post方式
4. github响应请求返回access_token
5. 利用access_token请求github提供的user接口
   https://api.github.com/user?access_token=xxxxx   Get方式
6. 返回Git用户，存入数据，更新登陆状态

## Redis实现用户三天免登陆
1. 用户登陆成功后,利用UUID生成token,将token放入cookie中并设置3天有效期
```java
String token = UUID.randomUUID().toString();
Cookie cookie = new Cookie("token", token);
cookie.setMaxAge(60*60*24*3);
response.addCookie(cookie);
```
2. 将用户信息存入redis中，设置有效时间为3天
```java
redisTemplate.opsForValue().set(user.getToken(), user,60*60*24*3, TimeUnit.SECONDS);
```
3. 编写拦截器,查看本地是否有token,若存在,则从redis中查询出用户信息存入session,若无则返回登陆页面
```java
Cookie[] cookies = request.getCookies();
if (cookies == null){
   return true;
}
for (Cookie cookie : cookies) {
   if("token".equals(cookie.getName())){
       String token = cookie.getValue();
       Object o = redisTemplate.opsForValue().get(token);
       System.out.println(o);
       if(o != null){
           request.getSession().setAttribute("user", o);
       }
       break;
   }
}
```

## 社区帖子的发布,修改,分页展示模块
1. 集成PageHelper分页插件,并配置bean,采用MybatisPlus为Mybatis做增强操作，减少项目大量SQL
```java
@Configuration
public class MybatisConfig {
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        //会将 RowBounds 中的 offset 参数当成 pageNum 使用，可以用页码和页面大小两个参数进行分页。
        p.setProperty("offsetAsPageNum","true");
        //使用 RowBounds 分页会进行 count 查询。
        p.setProperty("rowBoundsWithCount","true");
        //分页合理化参数，默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页。
        p.setProperty("reasonable","true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
    //悲观锁插件
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
}
```
2. 使用thymeleaf+Bootstrap+Jquery解析数据解析美化
所用文档链接:
- [MyBatisPlus文档]https://mp.baomidou.com/
- [Bootstrap文档]https://v3.bootcss.com/css/
- [SpringBoot文档]https://spring.io/


## 多级回复及评论观看数功能模块
1. 数据表设计采用单表设计
   - 通过type属性指定评论为问题评论还是评论回复
   - 通过parent_id在单表中指定级联关系
2. 问题显示页面
   - 首次进入问题页面应保证效率,仅展示一级评论,即问题的回复
   - 每条评论显示有点赞数及评论数,用户可点击评论查看二级评论,并显示出二级评论窗口
   
## Redis中tag标签库完善
1. 可以将标签全部放入Redis数据库中
2. 不允许提交问题时输入非法标签
3. 焦点移动到标签时,弹出标签库，用户选择符合自己的标签进行添加

## 通知回复功能模块
1. 采用RabbitMQ如何实现此功能？
   - 每条评论创建时,将通知纳入Queue中,交换器及队列如何设计,如何保证每个用户都能获取到自己的通知
   - 使用@RabbitListener获取队列中的数据
2. createNotification
   - 每当有一次评论请求成功时,同时创建一条通知,保存parrent_id问题的id,设置状态为未读,在通知页面显示评论内容及评论人
   - 使用Spring事务进行管理,保证评论与通知的原子性
3. 通知条数及内容展示
   - 使用拦截器查询当前用户在数据库中通知的未读条数,放入Session中
   - 用户请求通知列表,生成
      User评论你的（问题 or 评论）
     用户请求某条通知时，转发请求到Question页面并将该通知状态置为已读
     
## 集成富文本编辑器（支持Markdown）
1. [开源在线 Markdown 编辑器](https://pandao.github.io/editor.md/)
2. 引入依赖,添加可嵌入的 Markdown 在线编辑器（组件）
```javascript
<link rel="stylesheet" href="editormd/css/editormd.css" />
<div id="test-editor">
    <textarea style="display:none;">### 关于 Editor.md

**Editor.md** 是一款开源的、可嵌入的 Markdown 在线编辑器（组件），基于 CodeMirror、jQuery 和 Marked 构建。
    </textarea>
</div>
<script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="editormd/editormd.min.js"></script>
<script type="text/javascript">
    $(function() {
        var editor = editormd("test-editor", {
            // width  : "100%",
            // height : "100%",
            path   : "editormd/lib/"
        });
    });
</script>

<script type="text/javascript">
   $(function () {
       var editor = editormd("question-editor", {
           width: "100%",
           height: 350,
           path: "/js/lib/",
           delay: 0,
           watch: false,
           placeholder: "请输入问题描述",
           imageUpload: true,
           imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
           imageUploadURL: "/file/upload",
       });
   });
</script>
```
3. MarkDown To Html
```javascript
<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="question-view">
    <textarea style="display:none;" th:text="${question.description}"></textarea>
</div>
<script type="text/javascript">
    $(function () {
        editormd.markdownToHTML("question-view", {});
    });
</script>
```

## 环境切换
1. Git Oauth地址切换
2. login redirect_uri地址切换
3. application.properties
   - github.redirect.uri地址切换
   - http://localhost:8080/callback

