package org.example.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Делаем класс наследником Listener'а, чтобы плагин понимал,
 * что этот класс может реагировать на ивенты.
 */
public class ExampleEventHandler implements Listener {

    /**
     * Возьмем для примера ивент отправления сообщения в чат.
     * Прикрепим к нему аннотацию @EventHandler, чтобы было понятно,
     * что этот метод берет на себя ответственность слушателя ивентов
     * @param e ивент
     */
    @EventHandler
    public void onChat(final @NotNull AsyncChatEvent e) {
        // сообщение в виде класса Component - класс, который представляет
        // собой текст и используется тупо везде
        Component message = e.message();

        //создаем текстовый компонент красного цвета с текстом penis
        Component newMessage = Component.text("penis", NamedTextColor.RED);

        //поскольку ивент имеет два метода message - один из которых является
        //геттером, а второй сеттером, мы можем "поставить" нужное нам сообщение
        e.message(newMessage);

        //Сам отправитель этого сообщения
        Player player = e.getPlayer();

        UUID playerUUID = player.getUniqueId();
        String name = player.getName();

        //отображаемое имя игрока. часто используется в табе или чате
        Component displayName = player.displayName();

        e.renderer(((source, sourceDisplayName, message1, viewer) -> {
            return sourceDisplayName
                    .append(Component.text(" > ", NamedTextColor.GRAY))
                    .append(message1); //можно заменить на newMessage чтобы был penis
            //в этом случае message = message1, здесь мы используем функциональный интерфейс
            //ChatRenderer, который можно сократить до такого вот вида
            //Теперь наше сообщение будет выглядеть как:
            // Turbotaliz > сообщение
        }));
    }

    /**
     * Экземпляр обработчика события входа игрока на сервер
     * @param event ивент
     */
    @EventHandler
    public void onJoin(final @NotNull PlayerJoinEvent event) {
        Component playerDisplayName = event.getPlayer().displayName();
        Component newJoinMessage = playerDisplayName.append(
                Component.text(" присоединился к игре", NamedTextColor.WHITE)
        );

        event.joinMessage(newJoinMessage); //так же два метода c одним названием

    }

}
