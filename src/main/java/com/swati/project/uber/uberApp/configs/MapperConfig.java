package com.swati.project.uber.uberApp.configs;

import com.swati.project.uber.uberApp.dto.PointDTO;
import com.swati.project.uber.uberApp.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MapperConfig {

    /**
     * MapperConfig is really useful to convert one entity to another entity but sometimes
     * it can't convert somethings let's say the types are different and it can't figure
     * out how do you want to convert one thing to other so we have to specify it like we did below
     */


    @Bean
    public ModelMapper getModelMapper()
    {
        ModelMapper mapper = new ModelMapper();

        //way to convert pointDTO to point class
        mapper.typeMap(PointDTO.class, Point.class).setConverter(context ->{
            PointDTO pointDTO = context.getSource();
            return GeometryUtil.createPoint(pointDTO);
        });

        //way to convert Point to pointDTO back
        mapper.typeMap(Point.class, PointDTO.class).setConverter(context ->{
            Point point = context.getSource();
            double coordinates[] = {
                    point.getX(),
                    point.getY()
            };
            return new PointDTO(coordinates);
        });
        return mapper;
    }
}
