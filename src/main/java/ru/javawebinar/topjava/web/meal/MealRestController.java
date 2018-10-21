package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create {} for userId={}", meal, userId);
        return service.save(meal, userId);
    }

    public Meal update(Meal meal, int id) throws NotFoundException {
        int userId = SecurityUtil.authUserId();
        log.info("update {} for userId={}", id, userId);
        return service.update(meal, userId);
    }

    public Meal get(int id) throws NotFoundException {
        int userId = SecurityUtil.authUserId();
        log.info("get {} for userId={}", userId);
        return service.get(id, userId);
    }

    public void delete(int id) throws NotFoundException {
        int userId = SecurityUtil.authUserId();
        log.info("delete {} for userId={}", userId);
        service.delete(id, userId);
    }

    public List<MealWithExceed> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("get all for userId={}", userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();

        if(startDate == null)
            startDate = LocalDate.MIN;
        if(endDate == null)
            endDate = LocalDate.MAX;
        if(startTime == null)
            startTime = LocalTime.MIN;
        if(endTime == null)
            endTime = LocalTime.MAX;
        log.info("get all from {} {} to {} {} for userId={}", startDate, startTime, endDate, endTime, userId);
        return MealsUtil.getWithExceeded(service.getBetweenDateTimes(LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime), userId), SecurityUtil.authUserCaloriesPerDay());
    }

}