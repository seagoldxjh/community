/**
 * FileName: GithubProvider
 * Author:   xjh
 * Date:     2019-08-15 12:33
 * Description: Github授权提供者
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.provider;

import com.alibaba.fastjson.JSON;
import com.seagold.community.dto.AccessTokenDTO;
import com.seagold.community.dto.GithubUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Github授权提供者〉
 *
 * @author xjh
 * @create 2019-08-15
 * @since 1.0.0
 */
@Component
public class GithubProvider {
    /**
     * 使用okhttp发送post请求到github授权地址
     * @param accessTokenDTO
     * @return accessToken
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            return split[0].split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }return null;
    }

    /**
     * 通过github返回的accessToken获取github用户信息
     * @param accessToken
     * @return
     */
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            return JSON.parseObject(string,GithubUser.class);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}