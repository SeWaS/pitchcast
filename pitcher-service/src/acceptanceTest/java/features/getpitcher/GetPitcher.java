package features.getpitcher;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.pitchcast.pitcherservice.PitcherServiceApp;
import io.pitchcast.pitcherservice.domain.Pitcher;
import io.pitchcast.pitcherservice.domain.repository.PitcherRepository;
import io.pitchcast.pitcherservice.web.dto.PitchersDto;
import org.junit.jupiter.api.BeforeEach;
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
        classes = PitcherServiceApp.class
)
public class GetPitcher {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PitcherRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void shouldReturnAllPitchers() {
        // given
        List<Pitcher> testPitches = EnhancedRandom.randomListOf(3, Pitcher.class);
        repository.saveAll(testPitches);
        repository.flush();

        // when
        ResponseEntity<PitchersDto> getPitchesResponse = testRestTemplate.getForEntity("/pitcher/", PitchersDto.class);

        // then
        assertThat(getPitchesResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(getPitchesResponse.getBody().getPitchers()).hasSize(3);
    }

}
