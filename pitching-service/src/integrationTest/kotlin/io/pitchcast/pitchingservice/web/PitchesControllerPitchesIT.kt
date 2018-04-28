package io.pitchcast.pitchingservice.web

import io.github.benas.randombeans.api.EnhancedRandom
import io.github.benas.randombeans.api.EnhancedRandom.random
import io.pitchcast.pitchingservice.domain.Pitch
import io.pitchcast.pitchingservice.service.PitchesService
import io.pitchcast.pitchingservice.web.PitchesController
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import testing.WebMvcSliceTest
import java.util.*

@WebMvcSliceTest(forController = [PitchesController::class])
class PitchesControllerPitchesIT {

    @Autowired lateinit var mockMvc: MockMvc
    @MockBean lateinit var pitchesService: PitchesService

    @Test fun shouldReturn200IfWantingAllPitches() {

        given(this.pitchesService.allPitches).willReturn(Arrays.asList<Pitch>(
                random(Pitch::class.java),
                random(Pitch::class.java)
        ))

        mockMvc.perform(
                MockMvcRequestBuilders.get("/pitches/")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().`is`(200))

        verify(this.pitchesService, times(1)).getAllPitches()
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturn200IfWantingPitchesByPitcherId() {

        given(this.pitchesService.getPitchesByPitcher(12345L)).willReturn(EnhancedRandom.randomListOf(3, Pitch::class.java))

        mockMvc.perform(
                MockMvcRequestBuilders.get("/pitches/12345")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().`is`(200))

        verify(this.pitchesService, times(1)).getPitchesByPitcher(12345L)

    }

}