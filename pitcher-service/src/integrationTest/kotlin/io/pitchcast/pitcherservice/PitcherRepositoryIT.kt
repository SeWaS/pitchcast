package io.pitchcast.pitcherservice

import io.github.benas.randombeans.api.EnhancedRandom
import io.pitchcast.pitcherservice.domain.Pitcher
import io.pitchcast.pitcherservice.domain.repository.PitcherRepository
import io.pitchcast.pitcherservice.service.PitcherService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.validation.ConstraintViolationException

@ExtendWith(SpringExtension::class)
@ActiveProfiles("postgres")
@Import(PitcherService::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
open class PitcherRepositoryIT {

    @Autowired lateinit var pitcherService: PitcherService
    @Autowired lateinit var repository: PitcherRepository

    @Test fun `should save valid Pitcher`() {
        // given
        val pitcher = EnhancedRandom.random(Pitcher::class.java)

        // when
        pitcherService.savePitcher(pitcher)

        // then
        val storedPitch = repository.findAll()[0]

        assertThat(pitcher).isEqualToIgnoringGivenFields(storedPitch, "pitcherId")
    }

    @Test fun `should not save pitcher with null fields`() {

        val invalidPitcher = Pitcher()

        assertThatExceptionOfType(ConstraintViolationException::class.java).isThrownBy(
                { pitcherService.savePitcher(invalidPitcher) }
        )
    }

}