package com.swati.project.uber.uberApp.repositories;

import com.swati.project.uber.uberApp.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// ST_Distance(point1, point2) - we have this method used to calculate the distance between the passed points
// ST_DWithin(point1, 10000) - this is basically a boolean method that can return true or false if certain condition is true i.e if distance between two points  is less than 10000metres than this condition will be true and then that row will be picked
@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
    @Query(value = "SELECT d.*, ST_Distance(d.current_location, :pickUpLocation) AS distance " +
            "FROM driver d " +
            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickUpLocation, 10000) " +
            "ORDER BY distance " +
            "LIMIT 10", nativeQuery = true)
    List<Driver> findTenNearestDrivers(Point pickUpLocation);

    @Query(value = "SELECT d.* "+
            "FROM driver d " +
            "WHERE d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 15000) "+
            "ORDER BY d.rating DESC "+
            "LIMIT 10", nativeQuery = true)
    List<Driver> findTenNearbyTopRatedDrivers(Point pickupLocation);
    //HW - WRITE QUERY FOR TOP 10 RATED DRIVERS THAT ARE NEAREST - 1:30:44(TIME)
}
