package features.addpitcher

import io.github.benas.randombeans.api.EnhancedRandom.random
import io.pitchcast.pitcherservice.PitcherServiceApp
import io.pitchcast.pitcherservice.domain.repository.PitcherRepository
import io.pitchcast.pitcherservice.web.dto.PitcherDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import testing.AcceptanceTest

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@AcceptanceTest(forApp = [PitcherServiceApp::class])
class AddPitcher {

    @Autowired lateinit var testRestTemplate: TestRestTemplate
    @Autowired lateinit var repository: PitcherRepository

    @Test fun `should add pitcher`() {
        // given
        val newPitcher = random<PitcherDto>(PitcherDto::class.java)

        // when
        val addPitcherResponse = testRestTemplate.postForEntity("/pitcher/", newPitcher, Long::class.java)

        // then
        assertThat(addPitcherResponse.statusCodeValue).isEqualTo(201)

        val storedPitcher = repository.findById(addPitcherResponse.body).get()
        assertThat(storedPitcher).isEqualToIgnoringGivenFields(newPitcher, "pitcherId", "handed")
        assertThat(storedPitcher.handed.name).isEqualTo(newPitcher.handed.name)
    }

}