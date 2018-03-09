package io.pitchcast.pitchingservice.domain.repository;

import io.pitchcast.pitchingservice.domain.Pitch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PitchesRepository extends JpaRepository<Pitch, Long> {
    List<Pitch> findByPitcherId(Long pitcherId);
}
