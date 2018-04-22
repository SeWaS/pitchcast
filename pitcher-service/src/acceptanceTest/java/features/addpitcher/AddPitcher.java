package features.addpitcher;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.pitchcast.pitcherservice.PitcherServiceApp;
import io.pitchcast.pitcherservice.domain.Pitcher;
import io.pitchcast.pitcherservice.domain.repository.PitcherRepository;
import io.pitchcast.pitcherservice.web.dto.PitcherDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PitcherServiceApp.class
)
public class AddPitcher {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PitcherRepository repository;

    @Test
    public void shouldAddPitcher() {
        // given
        PitcherDto newPitcher = EnhancedRandom.random(PitcherDto.class);

        // when
        ResponseEntity<Long> addPitcherResponse = testRestTemplate.postForEntity("/pitcher/", newPitcher, Long.class);

        // then
        assertThat(addPitcherResponse.getStatusCodeValue()).isEqualTo(201);

        Pitcher storedPitcher = repository.findById(addPitcherResponse.getBody()).get();
        assertThat(storedPitcher).isEqualToIgnoringGivenFields(newPitcher, "pitcherId", "handed");
        assertThat(storedPitcher.getHanded().name()).isEqualTo(newPitcher.getHanded().name());
    }

}
