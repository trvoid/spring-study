package trvoid.bloomfilter;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RejectMapper {
    String SELECT = " SELECT FROM_PHONE_NO, TO_PHONE_NO FROM REJECT ";

    @Select(" SELECT COUNT(1) FROM ALL_OBJECTS " +
            " WHERE OBJECT_TYPE = 'TABLE' AND OBJECT_NAME = 'REJECT' ")
    int countTable();

    @Insert(" CREATE TABLE REJECT ( " +
            "    FROM_PHONE_NO VARCHAR2(20) NOT NULL, " +
            "    TO_PHONE_NO VARCHAR2(20) NOT NULL, " +
            "    CONSTRAINT REJECT_PK PRIMARY KEY (FROM_PHONE_NO, TO_PHONE_NO) " +
            " ) ")
    void createTable();

    @Insert(" INSERT INTO REJECT ( " +
            "    FROM_PHONE_NO, TO_PHONE_NO " +
            " ) VALUES ( " +
            "    #{reject.fromPhoneNo}, #{reject.toPhoneNo} " +
            " ) ")
    void insertReject(@Param("reject") Reject reject);

    @Select("SELECT COUNT(*) FROM REJECT WHERE FROM_PHONE_NO = #{fromPhoneNo} AND TO_PHONE_NO = #{toPhoneNo} ")
    int countReject(@Param("fromPhoneNo") String fromPhoneNo, @Param("toPhoneNo") String toPhoneNo);

    @Select(SELECT)
    List<Reject> findAll();
}
