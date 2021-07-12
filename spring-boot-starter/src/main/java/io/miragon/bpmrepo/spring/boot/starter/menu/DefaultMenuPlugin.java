package io.miragon.bpmrepo.spring.boot.starter.menu;

import io.miragon.bpmrepo.core.menu.api.plugin.MenuPlugin;
import io.miragon.bpmrepo.core.menu.api.transport.MenuItemTO;

import java.util.ArrayList;
import java.util.List;

public class DefaultMenuPlugin implements MenuPlugin {

    @Override
    public List<MenuItemTO> getMenuItems() {
        final List<MenuItemTO> menuItems = new ArrayList<>();
        MenuItemTO item = new MenuItemTO("Home", "", "", 1);
        menuItems.add(item);
        item = new MenuItemTO("Forms", "/formulare", "", 2);
        menuItems.add(item);
        item = new MenuItemTO("Integration", "/bausteine", "", 3);
        menuItems.add(item);
        return menuItems;
    }
}
