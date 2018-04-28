package features.getpitch

import io.github.benas.randombeans.api.EnhancedRandom
import io.pitchcast.pitchingservice.PitchingServiceApp
import io.pitchcast.pitchingservice.domain.Pitch
import io.pitchcast.pitchingservice.domain.repository.PitchesRepository
import io.pitchcast.pitchingservice.web.dto.PitchDto
import io.pitchcast.pitchingservice.web.dto.PitchesDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension
import testing.AcceptanceTest

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@AcceptanceTest(forApp = [PitchingServiceApp::class])
class GetPitch {

    @Autowired lateinit var testRestTemplate: TestRestTemplate
    @Autowired lateinit var repository: PitchesRepository

    @AfterEach
    fun tearDown() = repository.deleteAll()

    @Test fun shouldReturnAllPitches() {
        // given
        val testPitches = EnhancedRandom.randomListOf(3, Pitch::class.java)
        repository.saveAll(testPitches)
        repository.flush()

        // when
        val getPitchesResponse = testRestTemplate.getForEntity("/pitches/", PitchesDto::class.java)

        // then
        assertThat(getPitchesResponse.statusCodeValue).isEqualTo(200)
        assertThat(getPitchesResponse.body.pitches).hasSize(3)
    }

    @Test fun shouldReturnPitchesByPitcher() {
        // given
        val PITCHER = "NAME OF PITCHER"

        val testPitches = EnhancedRandom.randomListOf(3, Pitch::class.java)
        repository.saveAll(testPitches)
        repository.flush()

        val pitchForPitcher = EnhancedRandom.random(Pitch::class.java)
        pitchForPitcher.pitcherId = 123L
        pitchForPitcher.pitcherName = PITCHER
        repository.saveAndFlush(pitchForPitcher)

        // when
        val getPitchesResponse = testRestTemplate.getForEntity("/pitches/123", PitchesDto::class.java)

        // then
        assertThat(getPitchesResponse.statusCodeValue).isEqualTo(200)
        assertThat<PitchDto>(getPitchesResponse.body.pitches).hasSize(1)

        val receivedPitch = getPitchesResponse.body.pitches[0]

        assertThat(receivedPitch).isEqualToIgnoringGivenFields(pitchForPitcher, "pitchId", "pitchType", "pitchResult")
        assertThat(receivedPitch.pitchResult.name).isEqualTo(pitchForPitcher.pitchResult.name)
        assertThat(receivedPitch.pitchType.name).isEqualTo(pitchForPitcher.pitchType.name)
    }

}