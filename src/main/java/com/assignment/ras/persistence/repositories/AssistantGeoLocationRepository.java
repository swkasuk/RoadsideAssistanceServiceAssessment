package com.assignment.ras.persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.assignment.ras.persistence.entity.AssistantGeoLocationEntity;

public interface AssistantGeoLocationRepository extends JpaRepository<AssistantGeoLocationEntity, Integer> {

	public List<AssistantGeoLocationEntity> findByLocGeohashTxt(String geoHashValue);

	public List<AssistantGeoLocationEntity> findByActvAndAvailAsstAndByGeohash(String geoHashValue);

}
