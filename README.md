# Minecraft BankAPI
## Description
A plugin that stores player balances. Manages automatically saving these balances and can easily be referenced from other plugins
## Player Commands
- `/balance` shows your current balance in the bank
- `/send [player] [amount] ` sends gold in ingots to another player's bank
## API Usage
### How to retrieve the plugin
- Mark Bank plugin as a dependency to your plugin in `pom.xml`
- Import BankAPI and BankMain in your plugin with `import BankMain` and `import BankAPI`
- Get BankAPI instance with `BankAPI bank = ((BankMain) Bukkit.getPluginManager().getPlugin("BankAPI")).getBankAPI();`
### BankAPI Functions
- `void balance(UUID playerID)`
- `void deposit(UUID playerID, double amount)` 
- `void withdraw(UUID playerID, double amount)`
- `boolean send(UUID sender, UUID receiver, double amount)`
- `boolean has(UUID playerID, double amount)`
  - Returns True if playerID's balance is greater or equal to amount
- `void donate(UUID receiver, double amount)`
  - Generates amount and deposits it to receiver's bank
- `void donate(double amount)`
  - Generates amount and deposits it to all player's banks.
- `boolean save ()`
  - Saves current state of currencies of all players to json file
