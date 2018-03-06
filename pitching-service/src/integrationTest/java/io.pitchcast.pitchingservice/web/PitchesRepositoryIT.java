package io.pitchcast.pitchingservice.web;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.pitchcast.pitchingservice.domain.Pitch;
import io.pitchcast.pitchingservice.service.PitchesService;
import io.pitchcast.support.testing.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
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


@Category(IntegrationTest.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("mysql")
@Import(PitchesService.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PitchesRepositoryIT {

    @Autowired
    private PitchesService pitchesService;

    @Test
    public void shouldStoreValidPitchCorrectly() {
        // given
        Pitch validPitch = EnhancedRandom.random(Pitch.class);

        // when
        pitchesService.saveNewPitch(validPitch);

        // then
        Pitch foundPitch = pitchesService.getAllPitches().get(0);
        assertThat(foundPitch).isEqualToIgnoringGivenFields(validPitch, "pitchId", "pitchResult", "pitchType");
        assertThat(foundPitch.getPitchResult().name()).isEqualTo(validPitch.getPitchResult().name());
        assertThat(foundPitch.getPitchType().name()).isEqualTo(validPitch.getPitchType().name());
    }

    @Test
    public void shouldNotStoreInalidPitchCorrectly() {
        // given
        Pitch invalidPitch = new Pitch();

        // when
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(
                () -> pitchesService.saveNewPitch(invalidPitch)
        );
    }

}
