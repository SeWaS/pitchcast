package features.getpitcher

import io.github.benas.randombeans.api.EnhancedRandom
import io.pitchcast.pitcherservice.PitcherServiceApp
import io.pitchcast.pitcherservice.domain.Pitcher
import io.pitchcast.pitcherservice.domain.repository.PitcherRepository
import io.pitchcast.pitcherservice.web.dto.PitchersDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension
import testing.AcceptanceTest

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@AcceptanceTest(forApp = [PitcherServiceApp::class])
class GetPitcher {

    @Autowired lateinit var testRestTemplate: TestRestTemplate
    @Autowired lateinit var repository: PitcherRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    @Test fun `should return all pitchers`() {
        // given
        val testPitches = EnhancedRandom.randomListOf(3, Pitcher::class.java)
        repository.saveAll(testPitches)
        repository.flush()

        // when
        val getPitchesResponse = testRestTemplate.getForEntity("/pitcher/", PitchersDto::class.java)

        // then
        assertThat(getPitchesResponse.statusCodeValue).isEqualTo(200)
        assertThat(getPitchesResponse.body.pitchers).hasSize(3)
    }

}