package io.pitchcast.pitcherservice.web;

import io.pitchcast.pitcherservice.domain.Pitcher;
import io.pitchcast.pitcherservice.domain.PitcherHand;
import io.pitchcast.pitcherservice.web.dto.PitcherDto;
import io.pitchcast.pitcherservice.web.dto.PitcherHandDto;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DtoTransformer {

    public static List<Pitcher> transformToPitcher(List<PitcherDto> pitcherDtos) {
        return pitcherDtos.stream()
                .map(dto -> {
                    Pitcher pitcher = new Pitcher();
                    copyPitcherValues(dto, pitcher);
                    return pitcher;
                })
                .collect(Collectors.toList());
    }

    public static List<PitcherDto> transformToPitcherDto(List<Pitcher> pitchers) {
        return pitchers.stream()
                .map(pitcher -> {
                    PitcherDto pitcherDto = new PitcherDto();
                    copyPitcherDtoValues(pitcher, pitcherDto);
                    return pitcherDto;
                })
                .collect(Collectors.toList());
    }

    private static void copyPitcherValues(PitcherDto pitcherDto, Pitcher pitcher) {
        BeanUtils.copyProperties(pitcherDto, pitcher);
        pitcher.setHanded(PitcherHand.valueOf(pitcherDto.getHanded().name()));
    }

    private static void copyPitcherDtoValues(Pitcher pitcher, PitcherDto pitcherDto) {
        BeanUtils.copyProperties(pitcher, pitcherDto);
        pitcherDto.setHanded(PitcherHandDto.valueOf(pitcher.getHanded().name()));
    }

}
