package org.example;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.commands.ExampleCommand;
import org.example.events.ExampleEventHandler;

public class ExamplePaperPlugin extends JavaPlugin {

    public ExampleEventHandler eventHandler;

    /**
     * Метод, который воспроизводит плагин при включении
     */
    public void onEnable() {
        //если тебе не нужно разрегистрировать обработчик, пиши просто
        // new ExampleEventHandler();
        this.eventHandler = new ExampleEventHandler();
        Bukkit.getPluginManager().registerEvents(eventHandler, this);
        //создаем новый экземпляр ивент хендлера и после регистрируем его в плагине
        //метод принимает в себя Listener и JavaPlugin, некий Main

        //получаем "команду" с названием ... из файла plugin.yml где она зарегана
        //и устанавливаем для нее обработчик который наследует CommandExecutor
        getCommand("examplecommand").setExecutor(new ExampleCommand());
    }

    /**
     * Метод, который воспроизводит плагин при выключении
     */
    public void onDisable() {
        //удаление обработчика событий из листа обработчиков
        HandlerList.unregisterAll(eventHandler);
    }

}
