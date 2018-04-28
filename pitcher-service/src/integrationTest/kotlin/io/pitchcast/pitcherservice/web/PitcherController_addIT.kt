package io.pitchcast.pitcherservice.web

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.benas.randombeans.api.EnhancedRandom
import io.pitchcast.pitcherservice.domain.Pitcher
import io.pitchcast.pitcherservice.service.PitcherService
import io.pitchcast.pitcherservice.web.dto.PitcherDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
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
class PitcherController_addIT {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @MockBean lateinit var pitcherService: PitcherService

    @Test fun `should return 201 if pitcher was added successfully`() {
        // given
        val pitcherArgumentCaptor = ArgumentCaptor.forClass(Pitcher::class.java)

        val validPitcher = EnhancedRandom.random(PitcherDto::class.java)

        // then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pitcher/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPitcher))
        ).andExpect(MockMvcResultMatchers.status().`is`(201))

        verify<PitcherService>(this.pitcherService).savePitcher(pitcherArgumentCaptor.capture())

        val sentPitcher = pitcherArgumentCaptor.value

        assertThat(sentPitcher).isEqualToIgnoringGivenFields(validPitcher, "pitcherId", "handed")
        assertThat(sentPitcher.handed.name).isEqualTo(validPitcher.handed.name)

    }

    @Test fun `should return 400 if pitcher has null values`() {

        val validPitcher = PitcherDto()

        // then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pitcher/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPitcher))
        ).andExpect(MockMvcResultMatchers.status().`is`(400))

        verify<PitcherService>(pitcherService, times(0)).savePitcher(ArgumentMatchers.any())
    }

}