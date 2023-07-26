package demo.train.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.train.bean.Station;
import demo.train.bean.StopTime;
import demo.train.bean.TrainDailyInfo;
import demo.train.dao.StationDAO;
import demo.train.dao.StopTimeDAO;
import demo.train.dao.TrainDailyInfoDAO;
import demo.train.pojo.TrainInfo;
import demo.train.bean.*;

@Service
public class TrainService {
	@Autowired
	private TrainDailyInfoDAO trainDao;
	@Autowired
	private StopTimeDAO stopTimeDao;
	@Autowired
	private StationDAO stationDao;
	@Value("${urlApi}")
	String urlApi;
	@Value("${replaceUrlApi}")
	String replaceUrlApi;

	/**
	 * 取得車站名稱列表 用traininfo來裝載
	 * 
	 * @return ArrayList<TrainInfo>
	 */
	public ArrayList<TrainInfo> getStationNameList() {
		List<Station> stationList = stationDao.findAll();
		ArrayList<TrainInfo> stationNameList = new ArrayList<TrainInfo>();
		for (int i = 0; i < stationList.size(); i++) {
			String stationName = stationList.get(i).getStationName();
			String stationId = stationList.get(i).getStationId();
			String stationEname = stationList.get(i).getStationEname();
			TrainInfo trainInfo = new TrainInfo();
			trainInfo.setStartingStationId(stationId);
			trainInfo.setStation_name_zh_tw(stationName);
			trainInfo.setTrainDate(stationEname); // 借用traindate裝車站英文名
			stationNameList.add(trainInfo);
		}
		return stationNameList;
	}

