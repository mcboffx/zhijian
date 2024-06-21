package org.fgg2333.zhijian;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class about implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("about")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("zhijian server main plugins");
                player.sendMessage("powered by huyast111/fgg2333");
            } else {
                sender.sendMessage("zhijian server main plugins");
                sender.sendMessage("powered by huyast111/fgg2333");
            }
            return true;
        }
        return false;
    }
}
