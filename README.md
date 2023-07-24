# Fabric automessage

A server-side mod to send automated messages to all players in a server with a specific timeout between each message.
<br>The mod supports LuckPerms for permissions.
<br><br>
## Installation
Put the .jar file in the "mods" folder
**(Requires Fabric API)**
<br><br>

## Commands and permissions
All commands can be used by admins (permission level 3) or by users/groups with the specific permission


| Description        | Command                                     | Permission               | 
|--------------------|---------------------------------------------|--------------------------|
| Main command       | `/automessage`                              | `automessage.main`       |
| Add a message      | `/automessage add <message>`                | `automessage.add`        |
| List all messages  | `/automessage list`                         | `automessage.list`       |
| Remove a message   | `/automessage remove <index> `              | `automessage.remove`     |
| Change the timeout | `/automessage settimeout <time_in_seconds>` | `automessage.settimeout` |
| Reload config file | `/automessage reload`                       | `automessage.reload`     |


## Configuration
You can find the config file in `./config/automessage.json`
<br>Messages formatting uses [Adventure Text Format](https://docs.advntr.dev/minimessage/format.html).

### JSON example
```json5
{
  "intervalInSeconds": 120,
  "messages": [
    "Hello world",
    "<blue><bold>Follow me!",
    "<rainbow>Amazing!"
   ] //you can add your messages here
}
```
## Showcase
![Screenshot](https://i.imgur.com/eZftQI4.png)