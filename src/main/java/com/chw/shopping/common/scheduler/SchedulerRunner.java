package com.chw.shopping.common.scheduler;

import com.chw.shopping.ma.event.service.MaEventService;
import com.chw.shopping.ma.event.service.MaEventVO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SchedulerRunner implements CommandLineRunner {

    private final Scheduler scheduler;
    private final MaEventService eventService;

    @Override
    public void run(String... args) throws Exception {
        List<MaEventVO> events = eventService.selectAllList();

        if(events.isEmpty()) {
            System.out.println("No events found");
        } else {
            scheduler.startScheduledTasks(events);
            System.out.println("스케줄러 이벤트 실행!!!");
        }
    }

}
