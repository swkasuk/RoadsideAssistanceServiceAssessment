package com.assignment.ras.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ch.hsr.geohash.GeoHash;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

@Component

public class GeoHashUtil {

	private static Logger logger = LoggerFactory.getLogger(GeoHashUtil.class);

	/**
	 * This method computes the geohash for the given latitude,longitude and a
	 * precision
	 * 
	 * @param latitude
	 * @param longitude
	 * @param precision
	 * @return
	 */
	public static String geoHashCalculate(Double latitude, Double longitude, int precision) {
		GeoHash geohash = GeoHash.withCharacterPrecision(latitude, longitude, precision);
		String geoHashString = geohash.toBase32();
		//System.out.println("geoHashString   " +geoHashString);

		return geoHashString;
	}

	/**
	 * This method returns the distance between two coordinates.
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static double distanceBetweenTwoCoordinates(Double lat1, Double lon1, Double lat2, Double lon2) {
		GeodesicData g = Geodesic.WGS84.Inverse(lat1, lon1, lat2, lon2);
		logger.debug("Distance in Geodesic : {}" + g.s12);
		return g.s12;
	}
	


}
