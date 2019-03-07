package com.watch.dao;

import com.watch.pojo.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface PlaceDAO extends JpaRepository<Place,Integer> {

}
