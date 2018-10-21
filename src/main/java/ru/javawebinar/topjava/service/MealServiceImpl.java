package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public Meal update(Meal meal, int userId) throws NotFoundException {
        Meal updated = repository.save(meal, userId);
        if(updated == null)
            throw new NotFoundException("not found meal with id" + meal.getId());
        else
            return updated;
}

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal meal = repository.get(id, userId);
        if(meal == null)
            throw new NotFoundException("not found meal with id" + meal.getId());
        else
            return meal;
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if(!repository.delete(id, userId))
            throw new NotFoundException("not found meal with id" + id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId).stream().collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return repository.getBetween(startDateTime, endDateTime, userId);
    }

    @Override
    public List<Meal> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId) {
        return getBetweenDateTimes(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId);
    }
}