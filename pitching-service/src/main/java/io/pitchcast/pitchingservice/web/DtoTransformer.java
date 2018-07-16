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
import java.util.stream.Collectors;

public class DtoTransformer {

    public static List<Pitch> transformToPitch(List<PitchDto> pitcherDtos) {
        return pitcherDtos.stream()
                .map(dto -> {
                    Pitch pitch = new Pitch();
                    copyPitchesValue(dto, pitch);
                    return pitch;
                })
                .collect(Collectors.toList());
    }

    public static List<PitchDto> transformToPitchDto(List<Pitch> pitchers) {
        return pitchers.stream()
                .map(pitcher -> {
                    PitchDto pitchDto = new PitchDto();
                    copyPitchesDtoValue(pitcher, pitchDto);
                    return pitchDto;
                })
                .collect(Collectors.toList());
    }

    private static void copyPitchesValue(PitchDto pitchDto, Pitch pitch) {
        BeanUtils.copyProperties(pitchDto, pitch);
        pitch.setPitchResult(PitchResult.valueOf(pitchDto.getPitchResult().name()));
        pitch.setPitchType(PitchType.valueOf(pitchDto.getPitchType().name()));
    }

    private static void copyPitchesDtoValue(Pitch pitch, PitchDto pitchDto) {
        BeanUtils.copyProperties(pitch, pitchDto);
        pitchDto.setPitchResult(PitchResultDto.valueOf(pitch.getPitchResult().name()));
        pitchDto.setPitchType(PitchTypeDto.valueOf(pitch.getPitchType().name()));
    }

}
