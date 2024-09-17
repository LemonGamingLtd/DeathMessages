package net.joshb.deathmessages.config;

import net.joshb.deathmessages.DeathMessages;
import org.apache.commons.lang.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

public class ConfigManager {

    public ConfigManager(){ }
    private static ConfigManager instance = new ConfigManager();
    public static ConfigManager getInstance(){
        return instance;
    }

    public File backupDirectory = new File(DeathMessages.plugin.getDataFolder() + File.separator + "Backups");

    public void initialize(){
        if (!DeathMessages.plugin.getDataFolder().exists()) {
            DeathMessages.plugin.getDataFolder().mkdir();
        }
        EntityDeathMessages.getInstance().initialize();
        Gangs.getInstance().initialize();
        Messages.getInstance().initialize();
        PlayerDeathMessages.getInstance().initialize();
        Settings.getInstance().initialize();
        UserData.getInstance().initialize();
    }

    public void reload(){
        EntityDeathMessages.getInstance().reload();
        Gangs.getInstance().reload();
        Messages.getInstance().reload();
        PlayerDeathMessages.getInstance().reload();
        Settings.getInstance().reload();
    }

    public String backup(boolean excludeUserData){
        if(!backupDirectory.exists()){
            backupDirectory.mkdir();
        }
        String randomCode = RandomStringUtils.randomNumeric(4);
        File backupDir = new File(backupDirectory + File.separator + randomCode);
        backupDir.mkdir();
        try{
            copyFileToDirectory(EntityDeathMessages.getInstance().file, backupDir);
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            copyFileToDirectory(Gangs.getInstance().file, backupDir);
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            copyFileToDirectory(Messages.getInstance().file, backupDir);
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            copyFileToDirectory(PlayerDeathMessages.getInstance().file, backupDir);
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            copyFileToDirectory(Settings.getInstance().file, backupDir);
        } catch (IOException e){
            e.printStackTrace();
        }
        if(!excludeUserData){
            try{
                copyFileToDirectory(UserData.getInstance().file, backupDir);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return randomCode;
    }

    /*
        Returns true if the operation was successful.
        Returns false if the operation was not successful.
     */
    public boolean restore(String code, boolean excludeUserData){
        File backupDir = new File(backupDirectory + File.separator + code);
        if(!backupDir.exists()){
            return false;
        }
        try{
            String fileName = EntityDeathMessages.getInstance().fileName;
            File f = new File(backupDir + File.separator + fileName + ".yml");
            if(EntityDeathMessages.getInstance().file.delete()){
                copyFileToDirectory(f, DeathMessages.plugin.getDataFolder());
            } else {
                DeathMessages.plugin.getLogger().log(Level.SEVERE, "COULD NOT RESTORE " + fileName + ".");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            String fileName = Gangs.getInstance().fileName;
            File f = new File(backupDir + File.separator + fileName + ".yml");
            if(Gangs.getInstance().file.delete()){
                copyFileToDirectory(f, DeathMessages.plugin.getDataFolder());
            } else {
                DeathMessages.plugin.getLogger().log(Level.SEVERE, "COULD NOT RESTORE " + fileName + ".");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            String fileName = Messages.getInstance().fileName;
            File f = new File(backupDir + File.separator + fileName + ".yml");
            if(Messages.getInstance().file.delete()){
                copyFileToDirectory(f, DeathMessages.plugin.getDataFolder());
            } else {
                DeathMessages.plugin.getLogger().log(Level.SEVERE, "COULD NOT RESTORE " + fileName + ".");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            String fileName = PlayerDeathMessages.getInstance().fileName;
            File f = new File(backupDir + File.separator + fileName + ".yml");
            if(PlayerDeathMessages.getInstance().file.delete()){
                copyFileToDirectory(f, DeathMessages.plugin.getDataFolder());
            } else {
                DeathMessages.plugin.getLogger().log(Level.SEVERE, "COULD NOT RESTORE " + fileName + ".");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            String fileName = Settings.getInstance().fileName;
            File f = new File(backupDir + File.separator + fileName + ".yml");
            if(Settings.getInstance().file.delete()){
                copyFileToDirectory(f, DeathMessages.plugin.getDataFolder());
            } else {
                DeathMessages.plugin.getLogger().log(Level.SEVERE, "COULD NOT RESTORE " + fileName + ".");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        if(!excludeUserData){
            try{
                String fileName = UserData.getInstance().fileName;
                File f = new File(backupDir + File.separator + fileName + ".yml");
                if(UserData.getInstance().file.delete()){
                    copyFileToDirectory(f, DeathMessages.plugin.getDataFolder());
                } else {
                    DeathMessages.plugin.getLogger().log(Level.SEVERE, "COULD NOT RESTORE " + fileName + ".");
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        ConfigManager.getInstance().reload();
        return true;
    }

    public static void copyFileToDirectory(@NotNull File source, @NotNull File directory) throws IOException {
        Files.copy(source.toPath(), new File(directory, source.getName()).toPath());
    }
}
