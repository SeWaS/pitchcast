package io.pitchcast.pitchingservice.web;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.pitchcast.pitchingservice.domain.Pitch;
import io.pitchcast.pitchingservice.domain.repository.PitchesRepository;
import io.pitchcast.support.testing.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;


@Category(IntegrationTest.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("mysql")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PitchesRepositoryIT {

    @Autowired
    private PitchesRepository repository;

    @Test
    public void shouldStoreValidPitchCorrectly() {
        // given
        Pitch validPitch = EnhancedRandom.random(Pitch.class);

        // when
        Pitch storedPitch = repository.save(validPitch);
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        // then
        Pitch foundPitch = repository.findOne(storedPitch.getId());
        assertThat(foundPitch).isEqualToIgnoringGivenFields(validPitch, "pitchId", "pitchResult", "pitchType");
        assertThat(foundPitch.getPitchResult().name()).isEqualTo(validPitch.getPitchResult().name());
        assertThat(foundPitch.getPitchType().name()).isEqualTo(validPitch.getPitchType().name());
    }

    @Test
    public void shouldNotStoreInalidPitchCorrectly() {

        Pitch invalidPitch = new Pitch();

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(
                () -> repository.save(invalidPitch)
        );
    }

}
