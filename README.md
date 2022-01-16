# Minecraft BankAPI
## Description
A plugin that incorporates a bank system to Minecraft. All players have access to a free virtual bank that stores their gold. Other plugins can use this to charge players for access to features like vaults, teleportation, or payments.
## Commands
- `/bank` opens your personal bank
- `/balance` shows your current balance in the bank
- `/deposit` automatically transfers all gold from your inventory to your bank
- `/send [amount] [player]` sends gold in ingots to another player's bank
## API Usage
### How to retrieve the plugin
- Mark Bank plugin as a dependency to your plugin in `pom.xml`
- Import BankAPI in your plugin with `import BankAPI`
- Get BankAPI instance with `BankAPI bank = (BankAPI) Bukkit.getPluginManager().getPlugin("BankAPI")
### Functions
- `double balance(UUID playerID)`
- `boolean deposit(UUID playerID, double amount)` 
- `boolean withdraw(UUID playerID, double amount)`
- `boolean send(UUID sender, UUID receiver, double amount)`
- `boolean afford(UUID playerID, double amount)`
    - Returns True if playerID's balance is greater or equal to amount
- `boolean donate(UUID receiver, double amount)`
    - Generates amount and deposits it to receiver's bank
- `boolean donate(double amount)`
    - Generates amount and deposits it to all player's banks.
