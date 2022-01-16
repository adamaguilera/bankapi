package bankapi;

public record Investment (Currency currency, int amount) {
    
    public double balance () {
        return this.amount * this.currency.value();
    }
}
