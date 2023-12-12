package com.example.filehandling.repository;

import com.example.filehandling.entity.Cricketer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDataRepository extends JpaRepository<Cricketer,Long> {

}
