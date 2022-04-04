package plus.bookshelf.Dao.Mapper;

import org.springframework.stereotype.Repository;
import plus.bookshelf.Dao.DO.ThirdPartyUserAuthDO;

@Repository // 添加这个注解，Autowired的时候idea就不会报错了
public interface ThirdPartyUserAuthDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table third_party_user_auth_relation
     *
     * @mbg.generated
     */
    int insert(ThirdPartyUserAuthDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table third_party_user_auth_relation
     *
     * @mbg.generated
     */
    int insertSelective(ThirdPartyUserAuthDO record);
}