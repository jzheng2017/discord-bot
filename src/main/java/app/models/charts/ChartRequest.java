package app.models.charts;

public class ChartRequest {
    private String backgroundColor;
    private int height;
    private int width;
    private String format;
    private Chart chart;

    public ChartRequest(String backgroundColor, int height, int width, String format, Chart chart) {
        this.backgroundColor = backgroundColor;
        this.height = height;
        this.width = width;
        this.format = format;
        this.chart = chart;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }
}
