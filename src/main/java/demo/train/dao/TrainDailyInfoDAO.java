package demo.train.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.train.bean.TrainDailyInfo;

@Repository
public interface TrainDailyInfoDAO
		extends JpaRepository<TrainDailyInfo, Integer>, JpaSpecificationExecutor<TrainDailyInfo> {

//	@Query(value = "SELECT count(trainDate) from traindailyinfo where traindate = ?1", nativeQuery = true)
	public int countTrainDateByTrainDate(String date);

//	@Query(value = "FROM TrainDailyInfo where trainDate = ?1")
	public List<TrainDailyInfo> findByTrainDate(String trainDate);
	
}
