package demo.train.pojo;

public class TrainInfo {
    private String trainDate;
    private int trainNo;
    private String departureTime;
    private String arrivalTime;
    private String travelTime;
    private String startingStationNameZhTw;
    private String ending_station_nameZhTw;
    private String trainTypenameZhTw;
    private int tripLine;
    private String departureStation;
    private String arrivalStation;
    private String station_name_zh_tw;
    private String startingStationId;
    private String endingStationId;

    @Override
    public String toString() {
        return "TrainInfo{" +
                "trainDate='" + trainDate + '\'' +
                ", trainNo=" + trainNo +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", travelTime='" + travelTime + '\'' +
                ", startingStationNameZhTw='" + startingStationNameZhTw + '\'' +
                ", ending_station_nameZhTw='" + ending_station_nameZhTw + '\'' +
                ", trainTypenameZhTw='" + trainTypenameZhTw + '\'' +
                ", tripLine='" + tripLine + '\'' +
                ", departureStation='" + departureStation + '\'' +
                ", arrivalStation='" + arrivalStation + '\'' +
                ", station_name_zh_tw='" + station_name_zh_tw + '\'' +
                '}';
    }
    
    
    public String getStartingStationId() {
		return startingStationId;
	}


	public void setStartingStationId(String startingStationId) {
		this.startingStationId = startingStationId;
	}


	public String getEndingStationId() {
		return endingStationId;
	}


	public void setEndingStationId(String endingStationId) {
		this.endingStationId = endingStationId;
	}


	public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public String getStation_name_zh_tw() {
        return station_name_zh_tw;
    }

    public void setStation_name_zh_tw(String station_name_zh_tw) {
        this.station_name_zh_tw = station_name_zh_tw;
    }

    public String getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(String trainDate) {
        this.trainDate = trainDate;
    }

    public int getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(int trainNo) {
        this.trainNo = trainNo;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getStartingStationNameZhTw() {
        return startingStationNameZhTw;
    }

    public void setStartingStationNameZhTw(String startingStationNameZhTw) {
        this.startingStationNameZhTw = startingStationNameZhTw;
    }

    public String getEnding_station_nameZhTw() {
        return ending_station_nameZhTw;
    }

    public void setEnding_station_nameZhTw(String ending_station_nameZhTw) {
        this.ending_station_nameZhTw = ending_station_nameZhTw;
    }

    public String getTrainTypenameZhTw() {
        return trainTypenameZhTw;
    }

    public void setTrainTypenameZhTw(String trainTypenameZhTw) {
        this.trainTypenameZhTw = trainTypenameZhTw;
    }

    public int getTripLine() {
        return tripLine;
    }

    public void setTripLine(int tripLine) {
        this.tripLine = tripLine;
    }
}
