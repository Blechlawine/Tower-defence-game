package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.gui.Gui;
import de.marc.towerDefenceGame.utils.*;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;
import static de.marc.towerDefenceGame.utils.Settings.KeyBindings.GUI_INTERACT;

public class GuiTabs extends GuiComponent {

    private String[] values;
    private GuiComponent[][] tabContentComponents;
    private ArrayList<GuiTabContent> tabContents = new ArrayList<>();
    private String activeTab;
    private double tabHeight;
    protected Color color, initialColor, hoverColor;

    private Keybinding changeTabBinding;
    private int hoveredValueIndex = 0;

    public GuiTabs(Vector2 pos, String[] values, double width, double tabHeight, int defaultIndex, GuiComponent[][] tabContentComponents, double tabContentHeight, Color color, Color hoverColor) {
        super(pos);
        this.values = values;
        this.width = width;
        this.tabHeight = tabHeight;
        this.tabContentComponents = tabContentComponents;
        this.initialColor = color;
        this.hoverColor = hoverColor;

        this.color = this.initialColor;
        this.height = this.tabHeight * this.values.length;
        this.activeTab = this.values[defaultIndex];
        for (int i = 0; i < values.length; i++) {
            GuiComponent[] components = tabContentComponents[i];
            GuiTabContent tabContent = new GuiTabContent(
                    values[i],
                    new Vector2(pos).add(new Vector2(width, 0)),
                    Gui.windowSize.getX() - width, tabContentHeight,
                    components
            );
            tabContents.add(tabContent);
        }
        this.tabContents.get(defaultIndex).setVisible(true);
        this.changeTabBinding = new Keybinding(GUI_INTERACT, new KeyAction[]{DOWN}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (hovered) {
                    activeTab = values[hoveredValueIndex];
                    for(int i = 0; i < tabContents.size(); i++) {
                        GuiTabContent tabContent = tabContents.get(i);
                        tabContent.setVisible(i == hoveredValueIndex);
                    }
                }
            }
        };
    }

    @Override
    protected void onMouseOver(double relativeMouseX, double relativeMouseY) {
        this.hoveredValueIndex = (int)Math.round(relativeMouseY) / (int)this.tabHeight;
    }
    @Override
    protected void onMouseOut() {
        this.hoveredValueIndex = -1;
    }

    @Override
    public void render() {
        double padding = (this.tabHeight - TowerDefenceGame.theGame.getFontRenderer().getCharHeight(2)) / 2;
        double pressedTextOffset = this.tabHeight / 16 * 3;
        for(int i = 0; i < this.values.length; i++) {
            String value = this.values[i];
            Vector2 valuePos = new Vector2(this.pos).add(new Vector2(0, (this.tabHeight + 4) * i));

            String textureHandle = (this.activeTab.equals(value) ? "buttonpressed" : "button");
            // left side
            GLUtils.drawTexturedRect(valuePos.getX(), valuePos.getY(), this.tabHeight, this.tabHeight, 0, 0, 1, 1, textureHandle, new Color(1, 1, 1));
            // right side
            GLUtils.drawTexturedRect(valuePos.getX() + this.width - this.tabHeight, valuePos.getY(), this.tabHeight, this.tabHeight, 1, 0, -1, 1, textureHandle, new Color(1, 1, 1));
            // middle part
            GLUtils.drawTexturedRect(valuePos.getX() + this.tabHeight, valuePos.getY(), this.width - (this.tabHeight*2), this.tabHeight, 0.5, 0, 0.5, 1, textureHandle, new Color(1, 1, 1));

//            GLUtils.drawRect(valuePos.getX(), valuePos.getY(), this.width, this.tabHeight, (this.hoveredValueIndex == i || this.activeTab.equals(value) ? this.hoverColor : this.initialColor));
            TowerDefenceGame.theGame.getFontRenderer().drawString(value, new Vector2(valuePos).add(new Vector2(padding, padding + (this.activeTab.equals(value) ? pressedTextOffset / 2 : -pressedTextOffset / 2))), 2, new Color(Colors.TEXT));
        }
        for(GuiTabContent tabContent : this.tabContents) {
            tabContent.render();
        }
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        this.changeTabBinding.onEvent(event);
        for (GuiTabContent tabContent : tabContents) {
            tabContent.onEvent(event);
        }
    }
}
