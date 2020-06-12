package com.serviceorder.repositories;


import com.serviceorder.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Query(value = "select * from orders o join users u on o.user_id = u.user_id where u.username = :username and o.status = 1 or o.status =2", nativeQuery = true)
    List<Orders> findOrdersByUserName(@Param("username") String username);
}
