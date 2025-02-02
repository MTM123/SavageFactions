package com.massivecraft.factions.cmd;

import com.massivecraft.factions.SavageFactions;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.zcore.util.TL;

import java.util.HashMap;
import java.util.List;

public class CmdRules extends FCommand {
    public CmdRules() {
        super();
        aliases.add("r");
        aliases.add("rule");
        aliases.add("rules");

        this.optionalArgs.put("add/remove/set/clear", "");

        this.requirements = new CommandRequirements.Builder(Permission.RULES)
                .playerOnly()
                .memberOnly()
                .noErrorOnManyArgs()
                .build();
    }

    @Override
    public void perform(CommandContext context) {
        if (!SavageFactions.plugin.getConfig().getBoolean("frules.Enabled")) {
            context.msg(TL.COMMAND_RULES_DISABLED_MSG);
            return;
        }
        if (context.args.size() == 0) {
            HashMap<Integer, String> rules = context.faction.getRulesMap();
            if (rules.size() == 0) {
                List<String> ruleList = SavageFactions.plugin.getConfig().getStringList("frules.default-rules");
                context.sendMessage(SavageFactions.plugin.colorList(ruleList));

            } else {
                for (int i = 0; i <= rules.size() - 1; i++) {
                    context.sendMessage(SavageFactions.plugin.color(rules.get(i)));
                }
            }

        }
        if (context.args.size() == 1) {
            if (context.args.get(0).equalsIgnoreCase("add")) {
                context.msg(TL.COMMAND_RULES_ADD_INVALIDARGS);
            }
            if (context.args.get(0).equalsIgnoreCase("set")) {
                context.msg(TL.COMMAND_RULES_SET_INVALIDARGS);
            }
            if (context.args.get(0).equalsIgnoreCase("remove")) {
                context.msg(TL.COMMAND_RULES_REMOVE_INVALIDARGS);
            }
            if (context.args.get(0).equalsIgnoreCase("clear")) {
                context.faction.clearRules();
                context.msg(TL.COMMAND_RULES_CLEAR_SUCCESS);
            }

        }
        if (context.args.size() >= 2) {
            if (context.args.get(0).equalsIgnoreCase("add")) {
                String message = "";
                StringBuilder string = new StringBuilder(message);
                for (int i = 1; i <= context.args.size() - 1; i++) {
                    string.append(" " + context.args.get(i));
                }
                context.faction.addRule(string.toString());
                context.msg(TL.COMMAND_RULES_ADD_SUCCESS);
            }

            if (context.args.size() == 2) {
                if (context.args.get(0).equalsIgnoreCase("remove")) {
                    int index = context.argAsInt(1);
                    context.faction.removeRule(index - 1);
                    context.msg(TL.COMMAND_RULES_REMOVE_SUCCESS);
                }
            }

        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_RULES_DESCRIPTION;
    }
}
