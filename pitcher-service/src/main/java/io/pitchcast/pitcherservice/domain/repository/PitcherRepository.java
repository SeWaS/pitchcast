package io.pitchcast.pitcherservice.domain.repository;

import io.pitchcast.pitcherservice.domain.Pitcher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PitcherRepository extends JpaRepository<Pitcher, Long> {
}
