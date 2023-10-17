package net.aoissx.mc.paperfx.commands;

import net.aoissx.mc.paperfx.Paper_fx;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Fx implements CommandExecutor {
    private final String cmd = "fx";
    private final String subCmdAdd = "add";
    private final String subCmdRemove = "remove";
    private String AddTag = "FX_ADD_TAG";
    private String RemoveTag = "FX_REMOVE_TAG";
    private String UsingTag = "FX_USING_TAG";
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player p)){
            sender.sendMessage("This is only used player.");
            return false;
        }

        if(!(command.getName().equalsIgnoreCase(cmd ))){
            return false;
        }

        if(args.length == 0){
            p.sendMessage("Args is not enough.");
            return false;
        }

        if(!p.isOp()){
            p.sendMessage("You are not Operator.");
            return false;
        }

        //----- ----- -----
        String subCmd = args[0];
        Set<String> userTag = p.getScoreboardTags();

        if(subCmd.equalsIgnoreCase(subCmdAdd)){
            // register chest
            String msg;
            if(userTag.contains(AddTag)){
                p.removeScoreboardTag(AddTag);
                msg = "Disable fx add mode.";
            }else{
                p.addScoreboardTag(AddTag);
                msg = "Enable fx add mode.";
            }
            p.sendMessage(msg);
        }else if(subCmd.equalsIgnoreCase(subCmdRemove)){
            // remove chest
            String msg;
            if(userTag.contains(RemoveTag)){
                p.removeScoreboardTag(RemoveTag);
                msg = "Disable fx remove mode.";
            }else{
                p.addScoreboardTag(RemoveTag);
                msg = "Enable fx remove mode.";
            }
            p.sendMessage(msg);
        }else{
            p.sendMessage("Error sub cmd.");
            return false;
        }
        return true;

    }
}
