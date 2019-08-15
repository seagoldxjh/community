# 社区搭建
community of study

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
