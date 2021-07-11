package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.utils.Color;
import de.marc.towerDefenceGame.utils.Colors;
import de.marc.towerDefenceGame.utils.Vector2;

public class GuiTabContent extends GuiComponent {

    private GuiComponent[] content;
    private boolean visible = false;
    private String title;

    public GuiTabContent(String title, Vector2 pos, double width, double height, GuiComponent[] content) {
        super(pos);
        this.title = title;
        this.width = width;
        this.height = height;
        this.content = content;
        TowerDefenceGame.theGame.getLogger().debug(this.title, this.pos, content);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if(this.visible) {
            for (GuiComponent component : this.content) {
                component.onEvent(event);
            }
        }
    }

    @Override
    public void render() {
        if (this.visible) {
        TowerDefenceGame.theGame.getFontRenderer().drawString(this.title, new Vector2(this.pos).add(new Vector2(10, 10)), 3, new Color(Colors.TEXT));
            for(GuiComponent c : this.content) {
                c.render();
            }
        }
    }
}
