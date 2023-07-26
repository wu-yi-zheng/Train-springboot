package demo.train.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.train.bean.StopTime;
import jakarta.transaction.Transactional;

@Repository
public interface StopTimeDAO extends JpaRepository<StopTime, Integer>, JpaSpecificationExecutor<StopTime>{
	
	public List<StopTime> findByTrainDailyId(int trainDailyId);
	
//	@Query(value="FROM StopTime where trainDailyId = ?1 and stationId = ?2")
	public StopTime findByTrainDailyIdAndStationId(int trainDailyId, String stationId);
}
