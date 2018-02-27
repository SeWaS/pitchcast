package io.pitchcast.pitchingservice.web.dto;


public class PitchDto {

    private int x;
    private int y;
    private PitchResultDto pitchResult;
    private PitchTypeDto pitchType;

    public PitchDto() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public PitchResultDto getPitchResult() {
        return pitchResult;
    }

    public void setPitchResult(PitchResultDto pitchResult) {
        this.pitchResult = pitchResult;
    }

    public PitchTypeDto getPitchType() {
        return pitchType;
    }

    public void setPitchType(PitchTypeDto pitchType) {
        this.pitchType = pitchType;
    }
}
