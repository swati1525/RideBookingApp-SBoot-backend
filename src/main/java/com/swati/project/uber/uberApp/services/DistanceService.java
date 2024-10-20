package com.swati.project.uber.uberApp.services;

import org.locationtech.jts.geom.Point;

//we can use google maps matrix api but that is paid so We will use OSRM api
public interface DistanceService {

    double calculateDistance(Point src, Point dest);
}
