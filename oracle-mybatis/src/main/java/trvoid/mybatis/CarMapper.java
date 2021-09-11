package trvoid.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CarMapper {
    String SELECT = " SELECT ID, MODEL, MANUFACTURER FROM CAR ";

    @Select(" SELECT COUNT(1) FROM ALL_OBJECTS " +
            " WHERE OBJECT_TYPE = 'TABLE' AND OBJECT_NAME = 'CAR' ")
    int countTable();

    @Insert(" CREATE TABLE CAR ( " +
            "    ID NUMBER(10) NOT NULL, " +
            "    MODEL VARCHAR2(100) NOT NULL, " +
            "    MANUFACTURER VARCHAR2(100) NOT NULL, " +
            "    CONSTRAINT CAR_PK PRIMARY KEY (ID) " +
            " ) ")
    void createTable();

    @Insert(" INSERT INTO CAR ( " +
            "    ID, MODEL, MANUFACTURER " +
            " ) VALUES ( " +
            "    #{car.id}, #{car.model}, #{car.manufacturer} " +
            " ) ")
    void insertCar(@Param("car") Car car);

    @Select(SELECT + " WHERE ID = #{carId} ")
    Car getCar(@Param("carId") String carId);

    @Select(SELECT)
    List<Car> findAll();
}
