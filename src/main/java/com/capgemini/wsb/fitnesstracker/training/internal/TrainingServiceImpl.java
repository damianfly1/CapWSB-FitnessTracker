package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;

    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> findTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    @Override
    public List<Training> findCompletedTrainingsAfter(Date date) {
        return trainingRepository.findByEndTimeAfter(date);
    }

    @Override
    public List<Training> findTrainingsByActivity(String activity) {
        return trainingRepository.findByActivityType(ActivityType.valueOf(activity.toUpperCase()));
    }

    @Override
    public Training createTraining(Training training) {
        log.info("Creating Training {}", training);
        return trainingRepository.save(training);
    }

    @Override
    public Training updateTraining(Long id, Training training) {
        log.info("Updating Training with ID {}", id);
        return trainingRepository.findById(id)
                .map(existingTraining -> {
                    existingTraining.setStartTime(training.getStartTime());
                    existingTraining.setEndTime(training.getEndTime());
                    existingTraining.setActivityType(training.getActivityType());
                    existingTraining.setDistance(training.getDistance());
                    existingTraining.setAverageSpeed(training.getAverageSpeed());
                    existingTraining.setUser(training.getUser());
                    return trainingRepository.save(existingTraining);
                })
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));
    }

    @Override
    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }
}
