import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.qzk.mongo.Application;
import com.qzk.mongo.dao.PersonDao;
import com.qzk.mongo.entity.Address;
import com.qzk.mongo.entity.Person;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 测试
 * @Date 2023-07-06-11-25
 * @Author qianzhikang
 */
@SpringBootTest(classes = Application.class)
public class MongoTest {

    @Resource
    private PersonDao personDao;

    @Test
    void insert(){
        Person person = new Person(ObjectId.get(), "张三", 20,
                new Address("人民路", "上海市", "666666"));
        personDao.savePerson(person);
    }

    @Test
    void batchInsert(){
        Person person1 = new Person(ObjectId.get(), "李四", 21,
                new Address("人民路", "上海市", "666666"));
        Person person2 = new Person(ObjectId.get(), "王五", 22,
                new Address("人民路", "上海市", "666666"));
        List<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);
        personDao.saveBatchPerson(people);
    }

    @Test
    void del(){
        DeleteResult deleteResult = personDao.deleteById("64a6383e97f1f72050421e44");
        boolean b = deleteResult.wasAcknowledged();
        System.out.println(b);
    }


    @Test
    void update(){
        Person person = new Person();
        person.setId(new ObjectId("64a6383e97f1f72050421e43"));
        person.setAge(10);
        UpdateResult update = personDao.updateAge(person);
        long modifiedCount = update.getModifiedCount();
        System.out.println("修改行数：" + modifiedCount);
    }

    @Test
    void query(){
        List<Person> personList = this.personDao.queryPersonListByName("张三");
        for (Person person : personList) {
            System.out.println(person);
        }
    }


    @Test
    void queryPage(){
        List<Person> personList = this.personDao.queryPersonListPage(1, 2);
        for (Person person : personList) {
            System.out.println(person);
        }
    }

}
