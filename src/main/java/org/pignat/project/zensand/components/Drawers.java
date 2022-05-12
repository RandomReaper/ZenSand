package org.pignat.project.zensand.components;

import org.reflections.Reflections;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Drawers {

    private static final Logger LOGGER = Logger.getLogger(Drawers.class.getName());
    private static LinkedList<Drawer> list;

    private Drawers() {

    }

    private static void init() {
        if (list != null) {
            return;
        }

        list = new LinkedList<Drawer>();

        Reflections reflections = new Reflections("org.pignat.project.zensand");
        Set<Class<? extends Drawer>> subTypes = reflections.getSubTypesOf(Drawer.class);

        for (Class<? extends Drawer> c : subTypes) {
            /* exclude test class drawers */
            if (c.toString().contains("org.pignat.project.zensand.drawers"))
                try {
                    list.add(c.newInstance());
                } catch (InstantiationException e) {
                    LOGGER.log(Level.SEVERE, "", e);
                } catch (IllegalAccessException e) {
                    LOGGER.log(Level.SEVERE, "", e);
                }
        }

        Collections.sort(list, new Comparator<Drawer>() {
            @Override
            public int compare(Drawer d1, Drawer d2) {
                return d1.name().compareTo(d2.name());
            }
        });
    }

    public static int size() {
        init();
        return list.size();
    }

    public static Drawer get(int i, double ballSize) {
        init();
        i %= list.size();
        i += list.size();
        i %= list.size();
        Drawer d = list.get(i);
        d.init(ballSize);
        return d;
    }

}
