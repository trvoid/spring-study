package trvoid.mybatis;

public class Car {
    private int id;
    private String model;
    private String manufacturer;

    public Car(int id, String model, String manufacturer) {
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return String.format("id:%d, model:%s, manufacturer:%s", id, model, manufacturer);
    }
}
