package features.addpitcher;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.pitchcast.pitcherservice.PitcherServiceApp;
import io.pitchcast.pitcherservice.domain.Pitcher;
import io.pitchcast.pitcherservice.domain.repository.PitcherRepository;
import io.pitchcast.pitcherservice.web.dto.PitcherDto;
import io.pitchcast.support.testing.AcceptanceTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Category(AcceptanceTest.class)
@RunWith(SpringRunner.class)
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
        ResponseEntity<Long> addPitcherResponse = testRestTemplate.postForEntity("/pitcher/add", newPitcher, Long.class);

        // then
        assertThat(addPitcherResponse.getStatusCodeValue()).isEqualTo(201);

        Pitcher storedPitcher = repository.findOne(addPitcherResponse.getBody());
        assertThat(storedPitcher).isEqualToIgnoringGivenFields(newPitcher, "pitcherId", "handed");
        assertThat(storedPitcher.getHanded().name()).isEqualTo(newPitcher.getHanded().name());
    }

}
