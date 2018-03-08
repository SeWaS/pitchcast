package io.pitchcast.pitcherservice.web.pitchercontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pitchcast.pitcherservice.domain.Pitcher;
import io.pitchcast.pitcherservice.service.PitcherService;
import io.pitchcast.pitcherservice.web.PitcherController;
import org.junit.Test;
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

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PitcherController.class)
public class PitcherController_getIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PitcherService pitcherService;

    @Test
    public void shouldReturn200IfPitcher() throws Exception {

        given(this.pitcherService.getAllPitcher()).willReturn(Arrays.asList(
                random(Pitcher.class),
                random(Pitcher.class)
        ));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/pitcher/get")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is(200));

        verify(pitcherService, times(1)).getAllPitcher();
    }

}
