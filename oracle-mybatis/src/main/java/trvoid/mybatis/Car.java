package trvoid.mybatis;

public class Car {
    private int id;
    private String model;
    private String manufacturer;
    private String category;

    public Car(int id, String model, String manufacturer, String category) {
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("id:%d, model:%s, manufacturer:%s, category:%s", id, model, manufacturer, category);
    }
}
