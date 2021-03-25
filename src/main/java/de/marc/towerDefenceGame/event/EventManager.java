package de.marc.towerDefenceGame.event;

import de.marc.towerDefenceGame.utils.ListManager;

import java.util.ArrayList;

public class EventManager extends ListManager<Listener> {

    public EventManager() {

    }

    public void setup() {
        this.content = new ArrayList<Listener>();
    }

    public void hook(Event event) {
        if (this.content != null) {
            for (Listener listener : this.content) {
                listener.onEvent(event);
            }
        }
    }

    public void addListener(Listener listener) {
        if(!this.content.contains(listener))
            this.content.add(listener);
    }

    public void removeListener(Listener listener) {
        this.content.remove(listener);
    }

}
