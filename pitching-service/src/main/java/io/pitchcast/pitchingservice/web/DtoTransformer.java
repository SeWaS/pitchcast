package io.pitchcast.pitchingservice.web;

import io.pitchcast.pitchingservice.domain.Pitch;
import io.pitchcast.pitchingservice.domain.PitchResult;
import io.pitchcast.pitchingservice.domain.PitchType;
import io.pitchcast.pitchingservice.web.dto.PitchDto;
import io.pitchcast.pitchingservice.web.dto.PitchResultDto;
import io.pitchcast.pitchingservice.web.dto.PitchTypeDto;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class DtoTransformer {

    public static Pitch pitchDtoToPitch(PitchDto pitchDto) {
        Pitch pitch = new Pitch();
        copyPitchesValue(pitchDto, pitch);
        return pitch;
    }

    private static void copyPitchesValue(PitchDto pitchDto, Pitch pitch) {
        BeanUtils.copyProperties(pitchDto, pitch);
        pitch.setPitchResult(PitchResult.valueOf(pitchDto.getPitchResult().name()));
        pitch.setPitchType(PitchType.valueOf(pitchDto.getPitchType().name()));
    }

    public static List<PitchDto> listOfPitchToListOfPitchDto(List<Pitch> pitches) {
        List<PitchDto> pitchDtos = new ArrayList<>();
        for(Pitch pitch: pitches) {
            PitchDto pitchDto = new PitchDto();
            copyPitchesDtoValue(pitch, pitchDto);
            pitchDtos.add(pitchDto);
        }
        return pitchDtos;
    }

    private static void copyPitchesDtoValue(Pitch pitch, PitchDto pitchDto) {
        BeanUtils.copyProperties(pitch, pitchDto);
        pitchDto.setPitchResult(PitchResultDto.valueOf(pitch.getPitchResult().name()));
        pitchDto.setPitchType(PitchTypeDto.valueOf(pitch.getPitchType().name()));
    }

}
