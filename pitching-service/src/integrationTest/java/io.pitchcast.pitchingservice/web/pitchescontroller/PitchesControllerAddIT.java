package io.pitchcast.pitchingservice.web.pitchescontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.pitchcast.pitchingservice.domain.Pitch;
import io.pitchcast.pitchingservice.service.PitchesService;
import io.pitchcast.pitchingservice.web.PitchesController;
import io.pitchcast.pitchingservice.web.dto.PitchDto;
import io.pitchcast.pitchingservice.web.dto.PitchTypeDto;
import io.pitchcast.support.testing.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Category(IntegrationTest.class)
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PitchesController.class)
public class PitchesControllerAddIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PitchesService pitchesService;

    @Test
    public void shouldReturn201IfValidPitchWasAdded() throws Exception {

        ArgumentCaptor<Pitch> pitchArgumentCaptor = ArgumentCaptor.forClass(Pitch.class);

        PitchDto validPitch = EnhancedRandom.random(PitchDto.class);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/pitches/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(validPitch))
        ).andExpect(MockMvcResultMatchers.status().is(201));

        verify(this.pitchesService).saveNewPitch(pitchArgumentCaptor.capture());

        assertThat(pitchArgumentCaptor.getValue()).isEqualToIgnoringGivenFields(validPitch, "pitchId", "pitchType", "pitchResult");
        assertThat(pitchArgumentCaptor.getValue().getPitchResult().name()).isEqualTo(validPitch.getPitchResult().name());
        assertThat(pitchArgumentCaptor.getValue().getPitchType().name()).isEqualTo(validPitch.getPitchType().name());
    }

    @Test
    public void shouldReturn400IfNoPitchWasSent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pitches/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("")
        ).andExpect(MockMvcResultMatchers.status().is(400));

        verify(this.pitchesService, times(0)).saveNewPitch(any(Pitch.class));
    }

    @Test
    public void shouldReturn400IfEmptyPitchWasSent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pitches/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(""))
        ).andExpect(MockMvcResultMatchers.status().is(400));

        verify(this.pitchesService, times(0)).saveNewPitch(any(Pitch.class));
    }

    @Test
    public void shouldReturn400IfPitchHasNotAllowedNullValues() throws Exception {

        PitchDto invalidPitch = new PitchDto();
        invalidPitch.setX(10);
        invalidPitch.setY(20);
        invalidPitch.setPitchType(PitchTypeDto.FOURSEEM);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/pitches/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(invalidPitch))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

}
