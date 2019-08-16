/**
 * FileName: MybatisConfig
 * Author:   xjh
 * Date:     2019-08-16 13:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.seagold.community.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 〈一句话功能简述〉<br> 
 * 〈Mybatis分页插件PageHepler配置〉
 *
 * @author xjh
 * @create 2019-08-16
 * @since 1.0.0
 */
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
//    @Bean
//    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
//        return new OptimisticLockerInterceptor();
}