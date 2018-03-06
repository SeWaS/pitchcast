package io.pitchcast.pitcherservice.service;

import io.pitchcast.pitcherservice.domain.Pitcher;
import io.pitchcast.pitcherservice.domain.repository.PitcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class PitcherService {

    private PitcherRepository pitcherRepository;

    @Autowired
    public PitcherService(PitcherRepository pitcherRepository) {
        this.pitcherRepository = pitcherRepository;
    }

    public Long savePitcher(Pitcher pitcher) {
        return pitcherRepository.save(pitcher).getPitcherId();
    }

    public List<Pitcher> getAllPitcher() {
        return pitcherRepository.findAll();
    }
}
