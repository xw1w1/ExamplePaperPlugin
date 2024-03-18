package org.example.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

/**
 * Делаем из класса наследника ИсполнителяКоманды
 * в resources/plugin.yml посмотри как их регистрировать.
 *
 * Чтобы переключаться между обработчиками посмотри на пример написания первого.
 * Обязательно должна быть аннотация над методом, а он сам должен быть onCommand
 */
public class ExampleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //CommandSender это какая-то сущность, способная исполнять команды, будь то консоль или игрок

        //Pattern что-то там заставляет обьявлять игрока прямо здесь
        if (!(sender instanceof Player player)) { //если отправитель команды это не игрок
            sender.sendMessage("ты дэбил или куда то"); // а так же большинство классов наследует интерфейс Audience, благодаря чему
            //им можно отправлять сообщения
            return true; //команда выполнена успешно
        }

        //кастим отправителя сообщения к игроку, так как в 99% случаев это будет именно игрок
        //Player player = (Player) sender;
        //игрок наследует коммандсендера и в этом случае мы можем сделать такой каст

        String argsConnected = String.join(" ", args); //соединяем пробелом все элементы массива args команды.
        //если была введена "/examplecommand я люблю сосать члены"
        //строка будет равна "я люблю сосать члены"

        Component text = Component.text(argsConnected);
        //можем установить цвет даже после создания
        // метод color возвращает новый Component, но перекрашенный
        //по этому мы как бы должны приравнять старый text к новому из метода color
        text = text.color(NamedTextColor.AQUA);

        player.sendMessage(text); //отправит игроку голубой текст из его аргументов

        //Локация - это положение чего-либо в каком-либо мире на каких-либо координатах
        Location oldPlayerLocation = player.getLocation(); //получение локации игрока на момент исполнения команды

        //World nether = Bukkit.getWorld("nether"); // мир незера
        //World end = Bukkit.getWorld("the_end"); // энд
        //                                получаем объект мира по названию, x,  y,      z
        Location location = new Location(Bukkit.getWorld("world"), 0, 150, 0);

        //телепортируем игрока на созданную нами локацию
        player.teleport(location);
        return true;
    }

    public boolean onCommand0(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        String world = args[0];

        World parsedWorld = Bukkit.getWorld(world); // получаем мир по переданному названию.

        int x = Integer.parseInt(args[1]); //парсим числа в int, если это возможно и исполнитель команды не долбаеб
        int y = Integer.parseInt(args[2]);
        int z = Integer.parseInt(args[3]);

        // /command world 120 230 10
        Location location = new Location(parsedWorld, x, y, z);

        Player player = (Player) sender;
        //кастим сендера к игроку, как это работает написано в предыдущем примере

        //телепортируем игрока на созданную локацию
        player.teleport(location);
        return true; //успешное завершение команды
    }

    public boolean onCommand1(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        String world = args[0];

        // /command world 120 230 10

        World parsedWorld = Bukkit.getWorld(world); // получаем мир по переданному названию.

        if (parsedWorld == null) return false; //ядро не смогло найти мир, неудачное выполнение команды

        int x = Integer.parseInt(args[1]); //парсим числа в int, если это возможно и исполнитель команды не долбаеб
        int y = Integer.parseInt(args[2]);
        int z = Integer.parseInt(args[3]);

        String entityName = args[4];


        Location location = new Location(parsedWorld, x, y, z);

        //имя может быть введено неправильно, так что да, надо следить за этим
        EntityType entityType = EntityType.fromName(entityName);

        if (entityType == null) return false;//что-то пошло не так и энтити нету

        //спавним энтити в мире на заданной локации
        Entity entity = parsedWorld.spawnEntity(location, entityType);

        //получаем ближайших энтити в радиусе 20 блоков xyz
        List<Entity> nearbyEntities = entity.getNearbyEntities(20,20, 20);

        Logger simpleLogger = Logger.getLogger("SimpleLogger");
        //создаем самый простецкий логгер для вывода в консоль

        //используем Enhanced for each
        nearbyEntities.forEach(ent -> {
            //блок кода
            //ent - каждый элемент списка к которому будет применено действие
            String type = ent.getType().name(); // создаем строку которая будет равна названию типа энтити
            simpleLogger.info(type); //логируем этот тип в консоль сервера
        });

        return true; //успешное завершение команды
    }
}
