package paramonov.valentin.fiction.fic;

public class FICProperties {
    private double tolerance;
    private int minSubdivisions;
    private int maxSubdivisions;
    private int contrastLevels;
    private int brightnessLevels;
    private double domainStep;

    public double getTolerance() {
        return tolerance;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    public int getMinSubdivisions() {
        return minSubdivisions;
    }

    public void setMinSubdivisions(int minSubdivisions) {
        this.minSubdivisions = minSubdivisions;
    }

    public int getMaxSubdivisions() {
        return maxSubdivisions;
    }

    public void setMaxSubdivisions(int maxSubdivisions) {
        this.maxSubdivisions = maxSubdivisions;
    }

    public int getContrastLevels() {
        return contrastLevels;
    }

    public void setContrastLevels(int contrastLevels) {
        this.contrastLevels = contrastLevels;
    }

    public int getBrightnessLevels() {
        return brightnessLevels;
    }

    public void setBrightnessLevels(int brightnessLevels) {
        this.brightnessLevels = brightnessLevels;
    }

    public double getDomainStep() {
        return domainStep;
    }

    public void setDomainStep(double domainStep) {
        this.domainStep = domainStep;
    }
}
