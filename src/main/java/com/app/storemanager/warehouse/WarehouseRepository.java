package com.app.storemanager.warehouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {

    @Query("select t from Warehouse t where t.lastVisit =" +
            "(select MAX(p.lastVisit) From Warehouse p) AND t.owner =:owner")
    List<Warehouse> selectLastVisitedWarehouse(@Param("owner") String owner);
    List<Warehouse> findByOwner(String owner);
    List<Warehouse> findByOwnerMail(String mail);
}
