package de.marc.towerDefenceGame.event;

import de.marc.towerDefenceGame.utils.ListManager;

import java.util.ArrayList;
import java.util.List;

public class EventManager extends ListManager<Listener> {

    private List<Listener> uiListeners, gameListeners;

    public EventManager() {
        this.uiListeners = new ArrayList<>();
        this.gameListeners = new ArrayList<>();
        this.content = new ArrayList<>();
    }

    public void setup() {
    }

    public void hook(Event event) {
        if (this.content != null) {
            for (int i = 0; i < this.content.size(); i++) {
                Listener listener = this.content.get(i);
                listener.onEvent(event);
                if (event.isCancelled()) {
                    return;
                }
            }
        }
        if (this.uiListeners != null) {
            for (int i = 0; i < this.uiListeners.size(); i++) {
                Listener listener = this.uiListeners.get(i);
                listener.onEvent(event);
                if (event.isCancelled()) {
                    return;
                }
            }
        }
        if (this.gameListeners != null) {
            for (int i = 0; i < this.gameListeners.size(); i++) {
                Listener listener = this.gameListeners.get(i);
                listener.onEvent(event);
                if (event.isCancelled()) {
                    return;
                }
            }
        }
    }

    public void addUiListener(Listener listener) {
        if(!this.uiListeners.contains(listener))
            this.uiListeners.add(listener);
    }

    public void addGameListener(Listener listener) {
        if(!this.gameListeners.contains(listener))
            this.gameListeners.add(listener);
    }

    public void addGeneralListener(Listener listener) {
        if(!this.content.contains(listener))
            this.content.add(listener);
    }

    public void removeGeneralListener(Listener listener) {
        this.content.remove(listener);
    }

    public void removeUiListener(Listener listener) {
        this.uiListeners.remove(listener);
    }

    public void removeGameListener(Listener listener) {
        this.gameListeners.remove(listener);
    }
}
