package demo.train.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Table(name = "trainDailyInfo")
public class TrainDailyInfo {
	@Id
	@Column(name = "trainDailyId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int trainDailyId;
	@Column(name = "trainDate")
	private String trainDate;
	@Column(name = "trainNo")
	private String trainNo;
	@Column(name = "startStationId")
	private String startStationId;
	@Column(name = "endStationId")
	private String endStationId;
	@Column(name = "trainTypeName")
	private String trainTypeName;
	@Column(name = "direction")
	private int direction;
	@Column(name = "tripLine")
	private int tripLine;
	@Column(name = "wheelchairFlag")
	private int wheelchairFlag;
	@Column(name = "packageServiceFlag")
	private int packageServiceFlag;
	@Column(name = "diningFlag")
	private int diningFlag;
	@Column(name = "bikeFlag")
	private int bikeFlag;
	@Column(name = "breastFeedingFlag")
	private int breastFeedingFlag;
	@Column(name = "dailyFlag")
	private int dailyFlag;
	@Column(name = "serviceAddedFlag")
	private int serviceAddedFlag;
	@Column(name = "note")
	private String note;
	
	@Transient
	private List<StopTime> stopTimes;
	
	public TrainDailyInfo() {
		
	}
	
	public TrainDailyInfo(JSONObject jsonObject) {
        JSONObject jsonDailyInfo = jsonObject.getJSONObject("DailyTrainInfo");
        JSONArray stopTimesArr = jsonObject.getJSONArray("StopTimes");
        String trainDate = jsonObject.getString("TrainDate");
        List<StopTime> stopTimesList = new ArrayList<>();

        for (int i = 1; i <= stopTimesArr.length(); i++) {
            StopTime stoptimes = new StopTime();
            JSONObject stopTimeDetail = stopTimesArr.getJSONObject(i - 1);
            stoptimes.setStopSequence(i);
            stoptimes.setStationId(stopTimeDetail.getString("StationID"));
            stoptimes.setArrivalTime(getSQLFormatTime(trainDate, stopTimeDetail.getString("ArrivalTime")));
            stoptimes.setDepartureTime(getSQLFormatTime(trainDate, stopTimeDetail.getString("DepartureTime")));
            stopTimesList.add(stoptimes);
        }
        this.trainDate = trainDate;
        this.trainNo = jsonDailyInfo.getString("TrainNo");
        this.startStationId = jsonDailyInfo.getString("StartingStationID");
        this.endStationId = jsonDailyInfo.getString("EndingStationID");
        this.trainTypeName = jsonDailyInfo.getJSONObject("TrainTypeName").getString("Zh_tw");
        this.direction = jsonDailyInfo.getInt("Direction");
        this.tripLine = jsonDailyInfo.getInt("TripLine");
        this.wheelchairFlag = jsonDailyInfo.getInt("WheelchairFlag");
        this.packageServiceFlag = jsonDailyInfo.getInt("PackageServiceFlag");
        this.diningFlag = jsonDailyInfo.getInt("DiningFlag");
        this.bikeFlag = jsonDailyInfo.getInt("BikeFlag");
        this.breastFeedingFlag = jsonDailyInfo.getInt("BreastFeedingFlag");
        this.dailyFlag = jsonDailyInfo.getInt("DailyFlag");
        this.serviceAddedFlag = jsonDailyInfo.getInt("ServiceAddedFlag");
        this.note = jsonDailyInfo.getJSONObject("Note").getString("Zh_tw");
        this.stopTimes = stopTimesList;
	}
	
    private Date getSQLFormatTime(String date, String time) {
        String stoptime = "";
        stoptime = date + " " + time;
        Date sqltime = null;
        try {
        	sqltime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(stoptime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return sqltime;
    }
    
	public int getTrainDailyId() {
		return trainDailyId;
	}

	public void setTrainDailyId(int trainDailyId) {
		this.trainDailyId = trainDailyId;
	}

	public String getTrainDate() {
		return trainDate;
	}

	public void setTrainDate(String trainDate) {
		this.trainDate = trainDate;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getStartStationId() {
		return startStationId;
	}

	public void setStartStationId(String startStationId) {
		this.startStationId = startStationId;
	}

	public String getEndStationId() {
		return endStationId;
	}

	public void setEndStationId(String endStationId) {
		this.endStationId = endStationId;
	}

	public String getTrainTypeName() {
		return trainTypeName;
	}

	public void setTrainTypeName(String trainTypeName) {
		this.trainTypeName = trainTypeName;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getTripLine() {
		return tripLine;
	}

	public void setTripLine(int tripLine) {
		this.tripLine = tripLine;
	}

	public int getWheelchairFlag() {
		return wheelchairFlag;
	}

	public void setWheelchairFlag(int wheelchairFlag) {
		this.wheelchairFlag = wheelchairFlag;
	}

	public int getPackageServiceFlag() {
		return packageServiceFlag;
	}

	public void setPackageServiceFlag(int packageServiceFlag) {
		this.packageServiceFlag = packageServiceFlag;
	}

	public int getDiningFlag() {
		return diningFlag;
	}

	public void setDiningFlag(int diningFlag) {
		this.diningFlag = diningFlag;
	}

	public int getBikeFlag() {
		return bikeFlag;
	}

	public void setBikeFlag(int bikeFlag) {
		this.bikeFlag = bikeFlag;
	}

	public int getBreastFeedingFlag() {
		return breastFeedingFlag;
	}

	public void setBreastFeedingFlag(int breastFeedingFlag) {
		this.breastFeedingFlag = breastFeedingFlag;
	}

	public int getDailyFlag() {
		return dailyFlag;
	}

	public void setDailyFlag(int dailyFlag) {
		this.dailyFlag = dailyFlag;
	}

	public int getServiceAddedFlag() {
		return serviceAddedFlag;
	}

	public void setServiceAddedFlag(int serviceAddedFlag) {
		this.serviceAddedFlag = serviceAddedFlag;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<StopTime> getStopTime() {
		return stopTimes;
	}

	public void setStopTime(List<StopTime> stopTimes) {
		this.stopTimes = stopTimes;
	}
}
