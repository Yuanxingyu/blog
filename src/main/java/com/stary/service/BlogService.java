package com.stary.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.stary.dao.BlogDao;
import com.stary.model.po.BlogPo;
import com.stary.model.vo.BlogDesc;
import com.stary.model.vo.BlogView;
import com.stary.redis.RedisClusterClient;
import com.stary.redis.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BlogService {

    @Resource
    private BlogDao blogDao;
//    @Resource
//    private RedisClient redisClient;
    @Resource(name = "redisClusterClient")
    private RedisClusterClient redisClient;

    public boolean addBlog(BlogPo blogPo) {
        try {
            if (blogPo == null || StringUtils.isAnyBlank(
                    blogPo.getTitle(), blogPo.getRealContent(), blogPo.getShowContent())) {
                return false;
            }
            blogDao.addBlog(blogPo);
            //插入新博客后，删除博客介绍缓存，强制下次查询走库
            redisClient.delete(RedisKey.ALL_BLOG_DESC);
            return true;
        } catch (Exception e) {
            log.error("add blogPo error, blogPo:{}", JSON.toJSONString(blogPo), e);
            return false;
        }
    }

    public boolean modifyBlog(BlogPo blogPo) {
        try {
            if (blogPo == null || StringUtils.isAnyBlank(
                    blogPo.getTitle(), blogPo.getRealContent(), blogPo.getShowContent())) {
                return false;
            }
            blogDao.updateBlog(blogPo);
            //更新博客后，删除博客缓存，强制下次查询走库
            redisClient.delete(RedisKey.ALL_BLOG_DESC);
            redisClient.delete(RedisKey.getKey(RedisKey.BLOG_SHOW_PRE, String.valueOf(blogPo.getId())));
            return true;
        } catch (Exception e) {
            log.error("update blogPo error, blogPo:{}", JSON.toJSONString(blogPo), e);
            return false;
        }
    }

    public List<BlogDesc> queryAllBlogDesc() {
        try {
            String allBlogDescJson = redisClient.get(RedisKey.ALL_BLOG_DESC);
            if (StringUtils.isNotBlank(allBlogDescJson)) {
                return JSON.parseArray(allBlogDescJson, BlogDesc.class);
            }
            List<BlogDesc> blogDescList = Lists.transform(blogDao.queryAllBlog(), new Function<BlogPo, BlogDesc>() {
                @Override
                public BlogDesc apply(BlogPo input) {
                    return new BlogDesc(input);
                }
            });
            if (CollectionUtils.isNotEmpty(blogDescList)) {
                redisClient.setEx(RedisKey.ALL_BLOG_DESC, JSON.toJSONString(blogDescList), 60 * 60);
            }
            return blogDescList;
        } catch (Exception e) {
            log.error("query all blogPo error", e);
            return Collections.emptyList();
        }
    }

    public BlogView queryBlogShowById(int id) {
        try {
            String blogShowJson = redisClient.get(RedisKey.getKey(RedisKey.BLOG_SHOW_PRE, String.valueOf(id)));
            if (StringUtils.isNotBlank(blogShowJson)) {
                return JSON.parseObject(blogShowJson, BlogView.class);
            }
            BlogPo blogPo = blogDao.queryBlogById(id);
            BlogView blogView = null;
            if (blogPo != null) {
                blogView = new BlogView(blogPo);
                redisClient.setEx(
                        RedisKey.getKey(RedisKey.BLOG_SHOW_PRE, String.valueOf(id)),
                        JSON.toJSONString(blogView),
                        60 * 60);
            }
            return blogView;
        } catch (Exception e) {
            log.error("query blogPo by id error, id:{}", id, e);
            return null;
        }
    }

    public boolean delMultiBlogById(int[] ids) {
        try {
            if (ArrayUtils.isEmpty(ids)) {
                return true;
            }
            for (int id : ids) {
                redisClient.delete(RedisKey.getKey(RedisKey.BLOG_SHOW_PRE, String.valueOf(id)));
            }
            redisClient.delete(RedisKey.ALL_BLOG_DESC);
            blogDao.delBlogByIds(ids);
            return true;
        } catch (Exception e) {
            log.error("delete multi blog by id error, ids:{}", JSON.toJSONString(ids), e);
            return false;
        }
    }
}
