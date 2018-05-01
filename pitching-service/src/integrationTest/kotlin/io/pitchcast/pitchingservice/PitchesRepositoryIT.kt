package io.pitchcast.pitchingservice

import io.github.benas.randombeans.api.EnhancedRandom
import io.pitchcast.pitchingservice.domain.Pitch
import io.pitchcast.pitchingservice.domain.repository.PitchesRepository
import io.pitchcast.pitchingservice.service.PitchesService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import testing.DataJpaSliceTest
import javax.validation.ConstraintViolationException

@DataJpaSliceTest
@Import(PitchesService::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
open class PitchesRepositoryIT {

    @Autowired lateinit var pitchesService: PitchesService
    @Autowired lateinit var pitchesRepository: PitchesRepository

    @Test fun shouldStoreValidPitchCorrectly() {
        // given
        val validPitch = EnhancedRandom.random(Pitch::class.java)

        // when
        pitchesService.saveNewPitch(validPitch)

        // then
        val foundPitch = pitchesService.getAllPitches().get(0)
        assertThat(foundPitch).isEqualToIgnoringGivenFields(validPitch, "pitchId", "pitchResult", "pitchType")
        assertThat(foundPitch.getPitchResult().name).isEqualTo(validPitch.pitchResult.name)
        assertThat(foundPitch.getPitchType().name).isEqualTo(validPitch.pitchType.name)
    }

    @Test fun shouldNotStoreInvalidPitchCorrectly() {
        // given
        val invalidPitch = Pitch()

        // when
        assertThatExceptionOfType(ConstraintViolationException::class.java).isThrownBy(
                { pitchesService.saveNewPitch(invalidPitch) }
        )
    }

    @Test fun shouldFindPitchesByPitcherId() {
        // given
        val PITCHER_ID = 9876L

        val pitch1 = EnhancedRandom.random(Pitch::class.java)
        pitchesRepository.saveAndFlush(pitch1)

        val pitch2 = EnhancedRandom.random(Pitch::class.java)
        pitchesRepository.saveAndFlush(pitch2)

        val pitch3 = EnhancedRandom.random(Pitch::class.java)
        pitch3.pitcherId = PITCHER_ID
        pitchesRepository.saveAndFlush(pitch3)

        val pitch4 = EnhancedRandom.random(Pitch::class.java)
        pitch4.pitcherId = PITCHER_ID
        pitchesRepository.saveAndFlush(pitch4)

        // when
        val foundPitches = this.pitchesService.getPitchesByPitcher(PITCHER_ID)

        // then
        assertThat(foundPitches.size).isEqualTo(2)
    }

}