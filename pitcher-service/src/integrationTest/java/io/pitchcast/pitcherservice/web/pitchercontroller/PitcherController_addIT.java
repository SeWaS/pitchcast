package io.pitchcast.pitcherservice.web.pitchercontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.pitchcast.pitcherservice.domain.Pitcher;
import io.pitchcast.pitcherservice.service.PitcherService;
import io.pitchcast.pitcherservice.web.PitcherController;
import io.pitchcast.pitcherservice.web.dto.PitcherDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PitcherController.class)
public class PitcherController_addIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PitcherService pitcherService;

    @Test
    public void shouldReturn201IfPitcherWasAdded() throws Exception {
        // given
        ArgumentCaptor<Pitcher> pitcherArgumentCaptor = ArgumentCaptor.forClass(Pitcher.class);

        PitcherDto validPitcher = EnhancedRandom.random(PitcherDto.class);

        // then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pitcher/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validPitcher))
        ).andExpect(MockMvcResultMatchers.status().is(201));

        verify(this.pitcherService).savePitcher(pitcherArgumentCaptor.capture());

        Pitcher sentPitcher = pitcherArgumentCaptor.getValue();

        assertThat(sentPitcher).isEqualToIgnoringGivenFields(validPitcher, "pitcherId", "handed");
        assertThat(sentPitcher.getHanded().name()).isEqualTo(validPitcher.getHanded().name());

    }

    @Test
    public void shouldReturn400IfPitcherHasNullValues() throws Exception {

        PitcherDto validPitcher = new PitcherDto();

        // then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pitcher/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPitcher))
        ).andExpect(MockMvcResultMatchers.status().is(400));

        verify(pitcherService, times(0)).savePitcher(any());
    }

}
