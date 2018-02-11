package io.pitchcast.pitchingservice.service;

import io.pitchcast.pitchingservice.domain.Pitch;
import io.pitchcast.pitchingservice.domain.repository.PitchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PitchesService {

    private PitchesRepository pitchesRepository;

    @Autowired
    public PitchesService(PitchesRepository pitchesRepository) {
        this.pitchesRepository = pitchesRepository;
    }

    public Long saveNewPitch(Pitch pitch) {
        return pitchesRepository.save(pitch).getId();
    }

    public List<Pitch> getAllPitches() {
        return pitchesRepository.findAll();
    }
}
