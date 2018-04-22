package features.getpitch;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.pitchcast.pitchingservice.PitchingServiceApp;
import io.pitchcast.pitchingservice.domain.Pitch;
import io.pitchcast.pitchingservice.domain.repository.PitchesRepository;
import io.pitchcast.pitchingservice.web.dto.PitchDto;
import io.pitchcast.pitchingservice.web.dto.PitchesDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PitchingServiceApp.class
)
public class GetPitch {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PitchesRepository repository;

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void shouldReturnAllPitches() {
        // given
        List<Pitch> testPitches = EnhancedRandom.randomListOf(3, Pitch.class);
        repository.saveAll(testPitches);
        repository.flush();

        // when
        ResponseEntity<PitchesDto> getPitchesResponse = testRestTemplate.getForEntity("/pitches/", PitchesDto.class);

        // then
        assertThat(getPitchesResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(getPitchesResponse.getBody().getPitches()).hasSize(3);
    }

    @Test
    public void shouldReturnPitchesByPitcher() {
        // given
        final String PITCHER = "NAME OF PITCHER";

        List<Pitch> testPitches = EnhancedRandom.randomListOf(3, Pitch.class);
        repository.saveAll(testPitches);
        repository.flush();

        Pitch pitchForPitcher = EnhancedRandom.random(Pitch.class);
        pitchForPitcher.setPitcherId(123L);
        pitchForPitcher.setPitcherName(PITCHER);
        repository.saveAndFlush(pitchForPitcher);

        // when
        ResponseEntity<PitchesDto> getPitchesResponse = testRestTemplate.getForEntity("/pitches/123", PitchesDto.class);

        // then
        assertThat(getPitchesResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(getPitchesResponse.getBody().getPitches()).hasSize(1);

        PitchDto receivedPitch = getPitchesResponse.getBody().getPitches().get(0);

        assertThat(receivedPitch).isEqualToIgnoringGivenFields(pitchForPitcher, "pitchId", "pitchType", "pitchResult");
        assertThat(receivedPitch.getPitchResult().name()).isEqualTo(pitchForPitcher.getPitchResult().name());
        assertThat(receivedPitch.getPitchType().name()).isEqualTo(pitchForPitcher.getPitchType().name());
    }

}
