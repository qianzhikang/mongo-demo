package com.qzk.mongo.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.qzk.mongo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description mongodb dao
 * @Date 2023-07-06-11-03
 * @Author qianzhikang
 */
@Component
public class PersonDao {
    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 插入文档
     */
    public void savePerson(Person person) {
        // 默认不传递 collectionName 将会使用参数的类名首字母小写作为collection的名字
        this.mongoTemplate.save(person);
    }

    /**
     * 批量插入
     */
    public void saveBatchPerson(List<Person> list) {
        this.mongoTemplate.insertAll(list);
    }

    /**
     * 删除
     */
    public DeleteResult deleteById(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return this.mongoTemplate.remove(query, Person.class);
    }


    /**
     * 修改
     */
    public UpdateResult updateAge(Person person) {
        Query query = Query.query(Criteria.where("id").is(person.getId()));
        Update update = Update.update("age", person.getAge());
        return this.mongoTemplate.updateFirst(query, update, Person.class);
    }


    /**
     * 按名字查询Person
     */
    public List<Person> queryPersonListByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return this.mongoTemplate.find(query, Person.class);
    }


    /**
     * 分页查询
     */
    public List<Person> queryPersonListPage(Integer page, Integer rows) {
        Query query = new Query().limit(rows).skip((page - 1) * rows);
        return this.mongoTemplate.find(query, Person.class);
    }



}
