package io.pitchcast.pitcherservice.web;

import io.pitchcast.pitcherservice.domain.Pitcher;
import io.pitchcast.pitcherservice.domain.PitcherHand;
import io.pitchcast.pitcherservice.web.dto.PitcherDto;
import io.pitchcast.pitcherservice.web.dto.PitcherHandDto;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class DtoTransformer {

    public static Pitcher pitcherDtoToPitcher(PitcherDto pitcherDto) {
        Pitcher pitcher = new Pitcher();
        copyPitcherValues(pitcherDto, pitcher);
        return pitcher;
    }

    private static void copyPitcherValues(PitcherDto pitcherDto, Pitcher pitcher) {
        BeanUtils.copyProperties(pitcherDto, pitcher);
        pitcher.setHanded(PitcherHand.valueOf(pitcherDto.getHanded().name()));
    }

    public static List<PitcherDto> listOfPitcherToListOfPitcherDto(List<Pitcher> pitchers) {
        List<PitcherDto> pitcherDtos = new ArrayList<>();
        for(Pitcher pitcher: pitchers) {
            PitcherDto pitcherDto = new PitcherDto();
            copyPitcherDtoValues(pitcher, pitcherDto);
            pitcherDtos.add(pitcherDto);
        }
        return pitcherDtos;
    }

    private static void copyPitcherDtoValues(Pitcher pitcher, PitcherDto pitcherDto) {
        BeanUtils.copyProperties(pitcher, pitcherDto);
        pitcherDto.setHanded(PitcherHandDto.valueOf(pitcher.getHanded().name()));
    }

}
