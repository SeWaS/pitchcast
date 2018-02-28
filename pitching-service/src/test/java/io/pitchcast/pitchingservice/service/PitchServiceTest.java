package io.pitchcast.pitchingservice.service;

import io.pitchcast.pitchingservice.domain.Pitch;
import io.pitchcast.pitchingservice.domain.repository.PitchesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static io.github.benas.randombeans.api.EnhancedRandom.random;

@RunWith(MockitoJUnitRunner.class)
public class PitchServiceTest {

    @InjectMocks
    private PitchesService pitchesService;

    @Mock
    private PitchesRepository pitchesRepository;

    @Test
    public void shouldSavePitchesToRepository() {
        Pitch pitch = new Pitch();

        Pitch mockPitch = new Pitch();
        mockPitch.setId(1234L);
        given(pitchesRepository.save(any(Pitch.class))).willReturn(mockPitch);

        Long receivedId = pitchesService.saveNewPitch(pitch);

        assertThat(receivedId).isEqualTo(mockPitch.getId());
        verify(pitchesRepository).save(eq(pitch));
    }

    @Test
    public void shouldGetAllSavedPitchesFromRepository() {

        given(pitchesRepository.findAll()).willReturn(Arrays.asList(
                random(Pitch.class),
                random(Pitch.class),
                random(Pitch.class)
        ));

        List<Pitch> receivedPithes = pitchesService.getAllPitches();

        assertThat(receivedPithes).hasSize(3);
        verify(pitchesRepository, times(1)).findAll();
    }

}