	/**
	 * 查詢符合的資料
	 * 
	 * @param trainInfoForUser 出發站 抵達站 出發日期 出發時間
	 * @return
	 */
	public ArrayList<TrainInfo> getTrainInfo(TrainInfo trainInfoForUser) {
		ArrayList<TrainInfo> trainArr = new ArrayList<>();
		String trainDate = trainInfoForUser.getTrainDate();
		String departureTime = trainInfoForUser.getDepartureTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date userDepartureTime = sdf1.parse(trainDate + " " + departureTime);
			String departureId = trainInfoForUser.getDepartureStation();
			String departureName = stationDao.findStationNameByStationId(departureId);
			String arrivalId = trainInfoForUser.getArrivalStation();
			String arrivalName = stationDao.findStationNameByStationId(arrivalId);

			System.out.println("Service:" + trainInfoForUser.toString());
			List<TrainDailyInfo> trainList = trainDao.findByTrainDate(trainDate);
			System.out.println("trainList.size()=" + trainList.size());
			if (trainList.size() > 0) {
				for (int i = 0; i < trainList.size(); i++) {
					TrainDailyInfo trainDailyInfo = trainList.get(i);
					int trainDailyId = trainDailyInfo.getTrainDailyId();
					StopTime departureStation = stopTimeDao.findByTrainDailyIdAndStationId(trainDailyId, departureId);
					StopTime arrivalStation = stopTimeDao.findByTrainDailyIdAndStationId(trainDailyId, arrivalId);
					if (departureStation != null && arrivalStation != null) {
						Date time1 = departureStation.getDepartureTime();
						Date time2 = arrivalStation.getArrivalTime();
						if (userDepartureTime.before(time1) && time1.before(time2)) {
							String travelTime = getTrvelTime(time1, time2);
							TrainInfo trainInfo = new TrainInfo();
							trainInfo.setTrainDate(trainDate);
							trainInfo.setTrainNo(Integer.valueOf(trainDailyInfo.getTrainNo()));
							trainInfo.setDepartureTime(new SimpleDateFormat("HH:mm").format(time1));
							trainInfo.setArrivalTime(new SimpleDateFormat("HH:mm").format(time2));
							trainInfo.setTravelTime(travelTime);
							trainInfo.setStartingStationNameZhTw(departureName);
							trainInfo.setEnding_station_nameZhTw(arrivalName);
							trainInfo.setTrainTypenameZhTw(trainDailyInfo.getTrainTypeName());
							trainInfo.setTripLine(trainDailyInfo.getTripLine());
							trainArr.add(trainInfo);
						}
					}
				}
				sortTrainArr(trainArr);
				System.out.println("搜尋結果:" + trainArr.size());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return trainArr;
	}

	/**
	 * 將ArrayList<TrainInfo>trainArr依照出發時間做升序排序
	 * 
	 * @param departureTime arrivalTime
	 * @return 計算結果
	 */
	private void sortTrainArr(ArrayList<TrainInfo> trainArr) {
		Collections.sort(trainArr, new Comparator<TrainInfo>() {
			public int compare(TrainInfo o1, TrainInfo o2) {
				return o1.getDepartureTime().compareTo(o2.getDepartureTime());
			}
		});
	}

	/**
	 * 計算行駛時間
	 * 
	 * @param departureTime arrivalTime
	 * @return 計算結果
	 */
	private String getTrvelTime(Date departureTime, Date arrivalTime) {
		String travelTime = "";
		long Time_difference = arrivalTime.getTime() - departureTime.getTime();
		long hours = (Time_difference / (1000 * 60 * 60)) % 24;
		long minutes = (Time_difference / (1000 * 60)) % 60;
		if (hours > 0) {
			travelTime += hours + "小時";
		}
		if (minutes > 0) {
			travelTime += minutes + "分";
		}
		return travelTime;
	}

	/**
	 * 只檢查是否已有當日時刻表 無@return
	 */
	public void getTrainTimetable(String date) {
		if (urlApi != null || urlApi.contains("<date>")) {
			replaceUrlApi = urlApi.replace("<date>", date);
		} else {
			return;
		}
		JSONArray timetableArr = callTrainApi();
		if (timetableArr == null)
			return;
		insertData(timetableArr);
	}
	
	/**
	 * 先檢查是否已有當日時刻表
	 * 
	 * @return 資料是新增、已有還是新增失敗
	 */
	public String getTrainTimetable2(String date) {
		String result;
		if (urlApi != null || urlApi.contains("<date>")) {
			replaceUrlApi = urlApi.replace("<date>", date);
		} else {
			result = "連線失敗";
			return result;
		}
		if (checkDate(date)) {
			result = "已有資料";
		} else {
			JSONArray timetableArr = callTrainApi();
			if (timetableArr == null) {
				result = "取得失敗";
				return result;
			}
			result = insertData(timetableArr);
		}
		return result;
	}

	/**
	 * call 台鐵API
	 *
	 * @return JSONArray timetableArr
	 */
	private JSONArray callTrainApi() {
		BufferedReader bufferedReader;
		String lines;
		JSONArray jsonArray = null;
		try {
			URL url = new URL(replaceUrlApi);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
					+ "AppleWebkit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			httpConn.setUseCaches(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestProperty("Content-Type", "application/xml; charset=UTF-8");
			httpConn.connect();
			bufferedReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			StringBuffer stringBuffer = new StringBuffer("");
			while ((lines = bufferedReader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				stringBuffer.append(lines);
			}
			jsonArray = new JSONArray(stringBuffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	/**
	 * 將Json解析的JAVA物件寫入資料庫
	 *
	 * @param jsonArray 有傳回執行結果描述
	 */
	private String insertData(JSONArray jsonArray) {
		String updateResult;
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				TrainDailyInfo trainDailyInfo = new TrainDailyInfo(jsonObject);
				trainDao.save(trainDailyInfo);
				int trainDailyId = trainDailyInfo.getTrainDailyId();
				List<StopTime> stopTimes = trainDailyInfo.getStopTime();
				for (int j = 0; j < stopTimes.size(); j++) {
					StopTime stopTime = stopTimes.get(j);
					stopTime.setTrainDailyId(trainDailyId);
					stopTimeDao.save(stopTime);
				}
			}
			updateResult = "新增成功";
		} catch (IllegalArgumentException e) {
			updateResult = "新增失敗";
		}
		return updateResult;
	}

	/**
	 * 將Json解析的JAVA物件寫入資料庫 test用
	 * 
	 * @param jsonArray 無傳回執行結果
	 */
	private void insertData2(JSONArray jsonArray) {
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			TrainDailyInfo trainDailyInfo = new TrainDailyInfo(jsonObject);
			trainDao.save(trainDailyInfo);
			int trainDailyId = trainDailyInfo.getTrainDailyId();
			List<StopTime> stopTimes = trainDailyInfo.getStopTime();
			for (int j = 0; j < stopTimes.size(); j++) {
				StopTime stopTime = stopTimes.get(j);
				stopTime.setTrainDailyId(trainDailyId);
				stopTimeDao.save(stopTime);
			}
		}
	}

	/**
	 * 檢查是否已有當日資料
	 *
	 * @return boolean
	 */
	public Boolean checkDate(String date) {
		Boolean count = false;
		if (trainDao.countTrainDateByTrainDate(date) > 0) 
			count = true;
		return count;
	}

	/**
	 * 取回包含今天到後十天的日期
	 *
	 * @return list
	 */
	public ArrayList<String> dateList() {
		LocalDate nowDate = LocalDate.now();
		ArrayList<String> dateList = new ArrayList<>();
		for (int i = 0; i <= 1; i++) {
			dateList.add(i, nowDate.plusDays(i).toString());
		}
		return dateList;
	}
}
