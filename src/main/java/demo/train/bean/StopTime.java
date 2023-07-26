package demo.train.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;


@Entity
@Table(name = "stoptimes2")
public class StopTime {
	@Id
	@Column(name = "stoptime_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stoptime_id;
	
	@Column(name = "stopSequence")
	private int stopSequence;
	@Column(name = "stationId")
	private String stationId;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "arrivalTime")
	private Date arrivalTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "departureTime")
	private Date departureTime;
	@Column(name = "trainDailyId")
	private int trainDailyId;

	public int getStopSequence() {
		return stopSequence;
	}

	public void setStopSequence(int stopSequence) {
		this.stopSequence = stopSequence;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public int getTrainDailyId() {
		return trainDailyId;
	}

	public void setTrainDailyId(int trainDailyId) {
		this.trainDailyId = trainDailyId;
	}
}
