package com.ashwinbhatt.learnspring.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    /*
    * This Class extends CrudRepository. First argument in genertic of extends
    * refers to the data class which it represent, second argument is ID type of
    * the data class.
    * */

}
