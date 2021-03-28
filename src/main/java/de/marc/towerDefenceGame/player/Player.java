package de.marc.towerDefenceGame.player;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.Listener;
import de.marc.towerDefenceGame.event.events.*;
import de.marc.towerDefenceGame.render.Camera;
import de.marc.towerDefenceGame.utils.Vector2;

public class Player extends Camera implements Listener {

    private final Vector2 pos, motion;
    private double fastSpeed = 10;
    private double normalSpeed = 5;
    private double currentSpeed = this.normalSpeed;

    private boolean leftMouseDown = false;

    public Player() {
        TowerDefenceGame.theGame.getEventManager().addListener(this);
        this.pos = new Vector2(0D, 0D);
        this.motion = new Vector2(0D, 0D);
    }

    public void update(long partialMS) {
        // TODO: zeitdifferenz in betracht ziehen
        this.pos.add(Vector2.duplicate(this.motion).normalize().multiply(this.currentSpeed));
        this.setOffset(this.pos);
    }

    public void onEvent(Event event) {
        if (event instanceof KeyEvent) {
            KeyEvent e = (KeyEvent) event;
            Vector2 up = new Vector2(0D, 1);
            Vector2 left = new Vector2(1, 0D);
            Vector2 down = up.invert();
            Vector2 right = left.invert();
            switch (e.getKey()) {
                case W:
                    switch (e.getAction()) {
                        case DOWN:
                            this.motion.add(up);
                            break;
                        case UP:
                            this.motion.subtract(up);
                            break;
                    }
                    break;
                case A:
                    switch (e.getAction()) {
                        case DOWN:
                            this.motion.add(left);
                            break;
                        case UP:
                            this.motion.subtract(left);
                            break;
                    }
                    break;
                case S:
                    switch (e.getAction()) {
                        case DOWN:
                            this.motion.add(down);
                            break;
                        case UP:
                            this.motion.subtract(down);
                            break;
                    }
                    break;
                case D:
                    switch (e.getAction()) {
                        case DOWN:
                            this.motion.add(right);
                            break;
                        case UP:
                            this.motion.subtract(right);
                            break;
                    }
                    break;
                case L_SHIFT:
                    switch (e.getAction()) {
                        case DOWN:
                            this.currentSpeed = this.fastSpeed;
                            break;
                        case UP:
                            this.currentSpeed = this.normalSpeed;
                            break;
                    }
                    break;
                case SPACE:
                    switch (e.getAction()) {
                        case DOWN:
                            break;
                        case UP:
                            this.pos.setX(0);
                            this.pos.setY(0);
                            break;
                    }
                    break;
            }
        } else if (event instanceof MouseButtonEvent) {
            MouseButtonEvent e = (MouseButtonEvent) event;
            if (e.getButton() == 0) { // 0 is the left mouse button
                switch(e.getAction()) {
                    case DOWN:
                        this.leftMouseDown = true;
                        break;
                    case UP:
                        this.leftMouseDown = false;
                        break;
                }
            }
        } else if (event instanceof MouseScrollEvent) {
            MouseScrollEvent e = (MouseScrollEvent) event;
            // TODO: Zoom in / out
//            TowerDefenceGame.theGame.getLogger().debug("MouseScrollEvent in player", e.getX(), e.getY());
        } else if (event instanceof MouseMoveEvent) {
            MouseMoveEvent e = (MouseMoveEvent) event;
            if (this.leftMouseDown) {
                // move player with mouse
                this.pos.add(new Vector2(e.getDX(), e.getDY()));
            }
        } else if (event instanceof UpdateEvent) {
            UpdateEvent e = (UpdateEvent) event;
            this.update(e.partialMS);
        }
    }
}
