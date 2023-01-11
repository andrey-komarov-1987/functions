package com.example.functions.repository;

import com.example.functions.domain.Thing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThingRepository extends JpaRepository<Thing, Integer> {

    @Query("SELECT t FROM Thing t WHERE t = FUNCTION('any_int', :ids)")
    List<Thing> jpaFindByIds(@Param("ids") String ids);

    @Query(value = "SELECT t.id, t.name FROM thing t WHERE t.id = ANY(string_to_array(:ids, ',')\\:\\:int[])", nativeQuery = true)
    List<Thing> nativeFindByIds(@Param("ids") String ids);
}
