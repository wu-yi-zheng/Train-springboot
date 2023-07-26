package demo.train.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stations")
public class Station {
	@Id
	@Column(name="stationId")
	private String stationId;
	@Column(name="stationName")
	private String stationName;
	@Column(name="stationEname")
	private String stationEname;
	
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationIdString) {
		this.stationId = stationIdString;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStationEname() {
		return stationEname;
	}
	public void setStationEname(String stationEname) {
		this.stationEname = stationEname;
	}
	
}
