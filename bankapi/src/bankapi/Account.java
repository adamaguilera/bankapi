package bankapi;

import bankapi.Currency;

public class Account {
    final UUID playerID;
    final List[Investment] investments;

    final double balance () {
        return investments.stream().map(investment -> investment.balance()).sum();
    }
    
}
