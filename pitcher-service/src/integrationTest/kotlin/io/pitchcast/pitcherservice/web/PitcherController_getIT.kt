package io.pitchcast.pitcherservice.web

import io.github.benas.randombeans.api.EnhancedRandom.random
import io.pitchcast.pitcherservice.domain.Pitcher
import io.pitchcast.pitcherservice.service.PitcherService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import testing.WebMvcSliceTest

@WebMvcSliceTest(forController = [PitcherController::class])
class PitcherController_getIT {

    @Autowired lateinit var mockMvc: MockMvc
    @MockBean lateinit var pitcherService: PitcherService

    @Test fun `should return 200 if pitcher`() {

        given<List<Pitcher>>(this.pitcherService.allPitcher).willReturn(arrayListOf<Pitcher>(
                random<Pitcher>(Pitcher::class.java),
                random<Pitcher>(Pitcher::class.java)
        ))

        mockMvc.perform(
                MockMvcRequestBuilders.get("/pitcher/")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().`is`(200))

        verify<PitcherService>(pitcherService, times(1)).getAllPitcher()
    }

}