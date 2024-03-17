package me.mocadev.herokujavaapi.batch;

import lombok.RequiredArgsConstructor;
import me.mocadev.herokujavaapi.common.service.WorkService;
import org.springframework.stereotype.Component;

/**
 * 업무 알람
 *
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-04-27
 **/
@RequiredArgsConstructor
@Component
public class WorkAlarmBatch {

	private final WorkService workService;

	public void attendanceAlarm() {
		workService.sendAttendance();
	}

	public void leaveWorkAlarm() {
		workService.sendLeaveWork();
	}
}
