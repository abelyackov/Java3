public class Car {
    private String brand;
    private String color;
    private int racing;
    private int exp;
    private int pwrReserve;

    public Car() {
        this.brand = "noname";
        this.color = "unknown";
        this.racing = 0;
        this.pwrReserve = 0;
    }

    public Car(String color, int racing, int pwrReserve) {
        this.brand = "noname";
        this.color = color;
        this.racing = racing;
        this.pwrReserve = pwrReserve;
    }

    public Car(String brand, String color, int racing, int pwrReserve) {
        this.brand = brand;
        this.color = color;
        this.racing = racing;
        this.pwrReserve = pwrReserve;
    }

    public Car(String brand, String color, int racing, int exp, int pwrReserve) {
        this.brand = brand;
        this.color = color;
        this.racing = racing;
        this.exp = exp;
        this.pwrReserve = pwrReserve;
    }

    public String repaint(String color) {
        return this.color = color;
    }

    public void infoCar() {
        System.out.println("Brand: " + brand + ". Color: " + color + ". Racing: " + racing + ". Expenditure: " + exp + ". Power reserve: " + pwrReserve);
    }

}
