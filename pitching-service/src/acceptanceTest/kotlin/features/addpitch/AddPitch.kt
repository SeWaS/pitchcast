package features.addpitch

import io.github.benas.randombeans.api.EnhancedRandom
import io.pitchcast.pitchingservice.PitchingServiceApp
import io.pitchcast.pitchingservice.domain.repository.PitchesRepository
import io.pitchcast.pitchingservice.web.dto.PitchDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import testing.AcceptanceTest

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@AcceptanceTest(forApp = [PitchingServiceApp::class])
class AddPitch {

    @Autowired lateinit var testRestTemplate: TestRestTemplate
    @Autowired lateinit var repository: PitchesRepository

    @AfterEach
    fun tearDown() = repository.deleteAll()

    @Test
    fun shouldAddValidPitch() {
        // given
        val pitch = EnhancedRandom.random(PitchDto::class.java)

        // when
        val getPitchesResponse = testRestTemplate.postForEntity("/pitches/", pitch, Long::class.java)

        // then
        assertThat(getPitchesResponse.statusCodeValue).isEqualTo(201)

        val storedPitch = repository.findById(getPitchesResponse.body).get()
        assertThat(storedPitch).isEqualToIgnoringGivenFields(pitch, "pitchId", "pitchResult", "pitchType")
        assertThat(storedPitch.pitchResult.name).isEqualTo(pitch.pitchResult.name)
        assertThat(storedPitch.pitchType.name).isEqualTo(pitch.pitchType.name)
    }

}