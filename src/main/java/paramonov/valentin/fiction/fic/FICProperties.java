package paramonov.valentin.fiction.fic;

public class FICProperties {
    private Double tolerance;
    private Integer minSubdivisions;
    private Integer maxSubdivisions;
    private Integer contrastLevels;
    private Integer minContrastValue;
    private Integer maxContrastValue;
    private Integer brightnessLevels;
    private Integer minBrightnessValue;
    private Integer maxBrightnessValue;
    private Double domainStep;

    public Double getTolerance() {
        return tolerance;
    }

    public void setTolerance(Double tolerance) {
        this.tolerance = tolerance;
    }

    public Integer getMinSubdivisions() {
        return minSubdivisions;
    }

    public void setMinSubdivisions(Integer minSubdivisions) {
        this.minSubdivisions = minSubdivisions;
    }

    public Integer getMaxSubdivisions() {
        return maxSubdivisions;
    }

    public void setMaxSubdivisions(Integer maxSubdivisions) {
        this.maxSubdivisions = maxSubdivisions;
    }

    public Integer getContrastLevels() {
        return contrastLevels;
    }

    public void setContrastLevels(Integer contrastLevels) {
        this.contrastLevels = contrastLevels;
    }

    public Integer getMinContrastValue() {
        return minContrastValue;
    }

    public void setMinContrastValue(Integer minContrastValue) {
        this.minContrastValue = minContrastValue;
    }

    public Integer getMaxContrastValue() {
        return maxContrastValue;
    }

    public void setMaxContrastValue(Integer maxContrastValue) {
        this.maxContrastValue = maxContrastValue;
    }

    public Integer getBrightnessLevels() {
        return brightnessLevels;
    }

    public void setBrightnessLevels(Integer brightnessLevels) {
        this.brightnessLevels = brightnessLevels;
    }

    public Integer getMinBrightnessValue() {
        return minBrightnessValue;
    }

    public void setMinBrightnessValue(Integer minBrightnessValue) {
        this.minBrightnessValue = minBrightnessValue;
    }

    public Integer getMaxBrightnessValue() {
        return maxBrightnessValue;
    }

    public void setMaxBrightnessValue(Integer maxBrightnessValue) {
        this.maxBrightnessValue = maxBrightnessValue;
    }

    public Double getDomainStep() {
        return domainStep;
    }

    public void setDomainStep(Double domainStep) {
        this.domainStep = domainStep;
    }
}
