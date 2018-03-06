package io.pitchcast.pitcherservice;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.pitchcast.pitcherservice.domain.Pitcher;
import io.pitchcast.pitcherservice.domain.repository.PitcherRepository;
import io.pitchcast.pitcherservice.service.PitcherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@ActiveProfiles("postgres")
@Import(PitcherService.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PitcherRepositoryIT {

    @Autowired
    private PitcherService pitcherService;

    @Autowired
    private PitcherRepository repository;

    @Test
    public void shouldSaveValidPitcher() {
        // given
        Pitcher pitcher = EnhancedRandom.random(Pitcher.class);

        // when
        pitcherService.savePitcher(pitcher);

        // then
        Pitcher storedPitch = repository.findAll().get(0);

        assertThat(pitcher).isEqualToIgnoringGivenFields(storedPitch, "pitcherId");
    }

    @Test
    public void shouldNotSavePitcherWithNullFields() {
        // given
        Pitcher invalidPitcher = new Pitcher();

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(
                () -> pitcherService.savePitcher(invalidPitcher)
        );
    }
}
