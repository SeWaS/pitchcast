package features.getpitch;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.pitchcast.pitchingservice.PitchingServiceApp;
import io.pitchcast.pitchingservice.domain.Pitch;
import io.pitchcast.pitchingservice.domain.repository.PitchesRepository;
import io.pitchcast.pitchingservice.web.dto.PitchesDto;
import io.pitchcast.support.testing.AcceptanceTest;
import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Category(AcceptanceTest.class)
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PitchingServiceApp.class
)
public class GetPitch {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PitchesRepository repository;

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void shouldReturnAllPitches() {
        // given
        List<Pitch> testPitches = EnhancedRandom.randomListOf(3, Pitch.class);
        repository.save(testPitches);
        repository.flush();

        // when
        ResponseEntity<PitchesDto> getPitchesResponse = testRestTemplate.getForEntity("/pitches", PitchesDto.class);

        // then
        assertThat(getPitchesResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(getPitchesResponse.getBody().getPitches()).hasSize(3);
    }

}
