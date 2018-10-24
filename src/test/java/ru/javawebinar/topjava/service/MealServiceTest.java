package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_DINNER_ID, USER_ID);
        assertMatch(meal, USER_DINNER);
    }

    @Test
    public void delete() {
        service.delete(USER_DINNER_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), USER_SUPPER);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> list = service.getBetweenDates(
                LocalDate.of(2015, 06, 01),
                LocalDate.of(2015, 06, 01),
                USER_ID);
        assertMatch(list, USER_DINNER, USER_SUPPER);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> list = service.getBetweenDateTimes(
                LocalDateTime.of(2015, 06, 01, 11, 00),
                LocalDateTime.of(2015, 06, 01, 16, 00),
                ADMIN_ID
                );
        assertMatch(list, ADMIN_DINNER);
    }

    @Test
    public void getAll() {
        List<Meal> list = service.getAll(USER_ID);
        assertMatch(list, USER_DINNER, USER_SUPPER);
    }

    @Test
    public void update() {
        Meal updated = new Meal(ADMIN_SUPPER);
        updated.setDescription("Admin job");
        updated.setCalories(9000);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(ADMIN_SUPPER_ID, ADMIN_ID), updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(
                null,
                LocalDateTime.of(2015, 06, 01, 8, 00),
                "Meal",
                700);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(newMeal, created);
        assertMatch(service.getAll(USER_ID), newMeal, USER_DINNER, USER_SUPPER);
    }

    @Test(expected = NotFoundException.class)
    public void deleteForeign() {
        service.delete(USER_DINNER_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getForeign() {
        Meal meal = service.get(USER_DINNER_ID, ADMIN_ID);
        assertMatch(meal, USER_DINNER);
    }

    @Test(expected = NotFoundException.class)
    public void updateForeign() {
        Meal updated = new Meal(ADMIN_SUPPER);
        updated.setDescription("Admin job");
        updated.setCalories(9000);
        service.update(updated, USER_ID);
    }

}