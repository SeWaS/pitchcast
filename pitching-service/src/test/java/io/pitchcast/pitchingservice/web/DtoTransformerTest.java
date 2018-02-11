package io.pitchcast.pitchingservice.web;

import io.pitchcast.pitchingservice.domain.Pitch;
import io.pitchcast.pitchingservice.web.dto.PitchDto;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class DtoTransformerTest {

    @Test
    public void shouldTransformPitchDtoToPitch() {
        PitchDto pitchDto = random(PitchDto.class);
        Pitch pitch = DtoTransformer.pitchDtoToPitch(pitchDto);

        assertThat(pitchDto).isEqualToIgnoringGivenFields(pitch, "pitchId", "pitchType", "pitchResult");
        assertThat(pitchDto.getPitchResult().name()).isEqualTo(pitch.getPitchResult().name());
        assertThat(pitchDto.getPitchType().name()).isEqualTo(pitch.getPitchType().name());
    }

    @Test
    public void shouldTransformAListOfPitchesToAListOfPitchDtos() {
        List<Pitch> pitchList = Arrays.asList(
                random(Pitch.class),
                random(Pitch.class),
                random(Pitch.class)
        );

        List<PitchDto> pitchDtoList = DtoTransformer.listOfPitchToListOfPitchDto(pitchList);

        for(PitchDto pitchDto: pitchDtoList) {
            assertThat(pitchDto).isEqualToIgnoringGivenFields(pitchList.get(pitchDtoList.indexOf(pitchDto)), "pitchId", "pitchType", "pitchResult");
            assertThat(pitchDto.getPitchResult().name()).isEqualTo(pitchList.get(pitchDtoList.indexOf(pitchDto)).getPitchResult().name());
            assertThat(pitchDto.getPitchType().name()).isEqualTo(pitchList.get(pitchDtoList.indexOf(pitchDto)).getPitchType().name());
        }
    }

}
