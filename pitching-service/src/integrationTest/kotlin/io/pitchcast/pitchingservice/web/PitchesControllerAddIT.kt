package io.pitchcast.pitchingservice.web

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.benas.randombeans.api.EnhancedRandom
import io.pitchcast.pitchingservice.domain.Pitch
import io.pitchcast.pitchingservice.service.PitchesService
import io.pitchcast.pitchingservice.web.dto.PitchDto
import io.pitchcast.pitchingservice.web.dto.PitchTypeDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import testing.WebMvcSliceTest

@WebMvcSliceTest(forController = [PitchesController::class])
class PitchesControllerAddIT{

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @MockBean lateinit var pitchesService: PitchesService

    @Test fun shouldReturn201IfValidPitchWasAdded() {

        val pitchArgumentCaptor = ArgumentCaptor.forClass(Pitch::class.java)

        val validPitch = EnhancedRandom.random(PitchDto::class.java)

        mockMvc.perform(
                MockMvcRequestBuilders.post("/pitches/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(validPitch))
        ).andExpect(MockMvcResultMatchers.status().`is`(201))

        verify(this.pitchesService).saveNewPitch(pitchArgumentCaptor.capture())

        assertThat(pitchArgumentCaptor.value).isEqualToIgnoringGivenFields(validPitch, "pitchId", "pitchType", "pitchResult")
        assertThat(pitchArgumentCaptor.value.pitchResult.name).isEqualTo(validPitch.pitchResult.name)
        assertThat(pitchArgumentCaptor.value.pitchType.name).isEqualTo(validPitch.pitchType.name)
    }

    @Test fun shouldReturn400IfNoPitchWasSent() {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pitches/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("")
        ).andExpect(MockMvcResultMatchers.status().`is`(400))

        verify<PitchesService>(this.pitchesService, times(0)).saveNewPitch(any(Pitch::class.java))
    }

    @Test fun shouldReturn400IfEmptyPitchWasSent() {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pitches/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(""))
        ).andExpect(MockMvcResultMatchers.status().`is`(400))

        verify<PitchesService>(this.pitchesService, times(0)).saveNewPitch(any<Pitch>(Pitch::class.java))
    }

    @Test fun shouldReturn400IfPitchHasNotAllowedNullValues() {

        val invalidPitch = PitchDto()
        invalidPitch.x = 10
        invalidPitch.y = 20
        invalidPitch.pitchType = PitchTypeDto.FOURSEEM

        mockMvc.perform(
                MockMvcRequestBuilders.post("/pitches/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(invalidPitch))
        ).andExpect(MockMvcResultMatchers.status().`is`(400))
    }


}