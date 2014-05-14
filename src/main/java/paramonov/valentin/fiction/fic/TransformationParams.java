package paramonov.valentin.fiction.fic;

import paramonov.valentin.fiction.transformation.Transformation;

public class TransformationParams {
    private Transformation transformation;
    private double rms;
    private double contrastShift;
    private double brightnessShift;

    public TransformationParams(Transformation transformation, double rms, double contrastShift,
        double brightnessShift) {

        this.transformation = transformation;
        this.rms = rms;
        this.contrastShift = contrastShift;
        this.brightnessShift = brightnessShift;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    public double getRms() {
        return rms;
    }

    public void setRms(double rms) {
        this.rms = rms;
    }

    public double getContrastShift() {
        return contrastShift;
    }

    public void setContrastShift(double contrastShift) {
        this.contrastShift = contrastShift;
    }

    public double getBrightnessShift() {
        return brightnessShift;
    }

    public void setBrightnessShift(double brightnessShift) {
        this.brightnessShift = brightnessShift;
    }
}
