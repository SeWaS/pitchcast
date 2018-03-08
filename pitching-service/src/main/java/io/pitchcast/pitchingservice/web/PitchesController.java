package io.pitchcast.pitchingservice.web;

import io.pitchcast.pitchingservice.service.PitchesService;
import io.pitchcast.pitchingservice.web.dto.PitchDto;
import io.pitchcast.pitchingservice.web.dto.PitchesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PitchesController {

    private PitchesService pitchesService;

    @Autowired
    public PitchesController(PitchesService pitchesService) {
        this.pitchesService = pitchesService;
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addNewPitch(@RequestBody @Validated PitchDto pitchDto) {
        return new ResponseEntity(pitchesService.saveNewPitch(DtoTransformer.pitchDtoToPitch(pitchDto)), HttpStatus.CREATED);
    }

    @GetMapping("/pitches")
    public ResponseEntity<PitchesDto> getAllPitches() {
        PitchesDto receivedPitches = new PitchesDto();
        receivedPitches.setPitches(DtoTransformer.listOfPitchToListOfPitchDto(pitchesService.getAllPitches()));
        return ResponseEntity.ok(receivedPitches);
    }

}
