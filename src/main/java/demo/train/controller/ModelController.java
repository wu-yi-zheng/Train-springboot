package demo.train.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.train.pojo.TrainInfo;
import demo.train.service.TrainService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ModelController {
	@Autowired
	TrainService trainService;
	TrainInfo trainInfo = new TrainInfo();

	/**
	 * 查詢車站名稱
	 * 
	 * @return 跳轉到 StationName.html
	 */
	@GetMapping(value = "/stationList")
	public String getStationName(Model model) {
		model.addAttribute("stationNameList", trainService.getStationNameList());
		return "StationName";
	}

	/**
	 * 查詢車站頁面
	 *
	 * @return 跳轉到TrainInfo.html
	 */
    @PostMapping("/trainInfo")
    public String postTrainInfo(@ModelAttribute TrainInfo trainInfoForUser, Model model) {
        model.addAttribute("trainInfoList", trainService.getTrainInfo(trainInfoForUser));
        return "TrainInfo";
    }

	/**
	 * 查詢網頁入口 傳入stationNameList 用於下拉選單 trainInfo 讓前端傳遞參數
	 *
	 * @return 跳轉到Timetable.html
	 */
	@GetMapping({ "/timetable" })
	public String getEntrance(Model model) {
		model.addAttribute("trainInfo", trainInfo);
		model.addAttribute("stationNameList", trainService.getStationNameList());
		return "Timetable";
	}

	@GetMapping(value = "/callApi")
	@ResponseBody
	public ArrayList<String> callApi() {
		ArrayList<String> messageList = new ArrayList<>();
		ArrayList<String> dateList = trainService.dateList();
		for (String date : dateList) {
			String result = trainService.getTrainTimetable2(date);
			messageList.add(date + "時刻表: " + result);
		}
		return messageList;
	}
	
	@GetMapping(value = "/callTest2")
	@ResponseBody
	public void getData() {
		ArrayList<String> dateList = trainService.dateList();
		for (String date : dateList) {
			trainService.getTrainTimetable(date);
		}
	}

	@GetMapping(value = "/callTest")
	@ResponseBody
	public void callTest() {
		ArrayList<String> dateList = trainService.dateList();
		for (String date : dateList) {
			trainService.getTrainTimetable(date);
		}
	}

}
