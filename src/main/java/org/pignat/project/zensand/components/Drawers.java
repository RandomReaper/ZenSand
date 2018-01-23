package org.pignat.project.zensand.components;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Set;

import org.reflections.Reflections;

public class Drawers {
	private static LinkedList<Drawer> list;

	private static void init() {
		if (list != null) {
			return;
		}

		list = new LinkedList<Drawer>();

		Reflections reflections = new Reflections("org.pignat.project.zensand");
		Set<Class<? extends Drawer>> subTypes = reflections.getSubTypesOf(Drawer.class);

		for (Class<? extends Drawer> c : subTypes) {
			try {
				list.add(c.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		Collections.sort(list, new Comparator<Drawer>() {
			public int compare(Drawer d1, Drawer d2) {
				return d1.name().compareTo(d2.name());
			}
		});

		for (Drawer d : list) {
			System.out.println(d.name());
		}
	}

	public static int size() {
		init();
		return list.size();
	}

	public static Drawer get(int i) {
		init();
		i %= list.size();
		i += list.size();
		i %= list.size();
		Drawer d = list.get(i);
		d.init();
		return d;
	}

}
