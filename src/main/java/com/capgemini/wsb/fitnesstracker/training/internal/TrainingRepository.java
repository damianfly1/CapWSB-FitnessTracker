package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Finds all trainings associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of trainings for the specified user
     */
    default List<Training> findByUserId(Long userId) {
        return findAll().stream()
                .filter(training -> training.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    /**
     * Finds all trainings that ended after the specified date.
     *
     * @param date the end date to compare against
     * @return a list of trainings that ended after the specified date
     */
    default List<Training> findByEndTimeAfter(Date date) {
        return findAll().stream()
                .filter(training -> training.getEndTime().after(date))
                .collect(Collectors.toList());
    }

    /**
     * Finds all trainings of a specific activity type.
     *
     * @param activityType the type of activity to filter by
     * @return a list of trainings with the specified activity type
     */
    default List<Training> findByActivityType(ActivityType activityType) {
        return findAll().stream()
                .filter(training -> training.getActivityType() == activityType)
                .collect(Collectors.toList());
    }
}
