package com.chw.shopping.common.scheduler;

import com.chw.shopping.ma.event.service.MaEventVO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final List<ScheduledFuture<?>> futureTasks = new ArrayList<>();

    public void startScheduledTasks(List<MaEventVO> schedulerList) {
        stopScheduledTasks();

        if(schedulerList.isEmpty()) {
            System.out.println("Scheduler list is empty");
            return;
        }

        for(MaEventVO scheduler : schedulerList) {
            ScheduledFuture<?> future = taskScheduler.schedule(
                    () -> sendNotification(scheduler), Timestamp.valueOf(getNextExecutionTime(scheduler.getAlertTime()))
            );

            futureTasks.add(future);
            System.out.println("스케줄러 등록 : " + scheduler.getAlertTime() + " 이벤트 시작: " + scheduler.getStrtDt());
        }
    }

    private void sendNotification(MaEventVO scheduler) {
        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(scheduler.getStrtDt().atStartOfDay()) && now.isBefore(scheduler.getEndDt().atStartOfDay())) {
            System.out.println("알림 발송 (" + scheduler.getAlertTime() + ") " + scheduler.getCont());
        }
    }

    private LocalDateTime getNextExecutionTime(LocalTime time) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayTime = now.withHour(time.getHour()).withMinute(time.getMinute()).withSecond(0);
        return now.isAfter(todayTime) ? todayTime.plusDays(1) : todayTime;
    }

    /*
    private Date getNextExecutionTime(MaEventVO scheduler) throws ParseException {
        Date now = new Date();
        Date alertTime = timeFormat.parse(scheduler.getAlertTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, alertTime.getHours());
        calendar.set(Calendar.MINUTE, alertTime.getMinutes());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (now.after(calendar.getTime())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return calendar.getTime();
    }
     */

    public void stopScheduledTasks() {
        for(ScheduledFuture<?> future : futureTasks) {
            if(future != null) {
                future.cancel(false);
            }
        }
        futureTasks.clear();
        System.out.println("스케줄러 중지");
    }

}
