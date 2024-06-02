package com.capgemini.wsb.fitnesstracker.training.api;

public interface TrainingService {
    void deleteTraining(Long id);
    Training createTraining(Training training);
    Training updateTraining(Long id, Training training);
}
