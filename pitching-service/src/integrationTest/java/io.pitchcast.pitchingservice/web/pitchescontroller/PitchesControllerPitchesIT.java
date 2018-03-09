package io.pitchcast.pitchingservice.web.pitchescontroller;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.pitchcast.pitchingservice.domain.Pitch;
import io.pitchcast.pitchingservice.service.PitchesService;
import io.pitchcast.pitchingservice.web.PitchesController;
import io.pitchcast.support.testing.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Category(IntegrationTest.class)
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PitchesController.class)
public class PitchesControllerPitchesIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PitchesService pitchesService;

    @Test
    public void shouldReturn200IfWantingAllPitches() throws Exception {

        given(this.pitchesService.getAllPitches()).willReturn(Arrays.asList(
                random(Pitch.class),
                random(Pitch.class)
        ));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/pitches/")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is(200));

        verify(this.pitchesService, times(1)).getAllPitches();
    }

    @Test
    public void shouldReturn200IfWantingPitchesByPitcherId() throws Exception {

        given(this.pitchesService.getPitchesByPitcher(12345L)).willReturn(EnhancedRandom.randomListOf(3, Pitch.class));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/pitches/12345")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is(200));

        verify(this.pitchesService, times(1)).getPitchesByPitcher(12345L);

    }

}
