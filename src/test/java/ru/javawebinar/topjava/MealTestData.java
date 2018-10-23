package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final int USER_DINNER_ID = START_SEQ + 2;
    public static final int USER_SUPPER_ID = START_SEQ + 3;
    public static final int ADMIN_DINNER_ID = START_SEQ + 4;
    public static final int ADMIN_SUPPER_ID = START_SEQ + 5;


    public static final Meal USER_DINNER = new Meal(USER_DINNER_ID, LocalDateTime.of(2015, Month.JUNE, 1, 14, 00), "User dinner", 510);
    public static final Meal USER_SUPPER = new Meal(USER_SUPPER_ID, LocalDateTime.of(2015, Month.JUNE, 1, 21, 00), "User supper", 1500);
    public static final Meal ADMIN_DINNER = new Meal(ADMIN_DINNER_ID, LocalDateTime.of(2015, Month.JUNE, 1, 14, 00), "Admin dinner", 510);
    public static final Meal ADMIN_SUPPER = new Meal(ADMIN_SUPPER_ID, LocalDateTime.of(2015, Month.JUNE, 1, 20, 20), "Admin supper", 500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorOnFields().isEqualTo(expected);
    }
}
