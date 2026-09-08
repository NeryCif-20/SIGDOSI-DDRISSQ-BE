package com.ddrissq.sigdosi.common.mapper.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Named;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeometryTransformer {

    @Named("toPoint")
    public static Point toPoint(Geometry geometry) {
        return (Point) geometry;
    }

}
