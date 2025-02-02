package com.massivecraft.factions.cmd;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.SavageFactions;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.zcore.util.TL;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CmdNear extends FCommand {
    public CmdNear() {
        super();
        this.aliases.add("near");
        this.aliases.add("nearby");

        this.requirements = new CommandRequirements.Builder(Permission.NEAR)
                .playerOnly()
                .memberOnly()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (!SavageFactions.plugin.getConfig().getBoolean("fnear.Enabled")) {
            context.msg(TL.COMMAND_NEAR_DISABLED_MSG);
            return;
        }

        double range = SavageFactions.plugin.getConfig().getInt("fnear.Radius");
        String format = TL.COMMAND_NEAR_FORMAT.toString();
        context.msg(TL.COMMAND_NEAR_USE_MSG);
        for (Entity e : context.player.getNearbyEntities(range, 255, range)) {
            if (e instanceof Player) {
                Player player = (((Player) e).getPlayer());
                FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
                if (context.faction == fplayer.getFaction()) {
                    double distance = context.player.getLocation().distance(player.getLocation());
                    context.sendMessage(format.replace("{playername}", player.getDisplayName()).replace("{distance}", (int) distance + ""));
                }
            }

        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_NEAR_DESCRIPTION;
    }
}
