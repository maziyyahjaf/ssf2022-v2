package com.example.ssf2022.ssf2022_v2.repo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.ssf2022.ssf2022_v2.constant.Constant;

@Repository
public class MapRepo {

    @Qualifier(Constant.template01)
    private RedisTemplate<String, Object> template;

    public MapRepo(@Qualifier(Constant.template01) RedisTemplate<String, Object> template) {
		this.template = template;
	}

    public Boolean hasData(String redisKey) {
        return template.hasKey(redisKey);
    }
    
    public void add(String redisKey, String hashKey, String hashValue) {
        template.opsForHash().put(redisKey, hashKey, hashValue);
    }

     public Map<Object, Object> getAllEntries(String redisKey) {
        return template.opsForHash().entries(redisKey);
    }

    public Object getSavedArticleById(String redisKey, String hashKey) {
        return template.opsForHash().get(redisKey, hashKey);
    }

    public Long numOfSavedArticles(String redisKey) {
        return template.opsForHash().size(redisKey);
    }

    public Boolean deleteArticle(String redisKey, String hashKey) {

        if (template.opsForHash().delete(redisKey, hashKey) != 0) {
            return true;
        }
        return false;
    }


}
