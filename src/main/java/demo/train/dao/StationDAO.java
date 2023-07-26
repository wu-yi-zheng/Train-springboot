package demo.train.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.train.bean.Station;

@Repository
public interface StationDAO extends JpaRepository<Station, String>, JpaSpecificationExecutor<Station>{
	public Station findByStationId(String stationId);
	
	@Query(value="SELECT stationName FROM stations where stationId = ?1", nativeQuery = true)
	public String findStationNameByStationId(String stationId);
}
