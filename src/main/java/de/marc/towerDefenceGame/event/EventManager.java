package de.marc.towerDefenceGame.event;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.utils.ListManager;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class EventManager extends ListManager<Listener> {

    public EventManager() {
        this.content = new ArrayList<Listener>();

    }

    public void setup() {
    }

    public void hook(Event event) {
        if (this.content != null) {
            // for (Listener listener : this.content) throws ConcurrentModificationException sometimes...
            for (int i = 0; i < this.content.size(); i++) {
                Listener listener = this.content.get(i);
                listener.onEvent(event);
                if (event.isCancelled()) {
                    return;
                }
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
