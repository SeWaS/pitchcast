package features.addpitch;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.pitchcast.pitchingservice.PitchingServiceApp;
import io.pitchcast.pitchingservice.domain.Pitch;
import io.pitchcast.pitchingservice.domain.repository.PitchesRepository;
import io.pitchcast.pitchingservice.web.dto.PitchDto;
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

import static org.assertj.core.api.Java6Assertions.assertThat;

@Category(AcceptanceTest.class)
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PitchingServiceApp.class
)
public class AddPitch {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PitchesRepository repository;

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void shouldAddValidPitch() {
        // given
        PitchDto pitch = EnhancedRandom.random(PitchDto.class);

        // when
        ResponseEntity<Long> getPitchesResponse = testRestTemplate.postForEntity("/add", pitch, Long.class);

        // then
        assertThat(getPitchesResponse.getStatusCodeValue()).isEqualTo(201);

        Pitch storedPitch = repository.findOne(getPitchesResponse.getBody());
        assertThat(storedPitch).isEqualToIgnoringGivenFields(pitch, "pitchId", "pitchResult", "pitchType");
        assertThat(storedPitch.getPitchResult().name()).isEqualTo(pitch.getPitchResult().name());
        assertThat(storedPitch.getPitchType().name()).isEqualTo(pitch.getPitchType().name());
    }

}
