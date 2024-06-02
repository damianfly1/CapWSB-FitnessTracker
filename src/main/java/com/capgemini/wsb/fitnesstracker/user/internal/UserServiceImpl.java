package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsService;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final TrainingService trainingService;
    private final StatisticsService statisticsService;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting User with ID {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            List<Training> userTrainings = user.get().getTrainingList();
            for (Training training : userTrainings) {
                trainingService.deleteTraining(training.getId());
            }
            List<Statistics> userStatistics = user.get().getStatisticsList();
            for (Statistics statistics : userStatistics) {
                statisticsService.deleteStatistics(statistics.getId());
            }
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public List<User> findUsersByBirthDateAfter(LocalDate date) {
        log.info("Finding Users born before {}", date);
        return userRepository.findUsersByBirthDateAfter(date);
    }

    @Override
    public User updateUser(Long id, User userEntity) {
        log.info("Updating User with ID {}", id);
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(userEntity.getFirstName());
                    existingUser.setLastName(userEntity.getLastName());
                    existingUser.setBirthdate(userEntity.getBirthdate());
                    existingUser.setEmail(userEntity.getEmail());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}