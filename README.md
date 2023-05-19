![banner](https://github.com/ImCodist/simple-player-warps/assets/50346006/7a2f57f4-809d-466c-99f4-44d7122fa5d2)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/ImCodist/simple-player-warps?style=flat-square)
![GitHub all releases](https://img.shields.io/github/downloads/ImCodist/simple-player-warps/total?style=flat-square)
![GitHub](https://img.shields.io/github/license/ImCodist/simple-player-warps?style=flat-square)

# simple-player-warps
**SimplePlayerWarps** is **exactly** what the name says it is.
The plugin offers a solution for a *"player warps"* system, where each player has control over their own set of warps players can teleport to and visit from anywhere.

And that's it! The plugin aims to do *exactly* what it says and to do it the best it can.

I plan to add a plethora of customization options so you can mold to plugin exactly to your liking, for whatever kind of server you're running.

## Commands
- `/warp <name>` - Teleports the player to a created warp.
- `/warps [player]` - Returns a list of warps the player can teleport to.
- `/warpset <name>` - Creates a warp at a position for players to teleport to.
- `/warpdel <name>` - Deletes an existing warp.
- `/warpinfo <name>` - Returns basic information on a warp. (i.e location, creator, world)
- `/warpreload` - Reloads configuration and warps.

## Permissions
- `simpleplayerwarps.warp` - Allows a user to teleport to a warp. (default: true)
- `simpleplayerwarps.warps` - Allows a user to view every created warp. (default: true)
- `simpleplayerwarps.warpset` - Allows a user to create a warp. (default: true)
- `simpleplayerwarps.warpdel` - Allows a user to delete warps they own. (default: true)
- `simpleplayerwarps.warpinfo` - Allows a user to view information on a warp. (default: true)
- `simpleplayerwarps.warpreload` - Reloads the plugins configuration and warps folder. (default: op)
- `simpleplayerwarps.warpset.infinite` - Allows a user to create infinite warps. (default: op)
- `simpleplayerwarps.warpdel.others` - Allows a user to delete others warps. (default: op)

### NOTE: SimplePlayerWarps is ONLY for Paper servers (and loaders that use it as a base) as of right now. I will consider downgrading to Spigot if the demand is high enough.
